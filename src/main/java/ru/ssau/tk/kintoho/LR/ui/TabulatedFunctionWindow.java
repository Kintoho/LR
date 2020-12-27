package ru.ssau.tk.kintoho.LR.ui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

import ru.ssau.tk.kintoho.LR.functions.*;
import ru.ssau.tk.kintoho.LR.functions.factory.*;
import ru.ssau.tk.kintoho.LR.exceptions.*;

public class TabulatedFunctionWindow extends JDialog {

    private final List<Double> xValues = new ArrayList<>();
    private final List<Double> yValues = new ArrayList<>();
    private final AbstractTableModel tableModel = new TableModel(xValues, yValues);
    private final JTable table = new JTable(tableModel);
    private final JLabel label = new JLabel("Введите count:");
    private final JTextField countField = new JTextField("");
    private final JButton inputButton = new JButton("Ввести");
    private final JButton createButton = new JButton("Создать таблицу");
    private TabulatedFunction function;

    public TabulatedFunctionWindow() {
        super();
        getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(200, 200, 400, 500);
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
        addButtonListeners();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void addButtonListeners() {
        addListenerForInputButton();
        addListenerForCreateButton();
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
                    xValues.add(0.);
                    yValues.add(0.);
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
    public void addListenerForCreateButton() {
        createButton.addActionListener(event -> {
            try {
                double[] x = new double[xValues.size()];
                double[] y = new double[yValues.size()];
                x[0] = xValues.get(0);
                y[0] = yValues.get(0);
                for (int i = 1; i < xValues.size(); i++) {
                    if (xValues.get(i - 1) > xValues.get(i)) {
                        throw new ArrayIsNotSortedException();
                    }
                    x[i] = xValues.get(i);
                    y[i] = yValues.get(i);

                }
                function = new ArrayTabulatedFunctionFactory().create(x, y);
                System.out.println(function.toString());
            } catch (Exception e) {
                new Errors(this, e);
            }
            dispose();
        });
    }

    public static void main(String[] args) {
        new TabulatedFunctionWindow();
    }
}

