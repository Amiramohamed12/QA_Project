package com.bankapp.ui.frames;

import com.bankapp.ui.BaseFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Client account info page.
 */
public class UserViewAccountInfoFrame extends BaseFrame {

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
        Object[][] rows = {
                {"10001", "15,000"},
                {"10002", "8,300"},
                {"10003", "22,150"}
        };

        JTable accountTable = new JTable(new DefaultTableModel(rows, columns));
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
    }

    private void handleHome() {
        // TODO: keep selected account state when backend is ready.
        openFrame(new UserHomeFrame());
    }

    private void handleLogout() {
        // TODO: clear user session when backend is ready.
        openFrame(new LoginFrame());
    }
}
