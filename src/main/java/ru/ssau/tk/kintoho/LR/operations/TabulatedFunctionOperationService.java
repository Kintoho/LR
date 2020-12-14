package ru.ssau.tk.kintoho.LR.operations;

import ru.ssau.tk.kintoho.LR.exceptions.InconsistentFunctionsException;
import ru.ssau.tk.kintoho.LR.functions.Point;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;

public class TabulatedFunctionOperationService {
    private TabulatedFunctionFactory factory;

    public TabulatedFunctionOperationService(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionOperationService() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public static Point[] asPoints(TabulatedFunction tabulatedFunction) {
        int i = 0;
        Point[] points = new Point[tabulatedFunction.getCount()];
        for (Point newPoint : tabulatedFunction) {
            points[i++] = newPoint;
        }
        return points;
    }

    private interface BiOperation {
        double apply(double u, double v);
    }

    private TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BiOperation operation) {
        if (a.getCount() != b.getCount()) {
            throw new InconsistentFunctionsException();
        }
        Point[] aPoints = asPoints(a);
        Point[] bPoints = asPoints(b);
        double[] xValues = new double[a.getCount()];
        double[] yValues = new double[b.getCount()];
        for (int i = 0; i < xValues.length; i++) {
            if (aPoints[i].x - bPoints[i].x != 0) {
                throw new InconsistentFunctionsException();
            }
            xValues[i] = aPoints[i].x;
            yValues[i] = operation.apply(aPoints[i].y, bPoints[i].y);
        }
        return factory.create(xValues, yValues);
    }

    public TabulatedFunction sum(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, ((u, v) -> u + v));
    }

    public TabulatedFunction subtraction(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, ((u, v) -> u - v));
    }

    public TabulatedFunction multiplication(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, ((u, v) -> u * v));
    }

    public TabulatedFunction division(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, ((u, v) -> u / v));
    }

}
