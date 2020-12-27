package ru.ssau.tk.kintoho.LR.ui;


import javax.swing.*;
import java.awt.*;


public class Window extends JFrame {
    JMenu functions, settings;
    JMenuItem element1, element2;
    JButton settingsButton = new JButton("Настройки");

    public Window() {
        JFrame window = new JFrame("Calculator");
        functions = new JMenu("Functions");
        settings = new JMenu("Settings");
        JMenuBar bar = new JMenuBar();
        bar.add(functions);
        element1 = new JMenu("TabulatedFunction");
        element2 = new JMenu("MathFunctions");
        functions.add(element1);
        functions.add(element2);
        element2.add(new JMenuItem("Единичная функция"));
        element2.add(new JMenuItem("Квадратичная функция"));
        element2.add(new JMenuItem("Модуль"));
        element2.add(new JMenuItem("Нулевая функция"));
        element2.add(new JMenuItem("Тождественная функция"));
        element2.add(new JMenuItem("Увеличение значения на 5"));

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