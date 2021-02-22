package top.focess.expressionmfc.equation;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.focess.expressionmfc.operator.Operator;

public interface Inequationable {

    @NonNull
    Operator getOperator();
}
