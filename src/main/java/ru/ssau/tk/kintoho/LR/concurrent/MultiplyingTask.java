package ru.ssau.tk.kintoho.LR.concurrent;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;

public class MultiplyingTask implements Runnable{

    private final TabulatedFunction tabulatedFunction;
    public MultiplyingTask(TabulatedFunction tabulatedFunction) {
        this.tabulatedFunction = tabulatedFunction;
    }

    @Override
    public void run() {
        double x, y;
        for (int i = 0; i < tabulatedFunction.getCount(); i++) {
            x = tabulatedFunction.getX(i);
            synchronized (tabulatedFunction) {
                y = tabulatedFunction.getY(i);
                System.out.println(Thread.currentThread().getName() + " i = " + i + " x = " + x + " old y = " + y);
                tabulatedFunction.setY(i, y * 10);
                y = tabulatedFunction.getY(i);
            }
            System.out.println(Thread.currentThread().getName() + " i = " + i + " x = " + x + " new y = " + y);
        }
    }
}
