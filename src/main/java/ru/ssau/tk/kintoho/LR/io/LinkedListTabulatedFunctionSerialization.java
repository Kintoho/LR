package ru.ssau.tk.kintoho.LR.io;

import ru.ssau.tk.kintoho.LR.functions.*;
import ru.ssau.tk.kintoho.LR.operations.*;

import java.io.*;

public class LinkedListTabulatedFunctionSerialization {
    public static void main(String[] args) {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("output/serialized linked list functions.bin"))) {
            LinkedListTabulatedFunction linkedFunction = new LinkedListTabulatedFunction(new double[]{1., 2., 3., 4.}, new double[]{5., 6., 7., 8.});

            TabulatedFunction firstDerivative = new TabulatedDifferentialOperator().derive(linkedFunction);
            TabulatedFunction secondDerivative = new TabulatedDifferentialOperator().derive(firstDerivative);

            FunctionsIO.serialize(bufferedOutputStream, linkedFunction);
            FunctionsIO.serialize(bufferedOutputStream, firstDerivative);
            FunctionsIO.serialize(bufferedOutputStream, secondDerivative);

            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("output/serialized linked list functions.bin"));

            TabulatedFunction firstFunction = FunctionsIO.deserialize(bufferedInputStream);
            TabulatedFunction secondFunction = FunctionsIO.deserialize(bufferedInputStream);
            TabulatedFunction thirdFunction = FunctionsIO.deserialize(bufferedInputStream);

            System.out.println(firstFunction.toString());
            System.out.println('\n');
            System.out.println(secondFunction.toString());
            System.out.println('\n');
            System.out.println(thirdFunction.toString());

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
