package ru.ssau.tk.kintoho.LR.functions.factory;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);
}
