package com.bankapp.ui.frames;

import com.bankapp.ui.BaseFrame;

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
        submitButton.addActionListener(e -> handleSubmit());

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

    private void handleSubmit() {
        if (firstNameField.getText().trim().isEmpty()
                || lastNameField.getText().trim().isEmpty()
                || ssnField.getText().trim().isEmpty()
                || emailField.getText().trim().isEmpty()
                || new String(passwordField.getPassword()).trim().isEmpty()) {
            showError("Please fill in all registration fields.");
            return;
        }

        // TODO: implement real registration logic.
        showSuccess("Registration submitted.");
        openFrame(new LoginFrame());
    }
}
