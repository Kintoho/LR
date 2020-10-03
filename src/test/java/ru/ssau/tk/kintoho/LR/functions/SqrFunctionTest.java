package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SqrFunctionTest {
    private final static double DELTA = 0.00001;
    @Test
    public void testApply() {
        IdentityFunction testFunction = new IdentityFunction();
        assertEquals(testFunction.apply(4), 2.0, DELTA);
        assertNotEquals(testFunction.apply(2.0), 2.0, DELTA);
        assertEquals(testFunction.apply(64.0), 8.0, DELTA);
        assertEquals(testFunction.apply(36), 36, DELTA);
    }
}