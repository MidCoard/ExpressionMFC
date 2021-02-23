package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.InequationOperatorNotFoundException;
import top.focess.expressionmfc.expression.IExpression;

public abstract class Inequation extends Equation {

     static EquationOperator throwException() throws InequationOperatorNotFoundException {
        throw new InequationOperatorNotFoundException();
    }

    public Inequation(@NonNull IExpression left, @NonNull IExpression right, @NonNull EquationOperator operator) throws InequationOperatorNotFoundException {
        super(left, right,operator.isInequationOperator() ? operator:throwException());
    }
}
