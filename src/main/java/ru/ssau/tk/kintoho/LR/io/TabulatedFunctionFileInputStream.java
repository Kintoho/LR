package ru.ssau.tk.kintoho.LR.io;

import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;

import ru.ssau.tk.kintoho.LR.functions.*;

import java.io.*;


public class TabulatedFunctionFileInputStream {
    public static void main(String[] args) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("input/binary function.bin"))) {
            TabulatedFunction tabulatedFunc = FunctionsIO.readTabulatedFunction(bufferedInputStream, new ArrayTabulatedFunctionFactory());
            System.out.println(tabulatedFunc.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
