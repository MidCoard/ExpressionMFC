package top.focess.expressionmfc.expression.constant;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.DividedByZeroException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.complex.Fraction;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstant;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantFraction;
import top.focess.expressionmfc.operator.Operator;

public class ConstantFraction extends Fraction implements Constable {


    public ConstantFraction(Constable numerator, Constable denominator) {
        super(numerator, denominator);
    }

    @Override
    public double doubleValue() throws DividedByZeroException {
        if (this.getDenominator().isZero())
            throw new DividedByZeroException(this.getDenominator());
        return this.getNumerator().doubleValue() / this.getDenominator().doubleValue();
    }

    @Override
    public boolean isZero() {
        return this.getNumerator().isZero();
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
        SimpleConstable numerator = this.getNumerator().simplify();
        SimpleConstable denominator = this.getDenominator().simplify();
        if (numerator instanceof SimpleConstantFraction && denominator instanceof SimpleConstantFraction)
            return new SimpleConstantFraction(Operator.MULTIPLY.operate(((SimpleConstantFraction) numerator).getNumerator(), ((SimpleConstantFraction) denominator).getDenominator()), Operator.MULTIPLY.operate(((SimpleConstantFraction) numerator).getDenominator(), ((SimpleConstantFraction) denominator).getNumerator()));
        else if (numerator instanceof SimpleConstantFraction)
            return new SimpleConstantFraction(((SimpleConstantFraction) numerator).getNumerator(), Operator.MULTIPLY.operate(((SimpleConstantFraction) numerator).getDenominator(), (SimpleConstant) denominator));
        else if (denominator instanceof SimpleConstantFraction)
            return new SimpleConstantFraction(Operator.MULTIPLY.operate((SimpleConstant) numerator, ((SimpleConstantFraction) denominator).getDenominator()), ((SimpleConstantFraction) denominator).getNumerator());
        else return new SimpleConstantFraction((SimpleConstant) numerator, (SimpleConstant) denominator);
    }

    @Override
    public @NonNull Simplifiable simpleValue() {
        return this.simplify().simpleValue();
    }

    @Override
    public @NonNull Constable getNumerator() {
        return (Constable) super.getNumerator();
    }

    @Override
    public @NonNull Constable getDenominator() {
        return (Constable) super.getDenominator();
    }

    @Override
    public @NonNull Constable value() {
        return this.clone();
    }
}
