package top.focess.expressionmfc.equation.range;

import java.util.Arrays;

public class AndRange extends Range{
    private final Range[] ranges;

    @Override
    public boolean test(double v) {
        return Arrays.stream(ranges).allMatch(range -> range.test(v));
    }

    @Override
    public double getMin() {
        double ret = - Double.MAX_VALUE;
        for (Range range:this.ranges)
            if (range.getMin() > ret)
                ret = range.getMin();
        return ret;
    }

    @Override
    public double getMax() {
        double ret = Double.MAX_VALUE;
        for (Range range:this.ranges)
            if (range.getMax() < ret)
                ret = range.getMax();
        return ret;
    }

    public AndRange(Range... ranges) {
        this.ranges = ranges;
    }
}
