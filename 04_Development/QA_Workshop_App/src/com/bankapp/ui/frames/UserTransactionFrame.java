package com.bankapp.ui.frames;

import com.bankapp.models.Client;
import com.bankapp.services.SessionManager;
import com.bankapp.services.ValidationService;
import com.bankapp.ui.BaseFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Single client transaction page with tabs.
 */
public class UserTransactionFrame extends BaseFrame {

    private JTextField depositAccountField;
    private JTextField depositAmountField;
    private JTextField withdrawAccountField;
    private JTextField withdrawAmountField;
    private JTextField transferToField;
    private JTextField transferFromField;
    private JTextField transferAmountField;
    private JTextField historyAccountField;
    private DefaultTableModel historyTableModel;

    public UserTransactionFrame() {
        super("BankApp - Transaction");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Client");
        JPanel pagePanel = createPagePanel();

        JButton homeButton = createButton("Home");
        homeButton.setBounds(20, 20, 90, 45);
        homeButton.addActionListener(e -> handleHome());

        JLabel titleLabel = createPageTitle("Transaction page");
        titleLabel.setBounds(230, 20, 360, 40);

        JButton logoutButton = createButton("log-out");
        logoutButton.setBounds(630, 15, 140, 55);
        logoutButton.addActionListener(e -> handleLogout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(FIELD_FONT);
        tabbedPane.setBounds(30, 95, 760, 380);
        tabbedPane.addTab("Deposit", createDepositPanel());
        tabbedPane.addTab("Withdraw", createWithdrawPanel());
        tabbedPane.addTab("Fund Transfer", createFundTransferPanel());
        tabbedPane.addTab("Transaction History", createHistoryPanel());

        pagePanel.add(homeButton);
        pagePanel.add(titleLabel);
        pagePanel.add(logoutButton);
        pagePanel.add(tabbedPane);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

    private JPanel createDepositPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BACKGROUND_COLOR);

        JLabel accountLabel = createLabel("acc num:");
        accountLabel.setBounds(40, 95, 120, 30);

        depositAccountField = createTextField();
        depositAccountField.setBounds(160, 90, 170, 40);

        JLabel amountLabel = createLabel("amount:");
        amountLabel.setBounds(420, 95, 120, 30);

        depositAmountField = createTextField();
        depositAmountField.setBounds(530, 90, 150, 40);

        JButton sendButton = createButton("send");
        sendButton.setBounds(300, 220, 130, 55);
        sendButton.addActionListener(e -> handleDeposit());

        panel.add(accountLabel);
        panel.add(depositAccountField);
        panel.add(amountLabel);
        panel.add(depositAmountField);
        panel.add(sendButton);
        return panel;
    }

