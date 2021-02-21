package top.focess.expressionmfc.exception;

public class InvalidExpressionException extends ExpressionException {


    public InvalidExpressionException(String msg) {
        super("String Expression \"" + msg + "\" is invalid.");
    }
}
