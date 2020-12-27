package ru.ssau.tk.kintoho.LR.ui;


import javax.swing.*;
import java.awt.*;


public class Window extends JFrame {
    JButton settingsButton = new JButton("Настройки");

    public Window() {
        super();
        getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 500, 500);
        getContentPane().add(settingsButton);
        compose();
        addButtonListeners();
        setVisible(true);
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