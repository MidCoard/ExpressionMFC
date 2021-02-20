package top.focess.expressionmfc.argument;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.*;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantLong;
import top.focess.expressionmfc.operator.Operator;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Argument extends SimpleExpression implements SimpleMonomialable {

    public static final Argument NULL_ARGUMENT = new Argument("", SimpleConstantLong.ONE);
    private final String name;

    private Constable value;

    public Argument(@NonNull String name, @NonNull Constable value) {
        this.name = name;
        this.value = value;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public boolean isUnknown() {
        return false;
    }

    @NonNull
    public Constable getValue() throws UnknownArgumentException {
        return value;
    }

    public void setValue(@NonNull Constable value) {
        this.value = value;
    }

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        return this.getValue().clone();
    }

    @Override
    public @NonNull SimpleExpression clone() {
        return new Argument(this.name, this.value);
    }

    @Override
    public @NonNull SimpleExpression plus(SimpleExpression simpleExpression) {
        if (simpleExpression instanceof SimplePolynomial) {
            @NonNull List<SimpleMonomialable> monomialables = ((SimplePolynomial) simpleExpression).getMonomials();
            SimpleMonomialable[] array = monomialables.toArray(new SimpleMonomialable[monomialables.size() + 1]);
            array[array.length - 1] = new SimpleMonomial(SimpleConstantLong.ONE, this);
            return new SimplePolynomial(array);
        } else
            return new SimplePolynomial(new SimpleMonomial(SimpleConstantLong.ONE, this), (SimpleMonomialable) simpleExpression);

    }

    @Override
    public @NonNull SimpleExpression minus(SimpleExpression simpleExpression) {
        if (simpleExpression instanceof SimplePolynomial) {
            @NonNull List<SimpleMonomialable> monomialables = ((SimplePolynomial) simpleExpression).reverse().getMonomials();
            SimpleMonomialable[] array = monomialables.toArray(new SimpleMonomialable[monomialables.size() + 1]);
            array[array.length - 1] = new SimpleMonomial(SimpleConstantLong.ONE, this);
            return new SimplePolynomial(array);
        } else
            return new SimplePolynomial(new SimpleMonomial(SimpleConstantLong.ONE, this), ((SimpleMonomialable) simpleExpression).reverse());
    }

    @Override
    public @NonNull SimpleExpression multiply(SimpleExpression simpleExpression) {
        if (simpleExpression instanceof SimplePolynomial) {
            SimpleMonomialable[] monomialables = ((SimplePolynomial) simpleExpression).getMonomials().toArray(new SimpleMonomialable[0]);
            for (int i = 0; i < monomialables.length; i++)
                monomialables[i] = (SimpleMonomialable) Operator.MULTIPLY.operate(monomialables[i], new SimpleMonomial(SimpleConstantLong.ONE, this));
            return new SimplePolynomial(monomialables);
        } else {
            List<Argument> arguments = Lists.newArrayList();
            arguments.addAll(((SimpleMonomialable) simpleExpression).getArguments());
            return new SimpleMonomial(Operator.MULTIPLY.operate(this.getK(), ((SimpleMonomialable) simpleExpression).getK()), this, arguments.toArray(new Argument[0]));
        }
    }

    @Override
    public @NonNull Simplifiable divided(Simplifiable simplifiable) {
        if (simplifiable instanceof SimpleExpression)
            return new SimpleFraction(new SimpleMonomial(SimpleConstantLong.ONE, this), (SimpleExpression) simplifiable);
        else {
            if (simplifiable instanceof SimpleIFraction) {
                return new SimpleFraction(Operator.MULTIPLY.operate(new SimpleMonomial(SimpleConstantLong.ONE, this), ((SimpleIFraction) simplifiable).getDenominator()), ((SimpleIFraction) simplifiable).getNumerator());
            }
            //todo
        }
        return null;
    }

    @Override
    public @NonNull SimpleConstable getK() {
        return SimpleConstantLong.ONE;
    }

    @Override
    public @NonNull List<Argument> getArguments() {
        return Collections.singletonList(this);
    }

    @Override
    public @NonNull SimpleMonomial reverse() {
        return new SimpleMonomial(SimpleConstantLong.ONE.reverse(), this);
    }
}
