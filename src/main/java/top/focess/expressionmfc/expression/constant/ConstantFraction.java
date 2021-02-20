package top.focess.expressionmfc.expression.constant;

import com.sun.tools.internal.jxc.ap.Const;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.DivideByZeroException;
import top.focess.expressionmfc.expression.*;
import top.focess.expressionmfc.expression.complex.Fraction;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantFraction;
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
            throw new DivideByZeroException();
        return this.numerator.doubleValue() / this.denominator.doubleValue();
    }

    @Override
    public boolean isZero() throws DivideByZeroException {
        if (this.denominator.isZero())
            throw new DivideByZeroException();
        return this.numerator.isZero();
    }

    @Override
    @NonNull
    public ConstantFraction clone() {
        return new ConstantFraction(this.getNumerator(),this.getDenominator());
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
}
