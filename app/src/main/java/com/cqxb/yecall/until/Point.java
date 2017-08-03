package com.cqxb.yecall.until;

/**
 * 创建时间: 2017/2/10.
 * 类 描 述: GPS定位开门的点
 */

public class Point {

    private double x;

    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
