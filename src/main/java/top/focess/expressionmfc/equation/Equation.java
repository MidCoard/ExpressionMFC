package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.equation.range.Range;
import top.focess.expressionmfc.exception.IllegalUnknownArgumentException;
import top.focess.expressionmfc.exception.NoSolutionException;
import top.focess.expressionmfc.exception.UnknownArgumentNotFoundException;
import top.focess.expressionmfc.expression.IExpression;

public abstract class Equation {

    private final IExpression left;
    private final IExpression right;
    private final EquationOperator operator;

    public Equation(@NonNull IExpression left, @NonNull IExpression right,@NonNull EquationOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
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
    public SimpleEquation solve(Argument argument,Solution solution) throws UnknownArgumentNotFoundException, NoSolutionException, IllegalUnknownArgumentException {
        return this.solve(argument,solution,Range.leftCloseRightOpen(-10000,10000));
    }

    @NonNull
    public abstract SimpleEquation solve(Argument argument, Solution solution, Range range) throws IllegalUnknownArgumentException, UnknownArgumentNotFoundException, NoSolutionException;

    @Override
    @NonNull
    public String toString() {
        return this.left + " " + this.operator.getName() + " " + this.right;
    }

    public EquationOperator getOperator() {
        return operator;
    }
}
