package top.focess.expressionmfc.expression.simple;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Constable;
import top.focess.expressionmfc.expression.Expression;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.constant.ConstantFraction;
import top.focess.expressionmfc.operator.Operator;

import java.util.List;

public class SimpleFraction extends Expression implements SimpleIFraction {

    private final SimpleExpression numerator;

    private final SimpleExpression denominator;

    public SimpleFraction(SimpleExpression numerator, SimpleExpression denominator) {
        List<Argument> arguments = getSameArguments(numerator.getSameArguments(), denominator.getSameArguments());
        this.numerator = numerator.removeSameArguments(arguments);
        this.denominator = denominator.removeSameArguments(arguments);
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
        return this.numerator;
    }

    @Override
    public @NonNull SimpleExpression getDenominator() {
        return this.denominator;
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
            if (simplifiable instanceof SimpleIFraction)
                return new SimpleFraction(Operator.PLUS.operate(Operator.MULTIPLY.operate(this.getNumerator(), ((SimpleIFraction) simplifiable).getDenominator()), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleIFraction) simplifiable).getNumerator())), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleIFraction) simplifiable).getDenominator()));
            //todo
        }
        return null;
    }

    @Override
    public @NonNull Simplifiable minus(Simplifiable simplifiable) {
        if (simplifiable instanceof SimpleExpression)
            return new SimpleFraction(Operator.MINUS.operate(this.getNumerator(), Operator.MULTIPLY.operate(this.getDenominator(), (SimpleExpression) simplifiable)), this.getDenominator());
        else {
            if (simplifiable instanceof SimpleIFraction)
                return new SimpleFraction(Operator.MINUS.operate(Operator.MULTIPLY.operate(this.getNumerator(), ((SimpleIFraction) simplifiable).getDenominator()), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleIFraction) simplifiable).getNumerator())), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleIFraction) simplifiable).getDenominator()));
            //todo
        }
        return null;
    }

    @Override
    public @NonNull Simplifiable divided(Simplifiable simplifiable) {
        if (simplifiable instanceof SimpleExpression)
            return new SimpleFraction(this.getNumerator(), Operator.MULTIPLY.operate(this.getDenominator(), (SimpleExpression) simplifiable));
        else {
            if (simplifiable instanceof SimpleIFraction)
                return new SimpleFraction(Operator.MULTIPLY.operate(this.getNumerator(), ((SimpleIFraction) simplifiable).getDenominator()), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleIFraction) simplifiable).getNumerator()));
            //todo
        }
        return null;
    }

    @Override
    public @NonNull Simplifiable multiply(Simplifiable simplifiable) {
        if (simplifiable instanceof SimpleExpression)
            return new SimpleFraction(Operator.MULTIPLY.operate(this.getNumerator(), (SimpleExpression) simplifiable), this.getDenominator());
        else {
            if (simplifiable instanceof SimpleIFraction)
                return new SimpleFraction(Operator.MULTIPLY.operate(this.getNumerator(), ((SimpleIFraction) simplifiable).getNumerator()), Operator.MULTIPLY.operate(this.getDenominator(), ((SimpleIFraction) simplifiable).getDenominator()));
            //todo
        }
        return null;
    }

    @Override
    public @NonNull Simplifiable reverse() {
        return new SimpleFraction(this.getNumerator().reverse(), this.getDenominator());
    }

    @Override
    public @NonNull Constable value() throws UnknownArgumentException {
        return new ConstantFraction(this.getNumerator().value(), this.getDenominator().value());
    }

    @Override
    @NonNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.getNumerator().isNeedBracket()) {
            stringBuilder.append('(');
        }
        stringBuilder.append(this.getNumerator().toString());
        if (this.getNumerator().isNeedBracket()) {
            stringBuilder.append(')');
        }
        stringBuilder.append('/');
        if (this.getDenominator().isNeedBracket()) {
            stringBuilder.append('(');
        }
        stringBuilder.append(this.getDenominator().toString());
        if (this.getDenominator().isNeedBracket()) {
            stringBuilder.append(')');
        }
        return stringBuilder.toString();
    }
}
