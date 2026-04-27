package ui;

import database.ExerciseDAO;
import database.WorkoutPlanDAO;
import database.WorkoutPlanItemDAO;
import database.UserDAO;
import database.WorkoutDAO;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import models.Exercise;
import models.User;
import models.Workout;
import models.WorkoutPlan;
import models.WorkoutPlanItem;
import services.AuthService;
import services.ServiceResult;
import services.WorkoutPlanService;
import services.WorkoutService;

public class FitnessSwingApp {
    private static final String LOGIN_CARD = "login";
    private static final String REGISTER_CARD = "register";
    private static final String DASHBOARD_CARD = "dashboard";

    private final JFrame frame;
    private final JPanel rootPanel = new JPanel(new CardLayout());
    private final JPanel authPanel = new JPanel(new CardLayout());

    private final AuthService authService = new AuthService(new UserDAO());
    private final WorkoutService workoutService = new WorkoutService(new ExerciseDAO(), new WorkoutDAO());
    private final WorkoutPlanService workoutPlanService = new WorkoutPlanService(new WorkoutPlanDAO(), new WorkoutPlanItemDAO());

    private final JTextField loginUsernameField = new JTextField(18);
    private final JPasswordField loginPasswordField = new JPasswordField(18);

    private final JTextField registerUsernameField = new JTextField(18);
    private final JPasswordField registerPasswordField = new JPasswordField(18);
    private final JTextField registerWeightField = new JTextField(18);
    private final JTextField registerGoalField = new JTextField(18);

    private final JLabel welcomeLabel = new JLabel("Welcome");
    private final JLabel workoutCountValueLabel = new JLabel("0");
    private final DefaultTableModel workoutTableModel = new DefaultTableModel(
            new Object[]{"Log ID", "Exercise", "Sets", "Reps", "Weight (kg)", "Date"},
            0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable workoutTable = new JTable(workoutTableModel);

    private final Map<Integer, String> exerciseNameCache = new HashMap<>();
    private User currentUser;

    public FitnessSwingApp() {
        initializeLookAndFeel();

        frame = new JFrame("Fitness Management System");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1100, 720));
        frame.setLocationRelativeTo(null);

        buildAuthPanel();
        rootPanel.add(authPanel, LOGIN_CARD);
        rootPanel.add(buildDashboardPanel(), DASHBOARD_CARD);
        frame.setContentPane(rootPanel);

