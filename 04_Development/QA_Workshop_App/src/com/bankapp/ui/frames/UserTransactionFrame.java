package com.bankapp.ui.frames;

import com.bankapp.ui.BaseFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        Object[][] rows = {
                {"12/4", "Deposit", "500"},
                {"13/4", "Withdraw", "200"},
                {"14/4", "Transfer", "900"},
                {"15/4", "Deposit", "350"},
                {"16/4", "Withdraw", "120"}
        };

        JTable historyTable = new JTable(new DefaultTableModel(rows, columns));
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
        if (depositAccountField.getText().trim().isEmpty() || depositAmountField.getText().trim().isEmpty()) {
            showError("Please enter account number and amount.");
            return;
        }

        // TODO: implement real deposit logic.
        showSuccess("Deposit request submitted.");
    }

    private void handleWithdraw() {
        if (withdrawAccountField.getText().trim().isEmpty() || withdrawAmountField.getText().trim().isEmpty()) {
            showError("Please enter account number and amount.");
            return;
        }

        // TODO: implement real withdraw logic.
        showSuccess("Withdraw request submitted.");
    }

    private void handleFundTransfer() {
        if (transferToField.getText().trim().isEmpty()
                || transferFromField.getText().trim().isEmpty()
                || transferAmountField.getText().trim().isEmpty()) {
            showError("Please fill in all fund transfer fields.");
            return;
        }

        // TODO: implement real fund transfer logic.
        showSuccess("Fund transfer request submitted.");
    }

    private void handleHistorySearch() {
        if (historyAccountField.getText().trim().isEmpty()) {
            showError("Please enter an account number.");
            return;
        }

        // TODO: load transaction history from backend.
        showSuccess("Showing sample transaction history.");
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
