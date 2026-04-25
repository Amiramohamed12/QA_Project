package com.bankapp.ui.frames;

import com.bankapp.services.AuthService;
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

        AuthService authService = new AuthService();
        String role = authService.authenticate(email, password);
        System.out.print(role);
        openNextFrame(role);
        
    }

    private void openNextFrame(String role) {
        if ("admin".equals(role)) {
            openFrame(new AdminHomeFrame());
            return;
        }

        if ("client".equals(role)) {
            openFrame(new UserHomeFrame());
            return;
        }

        showError("Please enter valid data");
    }

    private void handleCreateAccount() {
        openFrame(new RegistrationFrame());
    }
}
