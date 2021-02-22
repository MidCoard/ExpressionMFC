package top.focess.expressionmfc.expression.simple;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.multi.ConstantExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantLong;
import top.focess.expressionmfc.operator.Operator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SimplePolynomial extends SimpleExpression {

    private final List<SimpleMonomialable> monomials;

    public SimplePolynomial(SimpleMonomialable... monomials) {
        if (monomials.length == 0)
            this.monomials = Collections.singletonList(SimpleConstantLong.ZERO);
        else {
            SimilarHashMap similarHashMap = new SimilarHashMap();
            for (SimpleMonomialable simpleMonomialable : monomials)
                similarHashMap.add(simpleMonomialable);
            this.monomials = similarHashMap.get();
        }
    }

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        ConstantExpression constantExpression = new ConstantExpression(false);
        constantExpression.addExpression(this.monomials.get(0).value());
        for (int i = 1; i < this.monomials.size(); i++)
            constantExpression.append(this.monomials.get(i).value());
        return constantExpression;
    }

    @Override
    public @NonNull SimplePolynomial clone() {
        return new SimplePolynomial(this.monomials.toArray(new SimpleMonomialable[0]));
    }

    @Override
    public @NonNull Simplifiable simpleValue() {
        SimpleMonomialable[] simpleMonomialables = new SimpleMonomialable[this.monomials.size()];
        for (int i = 0; i < simpleMonomialables.length; i++)
            simpleMonomialables[i] = this.getMonomials().get(i).simpleValue();
        return new SimplePolynomial(simpleMonomialables);
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
            SimpleMonomialable[] temp = new SimpleMonomialable[this.getMonomials().size() * ((SimplePolynomial) simpleExpression).getMonomials().size()];
            int pos = 0;
            for (SimpleMonomialable a : this.getMonomials())
                for (SimpleMonomialable b : ((SimplePolynomial) simpleExpression).getMonomials())
                    temp[pos++] = (SimpleMonomialable) Operator.MULTIPLY.operate(a, b);
            return new SimplePolynomial(temp);
        } else {
            SimpleMonomialable[] monomialables = new SimpleMonomialable[this.getMonomials().size()];
            for (int i = 0; i < monomialables.length; i++)
                monomialables[i] = (SimpleMonomialable) Operator.MULTIPLY.operate(this.getMonomials().get(i), simpleExpression);
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

    @Override
    public @NonNull List<Argument> getSameArguments() {
        List<Argument> ret = Lists.newArrayList();
        int size = Integer.MAX_VALUE;
        List<Argument> arguments = null;
        for (SimpleMonomialable simpleMonomialable : this.monomials)
            if (simpleMonomialable.getArguments().size() < size) {
                size = simpleMonomialable.getArguments().size();
                arguments = simpleMonomialable.getArguments();
            }
        List<List<Argument>> argumentsList = Lists.newArrayList();
        for (SimpleMonomialable simpleMonomialable : this.monomials)
            argumentsList.add(Lists.newArrayList(simpleMonomialable.getArguments()));
        for (Argument argument : Objects.requireNonNull(arguments)) {
            boolean flag = false;
            for (List<Argument> args : argumentsList)
                if (args.contains(argument))
                    args.remove(argument);
                else {
                    flag = true;
                    break;
                }
            if (!flag)
                ret.add(argument);
        }
        return ret;
    }

    @Override
    public @NonNull Simplifiable removeSameArguments(List<Argument> arguments) {
        SimpleMonomialable[] simpleMonomialables = new SimpleMonomialable[this.getMonomials().size()];
        for (int i = 0; i < simpleMonomialables.length; i++)
            simpleMonomialables[i] = this.getMonomials().get(i).removeSameArguments(arguments);
        return new SimplePolynomial(simpleMonomialables);
    }

    @Override
    public boolean isNeedBracket() {
        return this.monomials.size() != 1;
    }

    @Override
    @NonNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.monomials.size() - 1; i++) {
            stringBuilder.append(this.monomials.get(i));
            stringBuilder.append(" + ");
        }
        stringBuilder.append(this.monomials.get(this.monomials.size() - 1));
        return stringBuilder.toString();
    }

    private static class SimilarSimpleMonomialable {

        private final SimpleMonomialable monomialable;

        public SimilarSimpleMonomialable(SimpleMonomialable monomialable) {
            this.monomialable = monomialable;
        }

        public SimpleConstable getK() {
            return this.monomialable.getK();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof SimilarSimpleMonomialable && ((SimilarSimpleMonomialable) o).monomialable.getArguments().equals(this.monomialable.getArguments());
        }

        @Override
        public int hashCode() {
            return Objects.hash(monomialable.getArguments());
        }
    }

    private static class SimilarHashMap extends HashMap<SimilarSimpleMonomialable, SimpleConstable> {

        public void add(SimpleMonomialable monomialable) {
            SimilarSimpleMonomialable similarMonomialable = new SimilarSimpleMonomialable(monomialable);
            this.compute(similarMonomialable, (key, value) -> {
                if (value == null)
                    return similarMonomialable.getK();
                else return value.plus(similarMonomialable.getK());
            });
        }

        public List<SimpleMonomialable> get() {
            List<SimpleMonomialable> monomialables = Lists.newArrayList();
            for (SimilarSimpleMonomialable similarMonomialable : this.keySet()) {
                SimpleConstable constable;
                if (!(constable = this.get(similarMonomialable)).isZero())
                    if (similarMonomialable.monomialable.getArguments().size() == 0)
                        monomialables.add(constable.clone());
                    else
                        monomialables.add(new SimpleMonomial(constable, similarMonomialable.monomialable.getArguments().toArray(new Argument[0])));
            }
            if (monomialables.size() == 0)
                monomialables.add(SimpleConstantLong.ZERO);
            return monomialables;
        }
    }
}
