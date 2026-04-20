package com.bankapp.ui.frames;

import com.bankapp.ui.BaseFrame;

import javax.swing.*;

/**
 * Simple login page matching the wireframe.
 */
public class LoginFrame extends BaseFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        super("BankApp - Login");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Admin/Client");
        JPanel pagePanel = createPagePanel();

        JLabel titleLabel = createPageTitle("Login Page");
        titleLabel.setBounds(250, 35, 330, 35);

        JLabel emailLabel = createLabel("E-mail:");
        emailLabel.setBounds(80, 135, 140, 35);

        emailField = createTextField();
        emailField.setBounds(320, 135, 180, 45);

        JLabel passwordLabel = createLabel("pass:");
        passwordLabel.setBounds(80, 210, 140, 35);

        passwordField = createPasswordField();
        passwordField.setBounds(320, 210, 180, 45);

        JButton loginButton = createButton("login");
        loginButton.setBounds(395, 320, 140, 60);
        loginButton.addActionListener(e -> handleLogin());

        JButton createAccountButton = createLinkButton("Create new account");
        createAccountButton.setBounds(330, 430, 230, 40);
        createAccountButton.addActionListener(e -> handleCreateAccount());

        pagePanel.add(titleLabel);
        pagePanel.add(emailLabel);
        pagePanel.add(emailField);
        pagePanel.add(passwordLabel);
        pagePanel.add(passwordField);
        pagePanel.add(loginButton);
        pagePanel.add(createAccountButton);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            showError("Please enter email and password.");
            return;
        }

        // TODO: implement real login logic.
        Object[] options = {"Client Home", "Admin Home", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Front-end only demo.\nChoose which home page to open.",
                "Login",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            openFrame(new UserHomeFrame());
        } else if (choice == 1) {
            openFrame(new AdminHomeFrame());
        }
    }

    private void handleCreateAccount() {
        // TODO: connect registration navigation to real auth flow.
        openFrame(new RegistrationFrame());
    }
}
