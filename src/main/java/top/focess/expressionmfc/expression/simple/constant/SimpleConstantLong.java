package top.focess.expressionmfc.expression.simple.constant;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.DivideByZeroException;
import top.focess.expressionmfc.expression.Simplifiable;

public class SimpleConstantLong extends SimpleConstant {

    public static final SimpleConstantLong ZERO = new SimpleConstantLong(0);

    public static final SimpleConstantLong ONE = new SimpleConstantLong(1);

    private final long value;

    public SimpleConstantLong(long value) {
        this.value = value;
    }

    @Override
    public double doubleValue() throws DivideByZeroException {
        return this.value;
    }

    @Override
    public boolean isZero() {
        return this.value == 0;
    }

    @Override
    public @NonNull SimpleConstantLong clone() {
        return new SimpleConstantLong(this.value);
    }

    @Override
    public @NonNull SimpleConstant reverse() {
        return new SimpleConstantLong(-this.value);
    }

    @Override
    public @NonNull SimpleConstant plus(SimpleConstant simpleConstant) {
        if (simpleConstant instanceof SimpleConstantDouble)
            return new SimpleConstantDouble(this.value + simpleConstant.getValue().doubleValue());
         else
            return new SimpleConstantLong(this.value + simpleConstant.getValue().longValue());
    }

    @Override
    public @NonNull SimpleConstant minus(SimpleConstant simpleConstant) {
        if (simpleConstant instanceof SimpleConstantDouble)
            return new SimpleConstantDouble(this.value - simpleConstant.getValue().doubleValue());
        else
            return new SimpleConstantLong(this.value - simpleConstant.getValue().longValue());
    }

    @Override
    public @NonNull SimpleConstant multiply(SimpleConstant simpleConstant) {
        if (simpleConstant instanceof SimpleConstantDouble)
            return new SimpleConstantDouble(this.value * simpleConstant.getValue().doubleValue());
        else
            return new SimpleConstantLong(this.value * simpleConstant.getValue().longValue());
    }

    @Override
    public @NonNull Long getValue() {
        return this.value;
    }

    @NonNull
    public SimpleConstantDouble toDouble() {
        return new SimpleConstantDouble(this.value);
    }

    @NonNull
    public SimpleConstantFraction toFraction() {
        return new SimpleConstantFraction(this,SimpleConstantLong.ONE);
    }

}
