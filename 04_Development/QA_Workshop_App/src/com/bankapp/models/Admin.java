package com.bankapp.models;

import com.bankapp.enums.AccountType;
import com.bankapp.services.SessionManager;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Admin model class extending AbstractUser.
 * An Admin can manage client accounts and perform fund transfers.
 */
public class Admin extends AbstractUser {

    private String adminID;

    public Admin() {
        super();
    }

    public Admin(String userID, String firstName, String lastName,
                 String email, String password, String ssn, String adminID) {
        super(userID, firstName, lastName, email, password, ssn);
        this.adminID = adminID;
    }

    // --- Getters and Setters ---

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    // --- Methods ---

    /**
     * Creates a bank account for a client with the given user ID.
     * @return true if account creation succeeds
     */
    public boolean createClientAccount(String userID) {
        return createClientAccount(userID, AccountType.saving);
    }

    public boolean createClientAccount(String userID, AccountType accountType) {
        if (!validateUserID(userID) || accountType == null) {
            return false;
        }

        String generateSql = "SELECT COALESCE(MAX(CAST(account_number AS INTEGER)), 10000) + 1 FROM accounts";
        String insertSql   = "INSERT INTO accounts (account_number, user_id, account_type, balance) VALUES (?, ?, ?, 0)";

        try (Connection conn = DBConnection.connect()) {
            String newAccountNo;

            try (PreparedStatement genPs = conn.prepareStatement(generateSql);
                 ResultSet rs = genPs.executeQuery()) {
                newAccountNo = String.valueOf(rs.getInt(1));
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, newAccountNo);
                ps.setString(2, userID);
                ps.setString(3, accountType.name().toLowerCase());
                ps.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes a client account by account number.
     * @return true if deletion succeeds
     */
    public boolean deleteClientAccount(String accountNo) {
        String sql = "DELETE FROM accounts WHERE account_number = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Views client details for the given user ID.
     * @return the Client or null
     */
    public Client viewClientDetails(String userID) {
        String userSql    = "SELECT user_id, first_name, last_name, email, password, ssn FROM users WHERE user_id = ?";
        String accountSql = "SELECT account_number, account_type, balance FROM accounts WHERE user_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement userPs = conn.prepareStatement(userSql)) {

            userPs.setString(1, userID);

            try (ResultSet userRs = userPs.executeQuery()) {
                if (!userRs.next()) {
                    return null;
                }

                Client client = new Client(
                        userRs.getString("user_id"),
                        userRs.getString("first_name"),
                        userRs.getString("last_name"),
                        userRs.getString("email"),
                        userRs.getString("password"),
                        userRs.getString("ssn")
                );

                try (PreparedStatement accPs = conn.prepareStatement(accountSql)) {
                    accPs.setString(1, userID);

                    try (ResultSet accRs = accPs.executeQuery()) {
                        while (accRs.next()) {
                            BankAccount account = new BankAccount(
                                    accRs.getString("account_number"),
                                    AccountType.valueOf(accRs.getString("account_type")),
                                    accRs.getDouble("balance"),
                                    userID
                            );
                            client.getAccounts().add(account);
                        }
                    }
                }

                return client;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Edits client data.
     * @return true if edit succeeds
     */
    public boolean editClientData(String userID, String firstName,
                                  String lastName, String email) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, userID);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Transfers funds between two accounts.
     * @return true if transfer succeeds
     */
    public boolean fundTransfer(String fromAcc, String toAcc, double amount) {
        String currentUserID = SessionManager.getCurrentUserId();

        if (!validateAccountNo(fromAcc) || !validateAccountNo(toAcc)
                || fromAcc.equals(toAcc) || amount < 1000 || amount > 5000
                || amount != Math.floor(amount) || currentUserID == null) {
            return false;
        }

        String accountIdSql = "SELECT account_id FROM accounts WHERE account_number = ?";
        String debitSql     = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String creditSql    = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        String insertSql    = "INSERT INTO transactions (from_account_id, to_account_id, transaction_type, amount, performed_by) VALUES (?, ?, 'transfer', ?, ?)";

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement accountPs = conn.prepareStatement(accountIdSql);
                 PreparedStatement debitPs  = conn.prepareStatement(debitSql);
                 PreparedStatement creditPs = conn.prepareStatement(creditSql);
                 PreparedStatement insertPs = conn.prepareStatement(insertSql)) {

                int fromId;
                accountPs.setString(1, fromAcc);
                try (ResultSet rs = accountPs.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }
                    fromId = rs.getInt("account_id");
                }

                int toId;
                accountPs.setString(1, toAcc);
                try (ResultSet rs = accountPs.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }
                    toId = rs.getInt("account_id");
                }

                debitPs.setDouble(1, amount);
                debitPs.setString(2, fromAcc);
                if (debitPs.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }

                creditPs.setDouble(1, amount);
                creditPs.setString(2, toAcc);
                if (creditPs.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }

                insertPs.setInt(1, fromId);
                insertPs.setInt(2, toId);
                insertPs.setDouble(3, amount);
                insertPs.setInt(4, Integer.parseInt(currentUserID));
                insertPs.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Validates a user ID format.
     * @return true if valid
     */
    private boolean validateUserID(String userID) {
        return userID != null && userID.matches("\\d{5}");
    }

    /**
     * Validates an account number format.
     * @return true if valid
     */
    private boolean validateAccountNo(String accountNo) {
        return accountNo != null && accountNo.matches("\\d{5}");
    }
}
