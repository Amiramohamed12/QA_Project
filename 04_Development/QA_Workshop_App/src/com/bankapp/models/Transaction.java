package com.bankapp.models;

import com.bankapp.enums.TransactionType;
import java.util.Date;

/**
 * Transaction model class.
 * Fields: transactionID, amount (1000..5000), date, type, fromAccountNo, toAccountNo.
 */
public class Transaction {

    private String transactionID;
    private double amount;          // 1000..5000
    private Date date;
    private TransactionType type;
    private String fromAccountNo;   // 5 digits
    private String toAccountNo;     // 5 digits, nullable

    public Transaction() {
    }

    public Transaction(String transactionID, double amount, Date date,
                       TransactionType type, String fromAccountNo, String toAccountNo) {
        this.transactionID = transactionID;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.fromAccountNo = fromAccountNo;
        this.toAccountNo = toAccountNo;
    }

    // --- Getters and Setters ---

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getFromAccountNo() {
        return fromAccountNo;
    }

    public void setFromAccountNo(String fromAccountNo) {
        this.fromAccountNo = fromAccountNo;
    }

    public String getToAccountNo() {
        return toAccountNo;
    }

    public void setToAccountNo(String toAccountNo) {
        this.toAccountNo = toAccountNo;
    }

    // --- Methods ---

    /**
     * Executes the transaction.
     * @return true if transaction succeeds
     */
    public boolean doTransaction() {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates the transaction amount.
     * @return true if amount is valid (1000..5000)
     */
    private boolean validateAmount(double amount) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates the account number format.
     * @return true if valid
     */
    private boolean validateAccountNo(String accountNo) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Gets transaction details as a string.
     * @return transaction details
     */
    public String getTransactionDetails() {
        // TODO: implement backend logic later
        return "";
    }
}
