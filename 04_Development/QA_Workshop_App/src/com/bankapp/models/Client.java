package com.bankapp.models;

import com.bankapp.enums.AccountType;
import java.util.ArrayList;
import java.util.List;

/**
 * Client model class extending AbstractUser.
 * A Client can own up to 3 BankAccounts.
 */
public class Client extends AbstractUser {

    private List<BankAccount> accounts; // max = 3

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
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Creates a new bank account of the specified type.
     * @return the created BankAccount or null
     */
    public BankAccount createAccount(AccountType type) {
        // TODO: implement backend logic later
        return null;
    }

    /**
     * Views account info for the given user ID.
     * @return list of account info
     */
    public List<String> viewAccountInfo(String userID) {
        // TODO: implement backend logic later
        return new ArrayList<>();
    }

    /**
     * Views transaction history for the given account number.
     * @return list of transactions
     */
    public List<String> viewTransactionHistory(String accountNo) {
        // TODO: implement backend logic later
        return new ArrayList<>();
    }

    /**
     * Deposits amount into the given account.
     * @return true if deposit succeeds
     */
    public boolean deposit(String accountNo, double amount) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Withdraws amount from the given account.
     * @return true if withdrawal succeeds
     */
    public boolean withdraw(String accountNo, double amount) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Transfers funds between accounts.
     * @return true if transfer succeeds
     */
    public boolean fundTransfer(String fromAcc, String toAcc, double amount) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates whether the user has reached the max account limit.
     * @return true if user can create more accounts
     */
    public boolean validateMaxAccounts(String userId) {
        // TODO: implement backend logic later
        return false;
    }
}
