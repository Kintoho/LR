package ru.ssau.tk.kintoho.LR.functions.factory;

import ru.ssau.tk.kintoho.LR.functions.StrictTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.MathFunction;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);

    TabulatedFunction create(MathFunction source, double xFrom, double xTo, int count);

    default TabulatedFunction createStrict(double[] xValues, double[] yValues) {
        return new StrictTabulatedFunction(create(xValues, yValues));
    }
}
