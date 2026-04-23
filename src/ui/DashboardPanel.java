package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DashboardPanel extends JPanel {
    private final JLabel welcomeLabel;
    private final JLabel workoutCountValueLabel;

    public DashboardPanel(
            JLabel welcomeLabel,
            JLabel workoutCountValueLabel,
            JTable workoutTable,
            Runnable onAddWorkout,
            Runnable onViewAll,
            Runnable onViewRecent,
            Runnable onDateRange,
            Runnable onRefreshCount,
            Runnable onLogout
    ) {
        this.welcomeLabel = welcomeLabel;
        this.workoutCountValueLabel = workoutCountValueLabel;

        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout(12, 12));
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(24f));

        JPanel summary = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        summary.add(new JLabel("Workout Logs:"));
        workoutCountValueLabel.setFont(workoutCountValueLabel.getFont().deriveFont(18f));
        summary.add(workoutCountValueLabel);

        header.add(welcomeLabel, BorderLayout.NORTH);
        header.add(summary, BorderLayout.SOUTH);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton addWorkoutButton = new JButton("Add Workout");
        addWorkoutButton.addActionListener(e -> onAddWorkout.run());

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
}
