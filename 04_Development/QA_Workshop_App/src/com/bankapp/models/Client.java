package com.bankapp.models;

import com.bankapp.enums.AccountType;
import java.util.ArrayList;
import java.util.List;

/**
 * Client model class extending AbstractUser.
 * A Client can own up to 2 BankAccounts.
 */
public class Client extends AbstractUser {

    private List<BankAccount> accounts; // max = 2

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
        // TODO: Save the new account in the database later.
        BankAccount account = new BankAccount("12345", type, 0.0, getUserID());
        accounts.add(account);
        return account;
    }

    /**
     * Views account info for the given user ID.
     * @return list of account info
     */
    public List<String> viewAccountInfo(String userID) {
        // TODO: Load account info from the database later.
        List<String> accountInfo = new ArrayList<>();

        if ("12341".equals(userID)) {
            accountInfo.add("12345,2500.0");
            accountInfo.add("12346,3200.0");
        } else if ("12342".equals(userID)) {
            accountInfo.add("12347,1500.0");
        }

        return accountInfo;
    }

    /**
     * Views transaction history for the given account number.
     * @return list of transactions
     */
    public List<String> viewTransactionHistory(String accountNo) {
        // TODO: Load transaction history from the database later.
        List<String> history = new ArrayList<>();

        if ("12345".equals(accountNo)) {
            history.add("21/4,DEPOSIT,1500.0");
            history.add("20/4,WITHDRAW,1000.0");
            history.add("19/4,FUND_TRANSFER,2000.0");
        } else if ("54321".equals(accountNo)) {
            history.add("21/4,DEPOSIT,3000.0");
            history.add("18/4,WITHDRAW,1200.0");
        }

        return history;
    }

    /**
     * Deposits amount into the given account.
     * @return true if deposit succeeds
     */
    public boolean deposit(String accountNo, double amount) {
        // TODO: Deposit amount into the account in the database later.
        return true;
    }

    /**
     * Withdraws amount from the given account.
     * @return true if withdrawal succeeds
     */
    public boolean withdraw(String accountNo, double amount) {
        // TODO: Withdraw amount from the account in the database later.
        return true;
    }

    /**
     * Transfers funds between accounts.
     * @return true if transfer succeeds
     */
    public boolean fundTransfer(String fromAcc, String toAcc, double amount) {
        // TODO: Transfer funds between accounts in the database later.
        return true;
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
