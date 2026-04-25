package com.bankapp.ui.frames;

import com.bankapp.models.Admin;
import com.bankapp.models.Client;
import com.bankapp.services.ValidationService;
import com.bankapp.ui.BaseFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

        String[] columns = {"userID", "firstName", "lastName", "email", "accountCount"};
        tableModel = new DefaultTableModel(columns, 0);

        JTable accountTable = new JTable(tableModel);
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

    private void handleViewAccount() {
        String userID = userIdField.getText().trim();

        if (userID.isEmpty()) {
            showError("Please enter a user ID.");
            return;
        }

        ValidationService validationService = new ValidationService();

        if (!validationService.validateUserID(userID)) {
            showError("Invalid user ID.");
            return;
        }

        if (!validationService.isUserIDExists(userID)) {
            showError("User ID does not exist.");
            return;
        }

        Admin admin = new Admin();
        Client client = admin.viewClientDetails(userID);

      /*  if (client == null) {
            showError("Client details not found.");
            return;
        }*/

        showClientDetails(client);
    }

    private void showClientDetails(Client client) {
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[] {
                client.getUserID(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getAccounts().size()
        });
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
