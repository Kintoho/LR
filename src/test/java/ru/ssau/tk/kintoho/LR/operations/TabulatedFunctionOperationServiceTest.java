package ru.ssau.tk.kintoho.LR.operations;

import static org.testng.Assert.*;

import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.functions.*;

public class TabulatedFunctionOperationServiceTest {
    private final static double DELTA = 0.001;
    private final static double[] xValues = new double[]{1., 2., 3.};
    private final static double[] yValues = new double[]{4., 5., 6.};
    private final static double[] yValuesAnother = new double[]{7., 8., 9.};
    private final static TabulatedFunction testFirstFunction = new ArrayTabulatedFunction(xValues, yValues);
    private final static TabulatedFunction testSecondFunction = new LinkedListTabulatedFunction(xValues, yValuesAnother);

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
}