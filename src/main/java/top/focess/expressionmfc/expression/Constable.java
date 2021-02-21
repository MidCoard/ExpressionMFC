package top.focess.expressionmfc.expression;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.DivideByZeroException;
import top.focess.expressionmfc.expression.constant.ConstantFraction;
import top.focess.expressionmfc.expression.multi.ConstantExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.operator.Operator;
import top.focess.expressionmfc.util.MathHelper;

public interface Constable extends IExpression, Comparable<Constable> {

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

    @NonNull
    default ConstantFraction divided(Constable constable) {
        return new ConstantFraction(this, constable);
    }

    @NonNull
    default ConstantExpression plus(Constable expression) {
        ConstantExpression constantExpression = new ConstantExpression(this);
        return constantExpression.append(expression);
    }

    @NonNull
    default ConstantExpression minus(Constable expression) {
        return expression.plus(this).reverse();
    }

    @NonNull
    default ConstantExpression multiply(Constable expression) {
        ConstantExpression constantExpression = new ConstantExpression(this);
        return constantExpression.append(Operator.MULTIPLY, expression);
    }

    @Override
    default int compareTo(@NonNull Constable o) {
        double a;
        double b;
        try {
            a = this.doubleValue();
        } catch (DivideByZeroException e) {
            try {
                o.doubleValue();
            } catch (DivideByZeroException divideByZeroException) {
                return 0;
            }
            return 1;
        }
        try {
            b = o.doubleValue();
        } catch (DivideByZeroException e) {
            return -1;
        }
        if (MathHelper.abs(a - b) < Double.MIN_NORMAL)
            return 0;
        else return (int) (a - b);
    }
}
