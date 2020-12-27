package ru.ssau.tk.kintoho.LR.ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import ru.ssau.tk.kintoho.LR.exceptions.*;

public class Errors {
    Errors(Component parent, Exception e) {
        showErrorWindow(parent, e);
    }

    public void showErrorWindow(Component parent, Exception e) {
        String head = generateMessageForException(e);
        JOptionPane.showMessageDialog(parent, "Ошибка", head, JOptionPane.ERROR_MESSAGE);
    }

    private String generateMessageForException(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return "Задана некорректная функция";
        }
        if (e instanceof ArrayIsNotSortedException) {
            return "Массив неотсортирован";
        }
        if (e instanceof IOException) {
            return "Ошибка ввода/вывода";
        }
        if (e instanceof InconsistentFunctionsException) {
            return "Разная длина массивов";
        }

        {
            e.printStackTrace();
            return "Ты чево наделал...";
        }
    }
}
