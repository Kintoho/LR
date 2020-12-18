package ru.ssau.tk.kintoho.LR.io;

import ru.ssau.tk.kintoho.LR.functions.ArrayTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.operations.TabulatedDifferentialOperator;

import java.io.*;

public class ArrayTabulatedFunctionSerialization {
    public static void main(String[] args){
        File file = new File("output/serialized array functions.bin");

        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            double[] xValues = new double[]{1., 2., 3., 4.};
            double[] yValues = new double[]{5., 6., 7., 8.};
            ArrayTabulatedFunction functionATF = new ArrayTabulatedFunction(xValues, yValues);
            TabulatedFunction firstDerivative = new TabulatedDifferentialOperator().derive(functionATF);
            TabulatedFunction secondDerivative = new TabulatedDifferentialOperator().derive(firstDerivative);

            FunctionsIO.serialize(bufferedOutputStream, functionATF);
            FunctionsIO.serialize(bufferedOutputStream, firstDerivative);
            FunctionsIO.serialize(bufferedOutputStream, secondDerivative);

            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

            TabulatedFunction function = FunctionsIO.deserialize(bufferedInputStream);
            TabulatedFunction function1 = FunctionsIO.deserialize(bufferedInputStream);
            TabulatedFunction function2 = FunctionsIO.deserialize(bufferedInputStream);

            System.out.println("   ");
            System.out.println(function.toString());
            System.out.println("   ");
            System.out.println(function1.toString());
            System.out.println("   ");
            System.out.println(function2.toString());
            System.out.println("   ");

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }
}
