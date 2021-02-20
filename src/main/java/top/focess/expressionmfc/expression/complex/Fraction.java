package top.focess.expressionmfc.expression.complex;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.IFraction;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.constant.ConstantFraction;
import top.focess.expressionmfc.expression.simple.SimpleExpression;

public class Fraction extends ComplexExpression implements IFraction {

    public Fraction(IExpression numerator,IExpression denominator) {
        super(numerator,denominator);
    }

    @Override
    public @NonNull Simplifiable simplify() {
        //todo
        return null;
    }

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        return new ConstantFraction(this.getNumerator().value(), getDenominator().value());
    }

    @Override
    public @NonNull IExpression getNumerator() {
        return this.getChildExpression(0);
    }

    @Override
    public @NonNull IExpression getDenominator() {
        return this.getChildExpression(1);
    }

    @Override
    public @NonNull Fraction clone() {
        return new Fraction(this.getNumerator(),this.getDenominator());
    }
}
