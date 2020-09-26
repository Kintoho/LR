package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IdentityFunctionTest {
    private final static double DELTA = 0.00001;
    @Test
    public void testApply() {
        IdentityFunction testFunction = new IdentityFunction();
        assertEquals(testFunction.apply(1), 1.0,DELTA);
        assertNotEquals(testFunction.apply(2.0), 3.0,DELTA);
        assertEquals(testFunction.apply(123.0), 123.0,DELTA);
        assertEquals(testFunction.apply(666.0), 666.0,DELTA);
    }
}