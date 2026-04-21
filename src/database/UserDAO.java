package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

public class UserDAO {
	public boolean registerUser(User user) {
		String sql = "INSERT INTO users (username, password, current_weight, fitness_goal) VALUES (?, ?, ?, ?)";

		try {
			Connection conn = DBConnection.getConnection();
			if (conn == null) {
				return false;
			}

			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());
				stmt.setDouble(3, user.getCurrentWeight());
				stmt.setString(4, user.getFitnessGoal());

				return stmt.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			System.out.println("Error registering user: " + e.getMessage());
			return false;
		}
	}

	public User loginUser(String username, String password) {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

		try {
			Connection conn = DBConnection.getConnection();
			if (conn == null) {
				return null;
			}

			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, username);
				stmt.setString(2, password);

				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						return new User(
								rs.getInt("user_id"),
								rs.getString("username"),
								rs.getString("password"),
								rs.getDouble("current_weight"),
								rs.getString("fitness_goal")
						);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Error logging in user: " + e.getMessage());
		}

		return null;
	}
}
