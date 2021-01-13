package ru.ssau.tk.kintoho.LR.functions.factory;

import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.functions.*;

import static org.testng.Assert.assertTrue;

public class TabulatedFunctionFactoryTest {
    double[] xValues = new double[]{1., 2., 3., 4., 5.};
    double[] yValues = new double[]{6., 7., 8., 9., 10.};
    private final TabulatedFunctionFactory array = new ArrayTabulatedFunctionFactory();
    private final TabulatedFunctionFactory list = new LinkedListTabulatedFunctionFactory();

    @Test
    public void testCreate() {
        LinkedListTabulatedFunctionFactory testFirstFunction = new LinkedListTabulatedFunctionFactory();
        ArrayTabulatedFunctionFactory testSecondFunction = new ArrayTabulatedFunctionFactory();

        assertTrue(testFirstFunction.create(xValues, yValues) instanceof LinkedListTabulatedFunction);
        assertTrue(testSecondFunction.create(xValues, yValues) instanceof ArrayTabulatedFunction);
    }

    @Test
    public void testCreateStrict() {
        TabulatedFunction function = array.createStrict(xValues, yValues);
        assertTrue(function instanceof StrictTabulatedFunction);
        TabulatedFunction function1 = list.createStrict(xValues, yValues);
        assertTrue(function1 instanceof StrictTabulatedFunction);
    }
}