package top.focess.expressionmfc.expression.multi;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Expression;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantLong;
import top.focess.expressionmfc.operator.Operator;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MultiExpression extends Expression implements Iterable<MultiExpression.OperatorHelper> {

    protected final List<OperatorHelper> operatorHelpers;
    private Simplifiable[] values;
    private Integer[] lefts;
    private Integer[] rights;

    public MultiExpression(IExpression expression) {
        this.expressions.add(expression);
        this.operatorHelpers = Lists.newArrayList();
    }

    public MultiExpression() {
        this(SimpleConstantLong.ZERO);
    }

    protected MultiExpression(List<IExpression> expressions, List<OperatorHelper> operatorHelpers) {
        this.expressions.addAll(expressions);
        this.operatorHelpers = operatorHelpers;
    }

    public MultiExpression(boolean flag) {
        if (flag)
            this.expressions.add(SimpleConstantLong.ZERO);
        this.operatorHelpers = Lists.newArrayList();
    }

    private IExpression getOrDefault(int pos) {
        if (values[pos] == null)
            return this.expressions.get(pos);
        else return values[pos];
    }

    private int getLeft(int pos) {
        if (lefts[pos] == null || lefts[pos] == pos)
            return lefts[pos] = pos;
        else return lefts[pos] = getLeft(lefts[pos]);
    }

    private int getRight(int pos) {
        if (rights[pos] == null || rights[pos] == pos)
            return rights[pos] = pos;
        else return rights[pos] = getRight(rights[pos]);
    }


    @Override
    public @NonNull Simplifiable simplify() {
        int first = -1;
        int last = -1;
        values = new Simplifiable[this.expressions.size()];
        lefts = new Integer[this.expressions.size()];
        rights = new Integer[this.expressions.size()];
        for (OperatorHelper operatorHelper : this) {
            if (last == operatorHelper.getPosition())
                last = operatorHelper.getPosition() + 1;
            else {
                first = operatorHelper.getPosition();
                last = operatorHelper.getPosition() + 1;
            }
            Simplifiable a = getOrDefault(getLeft(first)).simplify();
            Simplifiable b = getOrDefault(getRight(last)).simplify();
            values[getLeft(first)] = values[getRight(last)] = operatorHelper.getOperator().operate(a, b);
            rights[first] = last;
            lefts[last] = first;
        }
        return values[0];
    }

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        AtomicReference<UnknownArgumentException> exception = new AtomicReference<>();
        Constable[] constables = this.expressions.stream().map(i -> {
            try {
                return i.value();
            } catch (UnknownArgumentException e) {
                exception.set(e);
                return null;
            }
        }).toArray(Constable[]::new);
        if (exception.get() != null)
            throw exception.get();
        return new ConstantExpression(constables, this.operatorHelpers);
    }

    @Override
    public @NonNull MultiExpression reverse() {
        return new MultiExpression().append(Operator.MINUS, this);
    }

    @Override
    @NonNull
    public MultiExpression clone() {
        return new MultiExpression(this.expressions, this.operatorHelpers);
    }

    @NonNull
    public MultiExpression append(@NonNull IExpression expression) {
        return this.append(Operator.PLUS, expression);
    }

    @NonNull
    public MultiExpression append(@NonNull Operator operator, @NonNull IExpression expression) {
        this.operatorHelpers.add(new OperatorHelper(operator, this.expressions.size() - 1));
        this.expressions.add(expression);
        return this;
    }

    @NonNull
    public MultiExpression addExpression(@NonNull IExpression expression) {
        this.expressions.add(expression);
        return this;
    }

    @Override
    @NonNull
    public Iterator<OperatorHelper> iterator() {
        return Sets.newTreeSet(this.operatorHelpers).iterator();
    }

    @Override
    public boolean isNeedBracket() {
        return operatorHelpers.stream().anyMatch(operatorHelper -> operatorHelper.getOperator() == Operator.PLUS);
    }

    @NonNull
    public MultiExpression addOperator(Operator operator) {
        this.operatorHelpers.add(new OperatorHelper(operator, this.expressions.size() - 1));
        return this;
    }

    public boolean checkSize() {
        return this.expressions.size() == this.operatorHelpers.size() + 1;
    }

    @Override
    @NonNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.expressions.get(0).isNeedBracket()) {
            stringBuilder.append('(');
        }
        stringBuilder.append(this.expressions.get(0).toString());
        if (this.expressions.get(0).isNeedBracket()) {
            stringBuilder.append(')');
        }
        for (int i = 1; i < this.expressions.size(); i++) {
            stringBuilder.append(' ');
            stringBuilder.append(this.operatorHelpers.get(i - 1).getOperator().getName());
            stringBuilder.append(' ');
            if (this.expressions.get(i).isNeedBracket()) {
                stringBuilder.append('(');
            }
            stringBuilder.append(this.expressions.get(i).toString());
            if (this.expressions.get(i).isNeedBracket()) {
                stringBuilder.append(')');
            }
        }
        return stringBuilder.toString();
    }

    public static class OperatorHelper implements Comparable<OperatorHelper> {

        private final Operator operator;
        private final int pos;

        public OperatorHelper(Operator operator, int pos) {
            this.operator = operator;
            this.pos = pos;
        }

        @Override
        public int compareTo(@NonNull OperatorHelper o) {
            if (o.getPriority() != this.getPriority())
                return o.getPriority() - this.getPriority();
            else return this.pos - o.pos;
        }

        public int getPriority() {
            return this.operator.getPriority();
        }

        public int getPosition() {
            return this.pos;
        }


        public Operator getOperator() {
            return this.operator;
        }
    }
}
