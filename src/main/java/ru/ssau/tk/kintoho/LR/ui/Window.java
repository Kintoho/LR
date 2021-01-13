package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.kintoho.LR.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    JMenu settings, functions;
    protected static TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();

    public Window() {
        JFrame window = new JFrame("Калькулятор");
        Image image = new ImageIcon("Хабиб.jpeg").getImage();
        window.setContentPane(new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, null);
            }
        });
        functions = new JMenu("Функции");
        settings = new JMenu("Настройки");
        JButton calculate = new JButton("Калькулятор");
        JMenuBar bar = new JMenuBar();
        bar.add(functions);
        bar.add(settings);
        functions.add(createMathFunction());
        functions.add(createTabulatedFunction());
        settings.add(settingsMenu());
        JButton diff = new JButton("Дифференцировать");
        diff.setSize(50, 50);
        window.add(diff);
        calculate.setSize(50, 50);
        window.add(calculate);
        window.setLayout(new FlowLayout());
        window.setJMenuBar(bar);
        window.setSize(600, 300);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calculate.addActionListener(e -> {
            Calculator calculator = new Calculator();
            calculator.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
            calculator.setVisible(true);
        });
        diff.addActionListener(e -> {
            DifferentialWindow differentialOperator = new DifferentialWindow();
            differentialOperator.setVisible(true);
        });
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
    }

    private JMenu settingsMenu() {
        JMenu settings = new JMenu("Настройки");
        JMenuItem open = new JMenuItem("Открыть");
        settings.add(open);
        open.addActionListener(e -> {
            SettingMenu settings1 = new SettingMenu();
            settings1.setVisible(true);
            factory = settings1.getFactory();
        });
        return settings;
    }

    private JMenu createMathFunction() {
        JMenu functions = new JMenu("Математические функции");
        JMenuItem open = new JMenuItem("Открыть");
        functions.add(open);
        open.addActionListener(e -> {
            MathFunctions open1 = new MathFunctions(factory);
            open1.setVisible(true);
        });
        return functions;
    }

    private JMenu createTabulatedFunction() {
        JMenu functions = new JMenu("Табулированная функция");
        JMenuItem itemCreate = new JMenuItem("Создать");
        functions.add(itemCreate);
        itemCreate.addActionListener(e -> {
            TabulatedFunctionWindow tabulatedFunction = new TabulatedFunctionWindow(factory);
            tabulatedFunction.setVisible(true);
        });
        return functions;
    }

    public static void main(String[] args) {
        new Window();
    }
}