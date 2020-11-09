package ru.ssau.tk.kintoho.LR.functions;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

public class AbstractTabulatedFunctionTest {
    private final static double DELTA = 0.001;
    public MockTabulatedFunction mock = new MockTabulatedFunction();

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
}