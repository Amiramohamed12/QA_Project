package com.bankapp.ui.frames;

import com.bankapp.enums.AccountType;
import com.bankapp.ui.BaseFrame;

import javax.swing.*;

/**
 * Client new account page.
 */
public class UserNewAccountFrame extends BaseFrame {

    private JComboBox<String> accountTypeCombo;

    public UserNewAccountFrame() {
        super("BankApp - New Account");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Client");
        JPanel pagePanel = createPagePanel();

        JButton homeButton = createButton("Home");
        homeButton.setBounds(20, 15, 90, 45);
        homeButton.addActionListener(e -> handleHome());

        JLabel titleLabel = createPageTitle("New Account page");
        titleLabel.setBounds(235, 15, 340, 40);

        JButton logoutButton = createButton("log-out");
        logoutButton.setBounds(630, 15, 140, 55);
        logoutButton.addActionListener(e -> handleLogout());

        JLabel accountTypeLabel = createLabel("Account type");
        accountTypeLabel.setBounds(45, 125, 180, 35);

        String[] accountTypes = {
                AccountType.SAVING.name(),
                AccountType.CURRENT.name()
        };
        accountTypeCombo = createComboBox(accountTypes);
        accountTypeCombo.setBounds(45, 165, 230, 45);

        JButton submitButton = createButton("Submit");
        submitButton.setBounds(330, 360, 150, 60);
        submitButton.addActionListener(e -> handleSubmit());

        pagePanel.add(homeButton);
        pagePanel.add(titleLabel);
        pagePanel.add(logoutButton);
        pagePanel.add(accountTypeLabel);
        pagePanel.add(accountTypeCombo);
        pagePanel.add(submitButton);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

    private void handleSubmit() {
        String selectedType = (String) accountTypeCombo.getSelectedItem();

        if (selectedType == null || selectedType.isBlank()) {
            showError("Please choose an account type.");
            return;
        }

        // TODO: implement real new account logic.
        showSuccess(selectedType + " account request submitted.");
    }

    private void handleHome() {
        // TODO: keep user page state when backend is ready.
        openFrame(new UserHomeFrame());
    }

    private void handleLogout() {
        // TODO: clear user session when backend is ready.
        openFrame(new LoginFrame());
    }
}
