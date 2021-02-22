package top.focess.expressionmfc.exception;

import top.focess.expressionmfc.argument.Argument;

public class UnknownArgumentNotFoundException extends EquationException {

    public UnknownArgumentNotFoundException(Argument argument) {
        super("Unknown Argument \"" + argument.getName() + "\" is not found.");
    }
}
