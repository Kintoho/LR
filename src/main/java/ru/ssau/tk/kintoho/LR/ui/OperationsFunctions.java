package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.operations.TabulatedFunctionOperationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;

public class OperationsFunctions extends JDialog {
    private final Map<String, TabulatedFunction> map = new HashMap<>();
    protected  TabulatedFunctionOperationService service;
    protected TabulatedFunction func3;

    public OperationsFunctions( TabulatedFunctionFactory factory, TabulatedFunction func1, TabulatedFunction func2) {
        setModal(true);
        setSize(new Dimension(400, 100));
        setLocationRelativeTo(null);
        service = new TabulatedFunctionOperationService(factory);
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"", "Сумма", "Разность", "Произведение", "Частное"
        });
        map.put("Сумма", service.sum(func1, func2));
        map.put("Разность", service.subtraction(func1, func2));
        map.put("Произведение", service.multiplication(func1, func2));
        map.put("Частное", service.division(func1, func2));
        comboBox.setEditable(true);
        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                func3 = map.get(e.getItem().toString());
            }
        });
        JButton result = new JButton("Выполнить");
        result.addActionListener(e -> {
            System.out.println(func3);
            dispose();
        });
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup().addComponent(comboBox).addComponent(result)));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(comboBox).addComponent(result)));
    }
}
