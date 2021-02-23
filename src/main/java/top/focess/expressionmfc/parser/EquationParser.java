package top.focess.expressionmfc.parser;

import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.argument.Argument;
import top.focess.expressionmfc.equation.EquationImp;
import top.focess.expressionmfc.exception.InvalidEquationException;
import top.focess.expressionmfc.exception.InvalidExpressionException;

import java.util.List;

public class EquationParser {

    private final String equationStr;
    private EquationImp equation;
    private final List<Argument> arguments;

    public EquationParser(String equationStr) throws InvalidExpressionException, InvalidEquationException {
        this.equationStr = equationStr;
        this.arguments = Lists.newArrayList();
        this.parse();
    }

    @NonNull
    public List<Argument> getArguments() {
        return arguments;
    }

    public Argument getArgument(String name) {
        for (Argument argument : this.arguments)
            if (argument.getName().equals(name))
                return argument;
        return null;
    }

    private void parse() throws InvalidEquationException, InvalidExpressionException {
        int pos = this.equationStr.indexOf('=');
        if (pos == -1 || this.equationStr.lastIndexOf('=') != pos)
            throw new InvalidEquationException(this.equationStr);
        String left = this.equationStr.substring(0,pos);
        String right = this.equationStr.substring(pos+1);
        ExpressionParser leftParser = new ExpressionParser(left);
        if (!leftParser.getExpression().checkSize())
            throw new InvalidEquationException(left);
        this.arguments.addAll(leftParser.getArguments());
        ExpressionParser rightParser = new ExpressionParser(right);
        if (!rightParser.getExpression().checkSize())
            throw new InvalidEquationException(right);
        this.arguments.addAll(rightParser.getArguments());
        this.equation = new EquationImp(leftParser.getExpression(),rightParser.getExpression());
    }

    public EquationImp getEquation() {
        return equation;
    }
}
