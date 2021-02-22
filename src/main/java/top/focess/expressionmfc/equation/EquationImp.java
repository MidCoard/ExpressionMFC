package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.IllegalUnknownArgumentException;
import top.focess.expressionmfc.exception.UnknownArgumentNotFoundException;
import top.focess.expressionmfc.expression.IExpression;

public class EquationImp extends Equation {

    public EquationImp(@NonNull IExpression left, @NonNull IExpression right) {
        super(left, right);
    }

    @Override
    public @NonNull SimpleEquation solve(Argument argument) throws UnknownArgumentNotFoundException, IllegalUnknownArgumentException {
        return null;
        //todo
    }
}
