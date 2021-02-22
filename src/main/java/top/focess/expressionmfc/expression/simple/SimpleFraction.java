package top.focess.expressionmfc.expression.simple;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Expression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.complex.Fraction;
import top.focess.expressionmfc.expression.constant.ConstantFraction;
import top.focess.expressionmfc.operator.Operator;
import top.focess.expressionmfc.util.Pair;

import java.util.List;

public class SimpleFraction extends Fraction implements Simplifiable {

    private static Pair<SimpleExpression,SimpleExpression> simplify(SimpleExpression numerator,SimpleExpression denominator) {
        List<Argument> arguments = getSameArguments(numerator.getSameArguments(), denominator.getSameArguments());
        return Pair.of(numerator.removeSameArguments(arguments),denominator.removeSameArguments(arguments));
    }

    public SimpleFraction(SimpleExpression numerator, SimpleExpression denominator) {
        super(simplify(numerator,denominator));
    }

    private static List<Argument> getSameArguments(List<Argument> a, List<Argument> b) {
        List<Argument> arguments = Lists.newArrayList();
        for (Argument argument : a)
            if (b.contains(argument))
                arguments.add(argument);
        return arguments;
    }

    @Override
    public @NonNull SimpleExpression getNumerator() {
        return (SimpleExpression) super.getNumerator();
    }

    @Override
    public @NonNull SimpleExpression getDenominator() {
        return (SimpleExpression) super.getDenominator();
    }

    @Override
    public @NonNull SimpleFraction clone() {
        return new SimpleFraction(this.getNumerator(), this.getDenominator());
    }

    @Override
    public @NonNull Simplifiable plus(Simplifiable simplifiable) {
        if (simplifiable instanceof SimpleExpression)
            return new SimpleFraction(Operator.PLUS.operate(this.getNumerator(), Operator.MULTIPLY.operate(this.getDenominator(), (SimpleExpression) simplifiable)), this.getDenominator());
        else {
            if (simplifiable instanceof SimpleFraction)
                return new SimpleFraction(Operator.PLUS.operate(Operator.MULTIPLY.operate(this.getNumerator(), ((SimpleFraction) simplifiable).getDenominator()), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleFraction) simplifiable).getNumerator())), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleFraction) simplifiable).getDenominator()));
            //todo
        }
        return null;
    }

    @Override
    public @NonNull Simplifiable minus(Simplifiable simplifiable) {
        if (simplifiable instanceof SimpleExpression)
            return new SimpleFraction(Operator.MINUS.operate(this.getNumerator(), Operator.MULTIPLY.operate(this.getDenominator(), (SimpleExpression) simplifiable)), this.getDenominator());
        else {
            if (simplifiable instanceof SimpleFraction)
                return new SimpleFraction(Operator.MINUS.operate(Operator.MULTIPLY.operate(this.getNumerator(), ((SimpleFraction) simplifiable).getDenominator()), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleFraction) simplifiable).getNumerator())), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleFraction) simplifiable).getDenominator()));
            //todo
        }
        return null;
    }

    @Override
    public @NonNull Simplifiable divided(Simplifiable simplifiable) {
        if (simplifiable instanceof SimpleExpression)
            return new SimpleFraction(this.getNumerator(), Operator.MULTIPLY.operate(this.getDenominator(), (SimpleExpression) simplifiable));
        else {
            if (simplifiable instanceof SimpleFraction)
                return new SimpleFraction(Operator.MULTIPLY.operate(this.getNumerator(), ((SimpleFraction) simplifiable).getDenominator()), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleFraction) simplifiable).getNumerator()));
            //todo
        }
        return null;
    }

    @Override
    public @NonNull Simplifiable multiply(Simplifiable simplifiable) {
        if (simplifiable instanceof SimpleExpression)
            return new SimpleFraction(Operator.MULTIPLY.operate(this.getNumerator(), (SimpleExpression) simplifiable), this.getDenominator());
        else {
            if (simplifiable instanceof SimpleFraction)
                return new SimpleFraction(Operator.MULTIPLY.operate(this.getNumerator(), ((SimpleFraction) simplifiable).getNumerator()), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleFraction) simplifiable).getDenominator()));
            //todo
        }
        return null;
    }

    @Override
    public @NonNull SimpleFraction reverse() {
        return new SimpleFraction(this.getNumerator().reverse(), this.getDenominator());
    }

}
