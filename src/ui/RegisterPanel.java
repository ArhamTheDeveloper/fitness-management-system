package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Registration screen for new users.
 * Collects username, password, starting weight, and fitness goal.
 */
public class RegisterPanel extends JPanel {

    public RegisterPanel(
            JTextField usernameField,
            JPasswordField passwordField,
            JTextField weightField,
            JTextField goalField,
            Runnable onRegister,
            Runnable onBackToLogin
    ) {
        setLayout(new GridBagLayout()); // center the card
        setBackground(AppTheme.BG);
        JPanel card = buildCard(usernameField, passwordField, weightField, goalField, onRegister, onBackToLogin);
        card.setPreferredSize(new Dimension(440, 420));
        add(card);
    }

    private JPanel buildCard(
            JTextField usernameField,
            JPasswordField passwordField,
            JTextField weightField,
            JTextField goalField,
            Runnable onRegister,
            Runnable onBackToLogin
    ) {
        JPanel card = new JPanel(new BorderLayout(0, 20));
        card.setBackground(AppTheme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(32, 36, 32, 36)
        ));

        // Header
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(AppTheme.SURFACE);
        header.add(AppTheme.titleLabel("Create Account"));
        header.add(Box.createVerticalStrut(4));
        header.add(AppTheme.subtitleLabel("Set up your fitness profile"));

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.SURFACE);

        AppTheme.styleTextField(usernameField);
        AppTheme.styleTextField(passwordField);
        AppTheme.styleTextField(weightField);
        AppTheme.styleTextField(goalField);

        // Placeholder hint for weight field
        weightField.setToolTipText("Enter your current weight in kilograms, e.g. 72.5");
        goalField.setToolTipText("e.g. Lose weight, Build muscle, Improve endurance");

        GridBagConstraints gbc = makeGbc();
        addField(form, gbc, 0, "Username", usernameField);
        addField(form, gbc, 1, "Password", passwordField);
        addField(form, gbc, 2, "Current Weight (kg)", weightField);
        addField(form, gbc, 3, "Fitness Goal", goalField);

        // Buttons
        JButton registerBtn = AppTheme.primaryButton("Register");
        registerBtn.setPreferredSize(new Dimension(120, 34));
        registerBtn.addActionListener(e -> onRegister.run());

        JButton backBtn = AppTheme.secondaryButton("Back to Login");
        backBtn.addActionListener(e -> onBackToLogin.run());

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.setBackground(AppTheme.SURFACE);
        actions.add(registerBtn);
        actions.add(backBtn);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.insets = new Insets(14, 0, 0, 0);
        form.add(actions, gbc);

        card.add(header, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        return card;
    }

    private GridBagConstraints makeGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 0, 6, 10);
        return gbc;
    }

    private void addField(JPanel form, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0;
        form.add(AppTheme.bodyLabel(labelText), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        form.add(field, gbc);
    }
}
