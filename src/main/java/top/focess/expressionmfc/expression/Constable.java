package top.focess.expressionmfc.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.DivideByZeroException;
import top.focess.expressionmfc.expression.complex.Fraction;
import top.focess.expressionmfc.expression.constant.ConstantFraction;
import top.focess.expressionmfc.expression.multi.ConstantExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.operator.Operator;

public interface Constable extends IExpression{

    @NonNull SimpleConstable simplify();

    @NonNull
    Constable clone();

    double doubleValue() throws DivideByZeroException;

    boolean isZero();

    @Override
    @NonNull Constable reverse();

    @Override
    @NonNull
    default Constable value() {
        return this.clone();
    }

    @NonNull default ConstantFraction divided(Constable constable) {
        return new ConstantFraction(this,constable);
    }

    @NonNull default ConstantExpression plus(Constable expression) {
        ConstantExpression constantExpression = new ConstantExpression(this);
        return constantExpression.append(expression);
    }

    @NonNull default ConstantExpression minus(Constable expression) {
        return expression.plus(this).reverse();
    }

    @NonNull default ConstantExpression multiply(Constable expression) {
        ConstantExpression constantExpression = new ConstantExpression(this);
        return constantExpression.append(Operator.MULTIPLY,expression);
    }
}
