package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.io.FunctionsIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Calculator extends JDialog {

    private final List<String> xValues1 = new ArrayList<>();
    private final List<String> yValues1 = new ArrayList<>();
    private final List<String> xValues2 = new ArrayList<>();
    private final List<String> yValues2 = new ArrayList<>();
    private final List<String> xValuesResult = new ArrayList<>();
    private final List<String> yValuesResult = new ArrayList<>();
    private final AbstractTableModel tableModel_1 = new TableModel(xValues1, yValues1) {
        @Serial
        private static final long serialVersionUID = 6951015012091406096L;
        private static final int Y_COLUMN_NUMBER = 2;

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == Y_COLUMN_NUMBER;
        }
    };
    private final JTable table_1 = new JTable(tableModel_1);
    private TabulatedFunction function1;
    private final AbstractTableModel tableModel_2 = new TableModel(xValues2, yValues2) {
        @Serial
        private static final long serialVersionUID = 3287465942502781647L;
        private static final int Y_COLUMN_NUMBER = 2;

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == Y_COLUMN_NUMBER;
        }
    };
    private final JTable table_2 = new JTable(tableModel_2);
    private TabulatedFunction function2;
    private final AbstractTableModel result = new TableModel(xValuesResult, yValuesResult) {
        @Serial
        private static final long serialVersionUID = 4949304988903288847L;

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    };
    private final JTable resultTable = new JTable(result);
    private TabulatedFunction functionResult;
    private int exc = 1;

    public Calculator(TabulatedFunctionFactory factory ) {
        JFrame calculator = new JFrame();
        setModal(true);
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileOpen.setDialogTitle("Загрузка функции");
        fileOpen.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        fileOpen.setAcceptAllFileFilterUsed(false);
        JFileChooser fileSave = new JFileChooser();
        fileSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileSave.setDialogTitle("Сохранение функции");
        fileSave.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        fileSave.setAcceptAllFileFilterUsed(false);
        setSize(new Dimension(1000, 500));
        table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll_1 = new JScrollPane(table_1);
        JScrollPane tableScroll_2 = new JScrollPane(table_2);
        JScrollPane tableScrollResult = new JScrollPane(resultTable);
        setLocationRelativeTo(null);

        JButton create1 = new JButton("Создать");
        create1.addActionListener(e -> {
            xValues1.clear();
            yValues1.clear();
            TabulatedFunctionWindow table1 = new TabulatedFunctionWindow(factory);
            table1.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
            table1.setVisible(true);
            for (int i = 0; i < table1.getCount(); i++) {
                xValues1.add(table1.getXValues().get(i));
                yValues1.add(table1.getYValues().get(i));
                tableModel_1.fireTableDataChanged();
            }
            function1 = table1.getFunction();
        });

        JButton create2 = new JButton("Создать");
        create2.addActionListener(e -> {
            xValues2.clear();
            yValues2.clear();
            TabulatedFunctionWindow table = new TabulatedFunctionWindow(factory);
            table.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
            table.setVisible(true);
            for (int i = 0; i < table.getCount(); i++) {
                xValues2.add(table.getXValues().get(i));
                yValues2.add(table.getYValues().get(i));
                tableModel_2.fireTableDataChanged();
            }
            function2 = table.getFunction();
        });

        JButton load1 = new JButton("Загрузить");
        load1.addActionListener(e -> {
            xValues1.clear();
            yValues1.clear();
            fileOpen.showOpenDialog(calculator);
            File file = fileOpen.getSelectedFile();
            if (file != null) {
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                    TabulatedFunction function = FunctionsIO.deserialize(in);
                    for (int i = 0; i < function.getCount(); i++) {
                        xValues1.add(i, String.valueOf(function.getX(i)));
                        yValues1.add(i, String.valueOf(function.getY(i)));
                        tableModel_1.fireTableDataChanged();
                    }
                    double[] xValues = new double[function.getCount()];
                    double[] yValues = new double[function.getCount()];
                    for (int i = 0; i < table_1.getRowCount(); i++) {
                        xValues[i] = Double.parseDouble(xValues1.get(i));
                        yValues[i] = Double.parseDouble(yValues1.get(i));
                    }

                    System.out.println(function.toString());
                    function1 = factory.create(xValues, yValues);
                } catch (IOException | ClassNotFoundException err) {
                    err.printStackTrace();
                }
            }
        });

        JButton load2 = new JButton("Загрузить");
        load2.addActionListener(e -> {
            xValues2.clear();
            yValues2.clear();
            fileOpen.showOpenDialog(calculator);
            File file = fileOpen.getSelectedFile();
            if (file != null) {
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                    TabulatedFunction function = FunctionsIO.deserialize(in);
                    for (int i = 0; i < function.getCount(); i++) {
                        xValues2.add(i, String.valueOf(function.getX(i)));
                        yValues2.add(i, String.valueOf(function.getY(i)));
                        tableModel_2.fireTableDataChanged();
                    }
                    double[] xValues = new double[function.getCount()];
                    double[] yValues = new double[function.getCount()];
                    for (int i = 0; i < table_1.getRowCount(); i++) {
                        xValues[i] = Double.parseDouble(xValues2.get(i));
                        yValues[i] = Double.parseDouble(yValues2.get(i));
                    }
                    System.out.println(function.toString());
                    function2 = factory.create(xValues, yValues);
                } catch (IOException | ClassNotFoundException err) {
                    err.printStackTrace();
                }
            }
        });

        JButton save1 = new JButton("Сохранить");
        save1.addActionListener(e -> {
            if (table_1.getRowCount() == 0) {
                JOptionPane.showMessageDialog(calculator, "Создайте функцию");
            } else {
                fileSave.showSaveDialog(calculator);
                File file = fileSave.getSelectedFile();
                if (file != null) {
                    double[] xValues = new double[table_1.getRowCount()];
                    double[] yValues = new double[table_1.getRowCount()];
                    for (int i = 0; i < table_1.getRowCount(); i++) {
                        xValues[i] = Double.parseDouble(table_1.getValueAt(i, 1).toString());
                        yValues[i] = Double.parseDouble(table_1.getValueAt(i, 2).toString());
                    }
                    function1 = factory.create(xValues, yValues);
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        FunctionsIO.serialize(out, function1);
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                }
            }
        });

        JButton save2 = new JButton("Сохранить");
        save2.addActionListener(e -> {
            if (table_2.getRowCount() == 0) {
                JOptionPane.showMessageDialog(calculator, "Создйте функцию");
            } else {
                fileSave.showSaveDialog(calculator);
                File file = fileSave.getSelectedFile();
                if (file != null) {
                    double[] xValues = new double[table_2.getRowCount()];
                    double[] yValues = new double[table_2.getRowCount()];
                    for (int i = 0; i < table_2.getRowCount(); i++) {
                        xValues[i] = Double.parseDouble(table_2.getValueAt(i, 1).toString());
                        yValues[i] = Double.parseDouble(table_2.getValueAt(i, 2).toString());
                    }
                    function2 = factory.create(xValues, yValues);
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        FunctionsIO.serialize(out, function2);
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                }
            }
        });

        JButton saveResult = new JButton("Сохранить");
        saveResult.addActionListener(e -> {
            if (resultTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(calculator, "Создайте функцию");
            } else {
                fileSave.showSaveDialog(calculator);
                File file = fileSave.getSelectedFile();
                if (file != null) {
                    double[] xValues = new double[resultTable.getRowCount()];
                    double[] yValues = new double[resultTable.getRowCount()];
                    for (int i = 0; i < resultTable.getRowCount(); i++) {
                        xValues[i] = Double.parseDouble(resultTable.getValueAt(i, 1).toString());
                        yValues[i] = Double.parseDouble(resultTable.getValueAt(i, 2).toString());
                    }
                    functionResult = factory.create(xValues, yValues);
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        FunctionsIO.serialize(out, functionResult);
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                }
            }
        });

        JButton operate = new JButton("Операция");
        operate.addActionListener(e -> {
            xValuesResult.clear();
            yValuesResult.clear();

            for (int i = 0; i < table_1.getRowCount() - 1; i++) {
                if (Double.parseDouble(xValues1.get(i)) != Double.parseDouble(xValues2.get(i))) {
                    JOptionPane.showMessageDialog(calculator, "Разные Х");
                    exc = 0;
                }
            }
            if (table_1.getRowCount() != table_2.getRowCount()) {
                JOptionPane.showMessageDialog(calculator, "Разные функции \n " + "... не делай так");
            } else if (table_1.getRowCount() == 0) {
                JOptionPane.showMessageDialog(calculator, "Создайте функции");
            } else if (exc != 0) {
                OperationsFunctions operator = new OperationsFunctions(factory, function1, function2);
                operator.setVisible(true);
                for (int i = 0; i < table_1.getRowCount(); i++) {
                    xValuesResult.add(String.valueOf(operator.func3.getX(i)));
                    yValuesResult.add(String.valueOf(operator.func3.getY(i)));
                }
                System.out.println(operator.func3.toString());
                functionResult = operator.func3;
                result.fireTableDataChanged();
            }
        });

        JLabel nameFirst = new JLabel("Первая функция");
        JLabel nameSecond = new JLabel("Вторая функция");
        JLabel nameResult = new JLabel("Результат");

        tableScroll_1.setPreferredSize(new Dimension(300, 300));
        tableScroll_1.setMaximumSize(new Dimension(300, 300));
        tableScroll_1.setMinimumSize(new Dimension(300, 300));

        tableScroll_2.setPreferredSize(new Dimension(300, 300));
        tableScroll_2.setMaximumSize(new Dimension(300, 300));
        tableScroll_2.setMinimumSize(new Dimension(300, 300));

        tableScrollResult.setPreferredSize(new Dimension(300, 300));
        tableScrollResult.setMaximumSize(new Dimension(300, 300));
        tableScrollResult.setMinimumSize(new Dimension(300, 300));

        operate.setPreferredSize(new Dimension(120, 26));
        operate.setMaximumSize(new Dimension(120, 26));
        operate.setMinimumSize(new Dimension(120, 26));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout
                .createSequentialGroup()
                .addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(nameFirst)
                        .addComponent(create1)
                        .addComponent(tableScroll_1)
                        .addGroup(layout
                                .createSequentialGroup()
                                .addComponent(load1)
                                .addComponent(save1)
                        )
                )
                .addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(nameSecond)
                        .addComponent(create2)
                        .addComponent(tableScroll_2)
                        .addGroup(layout
                                .createSequentialGroup()
                                .addComponent(load2)
                                .addComponent(save2)
                        )
                )
                .addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(nameResult)
                        .addGroup(layout.createSequentialGroup().addComponent(operate))
                        .addComponent(tableScrollResult)
                        .addGroup(layout.createSequentialGroup().addComponent(saveResult))
                )
        );
        layout.setVerticalGroup(layout
                .createParallelGroup()
                .addGroup(layout
                        .createSequentialGroup()
                        .addComponent(nameFirst)
                        .addComponent(create1)
                        .addComponent(tableScroll_1)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(load1)
                                .addComponent(save1)
                        )
                )
                .addGroup(layout
                        .createSequentialGroup()
                        .addComponent(nameSecond)
                        .addComponent(create2)
                        .addComponent(tableScroll_2)
                        .addGroup(layout
                                .createParallelGroup()
                                .addComponent(load2)
                                .addComponent(save2)
                        )
                )
                .addGroup(layout
                        .createSequentialGroup()
                        .addComponent(nameResult)
                        .addGroup(layout
                                .createParallelGroup()
                                .addComponent(operate)
                        )
                        .addComponent(tableScrollResult)
                        .addGroup(layout
                                .createParallelGroup()
                                .addComponent(saveResult)
                        )
                )
        );
    }
}