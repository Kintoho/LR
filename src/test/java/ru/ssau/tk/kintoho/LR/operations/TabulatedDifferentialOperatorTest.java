package ru.ssau.tk.kintoho.LR.operations;

import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.functions.factory.*;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.ArrayTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.Point;


import static org.testng.Assert.*;

public class TabulatedDifferentialOperatorTest {
    private final static double DELTA = 0.001;
    private final static TabulatedDifferentialOperator operatorOne = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
    private final static TabulatedDifferentialOperator operatorTwo = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());


    private final static double[] xValues = new double[]{1., 3., 5., 7., 9.};
    private final static double[] yValues = new double[]{0., 2., 4., 6., 8.};
    private final static double[] yValuesDerive = new double[]{1., 1., 1., 1., 1.};


    @Test
    public void testGetFactory() {
        assertTrue(operatorOne.getFactory() instanceof LinkedListTabulatedFunctionFactory);
        assertTrue(operatorTwo.getFactory() instanceof ArrayTabulatedFunctionFactory);
    }

    @Test
    public void testSetFactory() {
        operatorOne.setFactory(new ArrayTabulatedFunctionFactory());
        operatorTwo.setFactory(new LinkedListTabulatedFunctionFactory());
        assertTrue(operatorOne.getFactory() instanceof ArrayTabulatedFunctionFactory);
        assertTrue(operatorTwo.getFactory() instanceof LinkedListTabulatedFunctionFactory);
    }

    @Test
    public void testDerive() {
        TabulatedFunction derivedListFunction = operatorOne.derive(
                (new ArrayTabulatedFunctionFactory().create(xValues, yValues)));
        assertTrue(derivedListFunction instanceof LinkedListTabulatedFunction);
        int i = 0;
        for (Point point : derivedListFunction) {
            assertEquals(point.y, yValuesDerive[i++], DELTA);
        }
        TabulatedFunction derivedArrayFunction = operatorTwo.derive(
                (new LinkedListTabulatedFunctionFactory().create(xValues, yValues)));
        assertTrue(derivedArrayFunction instanceof ArrayTabulatedFunction);
        int j = 0;
        for (Point point : derivedArrayFunction) {
            assertEquals(point.y, yValuesDerive[j++], DELTA);
        }

    }
}
