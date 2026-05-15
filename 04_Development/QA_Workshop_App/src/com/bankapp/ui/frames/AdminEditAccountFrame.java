package com.bankapp.ui.frames;

import com.bankapp.models.Admin;
import com.bankapp.models.Client;
import com.bankapp.services.ValidationService;
import com.bankapp.ui.BaseFrame;

import javax.swing.*;

/**
 * Admin edit account page.
 */
public class AdminEditAccountFrame extends BaseFrame {

    private JTextField userIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JButton saveButton;

    public AdminEditAccountFrame() {
        super("BankApp - Edit Account Info");
    }

    @Override
    protected void initComponents() {
        JPanel rootPanel = createRootPanel("Admin");
        JPanel pagePanel = createPagePanel();

        JButton homeButton = createButton("Home");
        homeButton.setBounds(20, 20, 90, 45);
        homeButton.addActionListener(e -> handleHome());

        JLabel titleLabel = createPageTitle("Edit Account Info page");
        titleLabel.setBounds(200, 25, 390, 40);

        JButton logoutButton = createButton("log-out");
        logoutButton.setBounds(640, 20, 140, 55);
        logoutButton.addActionListener(e -> handleLogout());

        JLabel userIdLabel = createLabel("User-ID:");
        userIdLabel.setBounds(45, 110, 120, 35);

        userIdField = createTextField();
        userIdField.setBounds(190, 105, 120, 45);

        JButton searchButton = createCheckButton();
        searchButton.setBounds(405, 110, 35, 35);
        searchButton.addActionListener(e -> handleSearch());

        JLabel firstNameLabel = createLabel("First Name:");
        firstNameLabel.setBounds(35, 195, 140, 35);

        firstNameField = createTextField();
        firstNameField.setBounds(190, 190, 150, 45);
        firstNameField.setEnabled(false);

        JLabel lastNameLabel = createLabel("Last Name:");
        lastNameLabel.setBounds(35, 265, 140, 35);

        lastNameField = createTextField();
        lastNameField.setBounds(190, 260, 150, 45);
        lastNameField.setEnabled(false);

        JLabel emailLabel = createLabel("E-mail:");
        emailLabel.setBounds(65, 345, 120, 35);

        emailField = createTextField();
        emailField.setBounds(190, 340, 150, 45);
        emailField.setEnabled(false);

        saveButton = createButton("Save");
        saveButton.setBounds(370, 455, 150, 60);
        saveButton.setEnabled(false);
        saveButton.addActionListener(e -> handleEditAccount());

        pagePanel.add(homeButton);
        pagePanel.add(titleLabel);
        pagePanel.add(logoutButton);
        pagePanel.add(userIdLabel);
        pagePanel.add(userIdField);
        pagePanel.add(searchButton);
        pagePanel.add(firstNameLabel);
        pagePanel.add(firstNameField);
        pagePanel.add(lastNameLabel);
        pagePanel.add(lastNameField);
        pagePanel.add(emailLabel);
        pagePanel.add(emailField);
        pagePanel.add(saveButton);

        rootPanel.add(pagePanel);
        setContentPane(rootPanel);
    }

    private void handleSearch() {
        String userID = userIdField.getText().trim();

        if (userID.isEmpty()) {
            showError("Please enter valid User ID");
            return;
        }

        ValidationService validationService = new ValidationService();

        if (!validationService.validateUserID(userID)) {
            showError("Please enter valid User ID");
            return;
        }

        if (!validationService.isUserIDExists(userID)) {
            showError("Please enter valid User ID");
            return;
        }

        Admin admin = new Admin();
        Client client = admin.viewClientDetails(userID);

        if (client == null) {
            showError("Please enter valid User ID");
            return;
        }

        firstNameField.setEnabled(true);
        lastNameField.setEnabled(true);
        emailField.setEnabled(true);
        saveButton.setEnabled(true);

        firstNameField.setText(client.getFirstName());
        lastNameField.setText(client.getLastName());
        emailField.setText(client.getEmail());
    }

    private void handleEditAccount() {
        String userID = userIdField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();

        ValidationService validationService = new ValidationService();

        if (!validationService.validateUserID(userID)) {
            showError("Please enter valid User ID");
            return;
        }

        if (!validationService.isUserIDExists(userID)) {
            showError("Please enter valid User ID");
            return;
        }

        if (firstName.isEmpty() && lastName.isEmpty() && email.isEmpty()) {
            showError("No data updated.");
            return;
        }

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            showError("Please fill in all editable fields.");
            return;
        }

        if (!validationService.validateName(firstName)) {
            showError("Please enter valid User ID");
            return;
        }

        if (!validationService.validateName(lastName)) {
            showError("Please enter valid User ID");
            return;
        }

        if (!validationService.validateEmail(email)) {
            showError("Please enter valid User ID");
            return;
        }

        Admin admin = new Admin();
        boolean updated = admin.editClientData(userID, firstName, lastName, email);

        if (updated) {
            showSuccess("Account data updated successfully");
        } else {
            showError("Account update failed");
        }
    }

    private void handleHome() {
        // TODO: keep admin context when backend is ready.
        openFrame(new AdminHomeFrame());
    }

    private void handleLogout() {
        // TODO: clear admin session when backend is ready.
        openFrame(new LoginFrame());
    }
}
