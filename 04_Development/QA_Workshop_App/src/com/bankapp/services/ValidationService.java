package com.bankapp.services;

/**
 * Service skeleton for validation operations.
 * All methods are TODO placeholders only.
 */
public class ValidationService {

    private double minAmount = 1000;
    private double maxAmount = 5000;
    private int accountNoLength = 5;
    private int userIDLength = 5;
    private int maxAccounts = 3;
    private int maxPasswordLength = 30;
    private int minPasswordLength = 5;

    /**
     * Validates a user ID format (5 digits).
     * @return true if valid
     */
    public boolean validateUserID(String userID) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates an account number format (5 digits).
     * @return true if valid
     */
    public boolean validateAccountNo(String accountNo) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates a transaction amount (1000..5000).
     * @return true if valid
     */
    public boolean validateAmount(double amount) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates whether the account limit is reached.
     * @return true if under limit
     */
    public boolean validateAccountLimit(int count) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates the account type string.
     * @return true if valid
     */
    public boolean validateAccountType(String type) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates the SSN format (numeric).
     * @return true if valid
     */
    public boolean validateSSN(String ssn) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates a name (5..30, alpha only).
     * @return true if valid
     */
    public boolean validateName(String name) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Checks if a user ID already exists.
     * @return true if exists
     */
    public boolean isUserIDExists(String userID) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Checks if an account already exists.
     * @return true if exists
     */
    public boolean isAccountExists(String accountNo) {
        // TODO: implement backend logic later
        return false;
    }
}
