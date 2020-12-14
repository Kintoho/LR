package ru.ssau.tk.kintoho.LR.operations;

import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.functions.SqrFunction;

import static org.testng.Assert.assertEquals;

public class RightSteppingDifferentialOperatorTest {

    @Test
    public void testDerive() {
        double step = 1;
        SteppingDifferentialOperator differentialOperator = new RightSteppingDifferentialOperator(step);
        assertEquals(differentialOperator.derive(new SqrFunction()).apply(1), 3, 0.01);
    }
}