package com.bankapp.services;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidationService {

    public static final double MIN_AMOUNT         = 1000;
    public static final double MAX_AMOUNT         = 5000;
    public static final int    ACCOUNT_NO_LENGTH  = 5;
    public static final int    USER_ID_LENGTH     = 5;
    public static final int    MAX_ACCOUNTS       = 3;
    public static final int    MIN_PASSWORD_LENGTH = 5;
    public static final int    MAX_PASSWORD_LENGTH = 30;
    public static final int    MIN_NAME_LENGTH    = 5;
    public static final int    MAX_NAME_LENGTH    = 30;

    public boolean validateUserID(String userID) {
        return userID != null && userID.matches("\\d{" + USER_ID_LENGTH + "}");
    }

    public boolean validateAccountNo(String accountNo) {
        return accountNo != null && accountNo.matches("\\d{" + ACCOUNT_NO_LENGTH + "}");
    }

    public boolean validateAmount(double amount) {
        return amount >= MIN_AMOUNT && amount <= MAX_AMOUNT && amount == Math.floor(amount);
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

        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                && !email.contains("@.")
                && !email.contains("..");
    }

    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }

        if (!meetsPasswordConstraints(password)) {
            return false;
        }

        return password.length() >= MIN_PASSWORD_LENGTH
                && password.length() <= MAX_PASSWORD_LENGTH;
    }

    public boolean isUserIDExists(String userID) {
        String sql = "SELECT 1 FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isAccountExists(String accountNo) {
        String sql = "SELECT 1 FROM accounts WHERE account_number = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNo);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isAccountOwnedByUser(String accountNo, String userID) {
        if (accountNo == null || userID == null) {
            return false;
        }

        String sql = "SELECT 1 FROM accounts WHERE account_number = ? AND user_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNo);
            ps.setString(2, userID);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public double getAccountBalance(String accountNo) {
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private boolean meetsPasswordConstraints(String password) {
        boolean hasUpperCase = false;
        boolean hasDigit     = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }

            if (Character.isDigit(c)) {
                hasDigit = true;
            }

            if (hasUpperCase && hasDigit) {
                return true;
            }
        }

        return hasUpperCase && hasDigit;
    }
}
