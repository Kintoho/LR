package ru.ssau.tk.kintoho.LR.operations;

import ru.ssau.tk.kintoho.LR.concurrent.SynchronizedTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.Point;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction> {
    private TabulatedFunctionFactory factory;

    public TabulatedDifferentialOperator() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedDifferentialOperator(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    @Override
    public TabulatedFunction derive(TabulatedFunction function) {

        Point[] points = TabulatedFunctionOperationService.asPoints(function);
        double[] xValues = new double[function.getCount()];
        double[] yValues = new double[function.getCount()];
        for (int i = 0; i < xValues.length; i++) {
            xValues[i] = points[i].x;
        }
        for (int i = 0; i < yValues.length - 1; i++) {
            yValues[i] = (points[i + 1].y - points[i].y) / (points[i + 1].x - points[i].x);
        }
        yValues[xValues.length - 1] = yValues[xValues.length - 2];
        return factory.create(xValues, yValues);
    }

    public TabulatedFunction deriveSynchronously(TabulatedFunction function){

        if (function instanceof SynchronizedTabulatedFunction) {
            return ((SynchronizedTabulatedFunction) function).doSynchronously(this::derive);
        }
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(function);
        return synchronizedTabulatedFunction.doSynchronously(this::derive);
    }
}
