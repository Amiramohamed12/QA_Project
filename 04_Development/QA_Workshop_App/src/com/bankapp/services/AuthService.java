package com.bankapp.services;

/**
 * Service skeleton for authentication operations.
 * All methods are TODO placeholders only.
 */
public class AuthService {

    /**
     * Authenticates a user by email and password.
     * @return role string ("admin", "client") or null if failed
     */
    public String authenticate(String email, String password) {
        // TODO: implement backend logic later
        return null;
    }

    /**
     * Validates the email format.
     * @return true if email is valid
     */
    private boolean validateEmail(String email) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Validates the password format.
     * @return true if password is valid
     */
    private boolean validatePassword(String password) {
        // TODO: implement backend logic later
        return false;
    }

    /**
     * Determines where to redirect after login based on role.
     * @return screen/page name to redirect to
     */
    public String redirectAfterLogin(String role) {
        // TODO: implement backend logic later
        return "";
    }
}
