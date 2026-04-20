package com.bankapp.ui.frames;

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
        saveButton.addActionListener(e -> handleSave());

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
        if (userIdField.getText().trim().isEmpty()) {
            showError("Please enter a user ID.");
            return;
        }

        firstNameField.setEnabled(true);
        lastNameField.setEnabled(true);
        emailField.setEnabled(true);
        saveButton.setEnabled(true);

        firstNameField.setText("Omar");
        lastNameField.setText("Kandeel");
        emailField.setText("omar@example.com");

        // TODO: load real user info from backend.
        showSuccess("Sample user data loaded.");
    }

    private void handleSave() {
        if (firstNameField.getText().trim().isEmpty()
                || lastNameField.getText().trim().isEmpty()
                || emailField.getText().trim().isEmpty()) {
            showError("Please fill in all editable fields.");
            return;
        }

        // TODO: implement real admin save logic.
        showSuccess("Account info saved.");
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
