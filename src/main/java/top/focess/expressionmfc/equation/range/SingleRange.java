package top.focess.expressionmfc.equation.range;

import top.focess.expressionmfc.equation.EquationOperator;
import top.focess.expressionmfc.exception.InequationOperatorNotFoundException;

public class SingleRange extends Range{
    private final double v;
    private final EquationOperator operator;

    /***
     * test operator v
     * @param operator
     * @param v
     */
    public SingleRange(EquationOperator operator,double v) throws InequationOperatorNotFoundException {
        if (!operator.isInequationOperator())
            throw new InequationOperatorNotFoundException();
        this.v = v;
        this.operator = operator;
    }

    @Override
    public boolean test(double test) {
        return this.operator.test(test,v);
    }

    @Override
    public double getMin() {
        if (this.operator.isGreater())
            return this.v;
        else return - Double.MAX_VALUE;
    }

    @Override
    public double getMax() {
        if (this.operator.isLess())
            return this.v;
        else return Double.MAX_VALUE;
    }
}
