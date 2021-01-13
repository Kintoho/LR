package ru.ssau.tk.kintoho.LR.io;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.functions.ArrayTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.SqrFunction;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;

import java.io.*;
import java.util.Objects;

import static org.testng.Assert.assertEquals;

public class FunctionsIOTest {
    double[] xValues = new double[]{1., 2., 3., 4., 5.};
    double[] yValues = new double[]{6., 7., 8., 9., 10.};
    private final TabulatedFunction listFunction = new LinkedListTabulatedFunction(xValues, yValues);
    private final ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);

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

    @Test
    public void testSerializeDeserializeXml() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("temp/serialized array functions.XML"))) {
            FunctionsIO.serializeXml(out, arrayFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader in = new BufferedReader(new FileReader("temp/serialized array functions.XML"))) {
            TabulatedFunction deserializedArray = FunctionsIO.deserializeXml(in);
            assertEquals(arrayFunction.getCount(), deserializedArray.getCount());
            for (int i = 0; i < arrayFunction.getCount(); i++) {
                assertEquals(arrayFunction.getX(i), deserializedArray.getX(i));
                assertEquals(arrayFunction.getY(i), deserializedArray.getY(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJson() {
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);

        try (BufferedWriter out = new BufferedWriter(new FileWriter("temp/serialized array functions.Json"))) {
            FunctionsIO.serializeJson(out, arrayFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader in = new BufferedReader(new FileReader("temp/serialized array functions.Json"))) {
            TabulatedFunction resultArray = FunctionsIO.deserializeJson(in);

            assertEquals(arrayFunction.getCount(), resultArray.getCount());

            for (int i = 0; i < arrayFunction.getCount(); i++) {
                assertEquals(arrayFunction.getX(i), resultArray.getX(i));
                assertEquals(arrayFunction.getY(i), resultArray.getY(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
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