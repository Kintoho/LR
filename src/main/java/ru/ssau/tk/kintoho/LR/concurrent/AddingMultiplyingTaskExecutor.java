package ru.ssau.tk.kintoho.LR.concurrent;

import ru.ssau.tk.kintoho.LR.functions.ConstantFunction;
import ru.ssau.tk.kintoho.LR.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;

import java.util.ArrayList;
import java.util.List;

public class AddingMultiplyingTaskExecutor {
    public static void main(String[] args) throws InterruptedException {
        TabulatedFunction listFunction = new LinkedListTabulatedFunction(new ConstantFunction(2), 1, 100, 100);
        List<Thread> list = new ArrayList<>();
        Thread thread0 = new Thread(new MultiplyingTask(listFunction));
        Thread thread1 = new Thread(new MultiplyingTask(listFunction));
        Thread thread2 = new Thread(new AddingTask(listFunction));
        thread0.start();
        thread1.start();
        thread2.start();
        Thread.sleep(2000);
    }

}
