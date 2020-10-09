package ru.ssau.tk.kintoho.LR.functions;

abstract class AbstractTabulatedFunction {
    protected abstract   int floorIndexOfX(double x);
    protected abstract double extrapolateLeft(double x);
    protected abstract double extrapolateRight(double x);
    protected abstract double interpolate(double x, int floorIndex);
}
