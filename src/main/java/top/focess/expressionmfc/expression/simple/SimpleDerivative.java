package top.focess.expressionmfc.expression.simple;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.argument.DerivativeArgument;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.complex.Derivative;
import top.focess.expressionmfc.expression.complex.Fraction;
import top.focess.expressionmfc.expression.multi.MultiExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantLong;
import top.focess.expressionmfc.operator.Operator;

import java.util.List;

public class SimpleDerivative extends Derivative {

    public SimpleDerivative(Simplifiable simplifiable, Argument argument) {
        super(simplifiable, argument);
    }

    @NonNull
    private static SimplePolynomial differentiate(SimpleMonomialable simpleMonomialable, Argument argument) {
        List<SimpleMonomialable> simpleMonomialables = Lists.newArrayList();
        for (Argument arg : simpleMonomialable.getArguments()) {
            List<Argument> arguments = Lists.newArrayList(simpleMonomialable.getArguments());
            arguments.remove(arg);
            if (arg != argument)
                arguments.add(DerivativeArgument.getOrDefault(arg, argument));
            if (arguments.size() == 0)
                simpleMonomialables.add(simpleMonomialable.getK().clone());
            else
                simpleMonomialables.add(new SimpleMonomial(simpleMonomialable.getK(), arguments.toArray(new Argument[0])));
        }
        return new SimplePolynomial(simpleMonomialables.toArray(new SimpleMonomialable[0]));
    }

    @Override
    public @NonNull Simplifiable getOriginalExpression() {
        return (Simplifiable) super.getOriginalExpression();
    }

    @Override
    public @NonNull IExpression reverse() {
        return new SimpleDerivative(this.getOriginalExpression().reverse(), this.getArgument());
    }

    @Override
    public @NonNull IExpression clone() {
        return new SimpleDerivative(this.getOriginalExpression(), this.getArgument());
    }

    @Override
    public @NonNull Simplifiable simplify() {
        if (this.getOriginalExpression() instanceof SimpleConstable)
            return SimpleConstantLong.ZERO;
        else if (this.getOriginalExpression() instanceof SimpleFraction)
            return new Fraction(Operator.MINUS.operate(
                    Operator.MULTIPLY.operate(new Derivative(((SimpleFraction) this.getOriginalExpression()).getNumerator(), this.getArgument()), ((SimpleFraction) this.getOriginalExpression()).getDenominator()),
                    Operator.MULTIPLY.operate(((SimpleFraction) this.getOriginalExpression()).getNumerator(), new Derivative(((SimpleFraction) this.getOriginalExpression()).getDenominator(), this.getArgument()))
            ),
                    Operator.MULTIPLY.operate(((SimpleFraction) this.getOriginalExpression()).getDenominator(), ((SimpleFraction) this.getOriginalExpression()).getDenominator()))
                    .simplify();
        else if (this.getOriginalExpression() instanceof SimpleMonomialable)
            return differentiate((SimpleMonomialable) this.getOriginalExpression(), this.getArgument());
        else if (this.getOriginalExpression() instanceof SimplePolynomial) {
            MultiExpression multiExpression = new MultiExpression(((SimplePolynomial) this.getOriginalExpression()).getMonomials().get(0));
            for (int i = 1; i < ((SimplePolynomial) this.getOriginalExpression()).getMonomials().size(); i++)
                multiExpression.append(differentiate(((SimplePolynomial) this.getOriginalExpression()).getMonomials().get(i), this.getArgument()));
            return multiExpression.simplify();
        }
        return null;
    }

    @Override
    public @NonNull Simplifiable simpleValue() {
        return this.simplify().simpleValue();
    }

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        return this.simplify().value();
    }
}
