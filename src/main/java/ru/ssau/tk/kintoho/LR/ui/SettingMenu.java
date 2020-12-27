package ru.ssau.tk.kintoho.LR.ui;

import javax.swing.*;
import java.awt.*;
import java.util.*;


import ru.ssau.tk.kintoho.LR.functions.factory.*;

public class SettingMenu extends JDialog {
    private final JLabel label = new JLabel("Выберите тип фабрики:");
    private final JButton okButton = new JButton("OК");
    private final Map<String, TabulatedFunctionFactory> mapFunction = new HashMap<>();
    private final JComboBox<String> functionComboBox = new JComboBox<>();
    TabulatedFunctionFactory factory;

    public SettingMenu(TabulatedFunctionFactory factory) {
        this.factory = factory;
        Font fontLabel = new Font("Serif", Font.PLAIN, 14);
        label.setFont(fontLabel);
        Font okLabel = new Font("Serif", Font.PLAIN, 14);
        okButton.setFont(fontLabel);
        setTitle("Настройка");
        setSize(300, 300);
        fillMap();
        compose();
        setModal(true);
        addButtonListeners();
        setLocationRelativeTo(null);
    }

    public void compose() {
        //создаём менеджер компановки и скроллинг
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        //реализуем горизонтальную компановку
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(label)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(functionComboBox)
                        .addComponent(okButton))
        );
        //реализуем вертикальную компановку
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(functionComboBox)
                        .addComponent(okButton)
                ));
    }
//заполняем мапу
    public void fillMap() {
        mapFunction.put("Linked List", new LinkedListTabulatedFunctionFactory());
        mapFunction.put("Array", new ArrayTabulatedFunctionFactory());
        String[] functions = new String[2];
        int i = 0;
        for (String string : mapFunction.keySet()) {
            functions[i++] = string;
        }
        Arrays.sort(functions);
        for (String string : functions) {
            functionComboBox.addItem(string);
        }
    }
//реализация кнопки "OK"
    public void addButtonListeners() {
        okButton.addActionListener(event -> {
            try {
                String func = (String) functionComboBox.getSelectedItem();
                this.factory = mapFunction.get(func);
                this.dispose();
            } catch (Exception e) {
                Errors errorWindow = new Errors(this, e);
                errorWindow.showErrorWindow(this, e);
            }
        });
    }

    public static void main(TabulatedFunctionFactory factory) {
        SettingMenu settingsFrame = new SettingMenu(factory);
        settingsFrame.setVisible(true);
    }

}
