package ru.ssau.tk.kintoho.LR.functions;

import static org.testng.Assert.*;
import org.testng.annotations.Test;
public class UnitFunctionTest {
    UnitFunction testFunction = new UnitFunction();
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