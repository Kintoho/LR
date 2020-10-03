package ru.ssau.tk.kintoho.LR.functions;

public class ConstantFunction implements MathFunction   {
    private final double constant;

    public double getConstant() {
        return constant;
    }

    public ConstantFunction(double constant) {
        this.constant = constant;
    }

    public double apply(double x) {
        return constant;
    }
}
