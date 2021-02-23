package top.focess.expressionmfc.exception;

import top.focess.expressionmfc.argument.Argument;

public class UnknownArgumentException extends ExpressionException {

    private final Argument unknownArgument;

    public UnknownArgumentException(Argument argument) {
        super("Argument \"" + argument.getName() + "\" is unknown.");
        this.unknownArgument = argument;
    }

    public Argument getUnknownArgument() {
        return unknownArgument;
    }
}
