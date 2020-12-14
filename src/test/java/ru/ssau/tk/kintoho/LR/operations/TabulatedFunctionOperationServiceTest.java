package ru.ssau.tk.kintoho.LR.operations;

import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.functions.ArrayTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.Point;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.functions.factory.LinkedListTabulatedFunctionFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TabulatedFunctionOperationServiceTest {
    private final static double DELTA = 0.001;
    private final static double[] xValues = new double[]{1., 2., 3.};
    private final static double[] yValues = new double[]{4., 5., 6.};
    private final static double[] yValuesAnother = new double[]{7., 8., 9.};
    TabulatedFunction testFirstFunction = new ArrayTabulatedFunction(xValues, yValues);
    TabulatedFunction testSecondFunction = new LinkedListTabulatedFunction(xValues, yValuesAnother);

    @Test
    public void testAsPoints() {
        final Point[] pointsOne = TabulatedFunctionOperationService.asPoints(testFirstFunction);
        final Point[] pointsTwo = TabulatedFunctionOperationService.asPoints(testSecondFunction);
        for (int i = 0; i < xValues.length; i++) {
            assertEquals(pointsOne[i].x, testFirstFunction.getX(i), DELTA);
            assertEquals(pointsOne[i].y, testFirstFunction.getY(i), DELTA);
        }

        for (int i = 0; i < xValues.length; i++) {
            assertEquals(pointsTwo[i].x, testSecondFunction.getX(i), DELTA);
            assertEquals(pointsTwo[i].y, testSecondFunction.getY(i), DELTA);
        }
    }

    @Test
    public void testSetFactory() {

    }


    @Test
    public void testGetFactory() {
        TabulatedFunctionOperationService factory1  = new TabulatedFunctionOperationService(new ArrayTabulatedFunctionFactory());
        assertTrue(factory1.getFactory() instanceof ArrayTabulatedFunctionFactory);
        TabulatedFunctionOperationService factory2  = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());
        assertTrue(factory2.getFactory() instanceof LinkedListTabulatedFunctionFactory);
    }

    @Test
    public void testDoOperation(){

    }

    @Test
    public void testSum(){

    }

    @Test
    public void testSubtraction(){

    }

    @Test
    public void testMultiplication(){

    }

    @Test
    public void testDivision(){

    }
}