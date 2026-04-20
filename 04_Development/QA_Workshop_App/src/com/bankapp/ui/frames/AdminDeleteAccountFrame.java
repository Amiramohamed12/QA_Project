package com.bankapp.ui.frames;

import com.bankapp.ui.BaseFrame;

import javax.swing.*;

/**
 * Admin delete account page.
 */
public class AdminDeleteAccountFrame extends BaseFrame {

    private JTextField accountNoField;

    public AdminDeleteAccountFrame() {
        super("BankApp - Delete Account");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Admin");
        JPanel pagePanel = createPagePanel();

        JButton homeButton = createButton("Home");
        homeButton.setBounds(20, 20, 90, 45);
        homeButton.addActionListener(e -> handleHome());

        JLabel titleLabel = createPageTitle("Delete Account Page");
        titleLabel.setBounds(230, 25, 360, 40);

        JButton logoutButton = createButton("log-out");
        logoutButton.setBounds(620, 20, 140, 55);
        logoutButton.addActionListener(e -> handleLogout());

        JLabel accountNoLabel = createLabel("Account number:");
        accountNoLabel.setBounds(65, 200, 210, 35);

        accountNoField = createTextField();
        accountNoField.setBounds(310, 195, 150, 45);

        JButton deleteButton = createButton("Delete");
        deleteButton.setBounds(300, 400, 150, 60);
        deleteButton.addActionListener(e -> handleDelete());

        pagePanel.add(homeButton);
        pagePanel.add(titleLabel);
        pagePanel.add(logoutButton);
        pagePanel.add(accountNoLabel);
        pagePanel.add(accountNoField);
        pagePanel.add(deleteButton);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

    private void handleDelete() {
        if (accountNoField.getText().trim().isEmpty()) {
            showError("Please enter an account number.");
            return;
        }

        // TODO: implement real admin delete account logic.
        showSuccess("Delete account request submitted.");
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
