package com.bankapp.services;

public class ValidationService {

    public static final double MIN_AMOUNT = 1000;
    public static final double MAX_AMOUNT = 5000;
    public static final int ACCOUNT_NO_LENGTH = 5;
    public static final int USER_ID_LENGTH = 5;
    public static final int MAX_ACCOUNTS = 2;
    public static final int MIN_PASSWORD_LENGTH = 5;
    public static final int MAX_PASSWORD_LENGTH = 30;
    public static final int MIN_NAME_LENGTH = 5;
    public static final int MAX_NAME_LENGTH = 30;

    public boolean validateUserID(String userID) {
        return userID != null && userID.matches("\\d{" + USER_ID_LENGTH + "}");
    }

    public boolean validateAccountNo(String accountNo) {
        return accountNo != null && accountNo.matches("\\d{" + ACCOUNT_NO_LENGTH + "}");
    }

    public boolean validateAmount(double amount) {
        return amount >= MIN_AMOUNT && amount <= MAX_AMOUNT;
    }

    public boolean validateAccountLimit(int count) {
        return count < MAX_ACCOUNTS;
    }

    public boolean validateAccountType(String type) {
        if (type == null) {
            return false;
        }

        return type.equalsIgnoreCase("SAVING") || type.equalsIgnoreCase("CURRENT");
    }

    public boolean validateSSN(String ssn) {
        return ssn != null && !ssn.isEmpty() && ssn.matches("\\d+");
    }

    public boolean validateName(String name) {
        if (name == null) {
            return false;
        }

        return name.length() >= MIN_NAME_LENGTH
                && name.length() <= MAX_NAME_LENGTH
                && name.matches("[A-Za-z]+");
    }

    public boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        return email.contains("@") && email.contains(".") && !email.contains(" ");
    }

    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }

        return password.length() >= MIN_PASSWORD_LENGTH
                && password.length() <= MAX_PASSWORD_LENGTH;
    }

    public boolean isUserIDExists(String userID) {
        // TODO: Replace this temporary check with a database lookup later.
        return "12341".equals(userID) || "12342".equals(userID);
    }

    public boolean isAccountExists(String accountNo) {
        // TODO: Replace this temporary check with a database lookup later.
        return "12345".equals(accountNo) || "54321".equals(accountNo);
    }
}
