package ru.ssau.tk.kintoho.LR.ui;


import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;


public class Window extends JFrame {
    JMenu settings, functions;
    private TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
    public Window() {
        JFrame window = new JFrame("Calculator");
        functions = new JMenu("Functions");
        settings = new JMenu("Settings");
        JMenuBar bar = new JMenuBar();
        bar.add(functions);
        bar.add(settings);
        functions.add(createMathFunction());
        functions.add(createTabulatedFunction());
        settings.add(settingsMenu());

        window.setJMenuBar(bar);
        window.setSize(900, 600);
        window.setLayout(null);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    private JMenu createMathFunction() {
        JMenu functions = new JMenu("MathFunctions");
        JMenuItem open = new JMenuItem("Open");
        functions.add(open);
        open.addActionListener(e -> {
            MathFunctions open1 = new MathFunctions();
            open1.setVisible(true);
        });
        return functions;
    }

    private JMenu createTabulatedFunction() {
        JMenu functions = new JMenu("TabulatedFunction");
        JMenuItem itemCreate = new JMenuItem("Create");
        functions.add(itemCreate);
        itemCreate.addActionListener(e -> {
            TabulatedFunctionWindow tabulatedFunction = new TabulatedFunctionWindow(factory);
            tabulatedFunction.setVisible(true);
        });
        return functions;
    }

    private JMenu settingsMenu() {
        JMenu settings = new JMenu("Setting");
        JMenuItem open = new JMenuItem("Open");
        settings.add(open);
        open.addActionListener(e -> {
            SettingMenu settings1 = new SettingMenu();
            settings1.setVisible(true);
            factory = settings1.getFactory();
        });
        return settings;
    }

    public static void main(String[] args) {
       new Window();
    }
}