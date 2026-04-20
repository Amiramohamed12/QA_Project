package com.bankapp;

import com.bankapp.ui.frames.LoginFrame;

/**
 * Main entry point for the BankApp application.
 * Launches the Login screen.
 */
public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
