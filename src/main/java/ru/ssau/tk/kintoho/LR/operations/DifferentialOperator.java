package ru.ssau.tk.kintoho.LR.operations;

import ru.ssau.tk.kintoho.LR.functions.MathFunction;

public interface DifferentialOperator<T extends MathFunction> {
    T derive(T function);
}
