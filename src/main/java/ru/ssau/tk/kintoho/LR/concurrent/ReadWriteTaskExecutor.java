package ru.ssau.tk.kintoho.LR.concurrent;

import ru.ssau.tk.kintoho.LR.functions.*;


import java.util.ArrayList;
import java.util.List;

public class ReadWriteTaskExecutor {
    public static void main(String[] args) throws InterruptedException {
        TabulatedFunction listFunction = new LinkedListTabulatedFunction(new ZeroFunction(), 1, 10, 10);
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(new ReadWriteTask(listFunction));
            list.add(thread);
        }
        for (Thread thread : list) {
            thread.start();
        }
        Thread.sleep(2000);
        System.out.println(listFunction);
    }
}