package ru.ssau.tk.kintoho.LR.concurrent;

import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.functions.ArrayTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.Point;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class SynchronizedTabulatedFunctionTest {
    private final static double DELTA = 0.001;
    final double[] xValues = new double[]{4., 8., 15, 16., 23., 42.};
    final double[] yValues = new double[]{1., 2., 3., 4., 5., 6.};

    public SynchronizedTabulatedFunction synchronizedTabulatedFunction() {
        return new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues));
    }

    private SynchronizedTabulatedFunction getSynchronizedListFunction() {
        return new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xValues, yValues));
    }

    @Test
    public void testGetCount() {
        assertEquals(synchronizedTabulatedFunction().getCount(), 6., DELTA);
    }

    @Test
    public void testGetX() {
        assertEquals(synchronizedTabulatedFunction().getX(3), 16., DELTA);
    }

    @Test
    public void testGetY() {
        assertEquals(synchronizedTabulatedFunction().getY(3), 4., DELTA);
    }

    @Test
    public void testSetY() {
        final double[] xValues = new double[]{4., 8., 15., 16., 23., 42.};
        final double[] yValues = new double[]{2., 4., 6., 8., 10., 12.};
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xValues, yValues));
        synchronizedTabulatedFunction.setY(5, 777.);
        assertEquals(synchronizedTabulatedFunction.getY(5), 777., DELTA);
    }

    @Test
    public void testIndexOfX() {
        assertEquals(synchronizedTabulatedFunction().indexOfX(16.), 3, DELTA);
        assertEquals(synchronizedTabulatedFunction().indexOfX(42.), 5, DELTA);
    }

    @Test
    public void testIndexOfY() {
        assertEquals(synchronizedTabulatedFunction().indexOfY(4.), 3, DELTA);
        assertEquals(synchronizedTabulatedFunction().indexOfY(6.), 5, DELTA);
    }

    @Test
    public void testLeftBound() {
        assertEquals(synchronizedTabulatedFunction().leftBound(), 4, DELTA);
    }

    @Test
    public void testRightBound() {
        assertEquals(synchronizedTabulatedFunction().rightBound(), 42, DELTA);
    }

    @Test
    public void testIterator() {
        Iterator<Point> iterator = synchronizedTabulatedFunction().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(point.x, synchronizedTabulatedFunction().getX(i++));
        }
        assertEquals(i, synchronizedTabulatedFunction().getCount());
        assertThrows(NoSuchElementException.class, iterator::next);
        int j = 0;
        for (Point point : synchronizedTabulatedFunction()) {
            assertEquals(point.x, synchronizedTabulatedFunction().getX(j++));
        }
        assertEquals(j, synchronizedTabulatedFunction().getCount());
    }

    @Test
    public void testApply() {
        assertEquals(synchronizedTabulatedFunction().apply(2.), 0.5, DELTA);
        assertEquals(synchronizedTabulatedFunction().apply(1.), 0.25, DELTA);
        assertEquals(synchronizedTabulatedFunction().apply(23.), 5., DELTA);
    }

    @Test
    public void testDoSynchronously() {
        SynchronizedTabulatedFunction synchronizedTabulatedFunction = getSynchronizedListFunction();
        assertEquals((int) synchronizedTabulatedFunction.doSynchronously(SynchronizedTabulatedFunction::getCount), 6);
        assertEquals(synchronizedTabulatedFunction.doSynchronously(SynchronizedTabulatedFunction::rightBound), 42.);
    }
}