package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CreateFunc extends JDialog {
    private final JLabel labelCount = new JLabel("Количество точек: ");
    private final JTextField textFieldCount = new JTextField(2);
    private final JLabel labelInterval = new JLabel("Интервал: ");
    private final JLabel labelBracket1 = new JLabel("[ ");
    private final JLabel labelBracket3 = new JLabel(" ]");
    private final JLabel labelBracket2 = new JLabel(" ; ");
    private final JTextField textFieldTo = new JTextField();
    private final JTextField textFieldFrom = new JTextField();
    private final JButton buttonCreateFunction = new JButton("Создать функцию");
    public TabulatedFunction function;
    protected static JCheckBox checkBoxSave = new JCheckBox("Сохранить функцию");
    Map<String, MathFunction> functionMap = new HashMap<>();
    JComboBox<String> comboBoxFunctions = showComboBox();

    protected CreateFunc(Consumer<? super TabulatedFunction> callback) {
        super();
        getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setBounds(50, 50, 300, 200);
        getContentPane().add(labelCount);
        getContentPane().add(textFieldCount);
        getContentPane().add(labelInterval);
        getContentPane().add(labelBracket1);
        getContentPane().add(textFieldFrom);
        getContentPane().add(labelBracket2);
        getContentPane().add(textFieldTo);
        getContentPane().add(labelBracket3);
        getContentPane().add(buttonCreateFunction);
        getContentPane().add(comboBoxFunctions);
        compose();
        addButtonListeners();
        setVisible(true);
        callback.accept(function);
        dispose();
    }

    private void addButtonListeners() {
        buttonCreateFunction.addActionListener(
                e -> {
                    int count = Integer.parseInt(textFieldCount.getText());
                    double from = Double.parseDouble(textFieldFrom.getText());
                    double to = Double.parseDouble(textFieldTo.getText());
                    String str = comboBoxFunctions.getItemAt(comboBoxFunctions.getSelectedIndex());
                    if (str.equals("Константная функция")) {
                        String result = JOptionPane.showInputDialog("Введите значение константы");
                        double constant = Double.parseDouble(result);
                        function = Window.factory.create(new ConstantFunction(constant), from, to, count);
                    } else {
                        MathFunction mathFunction = functionMap.get(str);
                        function = Window.factory.create(mathFunction, from, to, count);
                    }
                    dispose();
                    System.out.println(function.toString());
                }
        );
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(labelCount)
                        .addComponent(textFieldCount))
                .addComponent(labelInterval)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(labelBracket1)
                        .addComponent(textFieldFrom)
                        .addComponent(labelBracket2)
                        .addComponent(textFieldTo)
                        .addComponent(labelBracket3))
                .addComponent(comboBoxFunctions)
                .addComponent(checkBoxSave)
                .addComponent(buttonCreateFunction)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelCount)
                        .addComponent(textFieldCount))
                .addComponent(labelInterval)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelBracket1)
                        .addComponent(textFieldFrom)
                        .addComponent(labelBracket2)
                        .addComponent(textFieldTo)
                        .addComponent(labelBracket3))
                .addComponent(comboBoxFunctions)
                .addComponent(checkBoxSave)
                .addComponent(buttonCreateFunction));
        setLocationRelativeTo(null);
    }

    private JComboBox<String> showComboBox() {
        functionMap.put("Единичная функция", new UnitFunction());
        functionMap.put("Квадратичная функция", new SqrFunction());
        functionMap.put("Модуль", new AbsoluteValueFunction());
        functionMap.put("Нулевая функция", new ZeroFunction());
        functionMap.put("Тождественная функция", new IdentityFunction());
        functionMap.put("Увеличение значения на 5", new PlusFunction());
        DefaultComboBoxModel<String> functions = new DefaultComboBoxModel<>();
        functions.addElement("Единичная функция");
        functions.addElement("Квадратичная функция");
        functions.addElement("Модуль");
        functions.addElement("Нулевая функция");
        functions.addElement("Тождественная функция");
        functions.addElement("Увеличение значения на 5");
        return new JComboBox<>(functions);
    }
}
