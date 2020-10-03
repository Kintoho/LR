package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ZeroFunctionTest {
    private final ZeroFunction testFunction = new ZeroFunction();

    @Test
    public void testGetConstant() {
        assertEquals(testFunction.apply(-322), 0.0, 0.001);
        assertEquals(testFunction.apply(1488), 0.0, 0.001);
        assertEquals(testFunction.apply(228), 0.0, 0.001);
    }

    @Test
    public void testApply() {
        assertEquals(testFunction.getConstant(), 0.0, 0.001);
    }
}