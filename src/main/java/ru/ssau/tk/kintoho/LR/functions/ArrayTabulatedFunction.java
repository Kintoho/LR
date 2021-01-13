package ru.ssau.tk.kintoho.LR.functions;

import ru.ssau.tk.kintoho.LR.exceptions.InterpolationException;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Serializable, Insertable, Removable {
    @Serial
    private static final long serialVersionUID = -3461243627333514321L;
    private double[] xValues;
    private double[] yValues;
    private int count;

    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues.length < 2 & yValues.length < 2) {
            throw new IllegalArgumentException("List size less than 2");
        }
        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);
        count = xValues.length;
        this.xValues = Arrays.copyOf(xValues, count);
        this.yValues = Arrays.copyOf(yValues, count);
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("List size less than 2");
        }
        if (xFrom >= xTo) {
            throw new IllegalArgumentException("Invalid XFrom value");
        }
        this.count = count;
        xValues = new double[count];
        yValues = new double[count];
        double step = (xTo - xFrom) / (count - 1);
        double xMomentValue = xFrom;
        for (int i = 0; i < count; i++) {
            xValues[i] = xMomentValue;
            yValues[i] = source.apply(xMomentValue);
            xMomentValue += step;
        }
    }

    @Override
    public int getCount() {
        return (count);
    }

    @Override
    public double getX(int index) {
        return xValues[index];
    }

    @Override
    public double getY(int index) {
        return yValues[index];
    }

    @Override
    public void setY(int index, double value) {
        yValues[index] = value;
    }

    @Override
    public double leftBound() {
        return xValues[0];
    }

    @Override
    public double rightBound() {
        return xValues[count - 1];
    }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; i++) {
            if (xValues[i] == x) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < count; i++) {
            if (yValues[i] == y) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x < xValues[0]) {
            throw new IllegalArgumentException("Invalid X value");
        }
        for (int i = 0; i + 1 < count; i++) {
            if (xValues[i + 1] > x) {
                return i;
            }
        }
        return count;
    }

    @Override
    protected double extrapolateLeft(double x) {

        return interpolate(x, xValues[0], xValues[1], yValues[0], yValues[1]);
    }

    @Override
    protected double extrapolateRight(double x) {

        return interpolate(x, xValues[count - 2], xValues[count - 1], yValues[count - 2], yValues[count - 1]);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (x < xValues[floorIndex] || x > xValues[floorIndex + 1]) {
            throw new InterpolationException("X is not within the interpolation interval");
        }

        return interpolate(x, xValues[floorIndex], xValues[floorIndex + 1], yValues[floorIndex], yValues[floorIndex + 1]);
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < count;
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Point point = new Point(xValues[i], yValues[i]);
                i++;
                return point;
            }
        };
    }

    @Override
    public void insert(double x, double y) {
        int i = indexOfX(x);
        if (i != -1) {
            setY(i, y);
        } else {
            double[] xValuesAnother = new double[count + 1];
            double[] yValuesAnother = new double[count + 1];
            if (x < leftBound()) {
                xValuesAnother[0] = x;
                yValuesAnother[0] = y;
                System.arraycopy(xValues, 0, xValuesAnother, 1, count);
                System.arraycopy(yValues, 0, yValuesAnother, 1, count);
            } else {
                System.arraycopy(xValues, 0, xValuesAnother, 0, floorIndexOfX(x) + 1);
                System.arraycopy(yValues, 0, yValuesAnother, 0, floorIndexOfX(x) + 1);
                xValuesAnother[floorIndexOfX(x) + 1] = x;
                yValuesAnother[floorIndexOfX(x) + 1] = y;
                System.arraycopy(xValues, floorIndexOfX(x) + 1, xValuesAnother, floorIndexOfX(x) + 2, count - floorIndexOfX(x) - 1);
                System.arraycopy(yValues, floorIndexOfX(x) + 1, yValuesAnother, floorIndexOfX(x) + 2, count - floorIndexOfX(x) - 1);
            }
            this.xValues = xValuesAnother;
            this.yValues = yValuesAnother;
            count++;
        }
    }

    @Override
    public void remove(int index){
        if (count <= 2) {
            throw new IllegalArgumentException();
        }
        double[] xTempValues = new double[count - 1];
        double[] yTempValues = new double[count - 1];

        System.arraycopy(xValues, 0, xTempValues, 0, index);
        System.arraycopy(yValues, 0, yTempValues, 0, index);
        System.arraycopy(xValues, index + 1, xTempValues, index, count - index - 1);
        System.arraycopy(yValues, index + 1, yTempValues, index, count - index - 1);
        this.xValues = xTempValues;
        this.yValues = yTempValues;
        count--;
    }
}

