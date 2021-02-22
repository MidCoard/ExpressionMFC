package top.focess.expressionmfc.exception;

import top.focess.expressionmfc.expression.IExpression;

public class DividedByZeroException extends ExpressionException {

    public DividedByZeroException(IExpression expression) {
        super("The value of Expression \"" + expression + "\" is zero.");
    }
}
