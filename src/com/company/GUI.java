package com.company;

import javax.swing. * ;

import static com.company.RPNCalc.calculateExpr;

public class GUI extends JFrame {
    public GUI() {
        JFrame f = new JFrame("RPN CALCULATOR");

        JButton b = new JButton("Calculate");
        b.setBounds(100, 100, 140, 40);

        JLabel label = new JLabel();
        label.setText("Enter expr :");
        label.setBounds(10, 10, 300, 100);

        JLabel label1 = new JLabel();
        label1.setBounds(250, 60, 300, 100);

        JLabel label3 = new JLabel();
        label3.setBounds(10, 120, 601, 60);

        JTextField textfield = new JTextField();
        textfield.setBounds(110, 50, 550, 30);

        JLabel label2 = new JLabel();
        label2.setBounds(10, 140, 700, 120);
        label2.setText("<html>Каждое число, знак, тернарный оператор и т.п. должны отделяться пробелом, если число" + " отрицательное, оно не должно иметь пробела между минусом и первой цифрой<br/>" + "Примеры ввода выражения:<br/> ( 2 + 3.5 ) / -7 * ( 3 / 2 - 8 )<br/>" + "( 2 - 7 ) < ( 3.9 * 2 ) ? ( 22 / 2 ) : -21<br/>" + "( 23.9 - 83 ) = 42 ? -0.242 : 50" + "</html>");
        //add to frame
        f.add(label1);
        f.add(label2);
        f.add(label3);
        f.add(textfield);

        f.add(label);
        f.add(b);
        f.setSize(700, 300);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b.addActionListener(arg0 ->{

                String expr = textfield.getText();

        try {
            String postfix = Parser.infixToPostfix(expr);
            double result = calculateExpr(Parser.makeTokens(postfix));
            label3.setText("RPN expression: " + postfix);
            label1.setText("Result: " + result);
        } catch(IllegalArgumentException e) {
            label1.setText("Result: Dividing by zero exception");
            label3.setText("RPN expression: Dividing by zero exception");
        }
        catch(Exception e) {
            label1.setText("Result: Wrong input");
            label3.setText("RPN expression: Wrong input");
        }
		});
    }
}