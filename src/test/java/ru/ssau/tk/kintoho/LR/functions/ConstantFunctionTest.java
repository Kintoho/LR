package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ConstantFunctionTest {

    @Test
    public void testGetConstant() {
        ConstantFunction testFunction = new ConstantFunction(5);
        assertEquals(testFunction.apply(322), 5.0,0.001);
        assertEquals(testFunction.apply(135), 5.0,0.001);
        assertEquals(testFunction.apply(777), 5.0,0.001);
    }
}