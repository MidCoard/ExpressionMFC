package top.focess.expressionmfc.expression.simple;

import com.google.common.collect.Lists;
import com.sun.org.apache.xpath.internal.Arg;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.multi.ConstantExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstant;
import top.focess.expressionmfc.operator.Operator;

import java.util.List;

public class SimpleMonomial extends SimpleExpression implements SimpleMonomialable {

    private final SimpleConstable k;
    private final Argument first;
    private final List<Argument> arguments;

    public SimpleMonomial(SimpleConstable k,Argument first, Argument... arguments) {
        this.k = k;
        this.first = first;
        this.arguments = Lists.newArrayList(arguments);
    }

    @Override
    @NonNull
    public SimpleConstable getK() {
        return k;
    }

    @Override
    @NonNull
    public List<Argument> getArguments() {
        List<Argument> arguments = Lists.newArrayList(this.first);
        arguments.addAll(this.arguments);
        return arguments;
    }

    @Override
    public @NonNull SimpleMonomial reverse() {
        return new SimpleMonomial(this.getK().reverse(),this.first,this.getArguments().toArray(new Argument[0]));
    }

    @Override
    public @NonNull SimpleMonomial clone() {
        return new SimpleMonomial(this.getK(),this.first,this.getArguments().toArray(new Argument[0]));
    }

    @Override
    public @NonNull SimpleExpression plus(SimpleExpression simpleExpression) {
        if (simpleExpression instanceof SimplePolynomial) {
            @NonNull List<SimpleMonomialable> monomialables = ((SimplePolynomial) simpleExpression).getMonomials();
            SimpleMonomialable[] array = monomialables.toArray(new SimpleMonomialable[monomialables.size() + 1]);
            array[array.length - 1] = this;
            return new SimplePolynomial(array);
        } else
            return new SimplePolynomial(this, (SimpleMonomialable) simpleExpression);

    }

    @Override
    public @NonNull SimpleExpression minus(SimpleExpression simpleExpression) {
        if (simpleExpression instanceof SimplePolynomial) {
            @NonNull List<SimpleMonomialable> monomialables = ((SimplePolynomial) simpleExpression).reverse().getMonomials();
            SimpleMonomialable[] array = monomialables.toArray(new SimpleMonomialable[monomialables.size() + 1]);
            array[array.length - 1] = this;
            return new SimplePolynomial(array);
        } else
            return new SimplePolynomial(this,( (SimpleMonomialable) simpleExpression).reverse());
    }

    @Override
    public @NonNull SimpleExpression multiply(SimpleExpression simpleExpression) {
        if (simpleExpression instanceof SimplePolynomial) {
            SimpleMonomialable[] monomialables = ((SimplePolynomial) simpleExpression).getMonomials().toArray(new SimpleMonomialable[0]);
            for (int i = 0;i<monomialables.length;i++)
                monomialables[i] = (SimpleMonomialable) Operator.MULTIPLY.operate(monomialables[i],this);
            return new SimplePolynomial(monomialables);
        } else {
            List<Argument> arguments = Lists.newArrayList(this.arguments);
            arguments.addAll(((SimpleMonomialable)simpleExpression).getArguments());
            return new SimpleMonomial(Operator.MULTIPLY.operate(this.getK(),((SimpleMonomialable)simpleExpression).getK()),this.first,arguments.toArray(new Argument[0]));
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

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        ConstantExpression constantExpression = new ConstantExpression(this.getK());
        constantExpression.append(Operator.MULTIPLY,this.first.getValue());
        for (Argument argument:this.arguments)
            constantExpression.append(Operator.MULTIPLY,argument.getValue());
        return constantExpression;
    }
}
