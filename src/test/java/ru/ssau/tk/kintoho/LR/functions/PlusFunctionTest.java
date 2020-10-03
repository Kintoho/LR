package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PlusFunctionTest {
    private final static double DELTA = 0.00001;
    @Test
    public void testApply() {
        IdentityFunction testFunction = new IdentityFunction();
        assertEquals(testFunction.apply(1+5), 6.0, DELTA);
        assertNotEquals(testFunction.apply(2.0+5), 6.0, DELTA);
        assertEquals(testFunction.apply(123.0+5), 128.0, DELTA);
        assertEquals(testFunction.apply(32+5), 37, DELTA);
    }
}