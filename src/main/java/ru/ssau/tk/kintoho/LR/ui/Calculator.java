package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.io.FunctionsIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class Calculator extends JDialog {
    private TabulatedFunction function1;
    private TabulatedFunction function2;
    private TabulatedFunction functionResult;

    private final TableModelTabulated tableModel1 = new TableModelTabulated(function1) {
        @Serial
        private static final long serialVersionUID = 6951015012091406096L;
        private static final int Y_COLUMN_NUMBER = 2;

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == Y_COLUMN_NUMBER;
        }
    };
    private final TableModelTabulated tableModel2 = new TableModelTabulated(function2) {
        @Serial
        private static final long serialVersionUID = 3287465942502781647L;
        private static final int Y_COLUMN_NUMBER = 2;

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == Y_COLUMN_NUMBER;
        }
    };
    private final TableModelTabulated resultTable = new TableModelTabulated(functionResult) {
        @Serial
        private static final long serialVersionUID = 4949304988903288847L;

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    };

    private final JTable table1 = new JTable(tableModel1);
    private final JTable table2 = new JTable(tableModel2);

    private int exc = 1;

    public Calculator(TabulatedFunctionFactory factory) {
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
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JTable result = new JTable(resultTable);
        result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll_1 = new JScrollPane(table1);
        JScrollPane tableScroll_2 = new JScrollPane(table2);
        JScrollPane tableScrollResult = new JScrollPane(result);
        setLocationRelativeTo(null);

        JButton create1 = new JButton("Создать");
        create1.addActionListener(e -> {
            TabulatedFunctionWindow table1 = new TabulatedFunctionWindow(factory);
            table1.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
            table1.setVisible(true);
            function1 = table1.getFunction();
            tableModel1.setFunction(function1);
            tableModel1.fireTableDataChanged();
        });

        JButton create2 = new JButton("Создать");
        create2.addActionListener(e -> {
            TabulatedFunctionWindow table = new TabulatedFunctionWindow(factory);
            table.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
            table.setVisible(true);
            function2 = table.getFunction();
            tableModel2.setFunction(function2);
            tableModel2.fireTableDataChanged();
        });

        JButton load1 = new JButton("Загрузить");
        load1.addActionListener(e -> {
            int returnVal = fileOpen.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {

                    function1 = FunctionsIO.deserialize(in);
                    for (int i = 0; i < function1.getCount(); i++) {
                        tableModel1.setFunction(function1);
                        tableModel1.fireTableDataChanged();
                        fileSave.setEnabled(true);
                    }
                } catch (IOException | ClassNotFoundException exception) {
                    new Errors(this, exception);
                }
            }
        });

        JButton load2 = new JButton("Загрузить");
        load2.addActionListener(e -> {
            int returnVal = fileOpen.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {

                    function2 = FunctionsIO.deserialize(in);
                    for (int i = 0; i < function2.getCount(); i++) {
                        tableModel2.setFunction(function2);
                        tableModel2.fireTableDataChanged();
                        fileSave.setEnabled(true);
                    }
                } catch (IOException | ClassNotFoundException exception) {
                    new Errors(this, exception);
                }
            }
        });

        JButton save1 = new JButton("Сохранить");
        save1.addActionListener(e -> {
            int returnVal = fileSave.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = fileSave.getSelectedFile() + ".bin";
                int flag = 1;
                File file = new File(fileName);
                if (file.exists()) {
                    flag = -1;
                    int ind = JOptionPane.showConfirmDialog(this, "Файл с таким названием существует.",
                            "Предупреждение", JOptionPane.YES_NO_OPTION);
                    if (ind == 0) {
                        JOptionPane.showConfirmDialog(this, "Введите название файла", "Предупреждение", JOptionPane.YES_NO_OPTION);
                    }
                }
                if (flag != -1) {
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        if (function1 != null) {
                            FunctionsIO.serialize(out, function1);
                            JOptionPane.showMessageDialog(this,
                                    "Файл '" + file.getName() +
                                            " сохранен");
                        } else {
                            throw new IOException();
                        }
                    } catch (Exception exception) {
                        new Errors(this, exception);
                    }
                }
            }
        });

        JButton save2 = new JButton("Сохранить");
        save2.addActionListener(e -> {
            int returnVal = fileSave.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = fileSave.getSelectedFile() + ".bin";
                int flag = 1;
                File file = new File(fileName);
                if (file.exists()) {
                    flag = -1;
                    int ind = JOptionPane.showConfirmDialog(this, "Файл с таким названием существует.",
                            "Предупреждение", JOptionPane.YES_NO_OPTION);
                    if (ind == 0) {
                        JOptionPane.showConfirmDialog(this, "Введите название файла", "Предупреждение", JOptionPane.YES_NO_OPTION);
                    }
                }
                if (flag != -1) {
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        if (function2 != null) {
                            FunctionsIO.serialize(out, function2);
                            JOptionPane.showMessageDialog(this,
                                    "Файл '" + file.getName() +
                                            " сохранен");
                        } else {
                            throw new IOException();
                        }
                    } catch (Exception exception) {
                        new Errors(this, exception);
                    }
                }
            }
        });

        JButton saveResult = new JButton("Сохранить");
        saveResult.addActionListener(e -> {
            int returnVal = fileSave.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = fileSave.getSelectedFile() + ".bin";
                int flag = 1;
                File file = new File(fileName);
                if (file.exists()) {
                    flag = -1;
                    int ind = JOptionPane.showConfirmDialog(this, "Файл с таким названием существует.",
                            "Предупреждение", JOptionPane.YES_NO_OPTION);
                    if (ind == 0) {
                        JOptionPane.showConfirmDialog(this, "Введите название файла", "Предупреждение", JOptionPane.YES_NO_OPTION);
                    }
                }
                if (flag != -1) {
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        if (functionResult != null) {
                            FunctionsIO.serialize(out, functionResult);
                            JOptionPane.showMessageDialog(this,
                                    "Файл '" + file.getName() +
                                            " сохранен");
                        } else {
                            throw new IOException();
                        }
                    } catch (Exception exception) {
                        new Errors(this, exception);
                    }
                }
            }
        });

        JButton operate = new JButton("Операция");
        operate.addActionListener(e -> {
            for (int i = 0; i < table1.getRowCount() - 1; i++) {
                if (function1.getX(i) != function2.getX(i)) {
                    JOptionPane.showMessageDialog(calculator, "Разные Х");
                    exc = 0;
                }
            }
            if (table1.getRowCount() != table2.getRowCount()) {
                JOptionPane.showMessageDialog(calculator, "Разные функции \n ");
            } else if (table1.getRowCount() == 0) {
                JOptionPane.showMessageDialog(calculator, "Создайте функции");
            } else if (exc != 0) {
                OperationsFunctions operator = new OperationsFunctions(factory, function1, function2);
                operator.setVisible(true);
                result.setVisible(true);
                functionResult = resultTable.getFunction();
                functionResult = operator.func3;
                resultTable.setFunction(functionResult);
                System.out.println(operator.func3.toString());
                resultTable.fireTableDataChanged();
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