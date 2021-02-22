package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.IllegalUnknownArgumentException;
import top.focess.expressionmfc.exception.UnknownArgumentNotFoundException;
import top.focess.expressionmfc.expression.IExpression;

public abstract class Equation {

    private final IExpression left;
    private final IExpression right;

    public Equation(@NonNull IExpression left, @NonNull IExpression right) {
        this.left = left;
        this.right = right;
    }
    @NonNull
    public IExpression getLeft() {
        return left;
    }
    @NonNull
    public IExpression getRight() {
        return right;
    }
    @NonNull
    public abstract SimpleEquation solve(Argument argument) throws UnknownArgumentNotFoundException, IllegalUnknownArgumentException;
}
