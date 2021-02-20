package top.focess.expressionmfc.expression.simple;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.expression.SelfExpression;
import top.focess.expressionmfc.expression.Simplifiable;

public abstract class SimpleExpression extends SelfExpression implements Simplifiable {

    @Override
    public @NonNull Simplifiable simplify() {
        return this;
    }

    @Override
    @NonNull
    public abstract SimpleExpression clone();

    @NonNull
    public abstract SimpleExpression plus(SimpleExpression simpleExpression);

    @NonNull
    public abstract SimpleExpression minus(SimpleExpression simpleExpression);

    @NonNull
    public abstract SimpleExpression multiply(SimpleExpression simpleExpression);

    @Override
    public @NonNull Simplifiable plus(Simplifiable simplifiable) {
        return plus((SimpleExpression) simplifiable);
    }

    @Override
    public @NonNull Simplifiable minus(Simplifiable simplifiable) {
        return minus((SimpleExpression) simplifiable);
    }

    @Override
    public @NonNull Simplifiable multiply(Simplifiable simplifiable) {
        return multiply((SimpleExpression) simplifiable);
    }

    @NonNull
    public abstract SimpleExpression reverse();

}
