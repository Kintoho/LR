package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.*;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MathFunctions extends JDialog {
    private final JComboBox<String> functionComboBox = new JComboBox<>();
    private final JButton buttonCreateFunction = new JButton("Создать функцию");
    private final JLabel fromLabel = new JLabel("От");
    private final JLabel toLabel = new JLabel("До");
    private final JLabel countLabel = new JLabel("Количествo");
    private final JTextField countField = new JTextField();
    private final JTextField fromField = new JTextField();
    private final JTextField toField = new JTextField();
    private final Map<String, MathFunction> nameFunctionMap = new HashMap<>();
    protected TabulatedFunction function;

    public MathFunctions(TabulatedFunctionFactory factory) {
        getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setModal(true);
        setSize(500, 150);
        setTitle("Function");
        fillMap();
        getContentPane().add(countLabel);
        getContentPane().add(countField);
        getContentPane().add(fromLabel);
        getContentPane().add(fromField);
        getContentPane().add(toLabel);
        getContentPane().add(toField);
        getContentPane().add(buttonCreateFunction);
        getContentPane().add(functionComboBox);
        compose();
        addButtonListeners(factory);
        setLocationRelativeTo(null);
    }

    private void addButtonListeners(TabulatedFunctionFactory factory) {
        buttonCreateFunction.addActionListener(evt -> {
            try {
                String func = (String) functionComboBox.getSelectedItem();
                MathFunction selectedFunction = nameFunctionMap.get(func);
                double xFrom = Double.parseDouble(fromField.getText());
                double xTo = Double.parseDouble(toField.getText());
                int count = Integer.parseInt(countField.getText());
                function = factory.create(selectedFunction, xFrom, xTo, count);
                System.out.println(function.toString());
                setVisible(true);
                dispose();
            } catch (Exception e) {
                new Errors(this, e);
            }
        });
    }

    public void fillMap() {
        nameFunctionMap.put("Единичная функция", new UnitFunction());
        nameFunctionMap.put("Квадратичная функция", new SqrFunction());
        nameFunctionMap.put("Модуль", new AbsoluteValueFunction());
        nameFunctionMap.put("Нулевая функция", new ZeroFunction());
        nameFunctionMap.put("Тождественная функция", new IdentityFunction());
        nameFunctionMap.put("Увеличение значения на 5", new PlusFunction());

        String[] functions = new String[6];
        int i = 0;
        for (String string : nameFunctionMap.keySet()) {
            functions[i++] = string;
        }
        Arrays.sort(functions);
        for (String string : functions) {
            functionComboBox.addItem(string);
        }
    }

    public void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(fromLabel)
                        .addComponent(fromField)
                        .addComponent(toLabel)
                        .addComponent(toField)
                        .addComponent(countLabel)
                        .addComponent(countField))
                .addComponent(functionComboBox)
                .addComponent(buttonCreateFunction)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(fromLabel)
                        .addComponent(fromField)
                        .addComponent(toLabel)
                        .addComponent(toField)
                        .addComponent(countLabel)
                        .addComponent(countField))
                .addComponent(functionComboBox)
                .addComponent(buttonCreateFunction)
        );

    }
}