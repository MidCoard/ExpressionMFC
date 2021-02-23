package top.focess.expressionmfc.equation.range;

import java.util.Arrays;

public class OrRange extends Range{

    private final Range[] ranges;

    public OrRange(Range...ranges) {
        this.ranges = ranges;
    }

    @Override
    public boolean test(double v) {
        return Arrays.stream(ranges).anyMatch(range -> range.test(v));
    }

    @Override
    public double getMin() {
        double ret = Double.MAX_VALUE;
        for (Range range:this.ranges)
            if (range.getMin() < ret)
                ret = range.getMin();
        return ret;
    }

    @Override
    public double getMax() {
        double ret = - Double.MAX_VALUE;
        for (Range range:this.ranges)
            if (range.getMax() > ret)
                ret = range.getMax();
        return ret;
    }
}
