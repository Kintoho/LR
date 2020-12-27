package ru.ssau.tk.kintoho.LR.functions.factory;

import ru.ssau.tk.kintoho.LR.functions.MathFunction;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);
    TabulatedFunction create(MathFunction source, double xFrom, double xTo, int count);
}