    private JPanel createWithdrawPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BACKGROUND_COLOR);

        JLabel accountLabel = createLabel("acc num:");
        accountLabel.setBounds(40, 95, 120, 30);

        withdrawAccountField = createTextField();
        withdrawAccountField.setBounds(160, 90, 170, 40);

        JLabel amountLabel = createLabel("amount:");
        amountLabel.setBounds(420, 95, 120, 30);

        withdrawAmountField = createTextField();
        withdrawAmountField.setBounds(530, 90, 150, 40);

        JButton sendButton = createButton("send");
        sendButton.setBounds(300, 220, 130, 55);
        sendButton.addActionListener(e -> handleWithdraw());

        panel.add(accountLabel);
        panel.add(withdrawAccountField);
        panel.add(amountLabel);
        panel.add(withdrawAmountField);
        panel.add(sendButton);
        return panel;
    }

    private JPanel createFundTransferPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BACKGROUND_COLOR);

        JLabel toLabel = createLabel("Account Num To:");
        toLabel.setBounds(45, 70, 200, 30);

        transferToField = createTextField();
        transferToField.setBounds(300, 65, 160, 40);

        JLabel fromLabel = createLabel("Account Num From:");
        fromLabel.setBounds(45, 140, 230, 30);

        transferFromField = createTextField();
        transferFromField.setBounds(300, 135, 160, 40);

        JLabel amountLabel = createLabel("Amount:");
        amountLabel.setBounds(45, 210, 120, 30);

        transferAmountField = createTextField();
        transferAmountField.setBounds(300, 205, 160, 40);

        JButton sendButton = createButton("Send");
        sendButton.setBounds(310, 285, 130, 55);
        sendButton.addActionListener(e -> handleFundTransfer());

        panel.add(toLabel);
        panel.add(transferToField);
        panel.add(fromLabel);
        panel.add(transferFromField);
        panel.add(amountLabel);
        panel.add(transferAmountField);
        panel.add(sendButton);
        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BACKGROUND_COLOR);

        historyAccountField = createTextField();
        historyAccountField.setBounds(20, 20, 220, 40);

        JButton loadButton = createButton("Load");
        loadButton.setBounds(255, 20, 90, 40);
        loadButton.addActionListener(e -> handleHistorySearch());

        JLabel transactionsLabel = createLabel("Last 5 Transactions");
        transactionsLabel.setBounds(20, 75, 250, 30);

        String[] columns = {"Date", "Type", "Amount"};
        historyTableModel = new DefaultTableModel(columns, 0);

        JTable historyTable = new JTable(historyTableModel);
        styleTable(historyTable, false, true);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBounds(20, 120, 360, 210);
        scrollPane.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR));

        JButton backButton = createButton("Back");
        backButton.setBounds(510, 275, 120, 55);
        backButton.addActionListener(e -> handleHome());

        panel.add(historyAccountField);
        panel.add(loadButton);
        panel.add(transactionsLabel);
        panel.add(scrollPane);
        panel.add(backButton);
        return panel;
    }

    private void handleDeposit() {
        String accountNo = depositAccountField.getText().trim();
        String amountText = depositAmountField.getText().trim();

        if (accountNo.isEmpty()) {
            showError("Please enter valid account number");
            return;
        }

        if (amountText.isEmpty()) {
            showError("Please enter valid amount");
            return;
        }

        double amount = parseAmount(amountText);

        if (amount < 0) {
            return;
        }

        if (!validateAccountAndAmount(accountNo, amount, false)) {
            return;
        }

        Client client = new Client();
        boolean deposited = client.deposit(accountNo, amount);

        if (deposited) {
            showSuccess("Transaction done successfully");
        } else {
            showError("Deposit failed.");
        }
    }

    private void handleWithdraw() {
        String accountNo = withdrawAccountField.getText().trim();
        String amountText = withdrawAmountField.getText().trim();

        if (accountNo.isEmpty()) {
            showError("Please enter valid account number");
            return;
        }

        if (amountText.isEmpty()) {
            showError("Please enter valid amount");
            return;
        }

        double amount = parseAmount(amountText);

        if (amount < 0) {
            return;
        }

        if (!validateAccountAndAmount(accountNo, amount, true)) {
            return;
        }

        Client client = new Client();
        boolean withdrawn = client.withdraw(accountNo, amount);

        if (withdrawn) {
            showSuccess("Transaction done successfully");
        } else {
            showError("Withdraw failed.");
        }
    }

    private void handleFundTransfer() {
        String toAcc = transferToField.getText().trim();
        String fromAcc = transferFromField.getText().trim();
        String amountText = transferAmountField.getText().trim();

        if (toAcc.isEmpty() || fromAcc.isEmpty()) {
            showError("Please enter valid account number");
            return;
        }

        if (amountText.isEmpty()) {
            showError("Please enter valid amount");
            return;
        }

        if (fromAcc.equals(toAcc)) {
            showError("Please enter different valid account number");
            return;
        }

        double amount = parseAmount(amountText);

        if (amount < 0) {
            return;
        }

        ValidationService validationService = new ValidationService();
        String currentUserID = SessionManager.getCurrentUserId();

        if (!validationService.validateAccountNo(fromAcc)) {
            showError("Please enter valid account number");
            return;
        }

        if (!validationService.validateAccountNo(toAcc)) {
            showError("Please enter valid account number");
            return;
        }

        if (!validationService.isAccountExists(fromAcc)
                || !validationService.isAccountOwnedByUser(fromAcc, currentUserID)) {
            showError("Please enter valid account number");
            return;
        }

        if (!validationService.isAccountExists(toAcc)
                || !validationService.isAccountOwnedByUser(toAcc, currentUserID)) {
            showError("Please enter valid account number");
            return;
        }

        if (!validationService.validateAmount(amount)) {
            showError("Please enter valid amount");
            return;
        }

        if (validationService.getAccountBalance(fromAcc) < amount) {
            showError("Please enter valid amount");
            return;
        }

        Client client = new Client();
        boolean transferred = client.fundTransfer(fromAcc, toAcc, amount);

        if (transferred) {
            showSuccess("Transaction done successfully");
        } else {
            showError("Fund transfer failed.");
        }
    }

    private void handleHistorySearch() {
        String accountNo = historyAccountField.getText().trim();

        if (accountNo.isEmpty()) {
            showError("Please enter valid account number");
            return;
        }

        ValidationService validationService = new ValidationService();
        String currentUserID = SessionManager.getCurrentUserId();

        if (!validationService.validateAccountNo(accountNo)) {
            showError("Please enter valid account number");
            return;
        }

        if (!validationService.isAccountExists(accountNo)
                || !validationService.isAccountOwnedByUser(accountNo, currentUserID)) {
            showError("Please enter valid account number");
            return;
        }

        Client client = new Client();
        List<String> transactions = client.viewTransactionHistory(accountNo);

        historyTableModel.setRowCount(0);

        for (String transaction : transactions) {
            String[] transactionData = transaction.split(",", 3);

            if (transactionData.length == 3) {
                historyTableModel.addRow(new Object[] {
                        transactionData[0],
                        transactionData[1],
                        transactionData[2]
                });
            }
        }
    }

    private double parseAmount(String amountText) {
        if (!amountText.matches("\\d+")) {
            showError("Please enter valid amount");
            return -1;
        }

        try {
            return Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showError("Please enter valid amount");
            return -1;
        }
    }

    private boolean validateAccountAndAmount(String accountNo, double amount, boolean requireSufficientFunds) {
        ValidationService validationService = new ValidationService();
        String currentUserID = SessionManager.getCurrentUserId();

        if (!validationService.validateAccountNo(accountNo)) {
            showError("Please enter valid account number");
            return false;
        }

        if (!validationService.isAccountExists(accountNo)
                || !validationService.isAccountOwnedByUser(accountNo, currentUserID)) {
            showError("Please enter valid account number");
            return false;
        }

        if (!validationService.validateAmount(amount)) {
            showError("Please enter valid amount");
            return false;
        }

        if (requireSufficientFunds && validationService.getAccountBalance(accountNo) < amount) {
            showError("Please enter valid amount");
            return false;
        }

        return true;
    }

    private void handleHome() {
        // TODO: keep transaction tab state when backend is ready.
        openFrame(new UserHomeFrame());
    }

    private void handleLogout() {
        // TODO: clear user session when backend is ready.
        openFrame(new LoginFrame());
    }
}
