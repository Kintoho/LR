package ru.ssau.tk.kintoho.LR.operations;

import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.functions.SqrFunction;

import static org.testng.Assert.assertEquals;

public class LeftSteppingDifferentialOperatorTest {

    @Test
    public void testDerive() {
        double step = 1;
        SteppingDifferentialOperator differentialOperator = new LeftSteppingDifferentialOperator(step);
        assertEquals(differentialOperator.derive(new SqrFunction()).apply(1), 1, 0.01);
    }

}