package com.bankapp.models;

import com.bankapp.enums.AccountType;
import com.bankapp.services.SessionManager;
import com.bankapp.services.ValidationService;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Client model class extending AbstractUser.
 * A Client can own up to 3 BankAccounts.
 */
public class Client extends AbstractUser {

    private List<BankAccount> accounts;

    public Client() {
        super();
        this.accounts = new ArrayList<>();
    }

    public Client(String userID, String firstName, String lastName,
                  String email, String password, String ssn) {
        super(userID, firstName, lastName, email, password, ssn);
        this.accounts = new ArrayList<>();
    }

    // --- Getters and Setters ---

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    // --- Methods ---

    /**
     * Registers a new client.
     * @return true if registration succeeds
     */
    public boolean register(String firstName, String lastName,
                            String email, String ssn, String password) {
        String sql = "INSERT INTO users (first_name, last_name, email, ssn, password, role) VALUES (?, ?, ?, ?, ?, 'client')";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, ssn);
            ps.setString(5, password);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Creates a new bank account of the specified type.
     * @return the created BankAccount or null
     */
    public BankAccount createAccount(AccountType type) {
        String currentUserID = SessionManager.getCurrentUserId();
        if (type == null || currentUserID == null || !validateMaxAccounts(currentUserID)) {
            return null;
        }

        String generateSql = "SELECT COALESCE(MAX(CAST(account_number AS INTEGER)), 10000) + 1 FROM accounts";
        String insertSql   = "INSERT INTO accounts (account_number, user_id, account_type, balance) VALUES (?, ?, ?, 0)";

        try (Connection conn = DBConnection.connect()) {

            String newAccountNo;

            try (PreparedStatement genPs = conn.prepareStatement(generateSql);
                 ResultSet rs = genPs.executeQuery()) {
                newAccountNo = String.valueOf(rs.getInt(1));
            }

            try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                insertPs.setString(1, newAccountNo);
                insertPs.setInt(2, Integer.parseInt(currentUserID));
                insertPs.setString(3, type.name().toLowerCase());
                insertPs.executeUpdate();
            }

            BankAccount account = new BankAccount(newAccountNo, type, 0.0, currentUserID);
            accounts.add(account);
            return account;

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Views account info for the given user ID.
     * @return list of account info strings in "accountNo,balance" format
     */
    public List<String> viewAccountInfo(String userID) {
        String sql = "SELECT account_number, balance FROM accounts WHERE user_id = ?";
        List<String> accountInfo = new ArrayList<>();

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(userID));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    accountInfo.add(rs.getString("account_number") + "," + rs.getDouble("balance"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountInfo;
    }

    /**
     * Views transaction history for the given account number.
     * @return list of transactions in "date,type,amount" format
     */
    public List<String> viewTransactionHistory(String accountNo) {
        String currentUserID = SessionManager.getCurrentUserId();
        if (currentUserID == null) {
            return new ArrayList<>();
        }

        String sql = "SELECT t.created_at, t.transaction_type, t.amount " +
                     "FROM transactions t " +
                     "JOIN accounts a ON (a.account_id = t.from_account_id OR a.account_id = t.to_account_id) " +
                     "WHERE a.account_number = ? AND a.user_id = ? " +
                     "ORDER BY t.created_at DESC LIMIT 5";
        List<String> history = new ArrayList<>();

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNo);
            ps.setInt(2, Integer.parseInt(currentUserID));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    history.add(rs.getString("created_at") + "," +
                                rs.getString("transaction_type") + "," +
                                rs.getDouble("amount"));
                }
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }

