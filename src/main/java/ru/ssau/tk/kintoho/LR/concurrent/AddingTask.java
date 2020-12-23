package ru.ssau.tk.kintoho.LR.concurrent;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;

public class AddingTask implements Runnable{
    private final TabulatedFunction tabulatedFunction;
    public AddingTask(TabulatedFunction tabulatedFunction) {
        this.tabulatedFunction = tabulatedFunction;
    }

    @Override
    public void run() {
        for (int i = 0; i < tabulatedFunction.getCount(); i++) {
            double x = tabulatedFunction.getX(i);
            double y = tabulatedFunction.getY(i);
            System.out.println(Thread.currentThread().getName() + "i = " + i + "x = " + x +  "old y = " + y);
            tabulatedFunction.setY(i, y+3);
            y = tabulatedFunction.getY(i);
            System.out.println(Thread.currentThread().getName() + "i = " + i + "x = " + x +  "new y = " + y);
        }

    }
}
