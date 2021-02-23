package top.focess.expressionmfc.exception;


public class InvalidEquationException extends EquationException {
    public InvalidEquationException(String msg) {
        super("String Equation \"" + msg + "\" is invalid.");
    }
}
