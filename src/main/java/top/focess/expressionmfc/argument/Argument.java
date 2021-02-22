package top.focess.expressionmfc.argument;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.SimpleExpression;
import top.focess.expressionmfc.expression.simple.SimpleMonomial;
import top.focess.expressionmfc.expression.simple.SimpleMonomialable;
import top.focess.expressionmfc.expression.simple.SimplePolynomial;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantLong;
import top.focess.expressionmfc.operator.Operator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class Argument extends SimpleExpression implements SimpleMonomialable, Comparable<Argument> {

    private static final Object WEAK_OBJECT = new Object();

    private static final Map<Argument,Object> arguments = new WeakHashMap<>();


    private static final Constable NULL_CONSTABLE = new Constable() {
        @Override
        public double doubleValue() {
            return 0;
        }

        @Override
        public boolean isZero() {
            return true;
        }

        @Override
        public @NonNull Constable reverse() {
            return this;
        }

        @Override
        public @NonNull SimpleConstable simplify() {
            return new SimpleConstable() {
                @Override
                public @NonNull SimpleConstable clone() {
                    return this;
                }

                @Override
                public @NonNull SimpleConstable reverse() {
                    return this;
                }

                @Override
                public @NonNull SimpleConstable plus(SimpleConstable simpleConstable) {
                    return this;
                }

                @Override
                public @NonNull SimpleConstable minus(SimpleConstable simpleConstable) {
                    return this;
                }

                @Override
                public @NonNull SimpleConstable multiply(SimpleConstable simpleConstable) {
                    return this;
                }

                @Override
                public @NonNull Simplifiable plus(Simplifiable simplifiable) {
                    return this;
                }

                @Override
                public @NonNull Simplifiable minus(Simplifiable simplifiable) {
                    return this;
                }

                @Override
                public @NonNull Simplifiable divided(Simplifiable simplifiable) {
                    return this;
                }

                @Override
                public @NonNull Simplifiable multiply(Simplifiable simplifiable) {
                    return this;
                }

                @Override
                public double doubleValue() {
                    return 0;
                }

                @Override
                public boolean isZero() {
                    return false;
                }

                @Override
                @NonNull
                public String toString() {
                    return "ERROR";
                }
            };
        }

        @Override
        public @NonNull Simplifiable simpleValue() {
            return this.simplify().simpleValue();
        }

        @Override
        public @NonNull Constable clone() {
            return this;
        }

        @Override
        @NonNull
        public String toString() {
            return "ERROR";
        }
    };

    public static final Argument NULL_ARGUMENT = new Argument("", SimpleConstantLong.ONE) {
        @Override
        public @NonNull String toString() {
            return "1";
        }
    };
    private final String name;
    private boolean unknown;

    private Constable value;

    private Argument(@NonNull String name, @NonNull Constable value) {
        this(name,value,false);
    }

    private Argument(@NonNull String name, @NonNull Constable value,boolean unknown){
        this.name = name;
        this.value = value;
        this.unknown = unknown;
    }

    Argument(@NonNull String name) {
        this(name,NULL_CONSTABLE,true);
    }

    @NonNull
    public String getName() {
        return name;
    }

    public boolean isUnknown() {
        return this.unknown;
    }

    @NonNull
    public Constable getValue() throws UnknownArgumentException {
        if (this.isUnknown())
            throw new UnknownArgumentException(this);
        return value;
    }

    public Argument setValue(@NonNull Constable value) {
        this.value = value;
        this.unknown = false;
        return this;
    }

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        return this.getValue().clone();
    }

    @Override
    public @NonNull Argument clone() {
        return new Argument(this.name, this.value,this.unknown);
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
            SimpleMonomialable[] monomialables = new SimpleMonomialable[((SimplePolynomial) simpleExpression).getMonomials().size()];
            for (int i = 0; i < monomialables.length; i++)
                monomialables[i] = (SimpleMonomialable) Operator.MULTIPLY.operate(((SimplePolynomial) simpleExpression).getMonomials().get(i), new SimpleMonomial(SimpleConstantLong.ONE, this));
            return new SimplePolynomial(monomialables);
        } else {
            List<Argument> arguments = Lists.newArrayList(this);
            arguments.addAll(((SimpleMonomialable) simpleExpression).getArguments());
            return new SimpleMonomial(Operator.MULTIPLY.operate(this.getK(), ((SimpleMonomialable) simpleExpression).getK()), arguments.toArray(new Argument[0]));
        }
    }

    @Override
    public @NonNull Simplifiable divided(Simplifiable simplifiable) {
        return new SimpleMonomial(SimpleConstantLong.ONE, this).divided(simplifiable);
    }

    @Override
    public @NonNull SimpleConstable getK() {
        return SimpleConstantLong.ONE;
    }

    @Override
    public @NonNull SimpleMonomialable simpleValue() {
        return this.isUnknown() ? this.clone():this.value.simplify();
    }

    @Override
    public @NonNull List<Argument> getArguments() {
        return Collections.singletonList(this);
    }

    @Override
    public @NonNull SimpleMonomial reverse() {
        return new SimpleMonomial(SimpleConstantLong.ONE.reverse(), this);
    }

    @Override
    public @NonNull List<Argument> getSameArguments() {
        return Collections.singletonList(this);
    }

    @Override
    public @NonNull Argument removeSameArguments(List<Argument> arguments) {
        if (arguments.contains(this))
            return NULL_ARGUMENT;
        else return this.clone();
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int compareTo(Argument o) {
        return this.getName().compareTo(o.getName());
    }

    public void setUnknown() {
        this.unknown = true;
        this.value = NULL_CONSTABLE;
    }

    public static Argument getArgument(String name) {
        for (Argument argument:arguments.keySet())
            if (name.equals(argument.getName()))
                return argument;
        Argument argument = new Argument(name);
        arguments.put(argument,WEAK_OBJECT);
        return argument;
    }

    public static Argument getAndSetArgument(String name,Constable value) {
        return getArgument(name).setValue(value);
    }
}
