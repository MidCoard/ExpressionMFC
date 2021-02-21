package top.focess.expressionmfc.exception;

import top.focess.expressionmfc.expression.IExpression;

public class DivideByZeroException extends ExpressionException {

    public DivideByZeroException(IExpression expression) {
        super("The value of Expression \"" + expression + "\" is zero.");
    }
}
