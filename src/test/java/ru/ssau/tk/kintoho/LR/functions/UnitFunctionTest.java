package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
public class UnitFunctionTest {
    private final UnitFunction testFunction = new UnitFunction();
    @Test
    public void testGetConstant(){
        assertEquals(testFunction.apply(322), 1.0,0.001);
        assertEquals(testFunction.apply(13523), 1.0,0.001);
        assertEquals(testFunction.apply(777), 1.0,0.001);
    }
    @Test
    public void testApply() {
        assertEquals(testFunction.getConstant(), 1.0, 0.001);
    }
}