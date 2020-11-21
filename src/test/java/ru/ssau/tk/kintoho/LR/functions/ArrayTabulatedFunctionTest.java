package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.exceptions.InterpolationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class ArrayTabulatedFunctionTest {
    private final static double DELTA = 0.001;
    private final double[] valuesX = new double[]{1., 2., 3., 4., 5., 6., 7.};
    private final double[] valuesY = new double[]{1., 4., 9., 16., 25., 36., 49.};

    private final MathFunction sqr = new SqrFunction();

    private ArrayTabulatedFunction kX() {
        return new ArrayTabulatedFunction(valuesX, valuesY);

    }

    private ArrayTabulatedFunction sqr() {

        return new ArrayTabulatedFunction(sqr, 1., 7., 7);
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
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                kX().getX(100));
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                sqr().getX(-100));
    }

    @Test
    public void testGetY() {
        assertEquals(kX().getY(5), 36., DELTA);
        assertEquals(sqr().getY(1), 4., DELTA);
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                kX().getX(52));
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                sqr().getX(-11));
    }

    @Test
    public void testSetY() {
        ArrayTabulatedFunction testKX = new ArrayTabulatedFunction(valuesX, valuesY);
        ArrayTabulatedFunction testSqr = new ArrayTabulatedFunction(sqr, 1., 7., 7);
        testKX.setY(1, 14.);
        testSqr.setY(1, 14.);
        assertEquals(testKX.getY(1), 14., DELTA);
        assertEquals(testSqr.getY(1), 14., DELTA);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> testKX.setY(12, 22));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> testSqr.setY(-1, 3));
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
        assertEquals(kX().floorIndexOfX(1.), 0, DELTA);
        assertThrows(IllegalArgumentException.class, () ->
                kX().floorIndexOfX(-6));

        assertThrows(IllegalArgumentException.class, () ->
                sqr().floorIndexOfX(-111));
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
        assertThrows(InterpolationException.class, () -> kX().interpolate(4, 1));
        assertThrows(InterpolationException.class, () -> sqr().interpolate(7, 3));
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

    @Test
    public void testDifficultFunctions() {
        double xFrom = 2;
        double xTo = 14;
        int count = 36;
        MathFunction sqr = new SqrFunction();
        TabulatedFunction one = new ArrayTabulatedFunction(sqr, xFrom, xTo, count);
        TabulatedFunction two = new LinkedListTabulatedFunction(sqr, xFrom, xTo, count);
        assertEquals(one.getY(32), two.getY(32), DELTA);
        assertEquals(one.getY(11), two.getY(11), DELTA);
        assertEquals(one.getY(5), two.getY(5), DELTA);
    }

    @Test
    public void testIterator(){
        AbstractTabulatedFunction functionFirst = new ArrayTabulatedFunction(valuesX, valuesY);
        Iterator<Point> iterator = functionFirst.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(point.x, functionFirst.getX(i++));
        }
        assertThrows(NoSuchElementException.class, iterator::next);

        int j = 0;
        for (Point point : functionFirst) {
            assertEquals(point.x, functionFirst.getX(j++));
        }
        assertThrows(NoSuchElementException.class, iterator::next);

        SqrFunction sqr = new SqrFunction();
        ArrayTabulatedFunction functionSecond = new ArrayTabulatedFunction(sqr, 1, 5, 5);
        Iterator<Point> secondIterator = functionSecond.iterator();
        i = 0;
        while (iterator.hasNext()) {
            Point point = secondIterator.next();
            assertEquals(point.x, functionSecond.getX(i++));
        }
        assertThrows(NoSuchElementException.class, iterator::next);
        j = 0;
        for (Point point : functionSecond) {
            assertEquals(point.x, functionSecond.getX(j++));
        }
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}