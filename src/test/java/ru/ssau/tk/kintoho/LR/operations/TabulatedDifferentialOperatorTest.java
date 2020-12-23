package ru.ssau.tk.kintoho.LR.operations;

import org.testng.annotations.Test;
import ru.ssau.tk.kintoho.LR.functions.factory.*;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.ArrayTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.Point;


import static org.testng.Assert.*;

public class TabulatedDifferentialOperatorTest {
    private final static double DELTA = 0.001;
    private final static TabulatedDifferentialOperator operatorOne = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
    private final static TabulatedDifferentialOperator operatorTwo = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());


    private final static double[] xValues = new double[]{1., 3., 5., 7., 9.};
    private final static double[] yValues = new double[]{0., 2., 4., 6., 8.};
    private final static double[] yValuesDerive = new double[]{1., 1., 1., 1., 1.};


    @Test
    public void testGetFactory() {
        assertTrue(operatorOne.getFactory() instanceof LinkedListTabulatedFunctionFactory);
        assertTrue(operatorTwo.getFactory() instanceof ArrayTabulatedFunctionFactory);
    }

    @Test
    public void testSetFactory() {
        operatorOne.setFactory(new ArrayTabulatedFunctionFactory());
        operatorTwo.setFactory(new LinkedListTabulatedFunctionFactory());
        assertTrue(operatorOne.getFactory() instanceof ArrayTabulatedFunctionFactory);
        assertTrue(operatorTwo.getFactory() instanceof LinkedListTabulatedFunctionFactory);
    }

    @Test
    public void testDerive() {
        TabulatedFunction derivedListFunction = operatorOne.derive(
                (new ArrayTabulatedFunctionFactory().create(xValues, yValues)));
        assertTrue(derivedListFunction instanceof LinkedListTabulatedFunction);
        int i = 0;
        for (Point point : derivedListFunction) {
            assertEquals(point.y, yValuesDerive[i++], DELTA);
        }
        TabulatedFunction derivedArrayFunction = operatorTwo.derive(
                (new LinkedListTabulatedFunctionFactory().create(xValues, yValues)));
        assertTrue(derivedArrayFunction instanceof ArrayTabulatedFunction);
        int j = 0;
        for (Point point : derivedArrayFunction) {
            assertEquals(point.y, yValuesDerive[j++], DELTA);
        }
    }

    @Test
    public void testDeriveSynchronously(){
        TabulatedFunction linkedListTabulatedFunction = new LinkedListTabulatedFunction(new double[]{10, 11, 12, 13, 14}, new double[]{100, 121, 144, 169, 196});
        TabulatedDifferentialOperator differentialOperatorList = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction differentialFunctionList = differentialOperatorList.deriveSynchronously(linkedListTabulatedFunction);
        assertTrue(differentialFunctionList instanceof LinkedListTabulatedFunction);

        for (int i = 0; i < differentialFunctionList.getCount(); i++) {
            assertEquals(differentialFunctionList.getX(i), (10. + i));
        }

        assertEquals(differentialFunctionList.getY(0), 21.);
        assertEquals(differentialFunctionList.getY(1), 23.);
        assertEquals(differentialFunctionList.getY(2), 25.);
        assertEquals(differentialFunctionList.getY(3), 27.);
        assertEquals(differentialFunctionList.getY(4), 27.);

        TabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(new double[]{7, 8, 9, 10, 11, 12}, new double[]{490, 640, 820, 1000, 1210, 1440});
        TabulatedDifferentialOperator differentialOperatorArray = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());
        TabulatedFunction differentialFunctionArray = differentialOperatorArray.deriveSynchronously(arrayTabulatedFunction);
        assertTrue(differentialFunctionArray instanceof ArrayTabulatedFunction);

        for (int i = 0; i < differentialFunctionArray.getCount(); i++) {
            assertEquals(differentialFunctionArray.getX(i), (7. + i));
        }

        assertEquals(differentialFunctionArray.getY(0), 150.);
        assertEquals(differentialFunctionArray.getY(1), 180.);
        assertEquals(differentialFunctionArray.getY(2), 180.);
        assertEquals(differentialFunctionArray.getY(3), 210.);
        assertEquals(differentialFunctionArray.getY(4), 230.);
        assertEquals(differentialFunctionArray.getY(5), 230.);
    }
}
