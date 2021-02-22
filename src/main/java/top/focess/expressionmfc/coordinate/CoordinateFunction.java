package top.focess.expressionmfc.coordinate;

import com.google.common.collect.Lists;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.equation.EquationImp;

import java.awt.*;
import java.util.List;

public class CoordinateFunction {

    private final EquationImp equation;
    private final List<Argument> arguments;
    private final Color color;

    CoordinateFunction(EquationImp equation, Color color, Argument... unknownArguments) {
        this.equation = equation;
        this.color = color;
        this.arguments = Lists.newArrayList(unknownArguments);
    }

    public EquationImp getEquation() {
        return equation;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public Argument getArgument(int index) {
        return this.arguments.get(index);
    }

    public Color getColor() {
        return color;
    }
}
