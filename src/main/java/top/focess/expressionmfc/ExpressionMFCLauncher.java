package top.focess.expressionmfc;

import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.equation.Solution;
import top.focess.expressionmfc.exception.*;
import top.focess.expressionmfc.parser.EquationParser;

public class ExpressionMFCLauncher {

    public static void main(String[] args) throws DividedByZeroException, UnknownArgumentException, InvalidExpressionException, InvalidEquationException, UnknownArgumentNotFoundException, IllegalUnknownArgumentException {
//        Argument argument = new Argument("a", SimpleConstantLong.ONE);
//        SimpleFraction s = (SimpleFraction) argument.multiply(new SimpleConstantLong(5)).divided(SimpleConstantLong.ZERO).reverse().plus(SimpleConstantLong.ONE);
//        System.out.println(s);
//        System.out.println(s.value().doubleValue());
//        argument.setValue(SimpleConstantLong.ZERO);
//        System.out.println(argument.multiply(new SimpleConstantLong(5)).value().doubleValue());

//        ExpressionParser parser = new ExpressionParser("-1.0.0 + a");
//        ExpressionParser expressionParser = new ExpressionParser(" 1 - 2 * x");
//        Derivative derivative = new Derivative(expressionParser.getExpression().simplify(),Argument.getArgument("x"));
//        System.out.println(derivative.simplify());
        EquationParser parser1 = new EquationParser("1*x = x * x * 2");
        System.out.println(parser1.getEquation().solve(Argument.getArgument("x"), Solution.NEWTON).getAnswer());
//        IExpression expression = parser.getExpression();
//        System.out.println(parser1.getEquation());
//        Axis2Coordinate axis2Coordinate = new Axis2Coordinate();
//        axis2Coordinate.append(parser1.getEquation(), Argument.getArgument("x"),Argument.getArgument("y"));
//        axis2Coordinate.show();
//        SimpleMonomial simpleMonomial = new SimpleMonomial(SimpleConstantLong.ONE, Argument.getArgument("f"));
//        System.out.println(simpleMonomial);
//        System.out.println(simpleMonomial.simpleValue());
//        ExpressionParser expressionParser = new ExpressionParser(" a *(c*((b+a+c)*d))/(a+b) + 0.2 - 2 * 3 + 100 + a*100/a");
//        MultiExpression expression = expressionParser.getExpression();
//        System.out.println(expression);
//        Argument a = expressionParser.getArgument("a");
//        ExpressionParser parser2 = new ExpressionParser("-1*f/2");
////        SimpleFraction simpleFraction = (SimpleFraction) parser2.getExpression().simplify();
////        System.out.println(simpleFraction.getNumerator().getClass());
//        IExpression constantFraction = new Derivative(parser2.getExpression().simplify(), parser2.getArgument("f")).simplify();
//        System.out.println(constantFraction);
//        ExpressionParser parser = new ExpressionParser("1*f/g*g");
//        IExpression expression1 = parser.getExpression();
//        System.out.println(parser.getExpression());
//        Argument f = parser.getArgument("f");
//        System.out.println(f == parser2.getArgument("f"));
////        parser.getArgument("g").setValue(SimpleConstantLong.ONE);
//        SimpleFraction simpleFraction = (SimpleFraction) new Derivative(parser.getExpression(), parser2.getArgument("f")).simplify();
//        System.out.println(simpleFraction.getDenominator());
//        System.out.println(new Derivative(parser.getExpression(), parser2.getArgument("f")).simplify().value().doubleValue());
//        Argument b = expressionParser.getArgument("b");
//        Argument c = expressionParser.getArgument("c");
//        Argument d = expressionParser.getArgument("d");
//        a.setValue(SimpleConstantLong.ONE);
//        System.out.println(expression.simpleValue());
//        b.setValue(SimpleConstantLong.ONE);
//        System.out.println(expression.simpleValue());
//        c.setValue(SimpleConstantLong.ONE);
//        System.out.println(expression.simpleValue());
//        d.setValue(SimpleConstantLong.ONE);
//        System.out.println(expression.simpleValue());
//        System.out.println(expression.simplify());
//        System.out.println(expression.simplify().value());
//        System.out.println(expression.simplify().value().simplify());
//        System.out.println(expression.value().doubleValue());
//        d.setValue(new SimpleConstantDouble(200));
//        System.out.println(expression.value().doubleValue());
//        System.out.println(expressionParser.getArguments());

    }
}
