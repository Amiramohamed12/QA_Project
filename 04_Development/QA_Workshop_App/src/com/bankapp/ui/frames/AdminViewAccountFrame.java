package com.bankapp.ui.frames;

import com.bankapp.ui.BaseFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Admin view account page.
 */
public class AdminViewAccountFrame extends BaseFrame {

    private JTextField userIdField;

    public AdminViewAccountFrame() {
        super("BankApp - View Account Info");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Admin");
        JPanel pagePanel = createPagePanel();

        JButton homeButton = createButton("Home");
        homeButton.setBounds(20, 20, 90, 45);
        homeButton.addActionListener(e -> handleHome());

        JLabel titleLabel = createPageTitle("View Account Info page");
        titleLabel.setBounds(180, 20, 410, 40);

        JButton logoutButton = createButton("log-out");
        logoutButton.setBounds(645, 55, 140, 55);
        logoutButton.addActionListener(e -> handleLogout());

        JLabel userIdLabel = createLabel("User-ID:");
        userIdLabel.setBounds(40, 110, 120, 35);

        userIdField = createTextField();
        userIdField.setBounds(160, 105, 120, 45);

        JButton searchButton = createCheckButton();
        searchButton.setBounds(380, 105, 35, 35);
        searchButton.addActionListener(e -> handleSearch());

        String[] columns = {"ACC NUM", "Balance", "last tran"};
        Object[][] rows = {
                {"10001", "10,200", "Deposit"},
                {"10002", "8,150", "Withdraw"},
                {"10003", "3,700", "Transfer"}
        };

        JTable accountTable = new JTable(new DefaultTableModel(rows, columns));
        styleTable(accountTable, false, true);

        JScrollPane scrollPane = new JScrollPane(accountTable);
        scrollPane.setBounds(40, 195, 380, 130);
        scrollPane.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR));

        pagePanel.add(homeButton);
        pagePanel.add(titleLabel);
        pagePanel.add(logoutButton);
        pagePanel.add(userIdLabel);
        pagePanel.add(userIdField);
        pagePanel.add(searchButton);
        pagePanel.add(scrollPane);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

    private void handleSearch() {
        if (userIdField.getText().trim().isEmpty()) {
            showError("Please enter a user ID.");
            return;
        }

        // TODO: load user accounts from backend.
        showSuccess("Showing sample account data.");
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
