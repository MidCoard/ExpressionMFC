package top.focess.expressionmfc.expression.simple;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.complex.Fraction;
import top.focess.expressionmfc.operator.Operator;
import top.focess.expressionmfc.util.Pair;

import java.util.List;

public class SimpleFraction extends Fraction implements Simplifiable {

    public SimpleFraction(SimpleExpression numerator, SimpleExpression denominator) {
        super(simplify(numerator, denominator));
    }

    private static Pair<SimpleExpression, SimpleExpression> simplify(SimpleExpression numerator, SimpleExpression denominator) {
        List<Argument> arguments = getSameArguments(numerator.getSameArguments(), denominator.getSameArguments());
        Simplifiable numerator0 = numerator.removeSameArguments(arguments);
        Simplifiable denominator0 = denominator.removeSameArguments(arguments);
        if (numerator0 instanceof SimpleFraction && denominator0 instanceof SimpleFraction)
            return Pair.of(Operator.MULTIPLY.operate(((SimpleFraction) numerator0).getNumerator(), ((SimpleFraction) denominator0).getDenominator()), Operator.MULTIPLY.operate(((SimpleFraction) numerator0).getDenominator(), ((SimpleFraction) denominator0).getNumerator()));
        else if (numerator0 instanceof SimpleFraction)
            return Pair.of(((SimpleFraction) numerator0).getNumerator(), Operator.MULTIPLY.operate(((SimpleFraction) numerator0).getDenominator(), (SimpleExpression) denominator0));
        else if (denominator0 instanceof SimpleFraction)
            return Pair.of(Operator.MULTIPLY.operate((SimpleExpression) numerator0, ((SimpleFraction) denominator0).getDenominator()), ((SimpleFraction) denominator0).getNumerator());
        else
            return Pair.of((SimpleExpression) numerator0, (SimpleExpression) denominator0);
    }

    private static List<Argument> getSameArguments(List<Argument> a, List<Argument> b) {
        List<Argument> arguments = Lists.newArrayList();
        List<Argument> tempB = Lists.newArrayList(b);
        for (Argument argument : a)
            if (tempB.contains(argument)) {
                arguments.add(argument);
                tempB.remove(argument);
            }
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
