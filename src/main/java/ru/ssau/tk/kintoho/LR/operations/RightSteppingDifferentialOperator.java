package ru.ssau.tk.kintoho.LR.operations;

import ru.ssau.tk.kintoho.LR.functions.MathFunction;

public class RightSteppingDifferentialOperator extends SteppingDifferentialOperator {
    public RightSteppingDifferentialOperator(double step){
        super(step);
    }

    @Override
    public MathFunction derive(MathFunction function){
        return x -> (function.apply(x + step) - function.apply(x)) / step;
    }
}