        showLoginCard();
    }

    public void show() {
        frame.setVisible(true);
    }

    private void initializeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    private void buildAuthPanel() {
        authPanel.add(new LoginPanel(loginUsernameField, loginPasswordField, this::handleLogin, this::showRegisterCard), LOGIN_CARD);
        authPanel.add(new RegisterPanel(registerUsernameField, registerPasswordField, registerWeightField, registerGoalField, this::handleRegister, this::showLoginCard), REGISTER_CARD);
    }

    private JPanel buildDashboardPanel() {
        workoutTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return new DashboardPanel(
                welcomeLabel,
                workoutCountValueLabel,
                workoutTable,
                this::handleAddWorkout,
                this::handleEditWorkout,
                this::handleDeleteWorkout,
                this::handleManagePlans,
                this::handleViewAll,
                this::handleViewRecent,
                this::handleDateRange,
                this::updateWorkoutCount,
                this::handleLogout
        );
    }

    private void handleLogin() {
        String username = loginUsernameField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        ServiceResult<User> result = authService.loginUser(username, password);
        if (!result.isSuccess()) {
            showError(result.getMessage());
            return;
        }

        currentUser = result.getData();
        loginPasswordField.setText("");
        showDashboardCard();
        refreshDashboard();
    }

    private void handleRegister() {
        String username = registerUsernameField.getText().trim();
        String password = new String(registerPasswordField.getPassword());
        String weightText = registerWeightField.getText().trim();
        String goal = registerGoalField.getText().trim();

        double weight;
        try {
            weight = Double.parseDouble(weightText);
        } catch (NumberFormatException e) {
            showError("Enter a valid current weight.");
            return;
        }

        ServiceResult<Void> result = authService.registerUser(username, password, weight, goal);
        if (!result.isSuccess()) {
            showError(result.getMessage());
            return;
        }

        registerUsernameField.setText("");
        registerPasswordField.setText("");
        registerWeightField.setText("");
        registerGoalField.setText("");
        showInfo(result.getMessage());
        showLoginCard();
    }

    private void handleLogout() {
        currentUser = null;
        loginUsernameField.setText("");
        loginPasswordField.setText("");
        showLoginCard();
        clearTable();
    }

    private void handleAddWorkout() {
        if (currentUser == null) {
            return;
        }

        List<Exercise> exercises = workoutService.getAllExercises();
        if (exercises.isEmpty()) {
            showError("No exercises are available.");
            return;
        }

        WorkoutDialog.WorkoutFormResult formResult = WorkoutDialog.showAddWorkoutDialog(frame, exercises);
        if (formResult == null) {
            return;
        }

        ServiceResult<Void> saveResult = workoutService.addWorkoutLog(
                currentUser.getUserId(),
                formResult.getExercise().getExerciseId(),
                formResult.getSets(),
                formResult.getReps(),
                formResult.getWeightLifted()
        );

        if (!saveResult.isSuccess()) {
            showError(saveResult.getMessage());
            return;
        }

        showInfo(saveResult.getMessage());
        refreshDashboard();
    }

    private void handleEditWorkout() {
        if (currentUser == null) {
            return;
        }

        int selectedLogId = getSelectedLogId();
        if (selectedLogId <= 0) {
            showError("Select a workout row to edit.");
            return;
        }

        ServiceResult<Workout> workoutResult = workoutService.getWorkoutLogByIdForUser(currentUser.getUserId(), selectedLogId);
        if (!workoutResult.isSuccess()) {
            showError(workoutResult.getMessage());
            return;
        }

        List<Exercise> exercises = workoutService.getAllExercises();
        if (exercises.isEmpty()) {
            showError("No exercises are available.");
            return;
        }

        WorkoutDialog.WorkoutFormResult formResult = WorkoutDialog.showEditWorkoutDialog(frame, exercises, workoutResult.getData());
        if (formResult == null) {
            return;
        }

        ServiceResult<Void> updateResult = workoutService.updateWorkoutLog(
                currentUser.getUserId(),
                selectedLogId,
                formResult.getExercise().getExerciseId(),
                formResult.getSets(),
                formResult.getReps(),
                formResult.getWeightLifted()
        );

        if (!updateResult.isSuccess()) {
            showError(updateResult.getMessage());
            return;
        }

        showInfo(updateResult.getMessage());
        refreshDashboard();
    }

    private void handleDeleteWorkout() {
        if (currentUser == null) {
            return;
        }

        int selectedLogId = getSelectedLogId();
        if (selectedLogId <= 0) {
            showError("Select a workout row to delete.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                frame,
                "Delete selected workout log?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        ServiceResult<Void> deleteResult = workoutService.deleteWorkoutLog(currentUser.getUserId(), selectedLogId);
        if (!deleteResult.isSuccess()) {
            showError(deleteResult.getMessage());
            return;
        }

        showInfo(deleteResult.getMessage());
        refreshDashboard();
    }

    private void handleManagePlans() {
        if (currentUser == null) {
            return;
        }

        Object[] options = {"View Plans", "Create Plan", "Add Item to Plan", "Delete Plan", "Close"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(
                    frame,
                    "Manage workout plans",
                    "Workout Plans",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                handleViewPlans();
            } else if (choice == 1) {
                handleCreatePlan();
            } else if (choice == 2) {
                handleAddPlanItem();
            } else if (choice == 3) {
                handleDeletePlan();
            } else {
                return;
            }
        }
    }

    private void handleCreatePlan() {
        WorkoutDialog.PlanFormResult formResult = WorkoutDialog.showCreatePlanDialog(frame);
        if (formResult == null) {
            return;
        }

        ServiceResult<WorkoutPlan> result = workoutPlanService.createPlan(
                currentUser.getUserId(),
                formResult.getPlanName(),
                formResult.getDescription()
        );

        if (!result.isSuccess()) {
            showError(result.getMessage());
            return;
        }

        showInfo(result.getMessage());
    }

    private void handleViewPlans() {
        List<WorkoutPlan> plans = workoutPlanService.getPlansByUserId(currentUser.getUserId());
        if (plans.isEmpty()) {
            showInfo("No workout plans available.");
            return;
        }

        exerciseNameCache.clear();
        loadExerciseNames();

        StringBuilder builder = new StringBuilder();
        for (WorkoutPlan plan : plans) {
            builder.append("Plan #")
                    .append(plan.getPlanId())
                    .append(" - ")
                    .append(plan.getPlanName())
                    .append("\n");

            if (plan.getPlanDescription() != null && !plan.getPlanDescription().isBlank()) {
                builder.append("Description: ").append(plan.getPlanDescription()).append("\n");
            }

            ServiceResult<List<WorkoutPlanItem>> itemsResult = workoutPlanService.getPlanItemsByPlanIdForUser(
                    currentUser.getUserId(),
                    plan.getPlanId()
            );

            if (itemsResult.isSuccess() && itemsResult.getData() != null && !itemsResult.getData().isEmpty()) {
                for (WorkoutPlanItem item : itemsResult.getData()) {
                    builder.append("  ")
                            .append(item.getSortOrder())
                            .append(". ")
                            .append(resolveExerciseName(item.getExerciseId()))
                            .append(" - ")
                            .append(item.getTargetSets())
                            .append("x")
                            .append(item.getTargetReps())
                            .append(" @ ")
                            .append(String.format("%.2f", item.getTargetWeight()))
                            .append(" kg\n");
                }
            } else {
                builder.append("  (No items yet)\n");
            }

            builder.append("\n");
        }

        JOptionPane.showMessageDialog(frame, builder.toString(), "Workout Plans", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleAddPlanItem() {
        List<WorkoutPlan> plans = workoutPlanService.getPlansByUserId(currentUser.getUserId());
        if (plans.isEmpty()) {
            showError("Create a workout plan first.");
            return;
        }

        WorkoutPlan selectedPlan = selectPlan(plans, "Select a plan");
        if (selectedPlan == null) {
            return;
        }

        List<Exercise> exercises = workoutService.getAllExercises();
        if (exercises.isEmpty()) {
            showError("No exercises are available.");
            return;
        }

        ServiceResult<List<WorkoutPlanItem>> itemsResult = workoutPlanService.getPlanItemsByPlanIdForUser(
                currentUser.getUserId(),
                selectedPlan.getPlanId()
        );
        int nextSortOrder = 1;
        if (itemsResult.isSuccess() && itemsResult.getData() != null) {
            nextSortOrder = itemsResult.getData().size() + 1;
        }

        WorkoutDialog.PlanItemFormResult itemFormResult = WorkoutDialog.showAddPlanItemDialog(frame, exercises, nextSortOrder);
        if (itemFormResult == null) {
            return;
        }

        ServiceResult<Void> addItemResult = workoutPlanService.addPlanItem(
                currentUser.getUserId(),
                selectedPlan.getPlanId(),
                itemFormResult.getExercise().getExerciseId(),
                itemFormResult.getTargetSets(),
                itemFormResult.getTargetReps(),
                itemFormResult.getTargetWeight(),
                itemFormResult.getSortOrder()
        );

        if (!addItemResult.isSuccess()) {
            showError(addItemResult.getMessage());
            return;
        }

        showInfo(addItemResult.getMessage());
    }

    private void handleDeletePlan() {
        List<WorkoutPlan> plans = workoutPlanService.getPlansByUserId(currentUser.getUserId());
        if (plans.isEmpty()) {
            showError("No workout plans available to delete.");
            return;
        }

        WorkoutPlan selectedPlan = selectPlan(plans, "Select a plan to delete");
        if (selectedPlan == null) {
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                frame,
                "Delete plan '" + selectedPlan.getPlanName() + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        ServiceResult<Void> result = workoutPlanService.deletePlan(currentUser.getUserId(), selectedPlan.getPlanId());
        if (!result.isSuccess()) {
            showError(result.getMessage());
            return;
        }

        showInfo(result.getMessage());
    }

    private WorkoutPlan selectPlan(List<WorkoutPlan> plans, String title) {
        JComboBox<WorkoutPlan> planComboBox = new JComboBox<>(plans.toArray(new WorkoutPlan[0]));

        int result = JOptionPane.showConfirmDialog(
                frame,
                planComboBox,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        return (WorkoutPlan) planComboBox.getSelectedItem();
    }

    private void handleViewAll() {
        if (currentUser == null) {
            return;
        }

        populateWorkoutTable(workoutService.getAllWorkoutLogsByUserId(currentUser.getUserId()));
    }

    private void handleViewRecent() {
        if (currentUser == null) {
            return;
        }

        populateWorkoutTable(workoutService.getRecentWorkoutLogsByUserId(currentUser.getUserId()));
    }

    private void handleDateRange() {
        if (currentUser == null) {
            return;
        }

        WorkoutDialog.DateRangeResult dateRangeResult = WorkoutDialog.showDateRangeDialog(frame);
        if (dateRangeResult == null) {
            return;
        }

        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(dateRangeResult.getStartDate());
            endDate = LocalDate.parse(dateRangeResult.getEndDate());
        } catch (DateTimeParseException e) {
            showError("Use the date format yyyy-mm-dd.");
            return;
        }

        ServiceResult<List<Workout>> result = workoutService.getWorkoutLogsByDateRange(currentUser.getUserId(), startDate, endDate);
        if (!result.isSuccess()) {
            showError(result.getMessage());
            return;
        }

        populateWorkoutTable(result.getData());
    }

    private void refreshDashboard() {
        if (currentUser == null) {
            return;
        }

        welcomeLabel.setText("Welcome, " + currentUser.getUsername());
        updateWorkoutCount();
        handleViewAll();
    }

    private void updateWorkoutCount() {
        if (currentUser == null) {
            workoutCountValueLabel.setText("0");
            return;
        }

        workoutCountValueLabel.setText(String.valueOf(workoutService.getTotalWorkoutCountByUserId(currentUser.getUserId())));
    }

    private void populateWorkoutTable(List<Workout> workouts) {
        workoutTableModel.setRowCount(0);
        exerciseNameCache.clear();
        loadExerciseNames();

        if (workouts == null || workouts.isEmpty()) {
            return;
        }

        for (Workout workout : workouts) {
            workoutTableModel.addRow(new Object[]{
                    workout.getLogId(),
                    resolveExerciseName(workout.getExerciseId()),
                    workout.getSets(),
                    workout.getReps(),
                    String.format("%.2f", workout.getWeightLifted()),
                    workout.getLogDate() == null ? "" : workout.getLogDate().toString()
            });
        }
    }

    private void loadExerciseNames() {
        List<Exercise> exercises = workoutService.getAllExercises();
        for (Exercise exercise : exercises) {
            exerciseNameCache.put(exercise.getExerciseId(), exercise.getExerciseName());
        }
    }

    private String resolveExerciseName(int exerciseId) {
        return exerciseNameCache.getOrDefault(exerciseId, "Exercise #" + exerciseId);
    }

    private int getSelectedLogId() {
        int selectedRow = workoutTable.getSelectedRow();
        if (selectedRow < 0) {
            return -1;
        }

        Object value = workoutTableModel.getValueAt(selectedRow, 0);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void clearTable() {
        workoutTableModel.setRowCount(0);
    }

    private void showLoginCard() {
        ((CardLayout) authPanel.getLayout()).show(authPanel, LOGIN_CARD);
        ((CardLayout) rootPanel.getLayout()).show(rootPanel, LOGIN_CARD);
    }

    private void showRegisterCard() {
        ((CardLayout) authPanel.getLayout()).show(authPanel, REGISTER_CARD);
        ((CardLayout) rootPanel.getLayout()).show(rootPanel, LOGIN_CARD);
    }

    private void showDashboardCard() {
        ((CardLayout) rootPanel.getLayout()).show(rootPanel, DASHBOARD_CARD);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(frame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}