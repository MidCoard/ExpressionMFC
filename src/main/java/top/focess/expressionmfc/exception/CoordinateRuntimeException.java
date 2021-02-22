package top.focess.expressionmfc.exception;

public abstract class CoordinateRuntimeException  extends RuntimeException{

    public CoordinateRuntimeException(Exception exception) {
        super(exception);
    }
}
