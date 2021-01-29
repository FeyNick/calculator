package geekbrains.homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


/*
    в задании сказано добавить проверку, чтобы знаки не шли подряд, я сделал так, чтобы калькулятор сам это исправлял (убирал лишний знак)
    если нужно переделаю на вывод ошибки
 */



public class MyFrame extends JFrame {
    private final JTextField textField;
    private char operation;
    private double total;
    private boolean isFirstDigit;

    public boolean isOperation(char c) {
        return c == '-' || c == '+' || c == '*' || c == '/' ;
    }

    public MyFrame() {
        setTitle("Calculator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 480, 380);
        setVisible(true);

        JPanel panel = new JPanel();

        panel.setBackground(Color.GRAY);

        // текст
        textField = new JTextField(38); //38
        textField.setEditable(false);
        panel.add(textField);
        // кнопки
        JButton[] jbs = new JButton[9];
        for (int i = 0; i < jbs.length; i++) {
            jbs[i] = new JButton("" + (i + 1));
            jbs[i].addActionListener(this::action);
            jbs[i].setBackground(Color.PINK);
            panel.add(jbs[i]);
        }
        JButton buttonZero = new JButton("0");
        buttonZero.addActionListener(this::action);
        buttonZero.setBackground(Color.PINK);
        panel.add(buttonZero);
        JButton buttonDot = new JButton(".");
        buttonDot.addActionListener(this::action);
        buttonDot.setBackground(Color.PINK);
        panel.add(buttonDot);
        JButton buttonPlus = new JButton("+");
        buttonPlus.addActionListener(this::action);
        buttonPlus.setBackground(Color.PINK);
        panel.add(buttonPlus);
        JButton buttonMinus = new JButton("-");
        buttonMinus.addActionListener(this::action);
        buttonMinus.setBackground(Color.PINK);
        panel.add(buttonMinus);
        JButton buttonMultiple = new JButton("*");
        buttonMultiple.addActionListener(this::action);
        buttonMultiple.setBackground(Color.PINK);
        panel.add(buttonMultiple);
        JButton buttonDivide = new JButton("/");
        buttonDivide.addActionListener(this::action);
        buttonDivide.setBackground(Color.PINK);
        panel.add(buttonDivide);
        JButton buttonEquals = new JButton("=");
        buttonEquals.setBackground(Color.PINK);
        buttonEquals.addActionListener(e -> {
            testString(e);
            calculation(e);
        });
        panel.add(buttonEquals);
        JButton buttonReset = new JButton("C");
        buttonReset.addActionListener(this::reset);
        buttonReset.setBackground(Color.PINK);
        panel.add(buttonReset);


        add(panel);
        setVisible(true);
    }

    public void action(ActionEvent event) {
        textField.setText(textField.getText() + event.getActionCommand());
    }

    public void reset(ActionEvent event) {
        textField.setText(null);// Sets the text of this TextComponent to the specified text. If the text is null or empty, has the effect of simply deleting the old text.
    }

    private void testString(ActionEvent e) {  // проверка пар знаков
        String text = textField.getText();
        int counter = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (isOperation(c) && isOperation(text.charAt(i + 1))) {
                counter += 1;
            }
        }
        char[] arr = new char[text.length() - counter];
        int k = 0;
        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            if (isOperation(c) && isOperation(text.charAt(i + 1))) {
                arr[k] = text.charAt(i);
            }else{
                arr[k] = text.charAt(i);
                k++;
            }
        }
        reset(e);
        for (char c : arr) {
            textField.setText(textField.getText() + c);
        }


    }

    private void calculation(ActionEvent e) {
        String text = "0+" + textField.getText(); // с этим костылем я не разобрался пока, но он отлично работает
        String digit = "";
        // ищем знаки
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (isOperation(c)) {
                double currentValue = Double.parseDouble(digit);
                if (isFirstDigit) {
                    total += currentValue;
                    isFirstDigit = false;
                } else {
                    if (operation == '-') {
                        total -= currentValue;
                    } else if (operation == '+') {
                        total += currentValue;
                    } else if (operation == '*') {
                        total *= currentValue;
                    } else if (operation == '/') {
                        total /= currentValue;
                    }
                }
                digit = "";
                operation = c;
                continue;
            }

            digit += c;
        }

        double currentValue = Double.parseDouble(digit);
        if (operation == '-') {
            total -= currentValue;
        } else if (operation == '+') {
            total += currentValue;
        } else if (operation == '*') {
            total *= currentValue;
        } else if (operation == '/') {
            total /= currentValue;
        }

        isFirstDigit = true;
        textField.setText(String.valueOf(total));
        total = 0;

    }
}

