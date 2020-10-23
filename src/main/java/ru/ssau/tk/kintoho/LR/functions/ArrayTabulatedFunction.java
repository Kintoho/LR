package ru.ssau.tk.kintoho.LR.functions;

import java.util.Arrays;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction {
    private final double[] xValues;
    private final double[] yValues;
    private int count;

    private ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        count = xValues.length;
        this.xValues = Arrays.copyOf(xValues, count);
        this.yValues = Arrays.copyOf(yValues, count);
    }

    private ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count){
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
    public int getCount(){
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
        return xValues[count-1];
    }
}

