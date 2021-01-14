package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;
import java.util.List;

public class TableModelDiff extends AbstractTableModel {
    private static final int X_COLUMN_NUMBER = 0;
    private static final int Y_COLUMN_NUMBER = 1;

    @Serial
    private static final long serialVersionUID = -5870863572550121418L;

    private final List<String> xValues;
    private final List<String> yValues;
    private final boolean editable;
    private TabulatedFunction function;

    protected TableModelDiff(List<String> xValues, List<String> yValues, boolean editable) {
        this.xValues = xValues;
        this.yValues = yValues;
        this.editable = editable;
    }


    @Override
    public int getRowCount() {
        return xValues.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case X_COLUMN_NUMBER:
                return xValues.get(rowIndex);

            case Y_COLUMN_NUMBER:
                return yValues.get(rowIndex);

        }
        throw new UnsupportedOperationException();
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case X_COLUMN_NUMBER:
                break;
            case Y_COLUMN_NUMBER:
                yValues.set(rowIndex, String.valueOf(aValue));
                break;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case X_COLUMN_NUMBER -> false;
            case Y_COLUMN_NUMBER -> editable;
            default -> false;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case X_COLUMN_NUMBER -> "X: ";
            case Y_COLUMN_NUMBER -> "Y: ";
            default -> super.getColumnName(column);
        };
    }

    public TabulatedFunction getFunction() {
        return function;
    }

    public void setFunction(TabulatedFunction function) {
        this.function = function;
    }
}

