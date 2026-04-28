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

public class LoginPanel extends JPanel {
    public LoginPanel(JTextField usernameField, JPasswordField passwordField, Runnable onLogin, Runnable onSwitchToRegister) {
        setLayout(new BorderLayout(0, 18));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(28, 28, 28, 28),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel("FitnessMS", SwingConstants.LEFT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(28f));
        titleLabel.setForeground(Color.WHITE);
        JLabel subtitleLabel = new JLabel("Sign in to continue", SwingConstants.LEFT);
        subtitleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        GridBagConstraints gbc = baseConstraints();
        addLabeledField(form, gbc, 0, "Username", usernameField);
        addLabeledField(form, gbc, 1, "Password", passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> onLogin.run());

        JButton switchToRegisterButton = new JButton("Create Account");
        switchToRegisterButton.addActionListener(e -> onSwitchToRegister.run());

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.add(loginButton);
        actions.add(switchToRegisterButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
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
        label.setForeground(Color.WHITE);

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
