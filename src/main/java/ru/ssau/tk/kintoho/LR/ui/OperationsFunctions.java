package ru.ssau.tk.kintoho.LR.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.ssau.tk.kintoho.LR.functions.*;
import ru.ssau.tk.kintoho.LR.operations.*;
import ru.ssau.tk.kintoho.LR.functions.factory.*;

public class OperationsFunctions extends JDialog {
    Map<String, TabulatedFunction> map = new HashMap<>();
    TabulatedFunctionOperationService service;
    TabulatedFunction func3;
    private final ArrayList<String> stringsX3;
    private final ArrayList<String> stringsY3;

    public OperationsFunctions(int size, TabulatedFunctionFactory factory, TabulatedFunction func1, TabulatedFunction func2) {
        JDialog dialog = new JDialog();
        setModal(true);
        setSize(new Dimension(400, 100));
        setLocationRelativeTo(null);
        service = new TabulatedFunctionOperationService(factory);
        stringsY3 = new ArrayList<>();
        stringsX3 = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            stringsX3.add("");
            stringsY3.add("");
        }
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

    public TabulatedFunction getFunction3() {
        return func3;
    }

    public ArrayList<String> getStringsX3() {
        return stringsX3;
    }

    public ArrayList<String> getStringsY3() {
        return stringsY3;
    }
}
