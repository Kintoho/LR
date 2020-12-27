package ru.ssau.tk.kintoho.LR.ui;


import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Window extends JFrame {
    JMenu settings, functions;
    private TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
    private WindowModel tableModel = new WindowModel();
    public Window() {
        JFrame window = new JFrame("Calculator");
        functions = new JMenu("Functions");
        settings = new JMenu("Settings");
        JMenuBar bar = new JMenuBar();
        bar.add(functions);
        bar.add(settings);
        functions.add(createMathFunction());
        functions.add(createTabulatedFunction());

        window.setJMenuBar(bar);
        window.setSize(900, 600);
        window.setLayout(null);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
    }

    private JMenu createMathFunction() {
        JMenu functions = new JMenu("MathFunctions");
        JMenuItem open = new JMenuItem("Открыть");
        functions.add(open);
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MathFunctions open = new MathFunctions(factory, data -> tableModel.setFunction(data));
                open.setVisible(true);
            }
        });
        return functions;
    }

    private JMenu createTabulatedFunction() {
        JMenu functions = new JMenu("TabulatedFunction");
        JMenuItem itemCreate = new JMenuItem("Create");
        functions.add(itemCreate);
        itemCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TabulatedFunctionWindow tabulatedFunction = new TabulatedFunctionWindow(factory);
                tabulatedFunction.setVisible(true);
            }
        });
        return functions;
    }
    private void addButtonListeners() {
        addListenerForInputButton();
        addListenerForCreateButton();
        addListenerForCountButton();
    }
    private void addListenerForCountButton() {
    }

    private void addListenerForCreateButton() {
    }

    private void addListenerForInputButton() {
    }
    public static void main(String[] args) {
       new Window();
    }
}