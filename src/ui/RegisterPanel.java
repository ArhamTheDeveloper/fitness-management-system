package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class RegisterPanel extends JPanel {
    public RegisterPanel(
            JTextField usernameField,
            JPasswordField passwordField,
            JTextField weightField,
            JTextField goalField,
            Runnable onRegister,
            Runnable onBackToLogin
    ) {
        setLayout(new BorderLayout(0, 18));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(28, 28, 28, 28),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel("Create Account", SwingConstants.LEFT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(28f));
        titleLabel.setForeground(Color.BLACK);
        JLabel subtitleLabel = new JLabel("Set up a new fitness profile", SwingConstants.LEFT);
        subtitleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        GridBagConstraints gbc = baseConstraints();
        addLabeledField(form, gbc, 0, "Username", usernameField);
        addLabeledField(form, gbc, 1, "Password", passwordField);
        addLabeledField(form, gbc, 2, "Current Weight (kg)", weightField);
        addLabeledField(form, gbc, 3, "Fitness Goal", goalField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> onRegister.run());

        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> onBackToLogin.run());

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.add(registerButton);
        actions.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 0, 0, 0);
        form.add(actions, gbc);

        add(form, BorderLayout.CENTER);
    }

    private GridBagConstraints baseConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 12);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    private void addLabeledField(JPanel form, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.BLACK);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        form.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        field.setPreferredSize(new Dimension(280, 30));
        form.add(field, gbc);
    }
}
