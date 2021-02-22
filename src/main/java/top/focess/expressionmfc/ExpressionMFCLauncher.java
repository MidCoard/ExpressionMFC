package top.focess.expressionmfc;

import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.DividedByZeroException;
import top.focess.expressionmfc.exception.InvalidExpressionException;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.IExpression;
import top.focess.expressionmfc.expression.complex.Derivative;
import top.focess.expressionmfc.expression.multi.MultiExpression;
import top.focess.expressionmfc.expression.simple.SimpleFraction;
import top.focess.expressionmfc.expression.simple.SimpleMonomial;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantDouble;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantLong;
import top.focess.expressionmfc.util.ExpressionParser;

public class ExpressionMFCLauncher {

    public static void main(String[] args) throws DividedByZeroException, UnknownArgumentException, InvalidExpressionException {
//        Argument argument = new Argument("a", SimpleConstantLong.ONE);
//        SimpleFraction s = (SimpleFraction) argument.multiply(new SimpleConstantLong(5)).divided(SimpleConstantLong.ZERO).reverse().plus(SimpleConstantLong.ONE);
//        System.out.println(s);
//        System.out.println(s.value().doubleValue());
//        argument.setValue(SimpleConstantLong.ZERO);
//        System.out.println(argument.multiply(new SimpleConstantLong(5)).value().doubleValue());

        SimpleMonomial simpleMonomial = new SimpleMonomial(SimpleConstantLong.ONE, Argument.getArgument("f"));
        System.out.println(simpleMonomial);
        System.out.println(simpleMonomial.simpleValue());
        ExpressionParser expressionParser = new ExpressionParser(" a *(c*((b+a+c)*d))/(a+b) + 1 - 2 * 3 + 100 + a*100/a");
        MultiExpression expression = expressionParser.getExpression();
        System.out.println(expression.simplify());
        Argument a = expressionParser.getArgument("a");
        ExpressionParser parser2 = new ExpressionParser("1*f/2");
//        SimpleFraction simpleFraction = (SimpleFraction) parser2.getExpression().simplify();
//        System.out.println(simpleFraction.getNumerator().getClass());
        IExpression constantFraction = new Derivative(parser2.getExpression().simplify(), parser2.getArgument("f")).simplify();
        System.out.println(constantFraction);
        ExpressionParser parser = new ExpressionParser("1*f/g*g");
        IExpression expression1 = parser.getExpression();
        System.out.println(parser.getExpression());
        Argument f = parser.getArgument("f");
        System.out.println(f == parser2.getArgument("f"));
//        parser.getArgument("g").setValue(SimpleConstantLong.ONE);
        SimpleFraction simpleFraction = (SimpleFraction) new Derivative(parser.getExpression(), parser2.getArgument("f")).simplify();
        System.out.println(simpleFraction.getDenominator());
        System.out.println(new Derivative(parser.getExpression(), parser2.getArgument("f")).simplify().value().doubleValue());
        Argument b = expressionParser.getArgument("b");
        Argument c = expressionParser.getArgument("c");
        Argument d = expressionParser.getArgument("d");
        a.setValue(SimpleConstantLong.ONE);
        b.setValue(SimpleConstantLong.ONE);
        c.setValue(SimpleConstantLong.ONE);
        d.setValue(SimpleConstantLong.ONE);
//        System.out.println(expression.value());
        System.out.println(expression.simplify());
        System.out.println(expression.simplify().value());
        System.out.println(expression.simplify().value().simplify());
        System.out.println(expression.value().doubleValue());
        d.setValue(new SimpleConstantDouble(200));
        System.out.println(expression.value().doubleValue());
        System.out.println(expressionParser.getArguments());

    }
}
