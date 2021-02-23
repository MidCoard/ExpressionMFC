package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.expression.Constable;

public class SimpleEquationImp extends SimpleEquation{

    public SimpleEquationImp(Argument left, Constable right) {
        super(left, right);
    }

    @Override
    public @NonNull String toString() {
        return this.getLeft() + " = " + this.getRight();
    }
}
