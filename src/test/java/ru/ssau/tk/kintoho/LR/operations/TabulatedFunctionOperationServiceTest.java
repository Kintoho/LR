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
    private final TabulatedFunction testFirstFunction = new ArrayTabulatedFunction(xValues, yValues);
    private final TabulatedFunction testSecondFunction = new LinkedListTabulatedFunction(xValues, yValuesAnother);


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
        final TabulatedFunctionOperationService factory1 = new TabulatedFunctionOperationService();
        final TabulatedFunctionOperationService factory2 = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());

        factory1.setFactory(new LinkedListTabulatedFunctionFactory());
        factory2.setFactory(new ArrayTabulatedFunctionFactory());
        assertTrue(factory1.getFactory() instanceof LinkedListTabulatedFunctionFactory);
        assertTrue(factory2.getFactory() instanceof ArrayTabulatedFunctionFactory);
    }


    @Test
    public void testGetFactory() {
        final TabulatedFunctionOperationService factory1 = new TabulatedFunctionOperationService();
        assertTrue(factory1.getFactory() instanceof ArrayTabulatedFunctionFactory);
        final TabulatedFunctionOperationService factory2 = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());
        assertTrue(factory2.getFactory() instanceof LinkedListTabulatedFunctionFactory);
    }


    @Test
    public void testSum() {
        final TabulatedFunctionOperationService service1 = new TabulatedFunctionOperationService();
        final TabulatedFunctionOperationService service2 = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());
        final TabulatedFunctionOperationService service3 = new TabulatedFunctionOperationService();
        final TabulatedFunction a = new ArrayTabulatedFunctionFactory().create(xValues, yValues);
        final TabulatedFunction b = new LinkedListTabulatedFunctionFactory().create(xValues, yValuesAnother);
        final TabulatedFunction arraySum = service1.sum(a, b);
        final TabulatedFunction listSum = service2.sum(a, b);
        final TabulatedFunction arrayAndListSum = service3.sum(testFirstFunction, testSecondFunction);
        int i = 0;
        for (Point point : arraySum) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValues[i] + yValuesAnother[i++]);
        }
        int j = 0;
        for (Point point : listSum) {
            assertEquals(point.x, xValues[j]);
            assertEquals(point.y, yValues[j] + yValuesAnother[j++]);
        }
        int k = 0;
        for (Point point : arrayAndListSum) {
            assertEquals(point.x, xValues[k]);
            assertEquals(point.y, yValues[k] + yValuesAnother[k++]);
        }


        assertTrue(arraySum instanceof ArrayTabulatedFunction);
        assertTrue(listSum instanceof LinkedListTabulatedFunction);
        assertTrue(arrayAndListSum instanceof ArrayTabulatedFunction);
    }

    @Test
    public void testSubtraction() {
        final TabulatedFunctionOperationService service1 = new TabulatedFunctionOperationService();
        final TabulatedFunctionOperationService service2 = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());
        final TabulatedFunctionOperationService service3 = new TabulatedFunctionOperationService();
        final TabulatedFunction a = new ArrayTabulatedFunctionFactory().create(xValues, yValues);
        final TabulatedFunction b = new LinkedListTabulatedFunctionFactory().create(xValues, yValuesAnother);
        final TabulatedFunction arraySub = service1.subtraction(a, b);
        final TabulatedFunction listSub = service2.subtraction(a, b);
        final TabulatedFunction arrayAndListSub = service3.subtraction(testFirstFunction, testSecondFunction);
        int i = 0;
        for (Point point : arraySub) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValues[i] - yValuesAnother[i++]);
        }
        int j = 0;
        for (Point point : listSub) {
            assertEquals(point.x, xValues[j]);
            assertEquals(point.y, yValues[j] - yValuesAnother[j++]);
        }
        int k = 0;
        for (Point point : arrayAndListSub) {
            assertEquals(point.x, xValues[k]);
            assertEquals(point.y, yValues[k] - yValuesAnother[k++]);
        }


        assertTrue(arraySub instanceof ArrayTabulatedFunction);
        assertTrue(listSub instanceof LinkedListTabulatedFunction);
        assertTrue(arrayAndListSub instanceof ArrayTabulatedFunction);
    }

    @Test
    public void testMultiplication() {
        final TabulatedFunctionOperationService service1 = new TabulatedFunctionOperationService();
        final TabulatedFunctionOperationService service2 = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());
        final TabulatedFunctionOperationService service3 = new TabulatedFunctionOperationService();
        final TabulatedFunction a = new ArrayTabulatedFunctionFactory().create(xValues, yValues);
        final TabulatedFunction b = new LinkedListTabulatedFunctionFactory().create(xValues, yValuesAnother);
        final TabulatedFunction arrayMultiply = service1.multiplication(a, b);
        final TabulatedFunction listMultiply = service2.multiplication(a, b);
        final TabulatedFunction arrayAndListMultiply = service3.multiplication(testFirstFunction, testSecondFunction);
        int i = 0;
        for (Point point : arrayMultiply) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValues[i] * yValuesAnother[i++]);
        }
        int j = 0;
        for (Point point : listMultiply) {
            assertEquals(point.x, xValues[j]);
            assertEquals(point.y, yValues[j] * yValuesAnother[j++]);
        }
        int k = 0;
        for (Point point : arrayAndListMultiply) {
            assertEquals(point.x, xValues[k]);
            assertEquals(point.y, yValues[k] * yValuesAnother[k++]);
        }


        assertTrue(arrayMultiply instanceof ArrayTabulatedFunction);
        assertTrue(listMultiply instanceof LinkedListTabulatedFunction);
        assertTrue(arrayAndListMultiply instanceof ArrayTabulatedFunction);
    }

    @Test
    public void testDivision() {
        final TabulatedFunctionOperationService service1 = new TabulatedFunctionOperationService();
        final TabulatedFunctionOperationService service2 = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());
        final TabulatedFunctionOperationService service3 = new TabulatedFunctionOperationService();
        final TabulatedFunction a = new ArrayTabulatedFunctionFactory().create(xValues, yValues);
        final TabulatedFunction b = new LinkedListTabulatedFunctionFactory().create(xValues, yValuesAnother);
        final TabulatedFunction arrayDivide = service1.division(a, b);
        final TabulatedFunction listDivide = service2.division(a, b);
        final TabulatedFunction arrayAndListDivide = service3.division(testFirstFunction, testSecondFunction);
        int i = 0;
        for (Point point : arrayDivide) {
            assertEquals(point.x, xValues[i]);
            assertEquals(point.y, yValues[i] / yValuesAnother[i++]);
        }
        int j = 0;
        for (Point point : listDivide) {
            assertEquals(point.x, xValues[j]);
            assertEquals(point.y, yValues[j] / yValuesAnother[j++]);
        }
        int k = 0;
        for (Point point : arrayAndListDivide) {
            assertEquals(point.x, xValues[k]);
            assertEquals(point.y, yValues[k] / yValuesAnother[k++]);
        }


        assertTrue(arrayDivide instanceof ArrayTabulatedFunction);
        assertTrue(listDivide instanceof LinkedListTabulatedFunction);
        assertTrue(arrayAndListDivide instanceof ArrayTabulatedFunction);
    }
}