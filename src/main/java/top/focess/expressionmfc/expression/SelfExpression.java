package top.focess.expressionmfc.expression;

public abstract class SelfExpression extends Expression{

    public SelfExpression() {
        this.expressions.add(this);
    }
}
