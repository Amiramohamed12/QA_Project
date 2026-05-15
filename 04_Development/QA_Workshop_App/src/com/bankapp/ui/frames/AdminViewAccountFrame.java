package com.bankapp.ui.frames;

import com.bankapp.models.Admin;
import com.bankapp.models.BankAccount;
import com.bankapp.models.Client;
import com.bankapp.services.ValidationService;
import com.bankapp.ui.BaseFrame;
import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Admin view account page.
 */
public class AdminViewAccountFrame extends BaseFrame {

    private JTextField userIdField;
    private DefaultTableModel tableModel;

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
        searchButton.addActionListener(e -> handleViewAccount());

        String[] columns = {"Account Number", "Balance", "Last Transaction"};
        tableModel = new DefaultTableModel(columns, 0);

        JTable accountTable = new JTable(tableModel);
        styleTable(accountTable, true, false);

        JScrollPane scrollPane = new JScrollPane(accountTable);
        scrollPane.setBounds(40, 190, 800, 300);
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

    private void handleViewAccount() {
        String userID = userIdField.getText().trim();

        if (userID.isEmpty()) {
            showError("Please enter valid User ID");
            return;
        }

        ValidationService validationService = new ValidationService();

        if (!validationService.validateUserID(userID)) {
            showError("Please enter valid User ID");
            return;
        }

        if (!validationService.isUserIDExists(userID)) {
            showError("Please enter valid User ID");
            return;
        }

        Admin admin = new Admin();
        Client client = admin.viewClientDetails(userID);

        if (client == null) {
            showError("Please enter valid User ID");
            return;
        }

        showClientDetails(client);
    }

    private void showClientDetails(Client client) {
        tableModel.setRowCount(0);

        for (BankAccount account : client.getAccounts()) {
            tableModel.addRow(new Object[] {
                    account.getAccountNo(),
                    account.getBalance(),
                    getLastTransaction(account.getAccountNo())
            });
        }
    }

    private String getLastTransaction(String accountNo) {
        String sql = "SELECT t.created_at, t.transaction_type, t.amount " +
                     "FROM transactions t " +
                     "JOIN accounts a ON a.account_id = t.from_account_id OR a.account_id = t.to_account_id " +
                     "WHERE a.account_number = ? " +
                     "ORDER BY t.created_at DESC LIMIT 1";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("transaction_type") + " - " +
                           rs.getDouble("amount") + " - " +
                           rs.getString("created_at");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "No transactions";
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
