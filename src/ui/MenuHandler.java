package ui;

import database.ExerciseDAO;
import database.UserDAO;
import database.WorkoutDAO;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;
import models.Category;
import models.Exercise;
import models.User;
import models.Workout;

public class MenuHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final UserDAO userDAO = new UserDAO();
    private final ExerciseDAO exerciseDAO = new ExerciseDAO();
    private final WorkoutDAO workoutDAO = new WorkoutDAO();

    private User currentUser;

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== FitnessMS ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> handleRegister();
                case 2 -> handleLogin();
                case 3 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void handleRegister() {}
    private void handleLogin() {}
    private void showDashboard() {}
    private void handleAddWorkoutLog() {}
    private void handleViewAllLogs() {}
    private void handleViewRecentLogs() {}
    private void handleViewLogsByDateRange() {}
    private void handleViewWorkoutCount() {}
    private void printWorkouts(List<Workout> workouts) {}
}