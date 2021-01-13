package ru.ssau.tk.kintoho.LR.io;

import java.io.*;
import java.util.Objects;

import org.testng.annotations.AfterClass;
import ru.ssau.tk.kintoho.LR.functions.*;
import ru.ssau.tk.kintoho.LR.functions.factory.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FunctionsIOTest {
    double[] xValues = new double[]{1., 2., 3., 4., 5.};
    double[] yValues = new double[]{6., 7., 8., 9., 10.};
    private final TabulatedFunction listFunction = new LinkedListTabulatedFunction(xValues, yValues);

    @Test
    public void testReadAndWriteTabulatedFunction() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("temp/first function.txt"))) {
            FunctionsIO.writeTabulatedFunction(writer, listFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("temp/first function.txt"))) {
            TabulatedFunction arrayTest = FunctionsIO.readTabulatedFunction(reader, new ArrayTabulatedFunctionFactory());

            assertEquals(listFunction.getCount(), arrayTest.getCount());

            for (int i = 0; i < listFunction.getCount(); i++) {
                assertEquals(listFunction.getX(i), arrayTest.getX(i));
                assertEquals(listFunction.getY(i), arrayTest.getY(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInputAndOutputTabulatedFunction() {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("temp/second function.bin"))) {
            FunctionsIO.writeTabulatedFunction(out, listFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream("temp/second function.bin"))) {
            TabulatedFunction arrayTest = FunctionsIO.readTabulatedFunction(in, new ArrayTabulatedFunctionFactory());

            assertEquals(listFunction.getCount(), arrayTest.getCount());

            for (int i = 0; i < listFunction.getCount(); i++) {
                assertEquals(listFunction.getX(i), arrayTest.getX(i));
                assertEquals(listFunction.getY(i), arrayTest.getY(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSerializeAndDeserializeTabulatedFunction() {
        LinkedListTabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(new SqrFunction(), 0, 10, 10);

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("temp/serialized linked list functions.bin"))) {
            FunctionsIO.serialize(outputStream, tabulatedFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("temp/serialized linked list functions.bin"))) {
            TabulatedFunction resultArray = FunctionsIO.deserialize(inputStream);

            assertEquals(tabulatedFunction.getCount(), resultArray.getCount());

            for (int i = 0; i < tabulatedFunction.getCount(); i++) {
                assertEquals(tabulatedFunction.getX(i), resultArray.getX(i));
                assertEquals(tabulatedFunction.getY(i), resultArray.getY(i));
            }
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
    }

    @AfterClass
    public void deleteOnExit() {
        for (File myFile : Objects.requireNonNull(new File("temp").listFiles()))
            if (myFile.isFile() && myFile.delete()) {
                System.out.println(myFile.getName() + " deleted");
            }
    }
}