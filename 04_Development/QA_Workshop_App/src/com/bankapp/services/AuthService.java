package com.bankapp.services;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {

    private final ValidationService validationService = new ValidationService();

    public String authenticate(String email, String password) {
        SessionManager.clearSession();

        if (!validationService.validateEmail(email) || !validationService.validatePassword(password)) {
            return "INVALID";
        }

        String sql = "SELECT user_id, role FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String userID = rs.getString("user_id");
                    String role   = rs.getString("role");

                    SessionManager.setCurrentUserId(userID);
                    SessionManager.setCurrentRole(role);
                    return role;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
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