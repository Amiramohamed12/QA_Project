package com.bankapp.models;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract base class for all users (Admin and Client).
 * Fields: userID (5 digits, auto-generated), firstName, lastName, email, password, ssn.
 */
public abstract class AbstractUser {

    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String ssn;

    public AbstractUser() {
    }

    public AbstractUser(String userID, String firstName, String lastName,
                        String email, String password, String ssn) {
        this.userID    = userID;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.password  = password;
        this.ssn       = ssn;
    }

    // --- Getters and Setters ---

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    // --- Methods ---

    /**
     * Attempts to log in with the given credentials.
     * @return true if login succeeds
     */
    public boolean login(String email, String password) {
        String sql = "SELECT user_id FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    this.userID = rs.getString("user_id");
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Logs the user out.
     */
    public void logout() {
        com.bankapp.services.SessionManager.clearSession();
    }

    /**
     * Validates user credentials.
     * @return true if credentials are valid
     */
    private boolean validateCredentials() {
        return email != null && !email.isEmpty()
                && password != null && !password.isEmpty();
    }
}