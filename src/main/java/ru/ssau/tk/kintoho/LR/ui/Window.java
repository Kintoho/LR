package ru.ssau.tk.kintoho.LR.ui;

import javax.swing.*;

public class Window extends JFrame {
    JMenu menu1, menu2;
    JMenuItem a1, a2;

    public Window() {
        JFrame window = new JFrame("Calculator");
        menu1 = new JMenu("Functions");
        JMenuBar bar = new JMenuBar();
        a1 = new JMenuItem("TabulatedFunction");
        a2 = new JMenuItem("MathFunction");
        menu1.add(a1);
        menu1.add(a2);
        bar.add(menu1);
        window.setJMenuBar(bar);
        window.setSize(900, 600);
        window.setLayout(null);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new Window();
    }
}