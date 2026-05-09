package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Central styling constants and helper methods used across all UI panels.
 * Keeping everything here makes future color/font tweaks a one-file change.
 */
public final class AppTheme {

    // Palette
    public static final Color PRIMARY       = new Color(37, 99, 235);   // blue-600
    public static final Color PRIMARY_DARK  = new Color(29, 78, 216);   // blue-700 (hover)
    public static final Color DANGER        = new Color(220, 38, 38);   // red-600
    public static final Color DANGER_DARK   = new Color(185, 28, 28);   // red-700 (hover)
    public static final Color SUCCESS       = new Color(22, 163, 74);   // green-600
    public static final Color NEUTRAL       = new Color(107, 114, 128); // gray-500
    public static final Color NEUTRAL_DARK  = new Color(75, 85, 99);    // gray-600

    public static final Color BG            = new Color(249, 250, 251); // gray-50
    public static final Color SURFACE       = Color.WHITE;
    public static final Color BORDER_COLOR  = new Color(209, 213, 219); // gray-300
    public static final Color TEXT_PRIMARY  = new Color(17, 24, 39);    // gray-900
    public static final Color TEXT_MUTED    = new Color(107, 114, 128); // gray-500
    public static final Color TABLE_STRIPE  = new Color(243, 244, 246); // gray-100
    public static final Color TABLE_HEADER  = new Color(229, 231, 235); // gray-200

    // Fonts
    private static final String FONT_FAMILY = Font.DIALOG;
    public static final Font FONT_TITLE    = new Font(FONT_FAMILY, Font.BOLD,  24);
    public static final Font FONT_SUBTITLE = new Font(FONT_FAMILY, Font.PLAIN, 13);
    public static final Font FONT_LABEL    = new Font(FONT_FAMILY, Font.PLAIN, 13);
    public static final Font FONT_BOLD     = new Font(FONT_FAMILY, Font.BOLD,  13);
    public static final Font FONT_METRIC   = new Font(FONT_FAMILY, Font.BOLD,  20);
    public static final Font FONT_SMALL    = new Font(FONT_FAMILY, Font.PLAIN, 11);

    // Borders
    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        );
    }

    public static Border panelPadding() {
        return BorderFactory.createEmptyBorder(24, 28, 24, 28);
    }

    // Global UIManager defaults (call once at startup)
    public static void applyGlobalDefaults() {
        UIManager.put("Panel.background", BG);
        UIManager.put("OptionPane.background", BG);
        UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        UIManager.put("Label.foreground", TEXT_PRIMARY);
        UIManager.put("TextField.background", SURFACE);
        UIManager.put("TextField.foreground", TEXT_PRIMARY);
        UIManager.put("TextField.caretForeground", TEXT_PRIMARY);
        UIManager.put("PasswordField.background", SURFACE);
        UIManager.put("PasswordField.foreground", TEXT_PRIMARY);
        UIManager.put("ComboBox.background", SURFACE);
        UIManager.put("ComboBox.foreground", TEXT_PRIMARY);
        UIManager.put("Spinner.background", SURFACE);
        UIManager.put("Spinner.foreground", TEXT_PRIMARY);
        UIManager.put("Table.background", SURFACE);
        UIManager.put("Table.foreground", TEXT_PRIMARY);
        UIManager.put("Table.alternateRowColor", TABLE_STRIPE);
        UIManager.put("Table.gridColor", BORDER_COLOR);
        UIManager.put("TableHeader.background", TABLE_HEADER);
        UIManager.put("TableHeader.foreground", TEXT_PRIMARY);
        UIManager.put("ScrollPane.background", SURFACE);
        UIManager.put("TitledBorder.titleColor", TEXT_PRIMARY);
        UIManager.put("Button.background", PRIMARY);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", FONT_LABEL);
    }

    // Button factory

    /** Primary blue action button */
    public static JButton primaryButton(String text) {
        return styledButton(text, PRIMARY, PRIMARY_DARK);
    }

    /** Destructive action (red) */
    public static JButton dangerButton(String text) {
        return styledButton(text, DANGER, DANGER_DARK);
    }

    /** Neutral secondary button */
    public static JButton secondaryButton(String text) {
        return styledButton(text, NEUTRAL, NEUTRAL_DARK);
    }

    private static JButton styledButton(String text, Color bg, Color hoverBg) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;

            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                        hovered = true; repaint();
                    }
                    @Override public void mouseExited(java.awt.event.MouseEvent e) {
                        hovered = false; repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(
                        java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered && isEnabled() ? hoverBg : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setForeground(Color.WHITE);
        btn.setFont(FONT_LABEL);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 20, 32));
        return btn;
    }

    // Component helpers

    public static JLabel titleLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    public static JLabel subtitleLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_SUBTITLE);
        lbl.setForeground(TEXT_MUTED);
        return lbl;
    }

    public static JLabel bodyLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    public static void styleTextField(JComponent field) {
        field.setFont(FONT_LABEL);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        field.setBackground(SURFACE);
        field.setPreferredSize(new Dimension(260, 32));
    }

    public static void styleTable(JTable table) {
        table.setFont(FONT_LABEL);
        table.setRowHeight(28);
        table.setShowGrid(true);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(new Color(219, 234, 254)); // blue-100
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setBackground(SURFACE);
        table.setForeground(TEXT_PRIMARY);
        table.setFillsViewportHeight(true);

        // Style header
        table.getTableHeader().setFont(FONT_BOLD);
        table.getTableHeader().setBackground(TABLE_HEADER);
        table.getTableHeader().setForeground(TEXT_PRIMARY);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 32));
    }

    public static JScrollPane styledScrollPane(JTable table, String title) {
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        title,
                        javax.swing.border.TitledBorder.LEFT,
                        javax.swing.border.TitledBorder.TOP,
                        FONT_BOLD,
                        TEXT_PRIMARY
                ),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));
        sp.getViewport().setBackground(SURFACE);
        return sp;
    }

    public static JPanel surfacePanel() {
        JPanel p = new JPanel();
        p.setBackground(SURFACE);
        return p;
    }

    private AppTheme() {}
}
