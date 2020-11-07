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
        assertEquals(kX().getX(3), 4., DELTA);
        assertEquals(sqr().getX(6), 7., DELTA);
    }

    @Test
    public void testGetY() {
        assertEquals(kX().getY(5), 36., DELTA);
        assertEquals(sqr().getY(1), 4., DELTA);
    }

    @Test
    public void testSetY() {
        LinkedListTabulatedFunction testKX = new LinkedListTabulatedFunction(valuesX, valuesY);
        LinkedListTabulatedFunction testSqr = new LinkedListTabulatedFunction(sqr, 1., 7., 7);
        testKX.setY(1, 14.);
        testSqr.setY(1, 14.);
        assertEquals(testKX.getY(1), 14., DELTA);
        assertEquals(testSqr.getY(1), 14., DELTA);
    }

    @Test
    public void testIndexOfX() {
        assertEquals(kX().indexOfX(2), 1, DELTA);
        assertEquals(sqr().indexOfX(6), 5, DELTA);
        assertEquals(kX().indexOfX(1234), -1, DELTA);
        assertEquals(sqr().indexOfX(245), -1, DELTA);
    }

    @Test
    public void testIndexOfY() {
        assertEquals(kX().indexOfY(9), 2, DELTA);
        assertEquals(sqr().indexOfY(9), 2, DELTA);
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
        assertEquals(sqr().floorIndexOfX(5.6), 4, DELTA);
        assertEquals(kX().floorIndexOfX(-1.), 0, DELTA);
    }

    @Test
    public void testExtrapolateLeft() {
        assertEquals(kX().extrapolateLeft(-1.), -5., DELTA);
        assertEquals(sqr().extrapolateLeft(0.5), -0.5, DELTA);
        assertEquals(kX().extrapolateLeft(0.), -2., DELTA);
    }

    @Test
    public void testExtrapolateRight() {
        assertEquals(kX().extrapolateRight(8.), 62., DELTA);
        assertEquals(sqr().extrapolateRight(6.5), 42.5, DELTA);
        assertEquals(kX().extrapolateRight(10.), 88., DELTA);
    }

    @Test
    public void testInterpolate() {
        assertEquals(kX().interpolate(3.5, 2), 12.5, DELTA);
        assertEquals(sqr().interpolate(3, 2), 9, DELTA);
    }

    @Test
    public void testApply() {
        assertEquals(kX().apply(-1.), -5., DELTA);
        assertEquals(kX().apply(2.), 4., DELTA);
        assertEquals(kX().apply(0.5), -0.5, DELTA);
        assertEquals(kX().apply(5.), 25., DELTA);


        assertEquals(sqr().apply(2.), 4., DELTA);
        assertEquals(sqr().apply(0.), -2., DELTA);
        assertEquals(sqr().apply(3.), 9., DELTA);
        assertEquals(sqr().apply(4.), 16., DELTA);
    }
}
