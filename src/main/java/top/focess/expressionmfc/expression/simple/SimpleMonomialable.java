package top.focess.expressionmfc.expression.simple;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.expression.Monomialable;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;

import java.util.List;

public interface SimpleMonomialable extends Monomialable, Simplifiable {

    @NonNull
    SimpleMonomialable removeSameArguments(List<Argument> arguments);

    @NonNull
    SimpleMonomialable reverse();

    @NonNull SimpleConstable getK();

    @NonNull
    Argument getFirst();

    @NonNull
    List<Argument> getLast();
}
