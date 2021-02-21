package top.focess.expressionmfc.expression.constant;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.DivideByZeroException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Expression;
import top.focess.expressionmfc.expression.IFraction;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.operator.Operator;

import java.util.Objects;

public class ConstantFraction extends Expression implements Constable, IFraction {

    private final Constable numerator;
    private final Constable denominator;

    public ConstantFraction(Constable numerator, Constable denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public double doubleValue() throws DivideByZeroException {
        if (this.denominator.isZero())
            throw new DivideByZeroException(this.denominator);
        return this.numerator.doubleValue() / this.denominator.doubleValue();
    }

    @Override
    public boolean isZero() {
        return this.numerator.isZero();
    }

    @Override
    public @NonNull ConstantFraction reverse() {
        return new ConstantFraction(this.getNumerator().reverse(), this.getDenominator());
    }

    @Override
    @NonNull
    public ConstantFraction clone() {
        return new ConstantFraction(this.getNumerator(), this.getDenominator());
    }

    @Override
    public @NonNull SimpleConstable simplify() {
        return Objects.requireNonNull(Operator.DIVIDED.operate(this.getNumerator().simplify(), this.getDenominator().simplify()));
    }

    @Override
    public @NonNull Constable getNumerator() {
        return this.numerator;
    }

    @Override
    public @NonNull Constable getDenominator() {
        return this.denominator;
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
