package com.bankapp.ui.frames;

import com.bankapp.models.Client;
import com.bankapp.services.SessionManager;
import com.bankapp.ui.BaseFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Client account info page.
 */
public class UserViewAccountInfoFrame extends BaseFrame {

    private DefaultTableModel tableModel;

    public UserViewAccountInfoFrame() {
        super("BankApp - View Account Info");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Client");
        JPanel pagePanel = createPagePanel();

        JButton homeButton = createButton("Home");
        homeButton.setBounds(20, 20, 90, 45);
        homeButton.addActionListener(e -> handleHome());

        JLabel titleLabel = createPageTitle("View Account Info page");
        titleLabel.setBounds(200, 20, 380, 40);

        JButton logoutButton = createButton("log-out");
        logoutButton.setBounds(610, 20, 140, 55);
        logoutButton.addActionListener(e -> handleLogout());

        String[] columns = {"Account Number", "Balance"};
        tableModel = new DefaultTableModel(columns, 0);

        JTable accountTable = new JTable(tableModel);
        styleTable(accountTable, false, true);

        JScrollPane scrollPane = new JScrollPane(accountTable);
        scrollPane.setBounds(55, 165, 320, 150);
        scrollPane.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR));

        pagePanel.add(homeButton);
        pagePanel.add(titleLabel);
        pagePanel.add(logoutButton);
        pagePanel.add(scrollPane);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);

        loadAccountInfo();
    }

    private void loadAccountInfo() {
        String userID = SessionManager.getCurrentUserId();

        if (userID == null || userID.isEmpty()) {
            showError("No active user session.");
            return;
        }

        Client client = new Client();
        List<String> accounts = client.viewAccountInfo(userID);

        tableModel.setRowCount(0);

        for (String account : accounts) {
            String[] accountData = account.split(",", 2);

            if (accountData.length == 2) {
                tableModel.addRow(new Object[] {accountData[0], accountData[1]});
            }
        }
    }

    private void handleHome() {
        // TODO: keep selected account state when backend is ready.
        openFrame(new UserHomeFrame());
    }

    private void handleLogout() {
        SessionManager.clearSession();
        openFrame(new LoginFrame());
    }
}
