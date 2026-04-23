package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import models.Exercise;

public class WorkoutDialog {
    public static WorkoutFormResult showAddWorkoutDialog(java.awt.Component parent, List<Exercise> exercises) {
        JComboBox<Exercise> exerciseBox = new JComboBox<>(exercises.toArray(new Exercise[0]));
        JSpinner setsSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        JSpinner repsSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        JSpinner weightSpinner = new JSpinner(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 2.5d));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = baseConstraints();
        addLabeledField(panel, gbc, 0, "Exercise", exerciseBox);
        addLabeledField(panel, gbc, 1, "Sets", setsSpinner);
        addLabeledField(panel, gbc, 2, "Reps", repsSpinner);
        addLabeledField(panel, gbc, 3, "Weight Lifted (kg)", weightSpinner);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Add Workout Log",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        Exercise selectedExercise = (Exercise) exerciseBox.getSelectedItem();
        if (selectedExercise == null) {
            return null;
        }

        return new WorkoutFormResult(
                selectedExercise,
                ((Number) setsSpinner.getValue()).intValue(),
                ((Number) repsSpinner.getValue()).intValue(),
                ((Number) weightSpinner.getValue()).doubleValue()
        );
    }

    public static DateRangeResult showDateRangeDialog(java.awt.Component parent) {
        JTextField startField = new JTextField(16);
        JTextField endField = new JTextField(16);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = baseConstraints();
        addLabeledField(panel, gbc, 0, "Start Date (yyyy-mm-dd)", startField);
        addLabeledField(panel, gbc, 1, "End Date (yyyy-mm-dd)", endField);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Filter Workout Logs",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        return new DateRangeResult(startField.getText().trim(), endField.getText().trim());
    }

    private static GridBagConstraints baseConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 12);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    private static void addLabeledField(JPanel form, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        form.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        field.setPreferredSize(new java.awt.Dimension(280, 30));
        form.add(field, gbc);
    }

    public static final class WorkoutFormResult {
        private final Exercise exercise;
        private final int sets;
        private final int reps;
        private final double weightLifted;

        public WorkoutFormResult(Exercise exercise, int sets, int reps, double weightLifted) {
            this.exercise = exercise;
            this.sets = sets;
            this.reps = reps;
            this.weightLifted = weightLifted;
        }

        public Exercise getExercise() {
            return exercise;
        }

        public int getSets() {
            return sets;
        }

        public int getReps() {
            return reps;
        }

        public double getWeightLifted() {
            return weightLifted;
        }
    }

    public static final class DateRangeResult {
        private final String startDate;
        private final String endDate;

        public DateRangeResult(String startDate, String endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }
    }
}
