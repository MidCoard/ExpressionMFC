package top.focess.expressionmfc.expression.simple;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Monomialable;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.multi.ConstantExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantLong;
import top.focess.expressionmfc.operator.Operator;

import java.util.List;

public class SimplePolynomial extends SimpleExpression {

    private final List<SimpleMonomialable> monomials;

    public SimplePolynomial(SimpleMonomialable... monomials) {
        this.monomials = Lists.newArrayList(monomials);
        if (this.monomials.size() == 0)
            this.monomials.add(SimpleConstantLong.ZERO);
    }

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        ConstantExpression constantExpression = new ConstantExpression();
        for (Monomialable monomialable:this.monomials)
            constantExpression.append(monomialable.value());
        return constantExpression;
    }

    @Override
    public @NonNull SimplePolynomial clone() {
        return new SimplePolynomial(this.monomials.toArray(new SimpleMonomialable[0]));
    }

    @Override
    public @NonNull SimpleExpression plus(SimpleExpression simpleExpression) {
        if (simpleExpression instanceof SimplePolynomial) {
            @NonNull List<SimpleMonomialable> monomialables = Lists.newArrayList(((SimplePolynomial) simpleExpression).getMonomials());
            monomialables.addAll(this.getMonomials());
            return new SimplePolynomial(monomialables.toArray(new SimpleMonomialable[0]));
        } else {
            @NonNull List<SimpleMonomialable> monomialables = this.getMonomials();
            SimpleMonomialable[] array = monomialables.toArray(new SimpleMonomialable[monomialables.size() + 1]);
            array[array.length - 1] = (SimpleMonomialable) simpleExpression;
            return new SimplePolynomial(array);
        }
    }

    @Override
    public @NonNull SimpleExpression minus(SimpleExpression simpleExpression) {
        if (simpleExpression instanceof SimplePolynomial) {
            @NonNull List<SimpleMonomialable> monomialables = Lists.newArrayList(((SimplePolynomial) simpleExpression).reverse().getMonomials());
            monomialables.addAll(this.getMonomials());
            return new SimplePolynomial(monomialables.toArray(new SimpleMonomialable[0]));
        } else {
            @NonNull List<SimpleMonomialable> monomialables = this.getMonomials();
            SimpleMonomialable[] array = monomialables.toArray(new SimpleMonomialable[monomialables.size() + 1]);
            array[array.length - 1] = (SimpleMonomialable) simpleExpression.reverse();
            return new SimplePolynomial(array);
        }
    }

    @Override
    public @NonNull SimpleExpression multiply(SimpleExpression simpleExpression) {
        if (simpleExpression instanceof SimplePolynomial) {
            SimpleMonomialable[] monomialables = this.getMonomials().toArray(new SimpleMonomialable[0]);
            SimpleMonomialable[] monomialables2 = this.getMonomials().toArray(new SimpleMonomialable[0]);
            SimpleMonomialable[] temp = new SimpleMonomialable[monomialables.length * monomialables2.length];
            int pos = 0;
            for (SimpleMonomialable a:monomialables)
                for (SimpleMonomialable b:monomialables2)
                    temp[pos++] = (SimpleMonomialable) Operator.MULTIPLY.operate(a,b);
            return new SimplePolynomial(temp);
        } else {
            SimpleMonomialable[] monomialables = this.getMonomials().toArray(new SimpleMonomialable[0]);
            for (int i = 0;i<monomialables.length;i++)
                monomialables[i] = (SimpleMonomialable) Operator.MULTIPLY.operate(monomialables[i],simpleExpression);
            return new SimplePolynomial(monomialables);
        }
    }

    @Override
    public @NonNull Simplifiable plus(Simplifiable simplifiable) {
        return plus((SimpleExpression) simplifiable);
    }

    @Override
    public @NonNull Simplifiable minus(Simplifiable simplifiable) {
        return minus((SimpleExpression) simplifiable);
    }

    @Override
    public @NonNull Simplifiable divided(Simplifiable simplifiable) {
        if (simplifiable instanceof SimpleExpression)
            return new SimpleFraction(this, (SimpleExpression) simplifiable);
        else {
            if (simplifiable instanceof SimpleIFraction)
                return new SimpleFraction(Operator.MULTIPLY.operate(this,((SimpleIFraction) simplifiable).getDenominator()),((SimpleIFraction) simplifiable).getNumerator() );
            //todo
        }
        return null;
    }

    @Override
    public @NonNull Simplifiable multiply(Simplifiable simplifiable) {
        return multiply((SimpleExpression) simplifiable);
    }

    @NonNull
    public List<SimpleMonomialable> getMonomials() {
        return monomials;
    }

    @NonNull
    public SimplePolynomial reverse() {
        return new SimplePolynomial(this.monomials.stream().map(SimpleMonomialable::reverse).toArray(SimpleMonomialable[]::new));
    }

}
