package top.focess.expressionmfc;

import top.focess.expressionmfc.argument.UnknownArgument;
import top.focess.expressionmfc.exception.DivideByZeroException;
import top.focess.expressionmfc.exception.InvalidExpressionException;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.multi.MultiExpression;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantDouble;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantLong;
import top.focess.expressionmfc.util.ExpressionParser;

public class ExpressionMFCLauncher {

    public static void main(String[] args) throws DivideByZeroException, UnknownArgumentException, InvalidExpressionException {
//        Argument argument = new Argument("a", SimpleConstantLong.ONE);
//        SimpleFraction s = (SimpleFraction) argument.multiply(new SimpleConstantLong(5)).divided(SimpleConstantLong.ZERO).reverse().plus(SimpleConstantLong.ONE);
//        System.out.println(s);
//        System.out.println(s.value().doubleValue());
//        argument.setValue(SimpleConstantLong.ZERO);
//        System.out.println(argument.multiply(new SimpleConstantLong(5)).value().doubleValue());
        ExpressionParser expressionParser = new ExpressionParser(" a *(c*((b+a+c)*d))/(a+b) + 1 - 2 * 3 + 100 + a*100/a");
        MultiExpression expression = expressionParser.getExpression();
        System.out.println(expression.simplify());
        UnknownArgument a = expressionParser.getArgument("a");
        UnknownArgument b = expressionParser.getArgument("b");
        UnknownArgument c = expressionParser.getArgument("c");
        UnknownArgument d = expressionParser.getArgument("d");
        a.setValue(SimpleConstantLong.ONE);
        b.setValue(SimpleConstantLong.ONE);
        c.setValue(SimpleConstantLong.ONE);
        d.setValue(SimpleConstantLong.ONE);
//        System.out.println(expression.value());
        System.out.println(expression.simplify());
        System.out.println(expression.simplify().value());
        System.out.println(expression.simplify().value().doubleValue());
        System.out.println(expression.value().doubleValue());
        d.setValue(new SimpleConstantDouble(200));
        System.out.println(expression.value().doubleValue());
        System.out.println(expressionParser.getArguments());

    }
}
