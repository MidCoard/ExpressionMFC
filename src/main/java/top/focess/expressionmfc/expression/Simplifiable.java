package top.focess.expressionmfc.expression;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Simplifiable extends IExpression {

    @Override
    @NonNull
    Simplifiable clone();

    @Override
    default @NonNull Simplifiable simplify() {
        return this.clone();
    }

    @NonNull
    Simplifiable plus(Simplifiable simplifiable);

    @NonNull
    Simplifiable minus(Simplifiable simplifiable);

    @NonNull
    Simplifiable divided(Simplifiable simplifiable);

    @NonNull
    Simplifiable multiply(Simplifiable simplifiable);

    @NonNull
    Simplifiable reverse();
}
