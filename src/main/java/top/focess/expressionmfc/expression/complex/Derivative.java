package top.focess.expressionmfc.expression.complex;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.SimpleDerivative;

public class Derivative extends ComplexExpression {

    private final Argument argument;

    public Derivative(IExpression expression, Argument argument) {
        super(expression);
        this.argument = argument;
    }

    @NonNull
    public IExpression getOriginalExpression() {
        return this.getChildExpression(0);
    }

    public @NonNull Argument getArgument() {
        return this.argument;
    }

    @Override
    public @NonNull IExpression reverse() {
        return new Derivative(this.getOriginalExpression().reverse(), this.argument);
    }

    @Override
    public @NonNull IExpression clone() {
        return new Derivative(this.getOriginalExpression(), this.argument);
    }

    @Override
    public @NonNull String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('d');
        if (this.getOriginalExpression().isNeedBracket() || this.getOriginalExpression() instanceof Fraction)
            stringBuilder.append('(');
        stringBuilder.append(this.getOriginalExpression().toString());
        if (this.getOriginalExpression().isNeedBracket() || this.getOriginalExpression() instanceof Fraction)
            stringBuilder.append(')');
        stringBuilder.append(" / d");
        stringBuilder.append(this.argument.toString());
        return stringBuilder.toString();
    }

    @Override
    public @NonNull Simplifiable simplify() {
        return new SimpleDerivative(this.getOriginalExpression().simplify(), this.argument).simplify();
    }

    @Override
    public @NonNull Simplifiable simpleValue() {
        return this.simplify().simpleValue();
    }

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        return this.simplify().value();
    }
}
