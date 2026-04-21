package com.bankapp.services;

public class AuthService {

    private static final String ADMIN_EMAIL = "admin@bank.com";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String CLIENT_EMAIL = "client@bank.com";
    private static final String CLIENT_PASSWORD = "client123";

    private final ValidationService validationService = new ValidationService();

    public String authenticate(String email, String password) {
        SessionManager.clearSession();

        if (!validationService.validateEmail(email) || !validationService.validatePassword(password)) {
            return "INVALID";
        }

        // TODO: replace hardcoded credentials with database lookup.
        // Pseudo logic:
        // 1. Query user by email.
        // 2. If user not found, return "INVALID".
        // 3. Compare entered password with stored password.
        // 4. If password matches, return stored role ("ADMIN" or "CLIENT").
        // 5. Otherwise return "INVALID".
        if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
            SessionManager.setCurrentUserId("00001");
            SessionManager.setCurrentRole("ADMIN");
            return "ADMIN";
        }

        if (email.equals(CLIENT_EMAIL) && password.equals(CLIENT_PASSWORD)) {
            SessionManager.setCurrentUserId("12341");
            SessionManager.setCurrentRole("CLIENT");
            return "CLIENT";
        }

        return "INVALID";
    }

    public String redirectAfterLogin(String role) {
        if ("ADMIN".equals(role)) {
            return "AdminHomeFrame";
        }

        if ("CLIENT".equals(role)) {
            return "UserHomeFrame";
        }

        return "LoginFrame";
    }
}
