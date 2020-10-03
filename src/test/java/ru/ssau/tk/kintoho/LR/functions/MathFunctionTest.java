package ru.ssau.tk.kintoho.LR.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MathFunctionTest {
    MathFunction X = new PlusFunction();
    MathFunction sqrX = new SqrFunction();
    MathFunction andThen = sqrX.andThen(X);
    MathFunction one = new UnitFunction();
    MathFunction zero = new ZeroFunction();
    MathFunction plus = new PlusFunction();

    private final static double DELTA = 0.00001;

    @Test
    public void testAndThen() {
        assertEquals(andThen.apply(1), 6.0, DELTA);
        assertEquals(andThen.andThen(one).apply(10), 1, DELTA);
        assertEquals(andThen.andThen(plus).apply(5),35, DELTA);
        assertEquals(andThen.andThen(zero).apply(10), 0, DELTA);
    }
}