package ru.ssau.tk.kintoho.LR.ui;

import ru.ssau.tk.kintoho.LR.functions.TabulatedFunction;
import ru.ssau.tk.kintoho.LR.io.FunctionsIO;
import ru.ssau.tk.kintoho.LR.operations.TabulatedDifferentialOperator;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class DifferentialWindow extends JDialog {

    private TabulatedFunction functionResult;
    private TabulatedFunction firstFunction;
    private final TableModelTabulated tableModelResult = new TableModelTabulated(functionResult) {
        @Serial
        private static final long serialVersionUID = 3187267819258444868L;

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    };
    private final JTable tableResult = new JTable(tableModelResult);
    private final TableModelTabulated tableModel = new TableModelTabulated(firstFunction);
    private final JTable table = new JTable(tableModel);
    private final JButton buttonResult = new JButton("Вычислить");
    private final JButton buttonSaveResult = new JButton("Сохранить результат");
    private final JButton buttonCreate = new JButton("Создать");
    private final JButton buttonSave = new JButton("Сохранить");
    private final JButton buttonDownload = new JButton("Загрузить");
    private final JFileChooser save = new JFileChooser();
    private final JFileChooser downloadChooser = new JFileChooser();
    private final TabulatedDifferentialOperator differentialOperator;

    protected DifferentialWindow() {
        getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setModal(true);
        setBounds(100, 100, 800, 400);
        differentialOperator = new TabulatedDifferentialOperator(Window.factory);
        compose();
        addButtonListeners();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setLocationRelativeTo(null);
    }

    private void addButtonListeners() {

        buttonCreate.addActionListener(e -> {
            Object[] buttonsName = {"Массив", "Функция", "Отмена"};
            int resultDialog = JOptionPane.showOptionDialog(new JFrame(), "Выберите способ создания",
                    "Создать", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, buttonsName, buttonsName[2]);
            if (resultDialog == 1) {
                MathFunctions tabulatedFunction = new MathFunctions(Window.factory);
                setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
                setVisible(true);
                firstFunction = tabulatedFunction.getFunction();
                tableModel.setFunction(firstFunction);
                tableModel.fireTableDataChanged();
            }
            if (resultDialog == 0) {
                TabulatedFunctionWindow table = new TabulatedFunctionWindow(Window.factory);
                table.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
                table.setVisible(true);
                firstFunction = table.getFunction();
                tableModel.setFunction(firstFunction);
                tableModel.fireTableDataChanged();
            }
        });

        buttonResult.addActionListener(e -> {
            table.clearSelection();
            functionResult = differentialOperator.derive(firstFunction);
            tableModelResult.setFunction(functionResult);
            tableModelResult.fireTableDataChanged();
        });

        buttonSave.addActionListener(e -> {
            int returnVal = save.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = save.getSelectedFile() + ".bin";
                int flag = 1;
                File file = new File(fileName);
                if (file.exists()) {
                    flag = -1;
                    int ind = JOptionPane.showConfirmDialog(this, "Файл с таким названием существует.",
                            "Предупреждение", JOptionPane.YES_NO_OPTION);
                    if (ind == 0) {
                        JOptionPane.showConfirmDialog(this, "Введите название файла", "Предупреждение", JOptionPane.YES_NO_OPTION);
                    }
                }
                if (flag != -1) {
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        if (firstFunction != null) {
                            FunctionsIO.serialize(out, firstFunction);
                            JOptionPane.showMessageDialog(this,
                                    "Файл '" + file.getName() +
                                            " сохранен");
                        } else {
                            throw new IOException();
                        }
                    } catch (Exception exception) {
                        new Errors(this, exception);
                    }
                }
            }
        });

        buttonSaveResult.addActionListener(e -> {
            int returnVal = save.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = save.getSelectedFile() + ".bin";
                int flag = 1;
                File file = new File(fileName);
                if (file.exists()) {
                    flag = -1;
                    int ind = JOptionPane.showConfirmDialog(this, "Файл с таким названием существует.",
                            "Предупреждение", JOptionPane.YES_NO_OPTION);
                    if (ind == 0) {
                        JOptionPane.showConfirmDialog(this, "Введите название файла", "Предупреждение", JOptionPane.YES_NO_OPTION);
                    }
                }
                if (flag != -1) {
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        if (functionResult != null) {
                            FunctionsIO.serialize(out, functionResult);
                            JOptionPane.showMessageDialog(this,
                                    "Файл '" + file.getName() + " сохранен");
                        } else {
                            throw new IOException();
                        }
                    } catch (Exception exception) {
                        new Errors(this, exception);
                    }
                }
            }
        });

        buttonDownload.addActionListener(e ->
        {
            int returnVal = downloadChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = downloadChooser.getSelectedFile();
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {

                    firstFunction = FunctionsIO.deserialize(in);
                    for (int i = 0; i < firstFunction.getCount(); i++) {
                        tableModel.setFunction(firstFunction);
                        tableModel.fireTableDataChanged();
                        buttonResult.setEnabled(true);
                    }
                } catch (IOException | ClassNotFoundException exception) {
                    new Errors(this, exception);
                }
            }
        });
    }

    private void compose() {
        JPanel panelResult = new JPanel();
        GroupLayout layoutResult = new GroupLayout(panelResult);
        panelResult.setLayout(layoutResult);
        layoutResult.setAutoCreateGaps(true);
        layoutResult.setAutoCreateContainerGaps(true);

        JScrollPane scrollPaneResult = new JScrollPane(tableResult);
        layoutResult.setHorizontalGroup(
                layoutResult.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layoutResult.createSequentialGroup()
                                .addComponent(buttonSaveResult)
                                .addComponent(buttonResult))
                        .addComponent(scrollPaneResult));

        layoutResult.setVerticalGroup(layoutResult.createSequentialGroup()
                .addGroup(layoutResult.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonSaveResult)
                        .addComponent(buttonResult))
                .addComponent(scrollPaneResult));


        JPanel panelInitial = new JPanel();
        GroupLayout layoutInitial = new GroupLayout(panelInitial);
        panelInitial.setLayout(layoutInitial);
        layoutInitial.setAutoCreateGaps(true);
        layoutInitial.setAutoCreateContainerGaps(true);
        JScrollPane scrollPaneInitial = new JScrollPane(table);
        layoutInitial.setHorizontalGroup(
                layoutInitial.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layoutInitial.createSequentialGroup()
                                .addComponent(buttonCreate)
                                .addComponent(buttonDownload)
                                .addComponent(buttonSave))
                        .addComponent(scrollPaneInitial));

        layoutInitial.setVerticalGroup(layoutInitial.createSequentialGroup()
                .addGroup(layoutInitial.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonCreate)
                        .addComponent(buttonDownload)
                        .addComponent(buttonSave))
                .addComponent(scrollPaneInitial));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup().addGroup(layout.createSequentialGroup()
                .addComponent(panelInitial)
                .addComponent(panelResult)));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup()
                .addComponent(panelInitial)
                .addComponent(panelResult)));
    }
}
