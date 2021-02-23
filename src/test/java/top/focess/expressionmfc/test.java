package top.focess.expressionmfc;

import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.coordinate.Axis2Coordinate;
import top.focess.expressionmfc.equation.EquationImp;
import top.focess.expressionmfc.equation.Solution;
import top.focess.expressionmfc.exception.*;
import top.focess.expressionmfc.expression.complex.Derivative;
import top.focess.expressionmfc.expression.complex.Fraction;
import top.focess.expressionmfc.expression.simple.SimpleMonomial;
import top.focess.expressionmfc.expression.simple.SimplePolynomial;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantDouble;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantFraction;
import top.focess.expressionmfc.expression.simple.constant.SimpleConstantLong;
import top.focess.expressionmfc.parser.EquationParser;
import top.focess.expressionmfc.parser.ExpressionParser;

public class test {

    public static void main(String[] args) throws DividedByZeroException, UnknownArgumentException, InvalidExpressionException, InvalidEquationException, IllegalUnknownArgumentException, UnknownArgumentNotFoundException, NoSolutionException {
        Argument argument = Argument.X;// argument x;
        Fraction fraction = new Fraction(SimpleConstantLong.ONE,argument); // fraction x/1
        Derivative derivative = new Derivative(fraction,argument); // d(1/x)/dx

        SimpleMonomial a = new SimpleMonomial(new SimpleConstantDouble(2.3),Argument.Y); // 2.3y
        SimpleMonomial b = new SimpleMonomial(new SimpleConstantFraction(new SimpleConstantLong(2),new SimpleConstantLong(3)),Argument.X); // 2/3*x
        SimplePolynomial simplePolynomial = new SimplePolynomial(a,b);// 2.3y + 2/3*x
        Argument.X.setValue(new SimpleConstantDouble(1));
        Argument.Y.setValue(new SimpleConstantDouble(1));
        System.out.println(simplePolynomial.value().doubleValue()); // 2.9666666666666663
        System.out.println(simplePolynomial.value().simplify().doubleValue()); // 8.899999999999999 / 3
        ExpressionParser parser = new ExpressionParser("y/x");
        System.out.println(new Derivative(parser.getExpression(),parser.getArgument("x")).simpleValue()); // (-1.0 + 1.0 * (y)') / 1.0


        System.out.println(simplePolynomial); // 2 / 3 * x + 2.3 * y

        SimpleConstantLong one = SimpleConstantLong.ONE;
        SimpleConstantLong two = new SimpleConstantLong(2);

        EquationImp equation = new EquationImp(Argument.X,new SimpleMonomial(SimpleConstantLong.ONE,Argument.X,Argument.X));
        System.out.println(equation); // x = 1 * x * x
        EquationParser equationParser = new EquationParser("x*x=x");
        System.out.println(equationParser.getEquation()); // x * x = x
        System.out.println(equationParser.getEquation().solve(Argument.X, Solution.NEWTON).getAnswer().doubleValue()); // -1.7045252056134216E-23 = 0

        EquationParser equationParser1 = new EquationParser("y=x + 1");
        EquationParser equationParser2 = new EquationParser("y=1/x");
        Axis2Coordinate axis2Coordinate = new Axis2Coordinate();
//        axis2Coordinate.append(equationParser1.getEquation());
        axis2Coordinate.append(equationParser2.getEquation());
        axis2Coordinate.show();
    }
}
