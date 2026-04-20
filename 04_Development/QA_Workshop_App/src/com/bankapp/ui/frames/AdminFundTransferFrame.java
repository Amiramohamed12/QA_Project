package com.bankapp.ui.frames;

import com.bankapp.ui.BaseFrame;

import javax.swing.*;

/**
 * Admin fund transfer page.
 */
public class AdminFundTransferFrame extends BaseFrame {

    private JTextField toAccountField;
    private JTextField fromAccountField;
    private JTextField amountField;

    public AdminFundTransferFrame() {
        super("BankApp - Fund Transfer");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Admin");
        JPanel pagePanel = createPagePanel();

        JButton homeButton = createButton("Home");
        homeButton.setBounds(20, 20, 90, 45);
        homeButton.addActionListener(e -> handleHome());

        JLabel titleLabel = createPageTitle("Fund Transfer Page");
        titleLabel.setBounds(230, 25, 360, 40);

        JButton logoutButton = createButton("log-out");
        logoutButton.setBounds(620, 20, 140, 55);
        logoutButton.addActionListener(e -> handleLogout());

        JLabel toLabel = createLabel("to:");
        toLabel.setBounds(90, 170, 80, 35);

        toAccountField = createTextField();
        toAccountField.setBounds(165, 165, 150, 45);

        JLabel amountLabel = createLabel("amount:");
        amountLabel.setBounds(405, 170, 110, 35);

        amountField = createTextField();
        amountField.setBounds(525, 165, 150, 45);

        JLabel fromLabel = createLabel("from:");
        fromLabel.setBounds(60, 270, 110, 35);

        fromAccountField = createTextField();
        fromAccountField.setBounds(165, 265, 150, 45);

        JButton submitButton = createButton("Submit");
        submitButton.setBounds(360, 410, 150, 60);
        submitButton.addActionListener(e -> handleSubmit());

        pagePanel.add(homeButton);
        pagePanel.add(titleLabel);
        pagePanel.add(logoutButton);
        pagePanel.add(toLabel);
        pagePanel.add(toAccountField);
        pagePanel.add(amountLabel);
        pagePanel.add(amountField);
        pagePanel.add(fromLabel);
        pagePanel.add(fromAccountField);
        pagePanel.add(submitButton);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

    private void handleSubmit() {
        if (toAccountField.getText().trim().isEmpty()
                || fromAccountField.getText().trim().isEmpty()
                || amountField.getText().trim().isEmpty()) {
            showError("Please fill in all transfer fields.");
            return;
        }

        // TODO: implement real admin fund transfer logic.
        showSuccess("Fund transfer request submitted.");
    }

    private void handleHome() {
        // TODO: keep admin page state when backend is ready.
        openFrame(new AdminHomeFrame());
    }

    private void handleLogout() {
        // TODO: clear admin session when backend is ready.
        openFrame(new LoginFrame());
    }
}
