package ui;

import database.ExerciseDAO;
import database.UserDAO;
import database.WorkoutDAO;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import models.Exercise;
import models.User;
import models.Workout;
import services.AuthService;
import services.ServiceResult;
import services.WorkoutService;

public class MenuHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthService authService = new AuthService(new UserDAO());
    private final WorkoutService workoutService = new WorkoutService(new ExerciseDAO(), new WorkoutDAO());

    private User currentUser;

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== FitnessMS ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> handleRegister();
                case 2 -> handleLogin();
                case 3 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void handleRegister() {
        System.out.println("\n--- Register ---");
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();
        double weight = readDouble("Enter Current Weight (kg): ");
        System.out.println("Enter Fitness Goal: ");
        String goal = scanner.nextLine();

        ServiceResult<Void> result = authService.registerUser(username, password, weight, goal);
        System.out.println(result.getMessage());
    }

    private void handleLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        ServiceResult<User> result = authService.loginUser(username, password);
        if (!result.isSuccess()) {
            System.out.println(result.getMessage());
            return;
        }

        currentUser = result.getData();
        System.out.println("Welcome, " + currentUser.getUsername() + "!");
        showDashboard();
    }

    private void showDashboard() {
        boolean loggedIn = true;
        while (loggedIn && currentUser != null) {
            System.out.println("\n=== Dashboard ===");
            System.out.println("1. Add Workout Log");
            System.out.println("2. View All Logs");
            System.out.println("3. View Recent Logs");
            System.out.println("4. View Logs By Date Range");
            System.out.println("5. View Workout Count");
            System.out.println("6. Logout");

            int choice = readInt("Choose: ");
            switch (choice) {
                case 1 -> handleAddWorkoutLog();
                case 2 -> handleViewAllLogs();
                case 3 -> handleViewRecentLogs();
                case 4 -> handleViewLogsByDateRange();
                case 5 -> handleViewWorkoutCount();
                case 6 -> {
                    loggedIn = false;
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void handleAddWorkoutLog() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        List<Exercise> exercises = workoutService.getAllExercises();
        if (exercises.isEmpty()) {
            System.out.println("No exercises available.");
            return;
        }

        System.out.println("\nAvailable Exercises:");
        Set<Integer> validExerciseIds = new HashSet<>();
        for (Exercise exercise : exercises) {
            validExerciseIds.add(exercise.getExerciseId());
            System.out.printf("%d. %s (%s)%n", exercise.getExerciseId(), exercise.getExerciseName(), exercise.getCategory());
        }

        int exerciseId = readInt("Enter Exercise ID: ");
        if (!validExerciseIds.contains(exerciseId)) {
            System.out.println("Invalid exercise ID.");
            return;
        }

        int sets = readInt("Enter Sets: ");
        int reps = readInt("Enter Reps: ");
        double weight = readDouble("Enter Weight Lifted (kg): ");

        ServiceResult<Void> result = workoutService.addWorkoutLog(
                currentUser.getUserId(),
                exerciseId,
                sets,
                reps,
                weight
        );
        System.out.println(result.getMessage());
    }

    private void handleViewAllLogs() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        List<Workout> workouts = workoutService.getAllWorkoutLogsByUserId(currentUser.getUserId());
        printWorkouts(workouts);
    }

    private void handleViewRecentLogs() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        List<Workout> workouts = workoutService.getRecentWorkoutLogsByUserId(currentUser.getUserId());
        printWorkouts(workouts);
    }

    private void handleViewLogsByDateRange() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        LocalDate startDate = readDate("Enter start date (yyyy-mm-dd): ");
        LocalDate endDate = readDate("Enter end date (yyyy-mm-dd): ");

        ServiceResult<List<Workout>> result = workoutService.getWorkoutLogsByDateRange(
                currentUser.getUserId(),
                startDate,
                endDate
        );

        if (!result.isSuccess()) {
            System.out.println(result.getMessage());
            return;
        }

        printWorkouts(result.getData());
    }

    private void handleViewWorkoutCount() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        int totalCount = workoutService.getTotalWorkoutCountByUserId(currentUser.getUserId());
        System.out.println("Total workout logs: " + totalCount);
    }

    private void printWorkouts(List<Workout> workouts) {
        if (workouts == null || workouts.isEmpty()) {
            System.out.println("No workout logs found.");
            return;
        }

        System.out.println("\n--- Workout Logs ---");
        for (Workout workout : workouts) {
            System.out.printf(
                    "Log ID: %d | Exercise ID: %d | Sets: %d | Reps: %d | Weight: %.2f kg | Date: %s%n",
                    workout.getLogId(),
                    workout.getExerciseId(),
                    workout.getSets(),
                    workout.getReps(),
                    workout.getWeightLifted(),
                    workout.getLogDate()
            );
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-mm-dd.");
            }
        }
    }
}