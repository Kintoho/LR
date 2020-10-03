package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AbsoluteValueFunctionTest {
    private final static double DELTA = 0.00001;

    @Test
    public void testApply() {
        MathFunction testFunction = new AbsoluteValueFunction();
        assertEquals(testFunction.apply(1.0), 1.0, DELTA);
        assertNotEquals(testFunction.apply(2.0), 3.0, DELTA);
        assertEquals(testFunction.apply(-123.0), 123.0, DELTA);
        assertEquals(testFunction.apply(666.0), 666.0, DELTA);
        assertNotEquals(testFunction.apply(2.0), -2.0, DELTA);
    }
}