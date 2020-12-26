package ru.ssau.tk.kintoho.LR.ui;

import javax.swing.*;

public class Window {
    Window(){
        JFrame jFrame = new JFrame("Calculator");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(920, 410);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }

    public static void main(String[] args){
        new Window();
    }
}
