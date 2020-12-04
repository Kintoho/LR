package ru.ssau.tk.kintoho.LR.functions.factory;

import static org.testng.Assert.*;

import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.LinkedListTabulatedFunction;

public class LinkedListTabulatedFunctionFactoryTest {
    private final double[] xValues = new double[]{4., 8., 12.};
    private final double[] yValues = new double[]{1., 2., 3.};
    private final TabulatedFunctionFactory testList = new LinkedListTabulatedFunctionFactory();

    @Test
    public void testCreate() {
        TabulatedFunction testFunction = testList.create(xValues, yValues);
        assertTrue(testFunction instanceof LinkedListTabulatedFunction);
    }
}