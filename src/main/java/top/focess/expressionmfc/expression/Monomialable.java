package top.focess.expressionmfc.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.expression.IExpression;

import java.util.List;

public interface Monomialable extends IExpression {

    @NonNull
    Constable getK();

    @NonNull
    List<Argument> getArguments();

    @NonNull
    Monomialable reverse();


}
