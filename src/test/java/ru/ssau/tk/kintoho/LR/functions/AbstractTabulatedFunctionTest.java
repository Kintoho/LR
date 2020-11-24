package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.exceptions.ArrayIsNotSortedException;
import ru.ssau.tk.kintoho.LR.exceptions.DifferentLengthOfArraysException;

import static org.testng.Assert.*;

public class AbstractTabulatedFunctionTest {
    private final static double DELTA = 0.001;
    public MockTabulatedFunction mock = new MockTabulatedFunction();
    private final double[] xArr = new double[]{1, 2, 3};
    private final double[] xArrSame = new double[]{1, 1, 2, 3};
    private final double[] yArr = new double[]{4, 5, 6, 12};
    private final double[] yArrPass = new double[]{1, 2, 3};
    private final double[] xArrWrong = new double[]{1, 4, 3};
    private final double[] xArrPass = new double[]{1, 3, 4};

    @Test
    public void testInterpolate() {
        assertEquals(mock.interpolate(6., 2., 10., 4., 8.), 6., DELTA);
    }

    @Test
    public void testApply() {
        assertEquals(mock.apply(3.), 4., DELTA);
        assertEquals(mock.apply(6.), 8., DELTA);
        assertEquals(mock.apply(2.), 2.666666666666667, DELTA);
        assertEquals(mock.apply(10.), 13.333333333333334, DELTA);
        assertNotEquals(mock.apply(6.), 10., DELTA);
    }

    @Test
    public void testCheckLengthIsTheSame() {
        assertThrows(DifferentLengthOfArraysException.class, () ->
                AbstractTabulatedFunction.checkLengthIsTheSame(xArr, yArr));
        AbstractTabulatedFunction.checkLengthIsTheSame(xArr, yArrPass);
    }

    @Test
    public void testCheckSorted() {
        assertThrows(ArrayIsNotSortedException.class, () ->
                AbstractTabulatedFunction.checkSorted(xArrWrong));
        AbstractTabulatedFunction.checkSorted(xArrPass);
        assertThrows(ArrayIsNotSortedException.class, () ->
                AbstractTabulatedFunction.checkSorted(xArrSame));
    }
}