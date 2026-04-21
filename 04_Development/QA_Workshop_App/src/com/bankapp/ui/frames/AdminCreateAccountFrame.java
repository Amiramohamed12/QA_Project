package com.bankapp.ui.frames;

import com.bankapp.enums.AccountType;
import com.bankapp.models.Admin;
import com.bankapp.services.ValidationService;
import com.bankapp.ui.BaseFrame;

import javax.swing.*;

/**
 * Admin create account page.
 */
public class AdminCreateAccountFrame extends BaseFrame {

    private JTextField userIdField;
    private JComboBox<String> accountTypeCombo;

    public AdminCreateAccountFrame() {
        super("BankApp - Create Account");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Admin");
        JPanel pagePanel = createPagePanel();

        JButton backButton = createButton("Back");
        backButton.setBounds(20, 20, 90, 45);
        backButton.addActionListener(e -> handleBack());

        JLabel titleLabel = createPageTitle("Create Account Page");
        titleLabel.setBounds(230, 25, 360, 40);

        JButton logoutButton = createButton("log-out");
        logoutButton.setBounds(620, 20, 140, 55);
        logoutButton.addActionListener(e -> handleLogout());

        JLabel userIdLabel = createLabel("User-ID:");
        userIdLabel.setBounds(55, 165, 140, 35);

        userIdField = createTextField();
        userIdField.setBounds(210, 160, 170, 45);

        JLabel accountTypeLabel = createLabel("Account type:");
        accountTypeLabel.setBounds(55, 245, 160, 35);

        String[] accountTypes = {
                AccountType.SAVING.name(),
                AccountType.CURRENT.name()
        };
        accountTypeCombo = createComboBox(accountTypes);
        accountTypeCombo.setBounds(210, 240, 170, 45);

        JButton submitButton = createButton("Submit");
        submitButton.setBounds(300, 395, 150, 60);
        submitButton.addActionListener(e -> handleCreateAccount());

        pagePanel.add(backButton);
        pagePanel.add(titleLabel);
        pagePanel.add(logoutButton);
        pagePanel.add(userIdLabel);
        pagePanel.add(userIdField);
        pagePanel.add(accountTypeLabel);
        pagePanel.add(accountTypeCombo);
        pagePanel.add(submitButton);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

    private void handleCreateAccount() {
        String userID = userIdField.getText().trim();
        String accountType = (String) accountTypeCombo.getSelectedItem();

        if (userID.isEmpty()) {
            showError("Please enter a user ID.");
            return;
        }

        ValidationService validationService = new ValidationService();

        if (!validationService.validateUserID(userID)) {
            showError("Invalid user ID.");
            return;
        }

        if (accountType == null || !validationService.validateAccountType(accountType)) {
            showError("Please choose a valid account type.");
            return;
        }

        if (!validationService.isUserIDExists(userID)) {
            showError("User ID does not exist.");
            return;
        }

        Admin admin = new Admin();
        admin.createClientAccount(userID);

        // TODO: Save the new account to the database later.
        showSuccess(accountType + " account created for user " + userID + ".");
    }

    private void handleBack() {
        // TODO: keep admin context when backend is ready.
        openFrame(new AdminHomeFrame());
    }

    private void handleLogout() {
        // TODO: clear admin session when backend is ready.
        openFrame(new LoginFrame());
    }
}
