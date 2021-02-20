package top.focess.expressionmfc.expression;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface IFraction extends IExpression {

    @NonNull
    IExpression getNumerator();

    @NonNull
    IExpression getDenominator();
}
