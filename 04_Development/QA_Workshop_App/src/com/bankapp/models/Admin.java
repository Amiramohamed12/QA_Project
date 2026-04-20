package com.bankapp.models;

/**
 * Admin model class extending AbstractUser.
 * An Admin can manage client accounts and perform fund transfers.
 */
public class Admin extends AbstractUser {

    private String adminID;

    public Admin() {
        super();
    }

    public Admin(String userID, String firstName, String lastName,
                 String email, String password, String ssn, String adminID) {
        super(userID, firstName, lastName, email, password, ssn);
        this.adminID = adminID;
    }

    // --- Getters and Setters ---

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    // --- Methods ---

    /**
     * Creates a bank account for a client with the given user ID.
     * @return true if account creation succeeds
     */
    public boolean createClientAccount(String userID) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Deletes a client account by account number.
     * @return true if deletion succeeds
     */
    public boolean deleteClientAccount(String accountNo) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Views client details for the given user ID.
     * @return the Client or null
     */
    public Client viewClientDetails(String userID) {
        // TODO: implement backend logic later
        return null;
    }

    /**
     * Edits client data.
     * @return true if edit succeeds
     */
    public boolean editClientData(String userID, String firstName,
                                  String lastName, String email) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Transfers funds between two accounts.
     * @return true if transfer succeeds
     */
    public boolean fundTransfer(String fromAcc, String toAcc, double amount) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates a user ID format.
     * @return true if valid
     */
    private boolean validateUserID(String userID) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates an account number format.
     * @return true if valid
     */
    private boolean validateAccountNo(String accountNo) {
        // TODO: implement backend logic later
        return false;
    }
}
