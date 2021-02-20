package top.focess.expressionmfc;

import org.checkerframework.checker.units.qual.A;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.exception.DivideByZeroException;
import top.focess.expressionmfc.exception.UnknownArgumentException;
import top.focess.expressionmfc.expression.Simplifiable;
import top.focess.expressionmfc.expression.simple.SimpleFraction;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstable;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantDouble;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantLong;

public class ExpressionMFCLauncher {

    public static void main(String[] args) throws DivideByZeroException, UnknownArgumentException {
        Argument argument = new Argument("a", SimpleConstantLong.ONE);
        SimpleFraction s = (SimpleFraction) argument.multiply(new SimpleConstantLong(5)).divided(argument).reverse().plus(SimpleConstantLong.ONE);
        System.out.println(s.getNumerator().value().doubleValue());
        System.out.println(argument.multiply(new SimpleConstantLong(5)).divided(argument).reverse().plus(SimpleConstantLong.ONE).value().doubleValue());
        argument.setValue(SimpleConstantLong.ZERO);
        System.out.println(argument.multiply(new SimpleConstantLong(5)).value().doubleValue());
    }
}
