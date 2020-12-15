package ru.ssau.tk.kintoho.LR.functions;

import ru.ssau.tk.kintoho.LR.exceptions.DifferentLengthOfArraysException;
import ru.ssau.tk.kintoho.LR.exceptions.ArrayIsNotSortedException;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    protected abstract int floorIndexOfX(double x);

    protected abstract double extrapolateLeft(double x);

    protected abstract double extrapolateRight(double x);

    protected abstract double interpolate(double x, int floorIndex);

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return (leftY + (x - leftX) * (rightY - leftY) / (rightX - leftX));
    }

    public double apply(double x) {
        if (x < leftBound()) {
            return (extrapolateLeft(x));
        } else if (x > rightBound()) {
            return (extrapolateRight(x));
        } else if (x > leftBound() && x < rightBound() && indexOfX(x) != -1) {
            return getY(indexOfX(x));
        } else return (interpolate(x, floorIndexOfX(x)));
    }

    protected static void checkLengthIsTheSame(double[] xValues, double[] yValues) {
        int xV, yV;
        xV = xValues.length;
        yV = yValues.length;
        if (xV != yV) {
            throw new DifferentLengthOfArraysException("Lengths of xValues and yValues are different");
        }
    }

    protected static void checkSorted(double[] xValues) {
        int xV;
        xV = xValues.length;
        for (int i = 0; (i + 1) < xV; i++) {
            if (xValues[i] >= xValues[i + 1]) {
                throw new ArrayIsNotSortedException("Array is not sorted");
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName()).append(" ").append("Size = ").append(this.getCount());
        for (Point point : this) {
            stringBuilder.append("\n").append("[").append(point.x).append("; ").append(point.y).append("]");
        }
        return stringBuilder.toString();
    }
}
