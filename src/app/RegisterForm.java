package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterForm extends JFrame {

    JTextField usernameField, emailField;
    JPasswordField passwordField;

    public RegisterForm() {
        setTitle("Register");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5,2,10,10));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton registerBtn = new JButton("Register");
        add(registerBtn);

        JButton backBtn = new JButton("Back");
        add(backBtn);

        registerBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> backToLogin());

        setVisible(true);
    }

    private void register() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if(UserDAO.registerUser(username, email, password)){
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
            backToLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Error! Try again.");
        }
    }

    private void backToLogin() {
        new LoginForm();
        this.dispose();
    }
}
