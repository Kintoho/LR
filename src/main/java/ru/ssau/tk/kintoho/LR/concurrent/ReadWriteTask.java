package ru.ssau.tk.kintoho.LR.concurrent;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;

public class ReadWriteTask implements Runnable {
    private final TabulatedFunction tabulatedFunction;

    public ReadWriteTask(TabulatedFunction tabulatedFunction) {
        this.tabulatedFunction = tabulatedFunction;
    }

    @Override
    public void run() {
        for (int i = 0; i < tabulatedFunction.getCount(); i++) {
            double x = tabulatedFunction.getX(i);
            double y;
            synchronized (tabulatedFunction) {
                y = tabulatedFunction.getY(i);
                System.out.println(Thread.currentThread().getName() + " before write: i=" + i + " " + "x=" + x + " " + "y=" + y);
                tabulatedFunction.setY(i, y + 1);
                y = tabulatedFunction.getY(i);
            }
            System.out.println(Thread.currentThread().getName() + " after write: i=" + i + " " + "x=" + x + " " + "y=" + y);
        }
    }
}
