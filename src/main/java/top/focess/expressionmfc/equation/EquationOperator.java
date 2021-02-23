package top.focess.expressionmfc.equation;

import com.google.common.collect.Lists;

import java.util.List;

public enum EquationOperator {
    EQUAL("="),GREATER(">"),LESS("<"),GREATER_EQUAL(">=",GREATER,EQUAL),LESS_EQUAL("<=",LESS,EQUAL);

    private final String name;
    private final List<EquationOperator> childOperators;

    private EquationOperator(String name, EquationOperator... childOperators) {
        this.name = name;
        this.childOperators = Lists.newArrayList(childOperators);
    }

    public boolean test(double a,double b) {
        if (this.childOperators.size() == 0)
            switch (this) {
                case EQUAL: return a == b;
                case GREATER: return a > b;
                case LESS: return a < b;
            }
        else for (EquationOperator operator:this.childOperators)
            if (operator.test(a,b))
                return true;
        return false;
    }

    public boolean isInequationOperator(){
        return this != EQUAL;
    }

    public String getName() {
        return name;
    }

    public boolean isGreater() {
        return this == GREATER || this.childOperators.contains(GREATER);
    }

    public boolean isLess() {
        return this == LESS || this.childOperators.contains(LESS);
    }
}
