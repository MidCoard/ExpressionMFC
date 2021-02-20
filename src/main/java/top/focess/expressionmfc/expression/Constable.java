package top.focess.expressionmfc.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.DivideByZeroException;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;

public interface Constable extends IExpression{

    @NonNull SimpleConstable simplify();

    @NonNull
    Constable clone();

    double doubleValue() throws DivideByZeroException;

    boolean isZero() throws DivideByZeroException;

    @Override
    @NonNull
    default Constable value() {
        return this.clone();
    }
}
