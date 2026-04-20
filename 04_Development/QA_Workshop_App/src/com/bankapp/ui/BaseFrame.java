package com.bankapp.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Shared frame setup and very simple styling helpers for the Swing pages.
 */
public abstract class BaseFrame extends JFrame {

    protected static final int DEFAULT_WIDTH = 920;
    protected static final int DEFAULT_HEIGHT = 680;
    protected static final Font ROLE_FONT = new Font("SansSerif", Font.BOLD, 20);
    protected static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 24);
    protected static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 20);
    protected static final Font BUTTON_FONT = new Font("SansSerif", Font.PLAIN, 20);
    protected static final Font FIELD_FONT = new Font("SansSerif", Font.PLAIN, 18);
    protected static final Color BACKGROUND_COLOR = Color.WHITE;
    protected static final Color ACCENT_BLUE = new Color(52, 152, 219);

    public BaseFrame(String title) {
        this(title, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public BaseFrame(String title, int width, int height) {
        super(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setResizable(false);
        initComponents();
    }

    protected abstract void initComponents();

    protected JPanel createRootPanel(String roleText) {
        JPanel rootPanel = new JPanel(null);
        rootPanel.setBackground(BACKGROUND_COLOR);

        JLabel roleLabel = new JLabel(roleText);
        roleLabel.setFont(ROLE_FONT);
        roleLabel.setBounds(20, 10, 220, 30);
        rootPanel.add(roleLabel);

        return rootPanel;
    }

    protected JPanel createPagePanel() {
        JPanel pagePanel = new JPanel(null);
        pagePanel.setBackground(BACKGROUND_COLOR);
        pagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        pagePanel.setBounds(10, 45, getWidth() - 35, getHeight() - 90);
        return pagePanel;
    }

    protected JLabel createPageTitle(String text) {
        JLabel titleLabel = new JLabel(text, SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        return titleLabel;
    }

    protected JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        return label;
    }

    protected JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(FIELD_FONT);
        field.setBorder(new LineBorder(Color.BLACK, 1, true));
        return field;
    }

    protected JTextField createTextField(int columns) {
        JTextField field = createTextField();
        field.setColumns(columns);
        return field;
    }

    protected JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(FIELD_FONT);
        field.setBorder(new LineBorder(Color.BLACK, 1, true));
        return field;
    }

    protected JPasswordField createPasswordField(int columns) {
        JPasswordField field = createPasswordField();
        field.setColumns(columns);
        return field;
    }

    protected JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(BACKGROUND_COLOR);
        button.setForeground(Color.BLACK);
        button.setBorder(new LineBorder(Color.BLACK, 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    protected JButton createLinkButton(String text) {
        JButton button = new JButton("<html><u>" + text + "</u></html>");
        button.setFont(new Font("SansSerif", Font.PLAIN, 18));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.BLACK);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    protected JButton createCheckButton() {
        JButton button = new JButton("\u2713");
        button.setFont(new Font("SansSerif", Font.BOLD, 20));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.BLACK, 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    protected JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(FIELD_FONT);
        comboBox.setBackground(BACKGROUND_COLOR);
        comboBox.setBorder(new LineBorder(Color.BLACK, 1));
        return comboBox;
    }

    protected void styleTable(JTable table, boolean showHeader, boolean colorFirstColumn) {
        table.setFont(FIELD_FONT);
        table.setRowHeight(42);
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setDefaultEditor(Object.class, null);

        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setFont(LABEL_FONT);
        }

        if (!showHeader) {
            table.setTableHeader(null);
        }

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable currentTable,
                                                           Object value,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row,
                                                           int column) {
                Component component = super.getTableCellRendererComponent(
                        currentTable, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                component.setBackground(Color.WHITE);
                component.setForeground(Color.BLACK);

                if (colorFirstColumn && column == 0) {
                    component.setBackground(ACCENT_BLUE);
                    component.setForeground(Color.WHITE);
                }

                return component;
            }
        };

        for (int column = 0; column < table.getColumnCount(); column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(renderer);
        }
    }

    protected void openFrame(JFrame nextFrame) {
        nextFrame.setVisible(true);
        dispose();
    }

    protected void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
