package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;

public class TableModelTabulated extends AbstractTableModel {
    private static final int INDEX_COLUMN_NUMBER = 0;
    private static final int X_COLUMN_NUMBER = 1;
    private static final int Y_COLUMN_NUMBER = 2;
    @Serial
    private static final long serialVersionUID = 3750548874098707606L;
    private TabulatedFunction function;

    public TableModelTabulated(TabulatedFunction function) {
        this.function = function;
    }

    @Override
    public int getRowCount() {
        return (function == null) ? 0 : function.getCount();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case INDEX_COLUMN_NUMBER:
                return rowIndex;
            case X_COLUMN_NUMBER:
                return function.getX(rowIndex);
            case Y_COLUMN_NUMBER:
                return function.getY(rowIndex);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) throws NumberFormatException {
        if (columnIndex == Y_COLUMN_NUMBER) {
            try {
                function.setY(rowIndex, Double.parseDouble(aValue.toString()));
            } catch (Exception e) {

                function.setY(rowIndex, 0.0);
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case INDEX_COLUMN_NUMBER, X_COLUMN_NUMBER -> false;
            case Y_COLUMN_NUMBER -> true;
            default -> false;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case INDEX_COLUMN_NUMBER -> "Индекс";
            case X_COLUMN_NUMBER -> "X";
            case Y_COLUMN_NUMBER -> "Y";
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
