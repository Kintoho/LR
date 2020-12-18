package ru.ssau.tk.kintoho.LR.io;

import java.io.*;

import ru.ssau.tk.kintoho.LR.functions.*;


public class TabulatedFunctionFileOutputStream {
    public static void main(String[] args) {
        ArrayTabulatedFunction functionArray = new ArrayTabulatedFunction(new double[]{1., 2., 3., 4,}, new double[]{5., 6., 7., 8.});
        LinkedListTabulatedFunction functionList = new LinkedListTabulatedFunction(new double[]{1., 2., 3., 4,}, new double[]{1., 4., 6., 8.});
        try (BufferedOutputStream outputStreamArray = new BufferedOutputStream(new FileOutputStream("output/array functionArray.bin"));
             BufferedOutputStream outputStreamList = new BufferedOutputStream(new FileOutputStream("output/array functionList.bin"))) {
            FunctionsIO.writeTabulatedFunction(outputStreamArray, functionArray);
            FunctionsIO.writeTabulatedFunction(outputStreamList, functionList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
