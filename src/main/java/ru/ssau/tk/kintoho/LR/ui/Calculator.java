package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.io.FunctionsIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Calculator extends JDialog {

    JMenu menuSettings = new JMenu("Выбор фабрики");
    JMenuBar menuBar = new JMenuBar();

    private final ArrayList<String> xValues;
    private final ArrayList<String> yValues;
    private final AbstractTableModel tableModel;
    private final JTable table;
    private TabulatedFunction function1;
    private TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
    int size;

    public Calculator() {
        JDialog calculator = new JDialog();
        setModal(true);
        menuBar.add(menuSettings);
        menuSettings.add(settings());
        setJMenuBar(menuBar);
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileOpen.setDialogTitle("Загрузка функции");
        fileOpen.addChoosableFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        fileOpen.setAcceptAllFileFilterUsed(false);
        JFileChooser fileSave = new JFileChooser();
        fileSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileSave.setDialogTitle("Сохранение функции");
        fileSave.addChoosableFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        fileSave.setAcceptAllFileFilterUsed(false);
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        tableModel = new TableModel(xValues, yValues);
        table = new JTable(tableModel);
        setSize(new Dimension(1000, 500));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        JButton create = new JButton("Создать");
        create.addActionListener(e -> {
            xValues.clear();
            yValues.clear();
            TabulatedFunctionWindow table = new TabulatedFunctionWindow(factory);
            //table.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
            table.setVisible(true);
            //function1 = table.getFunction();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                xValues.add(table.getXValues().get(i));
                yValues.add(table.getYValues().get(i));
            }
            tableModel.fireTableDataChanged();
            //function = table.getFunction();
        });
        JButton load = new JButton("Загрузить");
        load.addActionListener(e -> {
            xValues.clear();
            yValues.clear();
            fileOpen.showOpenDialog(calculator);
            File file = fileOpen.getSelectedFile();
            if (file != null) {
                try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                    TabulatedFunction function = FunctionsIO.readTabulatedFunction(in, factory);
                    for (int i = 0; i < function.getCount(); i++) {
                        xValues.add(i, String.valueOf(function.getX(i)));
                        yValues.add(i, String.valueOf(function.getY(i)));
                        tableModel.fireTableDataChanged();
                    }
                    double[] xValues2 = new double[function.getCount()];
                    double[] yValues2 = new double[function.getCount()];
                    for (int i = 0; i < table.getRowCount(); i++) {
                        xValues2[i] = Double.parseDouble(xValues.get(i));
                        yValues2[i] = Double.parseDouble(yValues.get(i));
                    }
                    size = function.getCount();
                    System.out.println(function.toString());
                    function1 = factory.create(xValues2, yValues2);
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
        });
        JButton save = new JButton("Сохранить");
        save.addActionListener(e -> {
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(calculator, "Создайте функцию");
            } else {
                fileSave.showSaveDialog(calculator);
                File file = fileSave.getSelectedFile();
                if (file != null) {
                    double[] xValues2 = new double[table.getRowCount()];
                    double[] yValues2 = new double[table.getRowCount()];
                    for (int i = 0; i < table.getRowCount(); i++) {
                        xValues2[i] = Double.parseDouble(table.getValueAt(i, 0).toString());
                        yValues2[i] = Double.parseDouble(table.getValueAt(i, 1).toString());
                    }

                    function1 = factory.create(xValues2, yValues2);

                    try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
                        FunctionsIO.writeTabulatedFunction(out, function1);
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                }
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane = new JScrollPane(table);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup().addComponent(create).addComponent(load).addComponent(save))
                .addComponent(tableScrollPane)
                .addGroup(layout.createParallelGroup()));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup().addComponent(create).addComponent(load).addComponent(save))
                .addComponent(tableScrollPane)
                .addGroup(layout.createSequentialGroup()));
        setLocationRelativeTo(null);
    }

    private JMenu settings() {
        JMenu set = new JMenu("Выбор фабрики");
        JMenuItem item = new JMenuItem("Открыть");
        set.add(item);
        item.addActionListener(e -> {
            SettingMenu settings = new SettingMenu();
            settings.setVisible(true);
            factory = settings.getFactory();
        });
        return set;
    }


}
