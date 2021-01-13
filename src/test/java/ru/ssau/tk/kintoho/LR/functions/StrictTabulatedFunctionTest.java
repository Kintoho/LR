package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class StrictTabulatedFunctionTest {
    private final static double DELTA = 0.0001;

    TabulatedFunction array = new StrictTabulatedFunction(
            new ArrayTabulatedFunction(new double[]{1., 2., 3.}, new double[]{4., 5., 6.}));

    TabulatedFunction list = new StrictTabulatedFunction(
            new LinkedListTabulatedFunction(new double[]{1., 2., 3., 4.}, new double[]{1., 4., 6., 8}));

    @Test
    public void testGetCount() {
        assertEquals(array.getCount(), 3);
        assertEquals(list.getCount(), 4);
    }

    @Test
    public void testGetX() {
        assertEquals(array.getX(0), 1., DELTA);
        assertEquals(list.getX(3), 4., DELTA);
    }

    @Test
    public void testGetY() {
        assertEquals(array.getY(0), 4., DELTA);
        assertEquals(list.getY(3), 8., DELTA);
    }

    @Test
    public void testSetY() {
        TabulatedFunction array = new StrictTabulatedFunction(
                new ArrayTabulatedFunction(new double[]{1., 2.}, new double[]{3., 4.}));
        TabulatedFunction list = new StrictTabulatedFunction(
                new LinkedListTabulatedFunction(new double[]{1., 2., 3., 4.}, new double[]{1., 4., 9., 16}));
        array.setY(0, 66.);
        list.setY(3, 2.);
        assertEquals(array.getY(0), 66., DELTA);
        assertEquals(list.getY(3), 2., DELTA);
    }

    @Test
    public void testIndexOfX() {
        assertEquals(array.indexOfX(3.), 2);
        assertEquals(list.indexOfX(1.), 0);
    }

    @Test
    public void testIndexOfY() {
        assertEquals(array.indexOfY(10.), -1);
        assertEquals(list.indexOfY(0.), -1);
    }

    @Test
    public void testLeftBound() {
        assertEquals(array.leftBound(), 1., DELTA);
        assertEquals(list.leftBound(), 1., DELTA);
    }

    @Test
    public void testRightBound() {
        assertEquals(array.rightBound(), 3., DELTA);
        assertEquals(list.rightBound(), 4., DELTA);
    }

    @Test
    public void testIterator() {
        int i = 0;
        for (Point point : list) {
            assertEquals(list.getX(i), point.x, DELTA);
            assertEquals(list.getY(i++), point.y, DELTA);
        }
        int j = 0;
        for (Point point : array) {
            assertEquals(array.getX(j), point.x, DELTA);
            assertEquals(array.getY(j++), point.y, DELTA);
        }
    }

    @Test
    public void testApply() {
        assertEquals(array.apply(3.), 6., DELTA);
        assertEquals(list.apply(4.), 8., DELTA);
        assertThrows(UnsupportedOperationException.class, () -> array.apply(1.5));
        assertThrows(UnsupportedOperationException.class, () -> list.apply(-1.));
    }
}