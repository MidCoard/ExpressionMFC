package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.operator.Operator;

public abstract class Inequation extends Equation implements Inequationable {
    private final Operator operator;

    public Inequation(@NonNull IExpression left, @NonNull IExpression right, @NonNull Operator operator) {
        super(left, right);
        this.operator = operator;
    }

    @NonNull
    @Override
    public Operator getOperator() {
        return operator;
    }
}
