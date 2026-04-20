package com.bankapp.models;

/**
 * Abstract base class for all users (Admin and Client).
 * Fields: userID (5 digits, auto-generated), firstName, lastName, email, password, ssn.
 */
public abstract class AbstractUser {

    private String userID;       // 5 digits, auto-generated
    private String firstName;    // 5..30, alpha only
    private String lastName;     // 5..30, alpha only
    private String email;        // unique, valid format
    private String password;     // 5..30, masked
    private String ssn;          // numeric, unique

    public AbstractUser() {
        // Default constructor
    }

    public AbstractUser(String userID, String firstName, String lastName,
                        String email, String password, String ssn) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.ssn = ssn;
    }

    // --- Getters and Setters ---

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    // --- Methods ---

    /**
     * Attempts to log in with the given credentials.
     * @return true if login succeeds
     */
    public boolean login(String email, String password) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Logs the user out.
     */
    public void logout() {
        // TODO: implement backend logic later
    }

    /**
     * Validates user credentials.
     * @return true if credentials are valid
     */
    private boolean validateCredentials() {
        // TODO: implement backend logic later
        return false;
    }
}
