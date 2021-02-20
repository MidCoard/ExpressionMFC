package top.focess.expressionmfc.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.simple.SimpleExpression;

public interface IExpression {

    @NonNull
    IExpression clone();

    @NonNull
    Simplifiable simplify();

    @NonNull
    Constable value() throws UnknownArgumentException;
}
