package ru.ssau.tk.kintoho.LR.ui;

import javax.swing.*;
import java.awt.*;


import ru.ssau.tk.kintoho.LR.functions.factory.*;

public class SettingMenu extends JDialog {
    private TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
    JRadioButton array;
    JRadioButton list;

    public SettingMenu() {
        super();
        setModal(true);
        setLocationRelativeTo(null);
        setSize(new Dimension(300, 300));
        JLabel label = new JLabel("Выберите фабрику:");
        array = new JRadioButton("Массив");
        list = new JRadioButton("Связный Список");
        ButtonGroup group = new ButtonGroup();
        group.add(array);
        group.add(list);
        array.addActionListener(e -> {
            if (e.getSource() == array) {
                factory = new ArrayTabulatedFunctionFactory();
            }
        });
        list.addActionListener(e -> {
            if (e.getSource() == list) {
                factory = new LinkedListTabulatedFunctionFactory();
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup().addComponent(label))
                .addGroup(layout.createSequentialGroup().addComponent(array).addComponent(list)));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(label))
                .addGroup(layout.createParallelGroup().addComponent(array).addComponent(list)));

    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }
}