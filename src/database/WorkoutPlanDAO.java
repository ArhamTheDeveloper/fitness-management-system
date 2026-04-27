package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.WorkoutPlan;

public class WorkoutPlanDAO {
    public int createWorkoutPlan(WorkoutPlan plan) {
        String sql = "INSERT INTO workout_plans (user_id, plan_name, plan_description) VALUES (?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return -1;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, plan.getUserId());
                stmt.setString(2, plan.getPlanName());
                stmt.setString(3, plan.getPlanDescription());

                int affected = stmt.executeUpdate();
                if (affected <= 0) {
                    return -1;
                }

                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating workout plan: " + e.getMessage());
        }

        return -1;
    }

    public List<WorkoutPlan> getWorkoutPlansByUserId(int userId) {
        String sql = "SELECT plan_id, user_id, plan_name, plan_description, created_at FROM workout_plans " +
                "WHERE user_id = ? ORDER BY created_at DESC";
        List<WorkoutPlan> plans = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return plans;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        plans.add(new WorkoutPlan(
                                rs.getInt("plan_id"),
                                rs.getInt("user_id"),
                                rs.getString("plan_name"),
                                rs.getString("plan_description"),
                                rs.getTimestamp("created_at")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching workout plans by userId: " + e.getMessage());
        }

        return plans;
    }

    public WorkoutPlan getWorkoutPlanById(int planId) {
        String sql = "SELECT plan_id, user_id, plan_name, plan_description, created_at FROM workout_plans WHERE plan_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return null;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, planId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new WorkoutPlan(
                                rs.getInt("plan_id"),
                                rs.getInt("user_id"),
                                rs.getString("plan_name"),
                                rs.getString("plan_description"),
                                rs.getTimestamp("created_at")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching workout plan by id: " + e.getMessage());
        }

        return null;
    }

    public boolean updateWorkoutPlan(WorkoutPlan plan) {
        String sql = "UPDATE workout_plans SET plan_name = ?, plan_description = ? WHERE plan_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, plan.getPlanName());
                stmt.setString(2, plan.getPlanDescription());
                stmt.setInt(3, plan.getPlanId());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error updating workout plan: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteWorkoutPlan(int planId) {
        String sql = "DELETE FROM workout_plans WHERE plan_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, planId);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting workout plan: " + e.getMessage());
        }

        return false;
    }
}
