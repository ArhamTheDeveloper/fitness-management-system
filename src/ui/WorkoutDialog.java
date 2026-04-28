package ui;

import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import models.Exercise;
import models.Workout;

public class WorkoutDialog {
    public static WorkoutFormResult showAddWorkoutDialog(Component parent, List<Exercise> exercises) {
        JComboBox<Exercise> exerciseBox = new JComboBox<>(exercises.toArray(new Exercise[0]));
        JSpinner setsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JSpinner repsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 2.5d));

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

    public static DateRangeResult showDateRangeDialog(Component parent) {
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

    public static WorkoutFormResult showEditWorkoutDialog(Component parent, List<Exercise> exercises, Workout workout) {
        if (workout == null) {
            return null;
        }

        JComboBox<Exercise> exerciseBox = new JComboBox<>(exercises.toArray(new Exercise[0]));
        JSpinner setsSpinner = new JSpinner(new SpinnerNumberModel(workout.getSets(), 1, 1000, 1));
        JSpinner repsSpinner = new JSpinner(new SpinnerNumberModel(workout.getReps(), 1, 1000, 1));
        JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(workout.getWeightLifted(), 0.0d, 1000.0d, 2.5d));

        for (Exercise exercise : exercises) {
            if (exercise.getExerciseId() == workout.getExerciseId()) {
                exerciseBox.setSelectedItem(exercise);
                break;
            }
        }

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = baseConstraints();
        addLabeledField(panel, gbc, 0, "Exercise", exerciseBox);
        addLabeledField(panel, gbc, 1, "Sets", setsSpinner);
        addLabeledField(panel, gbc, 2, "Reps", repsSpinner);
        addLabeledField(panel, gbc, 3, "Weight Lifted (kg)", weightSpinner);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Edit Workout Log",
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

    public static PlanFormResult showCreatePlanDialog(Component parent) {
        JTextField nameField = new JTextField(18);
        JTextField descriptionField = new JTextField(18);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = baseConstraints();
        addLabeledField(panel, gbc, 0, "Plan Name", nameField);
        addLabeledField(panel, gbc, 1, "Description", descriptionField);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Create Workout Plan",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        return new PlanFormResult(nameField.getText().trim(), descriptionField.getText().trim());
    }

    public static PlanItemFormResult showAddPlanItemDialog(Component parent, List<Exercise> exercises, int defaultSortOrder) {
        JComboBox<Exercise> exerciseBox = new JComboBox<>(exercises.toArray(new Exercise[0]));
        JSpinner setsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 1000, 1));
        JSpinner repsSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 1000, 1));
        JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(0.0d, 0.0d, 1000.0d, 2.5d));
        JSpinner sortOrderSpinner = new JSpinner(new SpinnerNumberModel(defaultSortOrder, 1, 1000, 1));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = baseConstraints();
        addLabeledField(panel, gbc, 0, "Exercise", exerciseBox);
        addLabeledField(panel, gbc, 1, "Target Sets", setsSpinner);
        addLabeledField(panel, gbc, 2, "Target Reps", repsSpinner);
        addLabeledField(panel, gbc, 3, "Target Weight (kg)", weightSpinner);
        addLabeledField(panel, gbc, 4, "Sort Order", sortOrderSpinner);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Add Plan Item",
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

        return new PlanItemFormResult(
                selectedExercise,
                ((Number) setsSpinner.getValue()).intValue(),
                ((Number) repsSpinner.getValue()).intValue(),
                ((Number) weightSpinner.getValue()).doubleValue(),
                ((Number) sortOrderSpinner.getValue()).intValue()
        );
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
        field.setPreferredSize(new Dimension(280, 30));
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

    public static final class PlanFormResult {
        private final String planName;
        private final String description;

        public PlanFormResult(String planName, String description) {
            this.planName = planName;
            this.description = description;
        }

        public String getPlanName() {
            return planName;
        }

        public String getDescription() {
            return description;
        }
    }

    public static final class PlanItemFormResult {
        private final Exercise exercise;
        private final int targetSets;
        private final int targetReps;
        private final double targetWeight;
        private final int sortOrder;

        public PlanItemFormResult(Exercise exercise, int targetSets, int targetReps, double targetWeight, int sortOrder) {
            this.exercise = exercise;
            this.targetSets = targetSets;
            this.targetReps = targetReps;
            this.targetWeight = targetWeight;
            this.sortOrder = sortOrder;
        }

        public Exercise getExercise() {
            return exercise;
        }

        public int getTargetSets() {
            return targetSets;
        }

        public int getTargetReps() {
            return targetReps;
        }

        public double getTargetWeight() {
            return targetWeight;
        }

        public int getSortOrder() {
            return sortOrder;
        }
    }

    public static ProgressFormResult showLogProgressDialog(Component parent) {
        JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(0.0d, 0.0d, 500.0d, 0.1d));
        JSpinner bodyFatSpinner = new JSpinner(new SpinnerNumberModel(0.0d, 0.0d, 100.0d, 0.5d));
        JSpinner chestSpinner = new JSpinner(new SpinnerNumberModel(0.0d, 0.0d, 200.0d, 0.5d));
        JSpinner waistSpinner = new JSpinner(new SpinnerNumberModel(0.0d, 0.0d, 200.0d, 0.5d));
        JSpinner hipsSpinner = new JSpinner(new SpinnerNumberModel(0.0d, 0.0d, 200.0d, 0.5d));
        JTextField notesField = new JTextField(18);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = baseConstraints();
        addLabeledField(panel, gbc, 0, "Weight (kg)", weightSpinner);
        addLabeledField(panel, gbc, 1, "Body Fat %", bodyFatSpinner);
        addLabeledField(panel, gbc, 2, "Chest (cm)", chestSpinner);
        addLabeledField(panel, gbc, 3, "Waist (cm)", waistSpinner);
        addLabeledField(panel, gbc, 4, "Hips (cm)", hipsSpinner);
        addLabeledField(panel, gbc, 5, "Notes", notesField);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Log Progress",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        Double weight = ((Number) weightSpinner.getValue()).doubleValue();
        Double bodyFat = ((Number) bodyFatSpinner.getValue()).doubleValue();
        Double chest = ((Number) chestSpinner.getValue()).doubleValue();
        Double waist = ((Number) waistSpinner.getValue()).doubleValue();
        Double hips = ((Number) hipsSpinner.getValue()).doubleValue();
        String notes = notesField.getText().trim();

        return new ProgressFormResult(
                weight > 0 ? weight : null,
                bodyFat > 0 ? bodyFat : null,
                chest > 0 ? chest : null,
                waist > 0 ? waist : null,
                hips > 0 ? hips : null,
                notes.isEmpty() ? null : notes
        );
    }

    public static final class ProgressFormResult {
        private final Double weight;
        private final Double bodyFatPercentage;
        private final Double chestCm;
        private final Double waistCm;
        private final Double hipsCm;
        private final String notes;

        public ProgressFormResult(Double weight, Double bodyFatPercentage, Double chestCm, Double waistCm, Double hipsCm, String notes) {
            this.weight = weight;
            this.bodyFatPercentage = bodyFatPercentage;
            this.chestCm = chestCm;
            this.waistCm = waistCm;
            this.hipsCm = hipsCm;
            this.notes = notes;
        }

        public Double getWeight() {
            return weight;
        }

        public Double getBodyFatPercentage() {
            return bodyFatPercentage;
        }

        public Double getChestCm() {
            return chestCm;
        }

        public Double getWaistCm() {
            return waistCm;
        }

        public Double getHipsCm() {
            return hipsCm;
        }

        public String getNotes() {
            return notes;
        }
    }
}