        return history;
    }

    /**
     * Deposits amount into the given account.
     * @return true if deposit succeeds
     */
    public boolean deposit(String accountNo, double amount) {
        String currentUserID = SessionManager.getCurrentUserId();
        ValidationService validationService = new ValidationService();
        if (currentUserID == null || !validationService.validateAmount(amount)) {
            return false;
        }

        String accountIdSql = "SELECT account_id FROM accounts WHERE account_number = ? AND user_id = ?";
        String updateSql    = "UPDATE accounts SET balance = balance + ? WHERE account_number = ? AND user_id = ?";
        String insertSql    = "INSERT INTO transactions (from_account_id, transaction_type, amount, performed_by) VALUES (?, 'deposit', ?, ?)";

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);

            try {
                int accountId;
                try (PreparedStatement idPs = conn.prepareStatement(accountIdSql)) {
                    idPs.setString(1, accountNo);
                    idPs.setInt(2, Integer.parseInt(currentUserID));
                    try (ResultSet rs = idPs.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }
                        accountId = rs.getInt("account_id");
                    }
                }

                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setDouble(1, amount);
                    updatePs.setString(2, accountNo);
                    updatePs.setInt(3, Integer.parseInt(currentUserID));
                    if (updatePs.executeUpdate() == 0) {
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setInt(1, accountId);
                    insertPs.setDouble(2, amount);
                    insertPs.setInt(3, Integer.parseInt(currentUserID));
                    insertPs.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException | NumberFormatException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Withdraws amount from the given account.
     * @return true if withdrawal succeeds
     */
    public boolean withdraw(String accountNo, double amount) {
        String currentUserID = SessionManager.getCurrentUserId();
        ValidationService validationService = new ValidationService();
        if (currentUserID == null || !validationService.validateAmount(amount)) {
            return false;
        }

        String accountIdSql = "SELECT account_id, balance FROM accounts WHERE account_number = ? AND user_id = ?";
        String updateSql    = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND user_id = ? AND balance >= ?";
        String insertSql    = "INSERT INTO transactions (from_account_id, transaction_type, amount, performed_by) VALUES (?, 'withdraw', ?, ?)";

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);

            try {
                int accountId;
                try (PreparedStatement idPs = conn.prepareStatement(accountIdSql)) {
                    idPs.setString(1, accountNo);
                    idPs.setInt(2, Integer.parseInt(currentUserID));
                    try (ResultSet rs = idPs.executeQuery()) {
                        if (!rs.next() || rs.getDouble("balance") < amount) {
                            conn.rollback();
                            return false;
                        }
                        accountId = rs.getInt("account_id");
                    }
                }

                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setDouble(1, amount);
                    updatePs.setString(2, accountNo);
                    updatePs.setInt(3, Integer.parseInt(currentUserID));
                    updatePs.setDouble(4, amount);
                    if (updatePs.executeUpdate() == 0) {
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setInt(1, accountId);
                    insertPs.setDouble(2, amount);
                    insertPs.setInt(3, Integer.parseInt(currentUserID));
                    insertPs.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException | NumberFormatException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Transfers funds between accounts.
     * @return true if transfer succeeds
     */
    public boolean fundTransfer(String fromAcc, String toAcc, double amount) {
        String currentUserID = SessionManager.getCurrentUserId();
        ValidationService validationService = new ValidationService();
        if (currentUserID == null || fromAcc == null || toAcc == null
                || fromAcc.equals(toAcc) || !validationService.validateAmount(amount)) {
            return false;
        }

        String fromAccountSql = "SELECT account_id, balance FROM accounts WHERE account_number = ? AND user_id = ?";
        String toAccountSql   = "SELECT account_id FROM accounts WHERE account_number = ?";
        String debitSql     = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND user_id = ? AND balance >= ?";
        String creditSql    = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        String insertSql    = "INSERT INTO transactions (from_account_id, to_account_id, transaction_type, amount, performed_by) VALUES (?, ?, 'transfer', ?, ?)";

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);

            try {
                int fromId;
                try (PreparedStatement idPs = conn.prepareStatement(fromAccountSql)) {
                    idPs.setString(1, fromAcc);
                    idPs.setInt(2, Integer.parseInt(currentUserID));
                    try (ResultSet rs = idPs.executeQuery()) {
                        if (!rs.next() || rs.getDouble("balance") < amount) {
                            conn.rollback();
                            return false;
                        }
                        fromId = rs.getInt("account_id");
                    }
                }

                int toId;
                try (PreparedStatement idPs = conn.prepareStatement(toAccountSql)) {
                    idPs.setString(1, toAcc);
                    try (ResultSet rs = idPs.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }
                        toId = rs.getInt("account_id");
                    }
                }

                try (PreparedStatement debitPs = conn.prepareStatement(debitSql)) {
                    debitPs.setDouble(1, amount);
                    debitPs.setString(2, fromAcc);
                    debitPs.setInt(3, Integer.parseInt(currentUserID));
                    debitPs.setDouble(4, amount);
                    if (debitPs.executeUpdate() == 0) {
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement creditPs = conn.prepareStatement(creditSql)) {
                    creditPs.setDouble(1, amount);
                    creditPs.setString(2, toAcc);
                    creditPs.executeUpdate();
                }

                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setInt(1, fromId);
                    insertPs.setInt(2, toId);
                    insertPs.setDouble(3, amount);
                    insertPs.setInt(4, Integer.parseInt(currentUserID));
                    insertPs.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException | NumberFormatException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Validates whether the user has reached the max account limit.
     * @return true if user can create more accounts
     */
    public boolean validateMaxAccounts(String userId) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE user_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(userId));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) < 3;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
