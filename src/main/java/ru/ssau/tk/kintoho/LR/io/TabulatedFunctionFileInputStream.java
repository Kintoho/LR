package ru.ssau.tk.kintoho.LR.io;

import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.operations.*;
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
        try {
            System.out.println("Введите размер и значения функции");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            TabulatedFunction tabulatedFunc = FunctionsIO.readTabulatedFunction(bufferedReader, new LinkedListTabulatedFunctionFactory());//ожидаем
            System.out.println(new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory()).derive(tabulatedFunc).toString());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
