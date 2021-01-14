package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.io.FunctionsIO;
import ru.ssau.tk.kintoho.LR.operations.TabulatedDifferentialOperator;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DifferentialWindow extends JDialog {

    private final List<String> xValuesResult = new ArrayList<>(0);
    private final List<String> yValuesResult = new ArrayList<>(0);
    private TabulatedFunction functionResult;
    private TabulatedFunction firstFunction;
    private final AbstractTableModel tableModelResult = new TableModelTabulated(functionResult);
    private final JTable tableResult = new JTable(tableModelResult);
    private final List<String> xValues = new ArrayList<>();
    private final List<String> yValues = new ArrayList<>();
    private final AbstractTableModel tableModel = new TableModelTabulated(firstFunction);
    private final JTable table = new JTable(tableModel);
    private final JButton button = new JButton("Вычислить");
    private final JButton buttonResult = new JButton("Сохранить результат");
    private final JButton buttonCreate = new JButton("Создать");
    private final JButton buttonSave = new JButton("Сохранить");
    private final JButton buttonDownload = new JButton("Загрузить");
    private final JFileChooser save = new JFileChooser();
    private final JFileChooser downloadChooser = new JFileChooser();
    private final TabulatedDifferentialOperator differentialOperator;

    protected DifferentialWindow() {
        getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setModal(true);
        setBounds(100, 100, 800, 400);
        differentialOperator = new TabulatedDifferentialOperator(Window.factory);
        compose();
        addButtonListeners();
        CreateFunc.checkBoxSave.setVisible(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setLocationRelativeTo(null);
    }

    private void addButtonListeners() {

        buttonCreate.addActionListener(e -> {
            Object[] buttonsName = {"Массив", "Функция", "Отмена"};
            int resultDialog = JOptionPane.showOptionDialog(new JFrame(), "Выберите способ создания",
                    "Создать", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, buttonsName, buttonsName[2]);
            xValues.clear();
            yValues.clear();
            xValuesResult.clear();
            yValuesResult.clear();
            if (resultDialog == 1) {
                new CreateFunc(function -> firstFunction = function);
                for (int i = 0; i < firstFunction.getCount(); i++) {
                    xValues.add(i, String.valueOf(firstFunction.getX(i)));
                    yValues.add(i, String.valueOf(firstFunction.getY(i)));
                    tableModel.fireTableDataChanged();
                }
            }
            if (resultDialog == 0){
                xValues.clear();
                yValues.clear();
                TabulatedFunctionWindow table = new TabulatedFunctionWindow(Window.factory);
                table.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
                table.setVisible(true);
                for (int i = 0; i < table.getCount(); i++) {
                    xValues.add(table.getXValues().get(i));
                    yValues.add(table.getYValues().get(i));
                    tableModel.fireTableDataChanged();
                }
                firstFunction = table.getFunction();
            }
        });
        button.addActionListener(e -> {
            table.clearSelection();
            xValuesResult.clear();
            yValuesResult.clear();
            double[] arrayX = convert(xValues);
            double[] arrayY = convert(yValues);
            functionResult = differentialOperator.derive(Window.factory.create(arrayX, arrayY));
            for (int i = 0; i < functionResult.getCount(); i++) {
                xValuesResult.add(i, String.valueOf(functionResult.getX(i)));
                yValuesResult.add(i, String.valueOf(functionResult.getY(i)));
                tableModelResult.fireTableDataChanged();
            }
        });
        buttonSave.addActionListener(e -> {
            int returnVal = save.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = save.getSelectedFile() + ".bin";
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
                        if (firstFunction != null) {
                            FunctionsIO.serialize(out, firstFunction);
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
        buttonResult.addActionListener(e -> {
            int returnVal = save.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = save.getSelectedFile() + ".bin";
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
        buttonDownload.addActionListener(e ->
        {
            int returnVal = downloadChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = downloadChooser.getSelectedFile();
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                    xValues.clear();
                    yValues.clear();
                    tableModel.fireTableDataChanged();
                    firstFunction = FunctionsIO.deserialize(in);
                    int count = firstFunction.getCount();
                    for (int i = 0; i < count; i++) {
                        xValues.add(i, String.valueOf(firstFunction.getX(i)));
                        yValues.add(i, String.valueOf(firstFunction.getY(i)));
                        tableModel.fireTableDataChanged();
                    }
                    buttonCreate.setEnabled(false);

                    button.setEnabled(true);
                    buttonSave.setEnabled(true);
                } catch (IOException | ClassNotFoundException exception) {
                    new Errors(this, exception);
                }
            }
        });
    }

    private void compose() {
        JPanel panelResult = new JPanel();
        GroupLayout layoutResult = new GroupLayout(panelResult);
        panelResult.setLayout(layoutResult);
        layoutResult.setAutoCreateGaps(true);
        layoutResult.setAutoCreateContainerGaps(true);

        JScrollPane scrollPaneResult = new JScrollPane(tableResult);
        layoutResult.setHorizontalGroup(
                layoutResult.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layoutResult.createSequentialGroup()
                                .addComponent(buttonResult)
                                .addComponent(button))
                        .addComponent(scrollPaneResult));

        layoutResult.setVerticalGroup(layoutResult.createSequentialGroup()
                .addGroup(layoutResult.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonResult)
                        .addComponent(button))
                .addComponent(scrollPaneResult));


        JPanel panelInitial = new JPanel();
        GroupLayout layoutInitial = new GroupLayout(panelInitial);
        panelInitial.setLayout(layoutInitial);
        layoutInitial.setAutoCreateGaps(true);
        layoutInitial.setAutoCreateContainerGaps(true);
        JScrollPane scrollPaneInitial = new JScrollPane(table);
        layoutInitial.setHorizontalGroup(
                layoutInitial.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layoutInitial.createSequentialGroup()
                                .addComponent(buttonCreate)
                                .addComponent(buttonDownload)
                                .addComponent(buttonSave))
                        .addComponent(scrollPaneInitial));

        layoutInitial.setVerticalGroup(layoutInitial.createSequentialGroup()
                .addGroup(layoutInitial.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonCreate)
                        .addComponent(buttonDownload)
                        .addComponent(buttonSave))
                .addComponent(scrollPaneInitial));


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup().addGroup(layout.createSequentialGroup()
                .addComponent(panelInitial)
                .addComponent(panelResult)));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup()
                .addComponent(panelInitial)
                .addComponent(panelResult)));
    }

    private double[] convert(List<String> values) {
        double[] array = new double[values.size()];
        for (int i = 0; i < values.size(); i++) {
            String num = values.get(i);
            array[i] = Double.parseDouble(num);
        }
        return array;
    }
}
