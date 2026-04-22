package services;

import database.UserDAO;
import models.User;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public ServiceResult<Void> registerUser(String username, String password, double currentWeight, String fitnessGoal) {
        if (username == null || username.isBlank()) {
            return ServiceResult.failure("Username cannot be empty.");
        }
        if (password == null || password.isBlank()) {
            return ServiceResult.failure("Password cannot be empty.");
        }
        if (currentWeight <= 0) {
            return ServiceResult.failure("Current weight must be greater than 0.");
        }
        if (fitnessGoal == null || fitnessGoal.isBlank()) {
            return ServiceResult.failure("Fitness goal cannot be empty.");
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password);
        user.setCurrentWeight(currentWeight);
        user.setFitnessGoal(fitnessGoal.trim());

        boolean created = userDAO.registerUser(user);
        if (!created) {
            return ServiceResult.failure("Registration failed. Username may already exist or DB is unavailable.");
        }

        return ServiceResult.success("Registration successful.", null);
    }

    public ServiceResult<User> loginUser(String username, String password) {
        if (username == null || username.isBlank()) {
            return ServiceResult.failure("Username cannot be empty.");
        }
        if (password == null || password.isBlank()) {
            return ServiceResult.failure("Password cannot be empty.");
        }

        User user = userDAO.loginUser(username.trim(), password);
        if (user == null) {
            return ServiceResult.failure("Invalid username or password.");
        }

        return ServiceResult.success("Login successful.", user);
    }
}