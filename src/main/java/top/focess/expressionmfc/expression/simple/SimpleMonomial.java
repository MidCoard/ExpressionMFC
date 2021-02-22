package top.focess.expressionmfc.expression.simple;

import com.google.common.collect.Lists;
import com.sun.org.apache.xpath.internal.Arg;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.multi.ConstantExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.operator.Operator;

import java.util.Collections;
import java.util.List;

public class SimpleMonomial extends SimpleExpression implements SimpleMonomialable {

    private final SimpleConstable k;
    private final List<Argument> arguments;

    public SimpleMonomial(SimpleConstable k, Argument... arguments) {
        this.k = k;
        if (arguments.length != 0)
            this.arguments = Lists.newArrayList(arguments);
        else this.arguments = Lists.newArrayList(Argument.NULL_ARGUMENT);
    }

    @Override
    public @NonNull SimpleMonomialable simpleValue() {
        ConstantExpression constantExpression = new ConstantExpression(this.getK());
        List<Argument> arguments = Lists.newArrayList();
        for (Argument argument:this.getArguments()) {
            if (!argument.isUnknown()) {
                try {
                    constantExpression.append(argument.getValue());
                } catch (UnknownArgumentException e) {
                    e.printStackTrace();
                }
            }
            else arguments.add(argument);
        }
        if (arguments.size() == 0)
            return constantExpression.simplify();
        else
            return new SimpleMonomial(constantExpression.simplify(), arguments.toArray(new Argument[0]) );
    }

    @Override
    @NonNull
    public SimpleConstable getK() {
        return k;
    }

    @Override
    @NonNull
    public List<Argument> getArguments() {
        return arguments;
    }

    @Override
    public @NonNull SimpleMonomial reverse() {
        return new SimpleMonomial(this.getK().reverse(), this.arguments.toArray(new Argument[0]));
    }

    @Override
    public @NonNull List<Argument> getSameArguments() {
        return this.getArguments();
    }

    @Override
    @NonNull
    public SimpleMonomial removeSameArguments(List<Argument> arguments) {
        List<Argument> a = Lists.newArrayList(this.getArguments());
        a.removeAll(arguments);
        if (a.size() == 0)
            return new SimpleMonomial(this.getK(), Argument.NULL_ARGUMENT);
        else
            return new SimpleMonomial(this.getK(),a.toArray(new Argument[0]));
    }

    @Override
    public @NonNull SimpleMonomial clone() {
        return new SimpleMonomial(this.getK(),this.getArguments().toArray(new Argument[0]));
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
            return new SimplePolynomial(this, ((SimpleMonomialable) simpleExpression).reverse());
    }

    @Override
    public @NonNull SimpleExpression multiply(SimpleExpression simpleExpression) {
        if (simpleExpression instanceof SimplePolynomial) {
            SimpleMonomialable[] monomialables = new SimpleMonomialable[((SimplePolynomial) simpleExpression).getMonomials().size()];
            for (int i = 0; i < monomialables.length; i++)
                monomialables[i] = (SimpleMonomialable) Operator.MULTIPLY.operate(((SimplePolynomial) simpleExpression).getMonomials().get(i), this);
            return new SimplePolynomial(monomialables);
        } else {
            List<Argument> arguments = Lists.newArrayList(this.arguments);
            arguments.addAll(((SimpleMonomialable) simpleExpression).getArguments());
            return new SimpleMonomial(Operator.MULTIPLY.operate(this.getK(), ((SimpleMonomialable) simpleExpression).getK()),arguments.toArray(new Argument[0]));
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
    public @NonNull Simplifiable multiply(Simplifiable simplifiable) {
        return multiply((SimpleExpression) simplifiable);
    }

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        ConstantExpression constantExpression = new ConstantExpression(this.getK());
        for (Argument argument : this.getArguments())
            constantExpression.append(Operator.MULTIPLY, argument.getValue());
        return constantExpression;
    }

    @Override
    @NonNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getK().toString());
        for (Argument argument : this.getArguments()) {
            if (argument != Argument.NULL_ARGUMENT) {
                stringBuilder.append(" * ");
                stringBuilder.append(argument.toString());
            }
        }
        return stringBuilder.toString();
    }
}
