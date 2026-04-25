package com.bankapp.models;

import com.bankapp.enums.AccountType;
import com.bankapp.enums.TransactionType;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * BankAccount model class.
 * Fields: accountNo (5 digits, auto-generated, unique), accountType, balance, ownerUserID.
 */
public class BankAccount {

    private String      accountNo;
    private AccountType accountType;
    private double      balance;
    private String      ownerUserID;

    public BankAccount() {
        this.balance = 0.0;
    }

    public BankAccount(String accountNo, AccountType accountType,
                       double balance, String ownerUserID) {
        this.accountNo   = accountNo;
        this.accountType = accountType;
        this.balance     = balance;
        this.ownerUserID = ownerUserID;
    }

    // --- Getters and Setters ---

    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getOwnerUserID() { return ownerUserID; }
    public void setOwnerUserID(String ownerUserID) { this.ownerUserID = ownerUserID; }

    // --- Methods ---

    /**
     * Gets account info for the given user ID.
     * @return account info string in "accountNo,balance" format
     */
    public String getAccountInfo(String userID) {
        String sql = "SELECT account_no, balance FROM accounts WHERE account_no = ? AND owner_user_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNo);
            ps.setString(2, userID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("account_no") + "," + rs.getDouble("balance");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Updates the balance for the given account.
     */
    public void updateBalance(double amount, String accountNo) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setString(2, accountNo);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the last transaction for the given account.
     * @return the last Transaction or null
     */
    public Transaction getLastTransaction(String accountNo) {
        String sql = "SELECT transaction_id, amount, date, type, from_account_no, to_account_no " +
                     "FROM transactions WHERE from_account_no = ? OR to_account_no = ? " +
                     "ORDER BY date DESC LIMIT 1";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNo);
            ps.setString(2, accountNo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Transaction tx = new Transaction();
                    tx.setTransactionID(rs.getString("transaction_id"));
                    tx.setAmount(rs.getDouble("amount"));
                    tx.setDate(rs.getDate("date"));
                    tx.setType(TransactionType.valueOf(rs.getString("type")));
                    tx.setFromAccountNo(rs.getString("from_account_no"));
                    tx.setToAccountNo(rs.getString("to_account_no"));
                    return tx;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Validates the account number format.
     * @return true if valid
     */
    public boolean validateAccountNo(String accountNo) {
        return accountNo != null && accountNo.matches("\\d{5}");
    }
}