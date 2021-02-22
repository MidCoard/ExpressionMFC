package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.operator.Operator;

public abstract class SimpleInequation extends SimpleEquation implements Inequationable {
    private final Operator operator;

    public SimpleInequation(Argument left, Constable right, Operator operator) {
        super(left, right);
        this.operator = operator;
    }

    @NonNull
    @Override
    public Operator getOperator() {
        return operator;
    }
}
