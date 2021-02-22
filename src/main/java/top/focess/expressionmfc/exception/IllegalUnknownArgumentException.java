package top.focess.expressionmfc.exception;

import top.focess.expressionmfc.argument.Argument;

public class IllegalUnknownArgumentException extends EquationException {
    public IllegalUnknownArgumentException(Argument argument) {
        super("Unknown Argument \""+ argument.getName() + "\" is unknown.");
    }
}
