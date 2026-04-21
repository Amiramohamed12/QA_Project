package com.bankapp.ui.frames;

import com.bankapp.models.Admin;
import com.bankapp.services.ValidationService;
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
        submitButton.addActionListener(e -> handleFundTransfer());

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

    private void handleFundTransfer() {
        String toAcc = toAccountField.getText().trim();
        String fromAcc = fromAccountField.getText().trim();
        String amountText = amountField.getText().trim();

        if (toAcc.isEmpty() || fromAcc.isEmpty() || amountText.isEmpty()) {
            showError("Please fill in all transfer fields.");
            return;
        }

        if (fromAcc.equals(toAcc)) {
            showError("From account and to account cannot be the same.");
            return;
        }

        ValidationService validationService = new ValidationService();

        if (!validationService.validateAccountNo(fromAcc)) {
            showError("Invalid from account number.");
            return;
        }

        if (!validationService.validateAccountNo(toAcc)) {
            showError("Invalid to account number.");
            return;
        }

        if (!validationService.isAccountExists(fromAcc)) {
            showError("From account does not exist.");
            return;
        }

        if (!validationService.isAccountExists(toAcc)) {
            showError("To account does not exist.");
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showError("Invalid amount.");
            return;
        }

        if (!validationService.validateAmount(amount)) {
            showError("Invalid amount.");
            return;
        }

        Admin admin = new Admin();
        boolean transferred = admin.fundTransfer(fromAcc, toAcc, amount);

        if (transferred) {
            showSuccess("Fund transfer completed successfully.");
        } else {
            showError("Fund transfer failed.");
        }
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
