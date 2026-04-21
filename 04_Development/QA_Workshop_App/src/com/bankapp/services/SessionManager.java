package com.bankapp.services;

public class SessionManager {

    private static String currentUserId;
    private static String currentRole;

    public static void setCurrentUserId(String userId) {
        currentUserId = userId;
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentRole(String role) {
        currentRole = role;
    }

    public static String getCurrentRole() {
        return currentRole;
    }

    public static void clearSession() {
        currentUserId = null;
        currentRole = null;
    }
}
