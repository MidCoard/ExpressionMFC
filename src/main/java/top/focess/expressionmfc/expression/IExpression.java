package top.focess.expressionmfc.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.complex.Fraction;
import top.focess.expressionmfc.expression.multi.MultiExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantDouble;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantLong;
import top.focess.expressionmfc.operator.Operator;

public interface IExpression {

    @NonNull
    IExpression reverse();

    @NonNull
    IExpression clone();

    @NonNull
    Simplifiable simplify();

    @NonNull
    Simplifiable simpleValue();

    @NonNull
    Constable value() throws UnknownArgumentException;

    default boolean isNeedBracket() {
        return false;
    }

    @NonNull
    default Fraction divided(IExpression expression) {
        return new Fraction(this, expression);
    }

    @NonNull
    default MultiExpression plus(IExpression expression) {
        MultiExpression multiExpression = new MultiExpression(this);
        return multiExpression.append(expression);
    }

    @NonNull
    default MultiExpression minus(IExpression expression) {
        MultiExpression multiExpression = new MultiExpression(this);
        return multiExpression.append(Operator.MINUS, expression);
    }

    @NonNull
    default MultiExpression multiply(IExpression expression) {
        MultiExpression multiExpression = new MultiExpression(this);
        return multiExpression.append(Operator.MULTIPLY, expression);
    }

    @NonNull
    default MultiExpression plus(double d) {
        return this.plus(new SimpleConstantDouble(d));
    }

    @NonNull
    default MultiExpression minus(double d) {
        return this.minus(new SimpleConstantDouble(d));
    }

    @NonNull
    default Fraction divided(double d) {
        return this.divided(new SimpleConstantDouble(d));
    }

    @NonNull
    default MultiExpression multiply(double d) {
        return this.multiply(new SimpleConstantDouble(d));
    }

    @NonNull
    default MultiExpression plus(long l) {
        return this.plus(new SimpleConstantLong(l));
    }

    @NonNull
    default MultiExpression minus(long l) {
        return this.minus(new SimpleConstantLong(l));
    }

    @NonNull
    default Fraction divided(long l) {
        return this.divided(new SimpleConstantLong(l));
    }

    @NonNull
    default MultiExpression multiply(long l) {
        return this.multiply(new SimpleConstantLong(l));
    }


}
