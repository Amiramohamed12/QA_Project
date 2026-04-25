package com.bankapp.models;

import com.bankapp.enums.TransactionType;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Transaction model class.
 * Fields: transactionID, amount (1000..5000), date, type, fromAccountNo, toAccountNo.
 */
public class Transaction {

    private String          transactionID;
    private double          amount;
    private Date            date;
    private TransactionType type;
    private String          fromAccountNo;
    private String          toAccountNo;

    public Transaction() {
    }

    public Transaction(String transactionID, double amount, Date date,
                       TransactionType type, String fromAccountNo, String toAccountNo) {
        this.transactionID = transactionID;
        this.amount        = amount;
        this.date          = date;
        this.type          = type;
        this.fromAccountNo = fromAccountNo;
        this.toAccountNo   = toAccountNo;
    }

    // --- Getters and Setters ---

    public String getTransactionID() { return transactionID; }
    public void setTransactionID(String transactionID) { this.transactionID = transactionID; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public String getFromAccountNo() { return fromAccountNo; }
    public void setFromAccountNo(String fromAccountNo) { this.fromAccountNo = fromAccountNo; }

    public String getToAccountNo() { return toAccountNo; }
    public void setToAccountNo(String toAccountNo) { this.toAccountNo = toAccountNo; }

    // --- Methods ---

    /**
     * Executes the transaction.
     * @return true if transaction succeeds
     */
    public boolean doTransaction() {
        String insertSql = "INSERT INTO transactions (amount, type, from_account_no, to_account_no) VALUES (?, ?, ?, ?)";
        String debitSql  = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
        String creditSql = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement insertPs = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement debitPs  = conn.prepareStatement(debitSql);
                 PreparedStatement creditPs = conn.prepareStatement(creditSql)) {

                insertPs.setDouble(1, amount);
                insertPs.setString(2, type.name());
                insertPs.setString(3, fromAccountNo);
                insertPs.setString(4, toAccountNo);
                insertPs.executeUpdate();

                try (ResultSet rs = insertPs.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.transactionID = String.valueOf(rs.getLong(1));
                    }
                }

                debitPs.setDouble(1, amount);
                debitPs.setString(2, fromAccountNo);
                debitPs.executeUpdate();

                if (toAccountNo != null && !toAccountNo.isEmpty()) {
                    creditPs.setDouble(1, amount);
                    creditPs.setString(2, toAccountNo);
                    creditPs.executeUpdate();
                }

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
     * Validates the transaction amount.
     * @return true if amount is valid (1000..5000)
     */
    private boolean validateAmount(double amount) {
        return amount >= 1000 && amount <= 5000;
    }

    /**
     * Validates the account number format.
     * @return true if valid
     */
    private boolean validateAccountNo(String accountNo) {
        return accountNo != null && accountNo.matches("\\d{5}");
    }

    /**
     * Gets transaction details as a string.
     * @return transaction details
     */
    public String getTransactionDetails() {
        return transactionID + "," + type + "," + amount + "," + fromAccountNo + "," + toAccountNo;
    }
}