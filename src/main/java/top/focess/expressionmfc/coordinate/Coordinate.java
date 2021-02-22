package top.focess.expressionmfc.coordinate;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class Coordinate {
    private final List<CoordinateFunction> functions = Lists.newArrayList();

    protected CoordinateFunction add(CoordinateFunction coordinateFunction) {
        this.functions.add(coordinateFunction);
        return coordinateFunction;
    }

    protected void remove(CoordinateFunction coordinateFunction) {
        this.functions.remove(coordinateFunction);
    }

    public abstract void show();

    protected List<CoordinateFunction> getFunctions() {
        return this.functions;
    }

}
