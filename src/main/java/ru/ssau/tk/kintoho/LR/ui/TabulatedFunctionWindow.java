package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.exceptions.ArrayIsNotSortedException;
import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TabulatedFunctionWindow extends JDialog {

    private final List<String> xValues = new ArrayList<>();
    private final List<String> yValues = new ArrayList<>();
    private final AbstractTableModel tableModel = new TableModel(xValues, yValues);
    private final JTable table = new JTable(tableModel);
    private final JLabel label = new JLabel("Введите число элементов:");
    private final JTextField countField = new JTextField();
    private final JButton inputButton = new JButton("Ввести");
    private final JButton createButton = new JButton("Создать таблицу");
    private TabulatedFunction function;
    int err = 1;

    public TabulatedFunctionWindow(TabulatedFunctionFactory factory) {
        setModal(true);
        getContentPane().setLayout(new FlowLayout());
        setSize(400, 500);
        Font fontLabel = new Font("Serif", Font.PLAIN, 14);
        label.setFont(fontLabel);
        Font fontInputButton = new Font("Serif", Font.PLAIN, 14);
        inputButton.setFont(fontInputButton);
        Font fontCreateButton = new Font("Serif", Font.PLAIN, 14);
        createButton.setFont(fontCreateButton);
        setTitle("Таблица");
        getContentPane().add(label);
        getContentPane().add(countField);
        getContentPane().add(inputButton);
        getContentPane().add(createButton);
        compose();
        addListenerForInputButton();
        addListenerForCreateButton(factory);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setLocationRelativeTo(null);
    }


    //метод для очистки таблицы
    public void clearTable(int n) {
        for (int i = 0; i < n; i++) {
            xValues.remove(n - i - 1);
            yValues.remove(n - i - 1);
            tableModel.fireTableDataChanged();
        }
    }

    //добавляем реализацию кнопки ввода
    public void addListenerForInputButton() {
        inputButton.addActionListener(event -> {
            try {
                createButton.setEnabled(false);
                int count = Integer.parseInt(countField.getText());
                clearTable(tableModel.getRowCount());
                for (int i = 0; i < count; i++) {
                    xValues.add("");
                    yValues.add("");
                    tableModel.fireTableDataChanged();
                }
                if (tableModel.getRowCount() > 1) {
                    createButton.setEnabled(true);
                }
            } catch (Exception e) {
                new Errors(this, e);
            }
        });
    }

    //добавляем реализацию кнопки "Создать" и выводим полученную функцию в консоль для проверки
    public void addListenerForCreateButton(TabulatedFunctionFactory factory) {
        createButton.addActionListener(event -> {
            try {
                double[] x = new double[xValues.size()];
                double[] y = new double[yValues.size()];

                for (int i = 0; i < xValues.size() - 1; i++) {
                    if (Double.parseDouble(xValues.get(i)) > Double.parseDouble(xValues.get(i + 1))) {
                        err = 0;
                    }
                }
                if (err != 0) {
                    for (int i = 0; i < xValues.size(); i++) {
                        x[i] = Double.parseDouble(xValues.get(i));
                        y[i] = Double.parseDouble(yValues.get(i));
                    }
                } else {
                    throw new ArrayIsNotSortedException();
                }

                function = factory.create(x,y);
                System.out.println(function.toString());
            } catch (Exception e) {
                new Errors(this, e);
            }
            dispose();
        });
    }

    void compose() {
        //создаём менеджер компановки и скроллинг
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane = new JScrollPane(table);
        //реализуем горизонтальную компановку
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(label)
                        .addComponent(countField)
                        .addComponent(inputButton))
                .addComponent(tableScrollPane)
                .addComponent(createButton)
        );
        //реализуем вертикальную компановку
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(label)
                        .addComponent(countField)
                        .addComponent(inputButton))
                .addComponent(tableScrollPane)
                .addComponent(createButton)
        );
    }

    public TabulatedFunction getFunction() {
        return function;
    }

    public void setFunction(TabulatedFunction function) {
        this.function = function;
    }

    public List<String> getXValues() {
        return xValues;
    }

    public List<String> getYValues() {
        return yValues;
    }
    public int getCount(){
        return Integer.parseInt(countField.getText());
    }
}