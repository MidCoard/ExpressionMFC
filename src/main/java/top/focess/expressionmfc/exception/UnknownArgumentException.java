package top.focess.expressionmfc.exception;

import top.focess.expressionmfc.argument.Argument;

public class UnknownArgumentException extends ExpressionException {


    public UnknownArgumentException(Argument argument) {
        super("Argument \"" + argument.getName() + "\" is unknown.");
    }
}
