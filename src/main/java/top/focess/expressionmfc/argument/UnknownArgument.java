package top.focess.expressionmfc.argument;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;

public class UnknownArgument extends Argument {

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
            };
        }

        @Override
        public @NonNull Constable clone() {
            return this;
        }
    };
    private boolean unknown;

    public UnknownArgument(String name) {
        super(name, NULL_CONSTABLE);
        this.unknown = true;
    }

    @Override
    public boolean isUnknown() {
        return this.unknown;
    }

    @Override
    public @NonNull Constable getValue() throws UnknownArgumentException {
        if (this.unknown)
            throw new UnknownArgumentException(this);
        return super.getValue();
    }

    @Override
    public void setValue(@NonNull Constable value) {
        if (value == NULL_CONSTABLE)
            return;
        this.unknown = false;
        super.setValue(value);
    }

    @Override
    @NonNull
    public UnknownArgument clone() {
        UnknownArgument unknownArgument = new UnknownArgument(this.getName());
        try {
            unknownArgument.setValue(super.getValue());
        } catch (UnknownArgumentException e) {
            e.printStackTrace();
        }
        return unknownArgument;
    }
}
