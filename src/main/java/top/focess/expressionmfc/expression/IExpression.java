package top.focess.expressionmfc.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.complex.Fraction;
import top.focess.expressionmfc.expression.multi.MultiExpression;
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
        return expression.plus(this).reverse();
    }

    @NonNull
    default MultiExpression multiply(IExpression expression) {
        MultiExpression multiExpression = new MultiExpression(this);
        return multiExpression.append(Operator.MULTIPLY, expression);
    }


}
