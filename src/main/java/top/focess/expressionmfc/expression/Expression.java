package top.focess.expressionmfc.expression;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public abstract class Expression implements IExpression {

    protected List<IExpression> expressions;

    public Expression() {
        this.expressions = Lists.newArrayList();
    }

    @NonNull
    public abstract IExpression clone();

    @NonNull
    public abstract String toString();

}
