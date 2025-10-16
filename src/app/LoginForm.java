
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginForm() {
        setTitle("Login");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4,2,10,10));

        // username
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // login button
        JButton loginBtn = new JButton("Login");
        add(loginBtn);

        // register button
        JButton registerBtn = new JButton("Register");
        add(registerBtn);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> openRegisterForm());

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = UserDAO.loginUser(username, password);
        if(user != null){
            JOptionPane.showMessageDialog(this, "Login successful! Welcome " + user.getUsername());
            // TODO: open main game window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        }
    }

    private void openRegisterForm() {
        new RegisterForm();
        this.dispose();
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}

