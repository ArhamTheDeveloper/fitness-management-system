package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.WorkoutPlanItem;

public class WorkoutPlanItemDAO {
    public boolean addWorkoutPlanItem(WorkoutPlanItem item) {
        String sql = "INSERT INTO workout_plan_items " +
                "(plan_id, exercise_id, target_sets, target_reps, target_weight, sort_order) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, item.getPlanId());
                stmt.setInt(2, item.getExerciseId());
                stmt.setInt(3, item.getTargetSets());
                stmt.setInt(4, item.getTargetReps());
                stmt.setDouble(5, item.getTargetWeight());
                stmt.setInt(6, item.getSortOrder());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error adding workout plan item: " + e.getMessage());
        }

        return false;
    }

    public List<WorkoutPlanItem> getWorkoutPlanItemsByPlanId(int planId) {
        String sql = "SELECT item_id, plan_id, exercise_id, target_sets, target_reps, target_weight, sort_order " +
                "FROM workout_plan_items WHERE plan_id = ? ORDER BY sort_order ASC, item_id ASC";
        List<WorkoutPlanItem> items = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return items;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, planId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        items.add(new WorkoutPlanItem(
                                rs.getInt("item_id"),
                                rs.getInt("plan_id"),
                                rs.getInt("exercise_id"),
                                rs.getInt("target_sets"),
                                rs.getInt("target_reps"),
                                rs.getDouble("target_weight"),
                                rs.getInt("sort_order")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching workout plan items by planId: " + e.getMessage());
        }

        return items;
    }

    public boolean updateWorkoutPlanItem(WorkoutPlanItem item) {
        String sql = "UPDATE workout_plan_items SET exercise_id = ?, target_sets = ?, target_reps = ?, " +
                "target_weight = ?, sort_order = ? WHERE item_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, item.getExerciseId());
                stmt.setInt(2, item.getTargetSets());
                stmt.setInt(3, item.getTargetReps());
                stmt.setDouble(4, item.getTargetWeight());
                stmt.setInt(5, item.getSortOrder());
                stmt.setInt(6, item.getItemId());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error updating workout plan item: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteWorkoutPlanItem(int itemId) {
        String sql = "DELETE FROM workout_plan_items WHERE item_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, itemId);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting workout plan item: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteWorkoutPlanItemsByPlanId(int planId) {
        String sql = "DELETE FROM workout_plan_items WHERE plan_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, planId);
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting workout plan items by planId: " + e.getMessage());
        }

        return false;
    }
}
