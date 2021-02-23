package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.equation.range.Range;
import top.focess.expressionmfc.exception.*;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.SimpleDerivative;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantDouble;
import top.focess.expressionmfc.operator.Operator;
import top.focess.expressionmfc.util.MathHelper;

public class EquationImp extends Equation {

    public EquationImp(@NonNull IExpression left, @NonNull IExpression right) {
        super(left, right,EquationOperator.EQUAL);
    }



    @Override
    public @NonNull SimpleEquation solve(Argument argument, Solution solution, Range range) throws IllegalUnknownArgumentException, UnknownArgumentNotFoundException, NoSolutionException {
        if (solution == Solution.NEWTON) {
            Simplifiable simplifiable = Operator.MINUS.operate(this.getLeft(), this.getRight()).simpleValue();
            System.out.println(this.getLeft().simpleValue());
            try {
                if (!simplifiable.value().isZero())
                    throw new NoSolutionException();
            } catch (UnknownArgumentException ignored) {
            }
            Simplifiable derivative = new SimpleDerivative(simplifiable,argument).simplify();
            Simplifiable fraction = Operator.DIVIDED.operate(simplifiable,derivative);
            double x = range.getMin();
            while (true) {
                if (!range.test(x))
                    throw new NoSolutionException();
                argument.setValue(new SimpleConstantDouble(x));
                try {
                    if (MathHelper.abs(simplifiable.value().doubleValue()) < 1e-15)
                        break;
                    x = x - fraction.value().doubleValue();
                } catch (UnknownArgumentException e) {
                    throw new IllegalUnknownArgumentException(e.getUnknownArgument());
                } catch (DividedByZeroException e) {
                    x+=0.002;
                }
            }
            return new SimpleEquationImp(argument,new SimpleConstantDouble(x));
        }
        return null;
        //todo
    }
}
