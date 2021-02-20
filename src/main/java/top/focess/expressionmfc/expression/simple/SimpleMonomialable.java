package top.focess.expressionmfc.expression.simple;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.expression.Monomialable;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;

public interface SimpleMonomialable extends Monomialable, Simplifiable {

    @NonNull
    SimpleMonomialable reverse();

    @NonNull SimpleConstable getK();
}
