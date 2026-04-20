package com.bankapp.models;

import com.bankapp.enums.AccountType;

/**
 * BankAccount model class.
 * Fields: accountNo (5 digits, auto-generated, unique), accountType, balance, ownerUserID.
 */
public class BankAccount {

    private String accountNo;       // 5 digits, auto-generated, unique
    private AccountType accountType;
    private double balance;         // default = 0
    private String ownerUserID;

    public BankAccount() {
        this.balance = 0.0;
    }

    public BankAccount(String accountNo, AccountType accountType,
                       double balance, String ownerUserID) {
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.balance = balance;
        this.ownerUserID = ownerUserID;
    }

    // --- Getters and Setters ---

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOwnerUserID() {
        return ownerUserID;
    }

    public void setOwnerUserID(String ownerUserID) {
        this.ownerUserID = ownerUserID;
    }

    // --- Methods ---

    /**
     * Gets account info for the given user ID.
     * @return account info string
     */
    public String getAccountInfo(String userID) {
        // TODO: implement backend logic later
        return "";
    }

    /**
     * Updates the balance for the given account.
     */
    public void updateBalance(double amount, String accountNo) {
        // TODO: implement backend logic later
    }

    /**
     * Gets the last transaction for the given account.
     * @return the last Transaction or null
     */
    public Transaction getLastTransaction(String accountNo) {
        // TODO: implement backend logic later
        return null;
    }

    /**
     * Validates the account number format.
     * @return true if valid
     */
    public boolean validateAccountNo(String accountNo) {
        // TODO: implement backend logic later
        return false;
    }
}
