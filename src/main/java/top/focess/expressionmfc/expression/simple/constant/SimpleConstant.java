package top.focess.expressionmfc.expression.simple.constant;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.SimpleExpression;
import top.focess.expressionmfc.expression.simple.SimpleMonomialable;

import java.util.List;

public abstract class SimpleConstant extends SimpleExpression implements SimpleConstable {

    @Override
    @NonNull
    public SimpleMonomialable removeSameArguments(List<Argument> arguments) {
        return this.clone();
    }

    @Override
    public @NonNull List<Argument> getSameArguments() {
        return Lists.newArrayList();
    }

    @Override
    @NonNull
    public SimpleConstable simplify() {
        return this.clone();
    }

    @Override
    @NonNull
    public abstract SimpleConstant clone();

    @Override
    @NonNull
    public SimpleConstant value() {
        return this.clone();
    }

    @NonNull
    public abstract SimpleConstant reverse();

    @NonNull
    public abstract SimpleConstant plus(SimpleConstant simpleConstant);


    @NonNull
    public abstract SimpleConstant minus(SimpleConstant simpleConstant);


    @NonNull
    public abstract SimpleConstant multiply(SimpleConstant simpleConstant);

    @NonNull
    @Override
    public Simplifiable plus(Simplifiable simplifiable) {
        return plus((SimpleConstant) simplifiable);
    }

    @Override
    public @NonNull SimpleConstable plus(SimpleConstable simpleConstable) {
        return plus((SimpleConstant) simpleConstable);
    }

    @Override
    public @NonNull SimpleExpression plus(SimpleExpression simpleExpression) {
        return plus((SimpleConstant) simpleExpression);
    }

    @Override
    public @NonNull SimpleConstable minus(SimpleConstable simpleConstable) {
        return minus((SimpleConstant) simpleConstable);
    }

    @Override
    public @NonNull SimpleExpression minus(SimpleExpression simpleExpression) {
        return minus((SimpleConstant) simpleExpression);
    }

    @Override
    public @NonNull Simplifiable minus(Simplifiable simplifiable) {
        return minus((SimpleConstant) simplifiable);
    }

    @Override
    public @NonNull SimpleConstable multiply(SimpleConstable simpleConstable) {
        return multiply((SimpleConstant) simpleConstable);
    }

    @Override
    public @NonNull SimpleExpression multiply(SimpleExpression simpleExpression) {
        return multiply((SimpleConstant) simpleExpression);
    }

    @Override
    public @NonNull Simplifiable multiply(Simplifiable simplifiable) {
        return multiply((SimpleConstant) simplifiable);
    }

    @NonNull
    public abstract Number getValue();


}
