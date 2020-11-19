package ru.ssau.tk.kintoho.LR.functions;

import java.util.Iterator;


public class MockTabulatedFunction extends AbstractTabulatedFunction implements Iterable<Point> {
    final double x0 = 3.;
    final double x1 = 6.;
    final double y0 = 4.;
    final double y1 = 8.;

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public double getX(int index) {
        if (index == 0) {
            return x0;
        } else {
            return x1;
        }
    }

    @Override
    public double getY(int index) {
        if (index == 0) {
            return y0;
        } else {
            return y1;
        }
    }

    @Override
    public void setY(int index, double value) {

    }

    @Override
    public int indexOfX(double x) {
        if (x == x0) {
            return 0;
        }
        if (x == x1) {
            return 1;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        if (y == y0) {
            return 0;
        }
        if (y == y1) {
            return 1;
        }
        return -1;
    }

    @Override
    public double leftBound() {
        return x0;
    }

    @Override
    public double rightBound() {
        return x1;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x < x0) {
            return 0;
        } else if (x >= x0 && x <= x1) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    protected double extrapolateLeft(double x) {
        return interpolate(x, x0, x1, y0, y1);
    }

    @Override
    protected double extrapolateRight(double x) {
        return interpolate(x, x0, x1, y0, y1);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        return interpolate(x, x0, x1, y0, y1);
    }

    @Override
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return super.interpolate(x, leftX, rightX, leftY, rightY);
    }

    @Override
    public double apply(double x) {
        return super.apply(x);

    }

    @Override
    public Iterator<Point> iterator() {
        throw new UnsupportedOperationException();
    }
}
