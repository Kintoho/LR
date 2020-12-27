package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.*;
import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MathFunctions extends JDialog {
    private JComboBox<String> functionComboBox = new JComboBox<>();
    private JLabel fromLabel = new JLabel("От");
    private JLabel toLabel = new JLabel("До");
    private JLabel countLabel = new JLabel("Количествo");
    private JTextField countField = new JTextField();
    private JTextField fromField = new JTextField();
    private JTextField toField = new JTextField();
    private JButton okButton = new JButton("OK");
    private Map<String, MathFunction> nameFunctionMap = new HashMap<>();

    TabulatedFunctionFactory factory;
    TabulatedFunction function;

    public MathFunctions(TabulatedFunctionFactory factory, Consumer<? super TabulatedFunction> callback) {
        setModal(true);
        this.factory = factory;
        this.setBounds(300, 200, 500, 150);
        fillMap();
        compose();

        setLocationRelativeTo(null);
    }

    public static void main(TabulatedFunctionFactory factory, Consumer<? super TabulatedFunction> callback) {
        MathFunctions app = new MathFunctions(factory, callback);
        app.setVisible(true);
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
                .addComponent(okButton)
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
                .addComponent(okButton)
        );
    }

    public void addButtonListeners(Consumer<? super TabulatedFunction> callback) {
        okButton.addActionListener(event -> {
            try {
                String func = (String) functionComboBox.getSelectedItem();
                MathFunction selectedFunction = nameFunctionMap.get(func);
                double from = Double.parseDouble(fromField.getText());
                double to = Double.parseDouble(toField.getText());
                int count = Integer.parseInt(countField.getText());
                MathFunction  mathFunction = nameFunctionMap.get(func);
                function = new ArrayTabulatedFunctionFactory().create(mathFunction, from, to, count);
                callback.accept(function);
                this.dispose();
            } catch (Exception e) {
                Errors errorWindow = new Errors(this, e);
                errorWindow.showErrorWindow(this, e);
            }
        });
    }

}
