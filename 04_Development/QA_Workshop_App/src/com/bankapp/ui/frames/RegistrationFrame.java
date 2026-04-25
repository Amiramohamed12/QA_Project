package com.bankapp.ui.frames;

import com.bankapp.services.ValidationService;
import com.bankapp.ui.BaseFrame;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import javax.swing.*;

/**
 * Simple registration page matching the wireframe.
 */
public class RegistrationFrame extends BaseFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField ssnField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public RegistrationFrame() {
        super("BankApp - Registration");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Admin/Client");
        JPanel pagePanel = createPagePanel();

        JLabel titleLabel = createPageTitle("Registration page");
        titleLabel.setBounds(220, 20, 370, 40);

        JLabel firstNameLabel = createLabel("First Name");
        firstNameLabel.setBounds(65, 50, 180, 25);

        firstNameField = createTextField();
        firstNameField.setBounds(65, 80, 220, 45);

        JLabel lastNameLabel = createLabel("Last Name");
        lastNameLabel.setBounds(65, 135, 180, 25);

        lastNameField = createTextField();
        lastNameField.setBounds(65, 165, 220, 45);

        JLabel ssnLabel = createLabel("SSN");
        ssnLabel.setBounds(65, 220, 180, 25);

        ssnField = createTextField();
        ssnField.setBounds(65, 250, 220, 45);

        JLabel emailLabel = createLabel("Email");
        emailLabel.setBounds(65, 305, 180, 25);

        emailField = createTextField();
        emailField.setBounds(65, 335, 220, 45);

        JLabel passwordLabel = createLabel("Password");
        passwordLabel.setBounds(65, 390, 180, 25);

        passwordField = createPasswordField();
        passwordField.setBounds(65, 420, 220, 45);

        firstNameField.setToolTipText("First name");
        lastNameField.setToolTipText("Last name");
        ssnField.setToolTipText("SSN");
        emailField.setToolTipText("Email");
        passwordField.setToolTipText("Password");

        JButton submitButton = createButton("Submit");
        submitButton.setBounds(310, 445, 150, 60);
        submitButton.addActionListener(e -> {
            try {
                handleRegister();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        pagePanel.add(titleLabel);
        pagePanel.add(firstNameLabel);
        pagePanel.add(firstNameField);
        pagePanel.add(lastNameLabel);
        pagePanel.add(lastNameField);
        pagePanel.add(ssnLabel);
        pagePanel.add(ssnField);
        pagePanel.add(emailLabel);
        pagePanel.add(emailField);
        pagePanel.add(passwordLabel);
        pagePanel.add(passwordField);
        pagePanel.add(submitButton);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

 private void handleRegister() {
    String firstName = getTrimmedText(firstNameField);
    String lastName = getTrimmedText(lastNameField);
    String email = getTrimmedText(emailField);
    String ssn = getTrimmedText(ssnField);
    String password = getTrimmedPassword(passwordField);

    if (!validateRegistrationInput(firstName, lastName, email, ssn, password)) {
        return;
    }

    String sql = "INSERT INTO users (first_name, last_name, email, ssn, password, role) VALUES (?, ?, ?, ?, ?, 'client')";

    try (Connection conn = DBConnection.connect();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, email);
        ps.setString(4, ssn);
        ps.setString(5, password);
        ps.executeUpdate();

    } catch (SQLException e) {
        showError("Registration failed. Please try again.");
        e.printStackTrace();
        return;
    }

    openFrame(new LoginFrame());
  }
  private String getTrimmedText(JTextField field) {
        return field.getText().trim();
    }

    private String getTrimmedPassword(JPasswordField field) {
        return new String(field.getPassword()).trim();
    }

    private boolean validateRegistrationInput(String firstName, String lastName,
                                              String email, String ssn, String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
                || ssn.isEmpty() || password.isEmpty()) {
            showError("Please enter valid data");
            return false;
        }

        ValidationService validationService = new ValidationService();

        if (!validationService.validateName(firstName)) {
            showError("Please enter valid data");
            return false;
        }

        if (!validationService.validateName(lastName)) {
            showError("Please enter valid data");
            return false;
        }

        if (!validationService.validateEmail(email)) {
            showError("Please enter valid data");
            return false;
        }

        if (!validationService.validateSSN(ssn)) {
            showError("Please enter valid data");
            return false;
        }

        if (!validationService.validatePassword(password)) {
           showError("Please enter valid data");
            return false;
        }

        return true;
    }
}
