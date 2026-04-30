package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;

public class DashboardPanel extends JPanel {
    private final JLabel welcomeLabel;
    private final JLabel workoutCountValueLabel;
    private final JLabel weeklyWorkoutValueLabel;
    private final JLabel plansCountValueLabel;
    private final JLabel weightTrendValueLabel;

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
        this.welcomeLabel = welcomeLabel;
        this.workoutCountValueLabel = workoutCountValueLabel;
        this.weeklyWorkoutValueLabel = weeklyWorkoutValueLabel;
        this.plansCountValueLabel = plansCountValueLabel;
        this.weightTrendValueLabel = weightTrendValueLabel;

        this.welcomeLabel.setForeground(Color.BLACK);
        this.workoutCountValueLabel.setForeground(Color.BLACK);
        this.weeklyWorkoutValueLabel.setForeground(Color.BLACK);
        this.plansCountValueLabel.setForeground(Color.BLACK);
        this.weightTrendValueLabel.setForeground(Color.BLACK);

        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout(12, 12));
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(24f));
        welcomeLabel.setForeground(Color.BLACK);

        JPanel summary = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        summary.add(metricCard("Total Logs", workoutCountValueLabel));
        summary.add(metricCard("This Week", weeklyWorkoutValueLabel));
        summary.add(metricCard("Plans", plansCountValueLabel));
        summary.add(metricCard("Weight Trend", weightTrendValueLabel));

        header.add(welcomeLabel, BorderLayout.NORTH);
        header.add(summary, BorderLayout.SOUTH);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton addWorkoutButton = new JButton("Add Workout");
        addWorkoutButton.addActionListener(e -> onAddWorkout.run());

        JButton editWorkoutButton = new JButton("Edit Selected");
        editWorkoutButton.addActionListener(e -> onEditWorkout.run());

        JButton deleteWorkoutButton = new JButton("Delete Selected");
        deleteWorkoutButton.addActionListener(e -> onDeleteWorkout.run());

        JButton plansButton = new JButton("Plans");
        plansButton.addActionListener(e -> onManagePlans.run());

        JButton progressButton = new JButton("Progress");
        progressButton.addActionListener(e -> onTrackProgress.run());

        JButton showAllButton = new JButton("View All");
        showAllButton.addActionListener(e -> onViewAll.run());

        JButton recentButton = new JButton("Recent");
        recentButton.addActionListener(e -> onViewRecent.run());

        JButton rangeButton = new JButton("Date Range");
        rangeButton.addActionListener(e -> onDateRange.run());

        JButton countButton = new JButton("Refresh Count");
        countButton.addActionListener(e -> onRefreshCount.run());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> onLogout.run());

        actions.add(addWorkoutButton);
        actions.add(editWorkoutButton);
        actions.add(deleteWorkoutButton);
        actions.add(plansButton);
        actions.add(progressButton);
        actions.add(showAllButton);
        actions.add(recentButton);
        actions.add(rangeButton);
        actions.add(countButton);
        actions.add(logoutButton);

        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        topSection.add(header);
        topSection.add(Box.createVerticalStrut(10));
        topSection.add(actions);

        workoutTable.setRowHeight(26);
        // Ensure table header text is visible on dark theme
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) workoutTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setForeground(Color.BLACK);
        headerRenderer.setBackground(new Color(235, 235, 235));
        headerRenderer.setFont(headerRenderer.getFont().deriveFont(Font.BOLD));
        workoutTable.getTableHeader().setDefaultRenderer(headerRenderer);
        workoutTable.getTableHeader().setOpaque(true);
        workoutTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(workoutTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Workout Logs"));

        add(topSection, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setWelcomeText(String text) {
        welcomeLabel.setText(text);
    }

    public void setWorkoutCount(int count) {
        workoutCountValueLabel.setText(String.valueOf(count));
    }

    private JPanel metricCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel titleLabel = new JLabel(title, SwingConstants.LEFT);
        titleLabel.setForeground(Color.BLACK);
        valueLabel.setFont(valueLabel.getFont().deriveFont(18f));
        valueLabel.setForeground(Color.BLACK);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.SOUTH);
        return card;
    }
}
