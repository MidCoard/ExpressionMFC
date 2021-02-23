package top.focess.expressionmfc.equation.range;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.equation.EquationOperator;
import top.focess.expressionmfc.exception.InequationOperatorNotFoundException;

public abstract class Range {

    public abstract boolean test(double v);

    @NonNull
    public static Range leftCloseRightOpen(double a,double b) {
        try {
            return new BothRange(new SingleRange(EquationOperator.GREATER_EQUAL,a),new SingleRange(EquationOperator.LESS,b));
        } catch (InequationOperatorNotFoundException e) {
            return null;
        }
    }

    public abstract double getMin();
    public abstract double getMax();
}
