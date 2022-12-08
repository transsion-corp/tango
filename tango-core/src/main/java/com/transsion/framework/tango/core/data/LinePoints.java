package com.transsion.framework.tango.core.data;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/9/6
 * @Version 1.0
 **/
public class LinePoints extends CommonData {

    private List<Point> points;

    public LinePoints() {
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public static class Point {
        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}
