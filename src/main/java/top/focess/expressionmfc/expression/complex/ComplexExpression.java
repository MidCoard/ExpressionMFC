package top.focess.expressionmfc.expression.complex;

import com.google.common.collect.Lists;
import top.focess.expressionmfc.expression.Expression;
import top.focess.expressionmfc.expression.IExpression;

import java.util.List;

public abstract class ComplexExpression extends Expression {

    private final List<IExpression> childExpressions;

    public ComplexExpression(IExpression... expressions) {
        this.childExpressions = Lists.newArrayList(expressions);
    }

    public IExpression getChildExpression(int pos) {
        return this.childExpressions.get(pos);
    }


}
