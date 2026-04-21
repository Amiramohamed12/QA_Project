package com.bankapp.ui.frames;

import com.bankapp.services.SessionManager;
import com.bankapp.ui.BaseFrame;

import javax.swing.*;

/**
 * Admin home page.
 */
public class AdminHomeFrame extends BaseFrame {

    public AdminHomeFrame() {
        super("BankApp - Admin Home");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Admin");
        JPanel pagePanel = createPagePanel();

        JLabel titleLabel = createPageTitle("Home page");
        titleLabel.setBounds(270, 15, 260, 40);

        JButton logoutButton = createButton("log-out");
        logoutButton.setBounds(640, 20, 140, 55);
        logoutButton.addActionListener(e -> handleLogout());

        JButton createAccountButton = createButton("Create Account");
        createAccountButton.setBounds(30, 80, 260, 55);
        createAccountButton.addActionListener(e -> handleCreateAccount());

        JButton deleteAccountButton = createButton("Delete Account");
        deleteAccountButton.setBounds(30, 160, 260, 55);
        deleteAccountButton.addActionListener(e -> handleDeleteAccount());

        JButton viewAccountsButton = createButton("View Accounts");
        viewAccountsButton.setBounds(30, 240, 260, 55);
        viewAccountsButton.addActionListener(e -> handleViewAccounts());

        JButton fundTransferButton = createButton("Fund Transfer");
        fundTransferButton.setBounds(30, 320, 260, 55);
        fundTransferButton.addActionListener(e -> handleFundTransfer());

        JButton editAccountButton = createButton("Edit Account Info");
        editAccountButton.setBounds(30, 400, 260, 70);
        editAccountButton.addActionListener(e -> handleEditAccountInfo());

        pagePanel.add(titleLabel);
        pagePanel.add(logoutButton);
        pagePanel.add(createAccountButton);
        pagePanel.add(deleteAccountButton);
        pagePanel.add(viewAccountsButton);
        pagePanel.add(fundTransferButton);
        pagePanel.add(editAccountButton);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

    private void handleCreateAccount() {
        // TODO: load admin create account data when backend is ready.
        openFrame(new AdminCreateAccountFrame());
    }

    private void handleDeleteAccount() {
        // TODO: load admin delete account data when backend is ready.
        openFrame(new AdminDeleteAccountFrame());
    }

    private void handleViewAccounts() {
        // TODO: load account list from backend.
        openFrame(new AdminViewAccountFrame());
    }

    private void handleFundTransfer() {
        // TODO: load admin transfer data when backend is ready.
        openFrame(new AdminFundTransferFrame());
    }

    private void handleEditAccountInfo() {
        // TODO: load editable account data from backend.
        openFrame(new AdminEditAccountFrame());
    }

    private void handleLogout() {
        SessionManager.clearSession();
        openFrame(new LoginFrame());
    }
}
