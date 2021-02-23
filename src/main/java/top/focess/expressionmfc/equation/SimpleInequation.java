package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.InequationOperatorNotFoundException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.operator.Operator;

public abstract class SimpleInequation extends SimpleEquation {

    public SimpleInequation(Argument left, Constable right, EquationOperator operator) throws InequationOperatorNotFoundException {
        super(left, right,operator.isInequationOperator()?operator:Inequation.throwException());
    }
}
