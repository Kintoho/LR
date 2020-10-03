package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MathFunctionTest {
    private final MathFunction sqrX = new SqrFunction();
    private final MathFunction plus = new PlusFunction();
    private final MathFunction sqrSomeFunction = sqrX.andThen(plus);
    private final MathFunction one = new UnitFunction();
    private final MathFunction zero = new ZeroFunction();

    private final static double DELTA = 0.00001;

    @Test
    public void testAndThen() {
        assertEquals(sqrSomeFunction.apply(1), 6.0, DELTA);
        assertEquals(sqrSomeFunction.andThen(one).apply(10), 1, DELTA);
        assertEquals(sqrSomeFunction.andThen(plus).apply(5),35, DELTA);
        assertEquals(sqrSomeFunction.andThen(zero).apply(10), 0, DELTA);
    }
}