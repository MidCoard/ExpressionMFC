package top.focess.expressionmfc.expression.simple.constant;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.SimpleMonomialable;

import java.util.List;

public interface SimpleConstable extends SimpleMonomialable, Constable {


    @Override
    @NonNull
    default SimpleConstable getK() {
        return this;
    }

    @Override
    @NonNull
    default List<Argument> getArguments() {
        return Lists.newArrayList(Argument.NULL_ARGUMENT);
    }

    @NonNull
    SimpleConstable clone();

    @NonNull
    default SimpleConstable simplify() {
        return this.clone();
    }

    @NonNull
    SimpleConstable reverse();

    @NonNull
    SimpleConstable plus(SimpleConstable simpleConstable);

    @NonNull
    SimpleConstable minus(SimpleConstable simpleConstable);

    @NonNull
    SimpleConstable multiply(SimpleConstable simpleConstable);
}
