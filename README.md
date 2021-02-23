# ExpressionMFC
## Description
ExpressionMFC is used to parse expressions. Even complex calculation operations such as derivation can be realized.

## Usage
In ExpressionMFC, we divide expressions into multiple expressions, complex expressions, simple expressions, and constant expressions.

Derivative expressions and fractional expressions are complex expressions, simple polynomials and simple monomials are simple expressions, and fixed values are constant expressions.

Multiple expression is a particularly complex expression, let’s talk about it last.

Not much to say, let’s look at an example,

```
        Argument argument = Argument.X; // argument x;
        Fraction fraction = new Fraction(SimpleConstantLong.ONE,argument); // fraction x/1
        Derivative derivative = new Derivative(fraction,argument); // d(1/x)/dx 
        
        SimpleMonomial a = new SimpleMonomial(new SimpleConstantDouble(2.3),Argument.Y); // 2.3y
        SimpleMonomial b = new SimpleMonomial(new SimpleConstantFraction(new SimpleConstantLong(2),new SimpleConstantLong(3)),Argument.X); // 2/3*x
        SimplePolynomial simplePolynomial = new SimplePolynomial(a,b); // 2.3y + 2/3*x
        
```

Note that all expressions can directly print their content, because they all implement the toString method.

```
        System.out.println(derivative); // d(1 / x) / dx
        System.out.println(simplePolynomial); // 2 / 3 * x + 2.3 * y
```

For complex expressions, we only need to call the **simplify** method, which can simplify complex expressions to simple expressions.

```
        System.out.println(derivative.simplify()); // -1 / (1 * x * x)
```

At the same time, we also have a method to directly convert a string into an expression,
but at present, we have slightly strict requirements on the format of a string. The multiplication sign between numbers and letters cannot be omitted, which is similar to -x , 2x are illegal.

```
        ExpressionParser parser = new ExpressionParser("y/x");
        System.out.println(new Derivative(parser.getExpression(),parser.getArgument("x")).simplify()); // (1 * (y)' * x + -1 * y) / (1 * x * x)
```

Note that we cache the arguments, that is, no matter where you get a argument x from, they are the same.

```
        ExpressionParser parser = new ExpressionParser("y/x");
        System.out.println(Argument.X == Argument.getArgument("x")); // true
        System.out.println(Argument.X == parser.getArgument("x")); // true
```

In addition, all expressions can call the **value** method to get a constant expression (if the argument has a value, convert it to a value, if there is no value, throw an error),
you can also call the **simpleValue** method to obtain a special simplified expression (if the argument has a value, convert it to a value, if there is no value, it remains as it is).

```
        SimpleMonomial a = new SimpleMonomial(new SimpleConstantDouble(2.3),Argument.Y); // 2.3y
        SimpleMonomial b = new SimpleMonomial(new SimpleConstantFraction(new SimpleConstantLong(2),new SimpleConstantLong(3)),Argument.X); // 2/3*x
        SimplePolynomial simplePolynomial = new SimplePolynomial(a,b);// 2.3y + 2/3*x
        Argument.X.setValue(new SimpleConstantDouble(1));
        Argument.Y.setValue(new SimpleConstantDouble(1));
        System.out.println(simplePolynomial.value()); // (2 / 3 * 1.0) + (2.3 * 1.0)
        System.out.println(simplePolynomial.value().simplify()); // 8.899999999999999 / 3
        ExpressionParser parser = new ExpressionParser("y/x");
        System.out.println(new Derivative(parser.getExpression(),parser.getArgument("x")).simpleValue()); // (-1.0 + 1.0 * (y)') / 1.0
```

A constant expression can directly call the **doubleValue** method to get its double value.

```
        SimpleMonomial a = new SimpleMonomial(new SimpleConstantDouble(2.3),Argument.Y); // 2.3y
        SimpleMonomial b = new SimpleMonomial(new SimpleConstantFraction(new SimpleConstantLong(2),new SimpleConstantLong(3)),Argument.X); // 2/3*x
        SimplePolynomial simplePolynomial = new SimplePolynomial(a,b);// 2.3y + 2/3*x
        Argument.X.setValue(new SimpleConstantDouble(1));
        Argument.Y.setValue(new SimpleConstantDouble(1));
        System.out.println(simplePolynomial.value().doubleValue()); // 2.9666666666666663
```

From two expressions, we can introduce the concept of equations (currently only equation equations). Similarly, we have a method to directly convert equation strings into equations.

```
        EquationImp equation = new EquationImp(Argument.X,new SimpleMonomial(SimpleConstantLong.ONE,Argument.X,Argument.X));
        System.out.println(equation); // x = 1 * x * x
        EquationParser equationParser = new EquationParser("x*x=x");
        System.out.println(equationParser.getEquation()); // x * x = x
```

With the equation, we can solve it and call the **solve** method to solve the equation (currently only one argument equation can be solved by Newton's method, and only one solution can be solved).

```
        EquationParser equationParser = new EquationParser("x*x=x");
        System.out.println(equationParser.getEquation()); // x * x = x
        System.out.println(equationParser.getEquation().solve(Argument.X,Solution.NEWTON).getAnswer().doubleValue()); // -1.7045252056134216E-23 = 0
```

At the same time, we have also introduced a two-dimensional coordinate system so that you can display your plane function.

```
        EquationParser equationParser1 = new EquationParser("y=x + 1");
        EquationParser equationParser2 = new EquationParser("y=x*x");
        Axis2Coordinate axis2Coordinate = new Axis2Coordinate();
        axis2Coordinate.append(equationParser1.getEquation());
        axis2Coordinate.append(equationParser2.getEquation());
        axis2Coordinate.show();
```

Finally, let's look at multiple expressions, they are more like intermediate products. We can use them to compose formulas.

```
        Argument argument = Argument.X;// argument x;
        SimpleMonomial a = new SimpleMonomial(new SimpleConstantDouble(2.3),Argument.Y); // 2.3y
        SimpleMonomial b = new SimpleMonomial(new SimpleConstantFraction(new SimpleConstantLong(2),new SimpleConstantLong(3)),Argument.X); // 2/3*x
        SimplePolynomial simplePolynomial = new SimplePolynomial(a,b);// 2.3y + 2/3*x
        MultiExpression multiExpression = new MultiExpression(SimpleConstantLong.ONE);
        multiExpression.append(Operator.PLUS,argument);
        multiExpression.append(Operator.MULTIPLY,argument);
        multiExpression.append(Operator.PLUS,argument);
        multiExpression.append(Operator.MINUS,simplePolynomial);
        System.out.println(multiExpression); // 1 + x * x + x - (2 / 3 * x + 2.3 * y)
        System.out.println(multiExpression.simplify()); // 1 + 1 / 3 * x + 1 * x * x + -2.3 * y
```