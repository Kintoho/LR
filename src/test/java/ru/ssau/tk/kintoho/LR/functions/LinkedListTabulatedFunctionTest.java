package ru.ssau.tk.kintoho.LR.functions;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

public class LinkedListTabulatedFunctionTest {
    private final static double DELTA = 0.001;
    private final double[] valuesX = new double[]{1., 2., 3., 4., 5., 6., 7.};
    private final double[] valuesY = new double[]{1., 4., 9., 16., 25., 36., 49.};

    private final MathFunction sqr = new SqrFunction();

    private LinkedListTabulatedFunction kX() {
        return new LinkedListTabulatedFunction(valuesX, valuesY);
    }

    private LinkedListTabulatedFunction sqr() {

        return new LinkedListTabulatedFunction(sqr, 1., 7., 7);
    }

    @Test
    public void testGetCount() {
        assertEquals(sqr().getCount(), 7, DELTA);
        assertEquals(kX().getCount(), 7, DELTA);
    }

    @Test
    public void testGetX() {
        assertEquals(kX().getX(3), 3., DELTA);
        assertEquals(sqr().getX(6), 6., DELTA);
    }

    @Test
    public void testGetY() {
        assertEquals(kX().getY(6), 36., DELTA);
        assertEquals(sqr().getY(2), 4., DELTA);
    }

    @Test
    public void testSetY() {
        LinkedListTabulatedFunction testKX = new LinkedListTabulatedFunction(valuesX, valuesY);
        LinkedListTabulatedFunction testSqr = new LinkedListTabulatedFunction(sqr, 1., 7., 7);
        testKX.setY(1, 14.);
        testSqr.setY(1, 14.);
        assertEquals(testKX.getY(1), 42., DELTA);
        assertEquals(testSqr.getY(1), 42., DELTA);
    }

    @Test
    public void testIndexOfX() {
        assertEquals(kX().indexOfX(2), 2, DELTA);
        assertEquals(sqr().indexOfX(6), 6, DELTA);
        assertEquals(kX().indexOfX(1234), -1, DELTA);
        assertEquals(sqr().indexOfX(245), -1, DELTA);
    }

    @Test
    public void testIndexOfY() {
        assertEquals(kX().indexOfY(9), 3, DELTA);
        assertEquals(sqr().indexOfY(9), 3, DELTA);
        assertEquals(kX().indexOfY(1234), -1, DELTA);
        assertEquals(sqr().indexOfY(245), -1, DELTA);
    }

    @Test
    public void testLeftBound() {
        assertEquals(kX().leftBound(), 1., DELTA);
        assertEquals(sqr().leftBound(), 1., DELTA);
    }

    @Test
    public void testRightBound() {
        assertEquals(kX().rightBound(), 7., DELTA);
        assertEquals(sqr().rightBound(), 7., DELTA);
    }

    @Test
    public void testFloorIndexOfX() {
        assertEquals(kX().floorIndexOfX(4.3), 3, DELTA);
        assertEquals(sqr().floorIndexOfX(25), 4, DELTA);
        assertEquals(kX().floorIndexOfX(-1.), 0, DELTA);
    }

    @Test
    public void testExtrapolateLeft() {
        assertEquals(kX().interpolate(2., 2), 4, DELTA);
        assertEquals(sqr().interpolate(4., 2), 14., DELTA);
        assertEquals(kX().interpolate(0., 0), 0., DELTA);
    }

    @Test
    public void testExtrapolateRight() {

    }

    @Test
    public void testInterpolate() {

    }

    @Test
    public void testApply() {

    }
}