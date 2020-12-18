package ru.ssau.tk.kintoho.LR.io;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.functions.factory.LinkedListTabulatedFunctionFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TabulatedFunctionFileReader {
    public static void main(String[] args) {
        File file = new File("input/function.txt");
        try (BufferedReader inputArray = new BufferedReader(new FileReader(file));
             BufferedReader inputList = new BufferedReader(new FileReader(file))) {
            TabulatedFunction arrayFunction = FunctionsIO.readTabulatedFunction(inputArray, new ArrayTabulatedFunctionFactory());
            System.out.println(arrayFunction.toString());

            TabulatedFunction listFunction = FunctionsIO.readTabulatedFunction(inputList, new LinkedListTabulatedFunctionFactory());
            System.out.println(listFunction.toString());
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}
