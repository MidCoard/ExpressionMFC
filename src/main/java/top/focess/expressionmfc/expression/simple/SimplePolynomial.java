package top.focess.expressionmfc.expression.simple;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Monomialable;
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
            for (SimpleMonomialable simpleMonomialable:monomials)
                similarHashMap.add(simpleMonomialable);
            this.monomials = similarHashMap.get();
        }

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
    public boolean isNeedBracket() {
        return this.monomials.size() != 1;
    }

    @Override
    @NonNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i<this.monomials.size() - 1;i++) {
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

    private static class SimilarHashMap extends HashMap<SimilarSimpleMonomialable,SimpleConstable> {

        public void add(SimpleMonomialable monomialable) {
            SimilarSimpleMonomialable similarMonomialable = new SimilarSimpleMonomialable(monomialable);
            this.compute(similarMonomialable,(key,value)->{
                if (value == null)
                    return similarMonomialable.getK();
                else return value.plus(similarMonomialable.getK());
            });
        }

        public List<SimpleMonomialable> get() {
            List<SimpleMonomialable> monomialables = Lists.newArrayList();
            for (SimilarSimpleMonomialable similarMonomialable:this.keySet()) {
                SimpleConstable constable;
                if (!(constable = this.get(similarMonomialable)).isZero())
                    monomialables.add(new SimpleMonomial(constable,similarMonomialable.monomialable.getFirst(),similarMonomialable.monomialable.getLast().toArray(new Argument[0])));
            }
            if (monomialables.size() == 0)
                monomialables.add(SimpleConstantLong.ZERO);
            return monomialables;
        }
    }
}
