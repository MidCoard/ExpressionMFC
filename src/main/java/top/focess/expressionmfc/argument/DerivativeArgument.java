package top.focess.expressionmfc.argument;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.expression.Constable;

import java.util.Map;
import java.util.WeakHashMap;

public class DerivativeArgument extends Argument{

    private static final Object WEAK_OBJECT = new Object();

    private static final Map<DerivativeArgument,Object> arguments = new WeakHashMap<>();

    private final Argument argument;
    private final Argument derivativeArgument;

    private DerivativeArgument(@NonNull Argument argument, @NonNull Argument derivativeArgument) {
        super("(" + argument.getName() + ")'");
        this.argument = argument;
        this.derivativeArgument = derivativeArgument;
    }

    public static DerivativeArgument getOrDefault(Argument argument,Argument derivativeArgument) {
        for (DerivativeArgument arg : arguments.keySet())
            if (arg.getArgument() == argument && arg.getDerivativeArgument() == derivativeArgument)
                return arg;
        DerivativeArgument ret = new DerivativeArgument(argument,derivativeArgument);
        arguments.put(ret,WEAK_OBJECT);
        return ret;
    }


    public Argument getArgument() {
        return argument;
    }

    public Argument getDerivativeArgument() {
        return derivativeArgument;
    }
}
