package top.focess.expressionmfc.expression.multi;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.C;
import top.focess.expressionmfc.exception.DivideByZeroException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.operator.Operator;

import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ConstantExpression extends MultiExpression implements Constable {

    protected ConstantExpression(Constable[] constables, List<OperatorHelper> operatorHelpers) {
        super(Lists.newArrayList(constables),operatorHelpers);
    }

    public ConstantExpression(Constable expression) {
        super(expression);
    }

    public ConstantExpression(){
        super();
    }
    @Override
    @NonNull
    public ConstantExpression value() {
        return this.clone();
    }

    @Override
    @NonNull
    public ConstantExpression clone() {
        return new ConstantExpression(this.expressions.toArray(new Constable[0]),this.operatorHelpers);
    }

    @Override
    public @NonNull SimpleConstable simplify() {
        return (SimpleConstable) super.simplify();
    }

    private Double[] values;

    private Double getOrDefault(int pos) throws DivideByZeroException {
        if (values[pos] == null)
            return ((Constable)this.expressions.get(pos)).doubleValue();
        else return values[pos];
    }

    @Override
    public double doubleValue() throws DivideByZeroException {
        int first = -1;
        int last = -1;
        values = new Double[this.expressions.size()];
        for (OperatorHelper operatorHelper:this) {
            if (last == operatorHelper.getPosition())
                last = operatorHelper.getPosition() + 1;
            else {
                first = operatorHelper.getPosition();
                last = operatorHelper.getPosition() + 1;
            }
            double a = getOrDefault(first);
            double b = getOrDefault(last);
            values[first] = values[last] =  operatorHelper.getOperator().operate(a,b);
        }
        return values[0];
    }

    @Override
    public boolean isZero() throws DivideByZeroException {

        //todo
        return false;
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
        return this.append(Operator.PLUS,expression);
    }

    public @NonNull ConstantExpression append(@NonNull Operator operator, @NonNull Constable expression) {
        return (ConstantExpression) super.append(operator, expression);
    }

}
