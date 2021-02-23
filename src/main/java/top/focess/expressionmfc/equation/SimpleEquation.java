package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.equation.range.Range;
import top.focess.expressionmfc.exception.UnknownArgumentNotFoundException;
import top.focess.expressionmfc.expression.Constable;

public abstract class SimpleEquation extends Equation {
    public SimpleEquation(Argument left, Constable right,EquationOperator operator) {
        super(left, right,operator);
    }

    @Override
    public @NonNull SimpleEquation solve(Argument argument, Solution solution, Range range) throws UnknownArgumentNotFoundException {
        if (argument.equals(this.getUnknownArgument()))
            return this;
        else throw new UnknownArgumentNotFoundException(argument);
    }

    @Override
    public @NonNull Argument getLeft() {
        return (Argument) super.getLeft();
    }

    @Override
    public @NonNull Constable getRight() {
        return (Constable) super.getRight();
    }

    @NonNull
    public Constable getAnswer() {
        return this.getRight();
    }

    @NonNull
    public Argument getUnknownArgument() {
        return this.getLeft();
    }
}
