package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PlusFunctionTest {
    private final static double DELTA = 0.00001;
    @Test
    public void testApply() {
        MathFunction testFunction = new PlusFunction();
        assertEquals(testFunction.apply(1), 6.0, DELTA);
        assertNotEquals(testFunction.apply(2.0), 5.0, DELTA);
        assertEquals(testFunction.apply(123.0), 128.0, DELTA);
        assertEquals(testFunction.apply(32), 37, DELTA);
    }
}