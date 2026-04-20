package com.bankapp.ui.frames;

import com.bankapp.ui.BaseFrame;

import javax.swing.*;

/**
 * Admin create account page.
 */
public class AdminCreateAccountFrame extends BaseFrame {

    private JTextField userIdField;

    public AdminCreateAccountFrame() {
        super("BankApp - Create Account");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Admin");
        JPanel pagePanel = createPagePanel();

        JButton homeButton = createButton("Home");
        homeButton.setBounds(20, 20, 90, 45);
        homeButton.addActionListener(e -> handleHome());

        JLabel titleLabel = createPageTitle("Create Account Page");
        titleLabel.setBounds(230, 25, 360, 40);

        JButton logoutButton = createButton("log-out");
        logoutButton.setBounds(620, 20, 140, 55);
        logoutButton.addActionListener(e -> handleLogout());

        JLabel userIdLabel = createLabel("User-ID:");
        userIdLabel.setBounds(55, 200, 140, 35);

        userIdField = createTextField();
        userIdField.setBounds(210, 195, 170, 45);

        JButton submitButton = createButton("Submit");
        submitButton.setBounds(300, 395, 150, 60);
        submitButton.addActionListener(e -> handleSubmit());

        pagePanel.add(homeButton);
        pagePanel.add(titleLabel);
        pagePanel.add(logoutButton);
        pagePanel.add(userIdLabel);
        pagePanel.add(userIdField);
        pagePanel.add(submitButton);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

    private void handleSubmit() {
        if (userIdField.getText().trim().isEmpty()) {
            showError("Please enter a user ID.");
            return;
        }

        // TODO: implement real admin create account logic.
        showSuccess("Create account request submitted.");
    }

    private void handleHome() {
        // TODO: keep admin context when backend is ready.
        openFrame(new AdminHomeFrame());
    }

    private void handleLogout() {
        // TODO: clear admin session when backend is ready.
        openFrame(new LoginFrame());
    }
}
