package com.transsion.framework.tango.core.handler;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.NotThreadSafe;
import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.core.data.LinePoints;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/9/6
 * @Version 1.0
 **/
@NotThreadSafe
public class RateExpressionHandler implements ExpressionHandler<LinePoints, LinePoints> {
    private LinePoints source;
    private LinePoints result;

    @Override
    public LinePoints handle() {
        if (source == null) {
            return null;
        }
        if (result == null) {
            result = doHandle(source);
        }
        return result;
    }

    @Override
    public void setSource(Identifier viewId, LinePoints source) {
        this.source = source;
    }

    private LinePoints doHandle(LinePoints source) {
        LinePoints linePoints = new LinePoints();
        linePoints.setId(source.getId());
        linePoints.setProperties(source.getProperties());

        if (Utility.isEmpty(source.getPoints())) {
            return linePoints;
        }
        LinePoints.Point prev = null;
        LinePoints.Point next = null;

        List<LinePoints.Point> rates = new ArrayList<>(source.getPoints().size());

        for (LinePoints.Point p : source.getPoints()) {
            if (prev == null) {
                prev = p;
                continue;
            } else {
                next = p;
                double h = next.getX() - prev.getX();
                double v = next.getY() - prev.getY();
                LinePoints.Point newP = new LinePoints.Point(
                        (prev.getX() + next.getX()) / 2, v / h);
                rates.add(newP);
            }
        }

        return linePoints;
    }
}
