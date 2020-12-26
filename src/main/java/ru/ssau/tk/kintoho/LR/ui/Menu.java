package ru.ssau.tk.kintoho.LR.ui;

import javax.swing.*;

public class Menu extends JFrame{
    Menu() {
        JMenu menu = new JMenu("Functions");
        JMenuBar jMenuBar = new JMenuBar();
        menu.add(new JMenuItem("TabulatedFunction"));
        menu.add(new JMenuItem("MathFunction"));
        jMenuBar.add(menu);
        setJMenuBar(jMenuBar);
        setSize(400, 400);
    }
}
