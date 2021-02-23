package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.argument.DerivativeArgument;
import top.focess.expressionmfc.exception.DividedByZeroException;
import top.focess.expressionmfc.exception.IllegalUnknownArgumentException;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.exception.UnknownArgumentNotFoundException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.SimpleDerivative;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantDouble;
import top.focess.expressionmfc.operator.Operator;
import top.focess.expressionmfc.util.MathHelper;

public class EquationImp extends Equation {

    public EquationImp(@NonNull IExpression left, @NonNull IExpression right) {
        super(left, right);
    }

    @Override
    public @NonNull SimpleEquation solve(Argument argument,Solution solution) throws UnknownArgumentNotFoundException, IllegalUnknownArgumentException {
        if (solution == Solution.NEWTON) {
            Simplifiable simplifiable = Operator.MINUS.operate(this.getLeft(), this.getRight()).simpleValue();
            Simplifiable derivative = new SimpleDerivative(simplifiable,argument).simplify();
            Simplifiable fraction = Operator.DIVIDED.operate(simplifiable,derivative);
            double x = 1;
            while (true) {
                argument.setValue(new SimpleConstantDouble(x));
                try {
                    if (MathHelper.abs(simplifiable.value().doubleValue()) < 1e-15)
                        break;
                    x = x - fraction.value().doubleValue();
                } catch (UnknownArgumentException e) {
                    throw new IllegalUnknownArgumentException(e.getUnknownArgument());
                } catch (DividedByZeroException e) {
                    x++;
                }
            }
            return new SimpleEquationImp(argument,new SimpleConstantDouble(x));
        }
        return null;
        //todo
    }

    @Override
    public @NonNull String toString() {
        return this.getLeft() + " = " + this.getRight();
    }
}
