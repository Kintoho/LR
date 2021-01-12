package ru.ssau.tk.kintoho.LR.ui;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;
import java.util.List;

public class TableModel extends AbstractTableModel {
    private static final int INDEX_COLUMN_NUMBER = 0;
    private static final int X_COLUMN_NUMBER = 1;
    private static final int Y_COLUMN_NUMBER = 2;
    @Serial
    private static final long serialVersionUID = -2955931764405857357L;

    private final List<String> xValues;
    private final List<String> yValues;

    public TableModel(List<String> xValues, List<String> yValues) {
        this.xValues = xValues;
        this.yValues = yValues;
    }

    @Override
    public int getRowCount() {
        return xValues.size();
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
                return xValues.get(rowIndex);
            case Y_COLUMN_NUMBER:
                return yValues.get(rowIndex);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) throws NumberFormatException {
        if (columnIndex == X_COLUMN_NUMBER) {
            try {
                xValues.set(rowIndex, String.valueOf(aValue.toString()));
            } catch (Exception e) {
                xValues.set(rowIndex, "");
            }
        } else if (columnIndex == Y_COLUMN_NUMBER) {
            try {
                yValues.set(rowIndex, String.valueOf(aValue.toString()));
            } catch (Exception e) {
                yValues.set(rowIndex, "");
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case X_COLUMN_NUMBER, Y_COLUMN_NUMBER -> true;
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
}