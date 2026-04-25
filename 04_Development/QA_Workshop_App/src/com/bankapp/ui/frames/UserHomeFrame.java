package com.bankapp.ui.frames;

import com.bankapp.services.SessionManager;
import com.bankapp.ui.BaseFrame;

import javax.swing.*;

/**
 * Client home page.
 */
public class UserHomeFrame extends BaseFrame {

    public UserHomeFrame() {
        super("BankApp - User Home");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Client");
        JPanel pagePanel = createPagePanel();

        JLabel titleLabel = createPageTitle("Home page");
        titleLabel.setBounds(270, 15, 260, 40);

        JButton logoutButton = createButton("log-out");
        logoutButton.setBounds(640, 20, 140, 55);
        logoutButton.addActionListener(e -> handleLogout());

        JButton newAccountButton = createButton("New Account");
        newAccountButton.setBounds(30, 95, 220, 55);
        newAccountButton.addActionListener(e -> handleNewAccount());

        JButton transactionButton = createButton("Transaction");
        transactionButton.setBounds(30, 185, 220, 55);
        transactionButton.addActionListener(e -> handleTransaction());

        JButton viewAccountInfoButton = createButton("View Account Info");
        viewAccountInfoButton.setBounds(30, 275, 245, 55);
        viewAccountInfoButton.addActionListener(e -> handleViewAccountInfo());

        pagePanel.add(titleLabel);
        pagePanel.add(logoutButton);
        pagePanel.add(newAccountButton);
        pagePanel.add(transactionButton);
        pagePanel.add(viewAccountInfoButton);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

    private void handleNewAccount() {
        // TODO: load user account data before opening this page.
        openFrame(new UserNewAccountFrame());
    }

    private void handleTransaction() {
        // TODO: load transaction data before opening this page.
        openFrame(new UserTransactionFrame());
    }

    private void handleViewAccountInfo() {
        // TODO: load account summary before opening this page.
        SessionManager s=new SessionManager();
        System.out.println(s.getCurrentUserId());
        openFrame(new UserViewAccountInfoFrame());
        
    }

    private void handleLogout() {
        SessionManager.clearSession();
        openFrame(new LoginFrame());
    }
}
