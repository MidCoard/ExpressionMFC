package top.focess.expressionmfc.expression.multi;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.DivideByZeroException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.operator.Operator;

import java.util.List;

public class ConstantExpression extends MultiExpression implements Constable {

    private Double[] values;
    private Integer[] lefts;
    private Integer[] rights;

    protected ConstantExpression(Constable[] constables, List<OperatorHelper> operatorHelpers) {
        super(Lists.newArrayList(constables), operatorHelpers);
    }

    public ConstantExpression(Constable expression) {
        super(expression);
    }

    public ConstantExpression() {
        super();
    }

    public ConstantExpression(boolean flag) {
        super(flag);
    }

    @Override
    @NonNull
    public ConstantExpression value() {
        return this.clone();
    }

    @Override
    @NonNull
    public ConstantExpression clone() {
        return new ConstantExpression(this.expressions.toArray(new Constable[0]), this.operatorHelpers);
    }

    @Override
    public @NonNull SimpleConstable simplify() {
        return (SimpleConstable) super.simplify();
    }

    private Double getOrDefault(int pos) throws DivideByZeroException {
        if (values[pos] == null)
            return ((Constable) this.expressions.get(pos)).doubleValue();
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
    public double doubleValue() throws DivideByZeroException {
        int first = -1;
        int last = -1;
        values = new Double[this.expressions.size()];
        lefts = new Integer[this.expressions.size()];
        rights = new Integer[this.expressions.size()];
        for (OperatorHelper operatorHelper : this) {
            if (last == operatorHelper.getPosition())
                last = operatorHelper.getPosition() + 1;
            else {
                first = operatorHelper.getPosition();
                last = operatorHelper.getPosition() + 1;
            }
            double a = getOrDefault(getLeft(first));
            double b = getOrDefault(getRight(last));
            values[getLeft(first)] = values[getRight(last)] = operatorHelper.getOperator().operate(a, b);
            rights[first] = last;
            lefts[last] = first;
        }
        return values[0];
    }

    @Override
    public boolean isZero() {
        return this.simplify().isZero();
    }

    @Override
    public @NonNull MultiExpression append(@NonNull IExpression expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull MultiExpression append(@NonNull Operator operator, @NonNull IExpression expression) {
        throw new UnsupportedOperationException();
    }

    public @NonNull ConstantExpression append(@NonNull Constable expression) {
        return this.append(Operator.PLUS, expression);
    }

    public @NonNull ConstantExpression append(@NonNull Operator operator, @NonNull Constable expression) {
        return (ConstantExpression) super.append(operator, expression);
    }

    @Override
    public @NonNull ConstantExpression reverse() {
        return new ConstantExpression().append(Operator.MINUS, this);
    }

    @Override
    public @NonNull MultiExpression addExpression(@NonNull IExpression expression) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    public ConstantExpression addExpression(@NonNull Constable constable) {
        return (ConstantExpression) super.addExpression(constable);
    }
}
