package top.focess.expressionmfc.expression.complex;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.constant.ConstantFraction;
import top.focess.expressionmfc.expression.simple.SimpleExpression;
import top.focess.expressionmfc.operator.Operator;
import top.focess.expressionmfc.util.Pair;

import java.util.Objects;

public class Fraction extends ComplexExpression {

    public Fraction(IExpression numerator, IExpression denominator) {
        super(numerator, denominator);
    }

    public Fraction(Pair<SimpleExpression, SimpleExpression> pair) {
        super(pair.getKey(),pair.getValue());
    }

    @Override
    public @NonNull Simplifiable simplify() {
        return Objects.requireNonNull(Operator.DIVIDED.operate(this.getNumerator().simplify(), this.getDenominator().simplify()));
    }

    @Override
    public @NonNull Simplifiable simpleValue() {
        return this.simplify().simpleValue();
    }

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        return new ConstantFraction(this.getNumerator().value(), getDenominator().value());
    }

    public @NonNull IExpression getNumerator() {
        return this.getChildExpression(0);
    }

    public @NonNull IExpression getDenominator() {
        return this.getChildExpression(1);
    }

    @Override
    public @NonNull Fraction reverse() {
        return new Fraction(this.getNumerator().reverse(), this.getDenominator());
    }

    @Override
    public @NonNull Fraction clone() {
        return new Fraction(this.getNumerator(), this.getDenominator());
    }

    @Override
    @NonNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.getNumerator().isNeedBracket()) {
            stringBuilder.append('(');
        }
        stringBuilder.append(this.getNumerator().toString());
        if (this.getNumerator().isNeedBracket()) {
            stringBuilder.append(')');
        }
        stringBuilder.append('/');
        if (this.getDenominator().isNeedBracket()) {
            stringBuilder.append('(');
        }
        stringBuilder.append(this.getDenominator().toString());
        if (this.getDenominator().isNeedBracket()) {
            stringBuilder.append(')');
        }
        return stringBuilder.toString();
    }
}
