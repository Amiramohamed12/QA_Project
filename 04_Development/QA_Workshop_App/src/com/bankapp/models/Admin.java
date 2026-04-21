package com.bankapp.models;

import com.bankapp.enums.AccountType;

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
        // TODO: Delete the account from the database later.
        return true;
    }

    /**
     * Views client details for the given user ID.
     * @return the Client or null
     */
    public Client viewClientDetails(String userID) {
        // TODO: Load client details from the database later.
        Client client;

        if ("12341".equals(userID)) {
            client = new Client("12341", "Ahmed", "Hassan", "ahmed@example.com", "client123", "111111");
            client.getAccounts().add(new BankAccount("12345", AccountType.SAVING, 2500, userID));
            return client;
        }

        if ("12342".equals(userID)) {
            client = new Client("12342", "Mona", "Kareem", "mona@example.com", "client123", "222222");
            client.getAccounts().add(new BankAccount("12346", AccountType.CURRENT, 3000, userID));
            client.getAccounts().add(new BankAccount("12347", AccountType.SAVING, 1500, userID));
            return client;
        }

        return null;
    }

    /**
     * Edits client data.
     * @return true if edit succeeds
     */
    public boolean editClientData(String userID, String firstName,
                                  String lastName, String email) {
        // TODO: Update client data in the database later.
        return true;
    }

    /**
     * Transfers funds between two accounts.
     * @return true if transfer succeeds
     */
    public boolean fundTransfer(String fromAcc, String toAcc, double amount) {
        // TODO: Transfer funds between accounts in the database later.
        return true;
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
