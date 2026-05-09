package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 * Main dashboard shown after a successful login.
 *
 * Layout (top to bottom):
 * [Header with welcome + logout]
 * [Metric cards row]
 * [Action bar rows]
 * [Workout logs table]
 */
public class DashboardPanel extends JPanel {

    public DashboardPanel(
            JLabel welcomeLabel,
            JLabel workoutCountValueLabel,
            JLabel weeklyWorkoutValueLabel,
            JLabel plansCountValueLabel,
            JLabel weightTrendValueLabel,
            JTable workoutTable,
            Runnable onAddWorkout,
            Runnable onEditWorkout,
            Runnable onDeleteWorkout,
            Runnable onManagePlans,
            Runnable onTrackProgress,
            Runnable onViewAll,
            Runnable onViewRecent,
            Runnable onDateRange,
            Runnable onRefreshCount,
            Runnable onLogout
    ) {
        setLayout(new BorderLayout(0, 12));
        setBackground(AppTheme.BG);
        setBorder(AppTheme.panelPadding());

        add(buildTopSection(
                welcomeLabel,
                workoutCountValueLabel, weeklyWorkoutValueLabel,
                plansCountValueLabel, weightTrendValueLabel,
                onAddWorkout, onEditWorkout, onDeleteWorkout,
                onManagePlans, onTrackProgress,
                onViewAll, onViewRecent, onDateRange, onRefreshCount,
                onLogout
        ), BorderLayout.NORTH);

        AppTheme.styleTable(workoutTable);
        JScrollPane sp = AppTheme.styledScrollPane(workoutTable, "Workout Logs");
        add(sp, BorderLayout.CENTER);
    }

    // Top section (header + metrics + action bar)

    private JPanel buildTopSection(
            JLabel welcomeLabel,
            JLabel totalLogsLabel,
            JLabel weeklyLabel,
            JLabel plansLabel,
            JLabel weightTrendLabel,
            Runnable onAdd, Runnable onEdit, Runnable onDelete,
            Runnable onPlans, Runnable onProgress,
            Runnable onViewAll, Runnable onRecent, Runnable onDateRange,
            Runnable onRefresh, Runnable onLogout
    ) {
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(AppTheme.BG);

        // Welcome row + logout button
        top.add(buildHeaderRow(welcomeLabel, onLogout));
        top.add(Box.createVerticalStrut(12));

        // Metric cards
        top.add(buildMetricsRow(totalLogsLabel, weeklyLabel, plansLabel, weightTrendLabel));
        top.add(Box.createVerticalStrut(12));

        // Action bar
        top.add(buildActionBar(onAdd, onEdit, onDelete, onPlans, onProgress, onViewAll, onRecent, onDateRange, onRefresh));
        top.add(Box.createVerticalStrut(4));

        return top;
    }

    private JPanel buildHeaderRow(JLabel welcomeLabel, Runnable onLogout) {
        welcomeLabel.setFont(AppTheme.FONT_TITLE);
        welcomeLabel.setForeground(AppTheme.TEXT_PRIMARY);

        JButton logoutBtn = AppTheme.dangerButton("Logout");
        logoutBtn.addActionListener(e -> onLogout.run());

        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(AppTheme.BG);
        row.add(welcomeLabel, BorderLayout.WEST);
        row.add(logoutBtn, BorderLayout.EAST);
        return row;
    }

    private JPanel buildMetricsRow(JLabel totalLogs, JLabel weekly, JLabel plans, JLabel weightTrend) {
        JPanel row = new JPanel(new GridLayout(1, 4, 12, 0));
        row.setBackground(AppTheme.BG);
        row.add(metricCard("Total Logs", totalLogs, AppTheme.PRIMARY));
        row.add(metricCard("This Week", weekly, new Color(16, 185, 129)));   // emerald
        row.add(metricCard("Plans", plans, new Color(139, 92, 246)));         // violet
        row.add(metricCard("Weight Trend", weightTrend, new Color(245, 158, 11))); // amber
        return row;
    }

    private JPanel metricCard(String title, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(0, 4));
        card.setBackground(AppTheme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        // Left color strip
        JPanel strip = new JPanel();
        strip.setBackground(accentColor);
        strip.setPreferredSize(new Dimension(4, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppTheme.FONT_SMALL);
        titleLabel.setForeground(AppTheme.TEXT_MUTED);

        valueLabel.setFont(AppTheme.FONT_METRIC);
        valueLabel.setForeground(AppTheme.TEXT_PRIMARY);
        valueLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel content = new JPanel(new BorderLayout(0, 4));
        content.setBackground(AppTheme.SURFACE);
        content.add(titleLabel, BorderLayout.NORTH);
        content.add(valueLabel, BorderLayout.CENTER);

        card.add(strip, BorderLayout.WEST);
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildActionBar(
            Runnable onAdd, Runnable onEdit, Runnable onDelete,
            Runnable onPlans, Runnable onProgress,
            Runnable onViewAll, Runnable onRecent, Runnable onDateRange, Runnable onRefresh
    ) {
        JPanel bar = new JPanel();
        bar.setLayout(new BoxLayout(bar, BoxLayout.Y_AXIS));
        bar.setBackground(AppTheme.BG);

        // Row 1: CRUD actions
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        row1.setBackground(AppTheme.BG);

        JButton addBtn = AppTheme.primaryButton("Add Workout");
        addBtn.addActionListener(e -> onAdd.run());

        JButton editBtn = AppTheme.secondaryButton("Edit Selected");
        editBtn.addActionListener(e -> onEdit.run());

        JButton deleteBtn = AppTheme.dangerButton("Delete Selected");
        deleteBtn.addActionListener(e -> onDelete.run());

        // Separator label
        JLabel sep1 = new JLabel("  |  ");
        sep1.setForeground(AppTheme.BORDER_COLOR);

        JButton plansBtn = AppTheme.primaryButton("Plans");
        plansBtn.addActionListener(e -> onPlans.run());

        JButton progressBtn = AppTheme.primaryButton("Progress");
        progressBtn.addActionListener(e -> onProgress.run());

        row1.add(addBtn);
        row1.add(editBtn);
        row1.add(deleteBtn);
        row1.add(sep1);
        row1.add(plansBtn);
        row1.add(progressBtn);

        // Row 2: filter actions
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        row2.setBackground(AppTheme.BG);

        JLabel filterLabel = new JLabel("Filter:");
        filterLabel.setFont(AppTheme.FONT_SMALL);
        filterLabel.setForeground(AppTheme.TEXT_MUTED);

        JButton viewAllBtn = AppTheme.secondaryButton("All Logs");
        viewAllBtn.addActionListener(e -> onViewAll.run());

        JButton recentBtn = AppTheme.secondaryButton("Recent");
        recentBtn.addActionListener(e -> onRecent.run());

        JButton dateRangeBtn = AppTheme.secondaryButton("Date Range");
        dateRangeBtn.addActionListener(e -> onDateRange.run());

        JButton refreshBtn = AppTheme.secondaryButton("Refresh");
        refreshBtn.addActionListener(e -> onRefresh.run());

        row2.add(filterLabel);
        row2.add(viewAllBtn);
        row2.add(recentBtn);
        row2.add(dateRangeBtn);
        row2.add(refreshBtn);

        bar.add(row1);
        bar.add(row2);
        return bar;
    }
}
