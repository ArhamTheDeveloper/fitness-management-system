package database;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import models.Workout;

public class WorkoutDAO {

    public boolean addWorkoutLog(Workout workout) {
        String sql = "INSERT INTO workout_logs (user_id, exercise_id, sets, reps, weight_lifted) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, workout.getUserId());
                stmt.setInt(2, workout.getExerciseId());
                stmt.setInt(3, workout.getSets());
                stmt.setInt(4, workout.getReps());
                stmt.setDouble(5, workout.getWeightLifted());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error adding workout log: " + e.getMessage());
            return false;
        }
    }

    public Workout getWorkoutLogById(int logId) {
        String sql = "SELECT log_id, user_id, exercise_id, sets, reps, weight_lifted, log_date " +
                    "FROM workout_logs WHERE log_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return null;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, logId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Workout(
                                rs.getInt("log_id"),
                                rs.getInt("user_id"),
                                rs.getInt("exercise_id"),
                                rs.getInt("sets"),
                                rs.getInt("reps"),
                                rs.getDouble("weight_lifted"),
                                rs.getTimestamp("log_date")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching workout log: " + e.getMessage());
        }

        return null;
    }

    public List<Workout> getWorkoutLogsByUserId(int userId){
        String sql = "SELECT log_id, user_id, exercise_id, sets, reps, weight_lifted, log_date " +
                     "FROM workout_logs WHERE user_id = ? ORDER BY log_date DESC";
        List<Workout> workouts = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            if(conn == null){
                return workouts;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        workouts.add(new Workout(
                                rs.getInt("log_id"),
                                rs.getInt("user_id"),
                                rs.getInt("exercise_id"),
                                rs.getInt("sets"),
                                rs.getInt("reps"),
                                rs.getDouble("weight_lifted"),
                                rs.getTimestamp("log_date")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching workouts by userId: " + e.getMessage());
        }

        return workouts;
    }

    public List<Workout> getWorkoutLogsByUserIdAndDateRange(int userId, Timestamp startDate, Timestamp endDate) {
        String sql = "SELECT log_id, user_id, exercise_id, sets, reps, weight_lifted, log_date " +
                     "FROM workout_logs WHERE user_id = ? AND log_date BETWEEN ? AND ? ORDER BY log_date DESC";
        List<Workout> workouts = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return workouts;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setTimestamp(2, startDate);
                stmt.setTimestamp(3, endDate);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        workouts.add(new Workout(
                                rs.getInt("log_id"),
                                rs.getInt("user_id"),
                                rs.getInt("exercise_id"),
                                rs.getInt("sets"),
                                rs.getInt("reps"),
                                rs.getDouble("weight_lifted"),
                                rs.getTimestamp("log_date")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching workouts by userId and date range: " + e.getMessage());
        }

        return workouts;
    }

    public boolean updateWorkoutLog(Workout workout) {
        String sql = "UPDATE workout_logs SET exercise_id = ?, sets = ?, reps = ?, weight_lifted = ? WHERE log_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, workout.getExerciseId());
                stmt.setInt(2, workout.getSets());
                stmt.setInt(3, workout.getReps());
                stmt.setDouble(4, workout.getWeightLifted());
                stmt.setInt(5, workout.getLogId());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error updating workout log: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteWorkoutLog(int logId) {
        String sql = "DELETE FROM workout_logs WHERE log_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, logId);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting workout log: " + e.getMessage());
            return false;
        }
    }

    public List<Workout> getRecentWorkoutLogsByUserId(int userId) {
        String sql = "SELECT log_id, user_id, exercise_id, sets, reps, weight_lifted, log_date " +
                     "FROM workout_logs WHERE user_id = ? ORDER BY log_date DESC LIMIT 5";
        List<Workout> workouts = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return workouts;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        workouts.add(new Workout(
                                rs.getInt("log_id"),
                                rs.getInt("user_id"),
                                rs.getInt("exercise_id"),
                                rs.getInt("sets"),
                                rs.getInt("reps"),
                                rs.getDouble("weight_lifted"),
                                rs.getTimestamp("log_date")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching recent workouts by userId: " + e.getMessage());
        }

        return workouts;
    }

    public int getTotalWorkoutCountByUserId(int userId) {
        String sql = "SELECT COUNT(*) AS total FROM workout_logs WHERE user_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return 0;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("total");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching total workout count by userId: " + e.getMessage());
        }

        return 0;
    }


}
