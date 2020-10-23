package ru.ssau.tk.kintoho.LR.functions;

abstract class AbstractTabulatedFunction implements TabulatedFunction {
    protected int floorIndexOfX(double x) {
        return 1;//TODO: Исправить
    }

    protected double extrapolateLeft(double x) {
        return 1;
    }

    protected double extrapolateRight(double x) {
        return 1;
    }

    protected double interpolate(double x, int floorIndex) {
        return 1;
    }

    public double apply(double x) {
        if (x < leftBound()) {
            return (extrapolateLeft(x));
        } else if (x > rightBound()) {
            return (extrapolateRight(x));
        } else if (x > leftBound() && x < rightBound()) {
            return (indexOfX(x));
        }


    }
}

