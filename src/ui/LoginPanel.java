package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * Login screen shown when the app first opens.
 * Centered card layout with username / password fields.
 */
public class LoginPanel extends JPanel {

    public LoginPanel(
            JTextField usernameField,
            JPasswordField passwordField,
            Runnable onLogin,
            Runnable onSwitchToRegister
    ) {
        setLayout(new GridBagLayout()); // center the card
        setBackground(AppTheme.BG);
        JPanel card = buildCard(usernameField, passwordField, onLogin, onSwitchToRegister);
        card.setPreferredSize(new Dimension(420, 340));
        add(card);
    }

    private JPanel buildCard(
            JTextField usernameField,
            JPasswordField passwordField,
            Runnable onLogin,
            Runnable onSwitchToRegister
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
        header.add(AppTheme.titleLabel("FitnessMS"));
        header.add(Box.createVerticalStrut(4));
        header.add(AppTheme.subtitleLabel("Sign in to your account"));

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.SURFACE);

        AppTheme.styleTextField(usernameField);
        AppTheme.styleTextField(passwordField);

        GridBagConstraints gbc = makeGbc();
        addField(form, gbc, 0, "Username", usernameField);
        addField(form, gbc, 1, "Password", passwordField);

        // Buttons
        JButton loginBtn = AppTheme.primaryButton("Login");
        loginBtn.setPreferredSize(new Dimension(120, 34));
        loginBtn.addActionListener(e -> onLogin.run());

        JButton registerBtn = AppTheme.secondaryButton("Create Account");
        registerBtn.addActionListener(e -> onSwitchToRegister.run());

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.setBackground(AppTheme.SURFACE);
        actions.add(loginBtn);
        actions.add(registerBtn);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(14, 0, 0, 0);
        form.add(actions, gbc);

        // Enter key submits from either field
        bindEnterKey(usernameField, onLogin);
        bindEnterKey(passwordField, onLogin);

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

    private void bindEnterKey(JComponent field, Runnable action) {
        field.getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
        field.getActionMap().put("submit", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { action.run(); }
        });
    }
}
