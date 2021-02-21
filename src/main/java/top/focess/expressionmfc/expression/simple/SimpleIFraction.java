package top.focess.expressionmfc.expression.simple;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.expression.IFraction;
import top.focess.expressionmfc.expression.Simplifiable;

public interface SimpleIFraction extends IFraction, Simplifiable {

    @NonNull SimpleExpression getNumerator();

    @NonNull SimpleExpression getDenominator();
}
