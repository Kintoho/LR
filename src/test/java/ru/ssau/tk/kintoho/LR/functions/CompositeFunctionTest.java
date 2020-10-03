package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class CompositeFunctionTest {
    private final static double DELTA = 0.00001;

    @Test
    public void testApply() {
        MathFunction pow = new SqrFunction();
        MathFunction identity = new IdentityFunction();
        MathFunction plus = new PlusFunction();
        CompositeFunction firstFunction = new CompositeFunction(plus, pow);
        assertEquals(firstFunction.apply(2), 49, DELTA);
        assertEquals(firstFunction.apply(5),25 , DELTA);
        assertNotEquals(firstFunction.apply(4), 16, DELTA);
        CompositeFunction secondFunction = new CompositeFunction(identity, pow);
        assertEquals(secondFunction.apply(1),1,DELTA);
        assertEquals(secondFunction.apply(2),  4, DELTA);
        assertNotEquals(secondFunction.apply(2), 2, DELTA);
        assertEquals(secondFunction.apply(0),0,DELTA);



    }
}