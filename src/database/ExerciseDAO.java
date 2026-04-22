package database;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Category;
import models.Exercise;

public class ExerciseDAO {
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT exercise_id, exercise_name, category FROM exercises";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return exercises;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Exercise exercise = new Exercise(
                            rs.getInt("exercise_id"),
                            rs.getString("exercise_name"),
                            Category.valueOf(rs.getString("category"))
                    );
                    exercises.add(exercise);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching exercises: " + e.getMessage());
        }

        return exercises;
    }

    public List<Exercise> getExercisesByCategory(Category category) {
    List<Exercise> exercises = new ArrayList<>();
    String sql = "SELECT exercise_id, exercise_name, category FROM exercises WHERE category = ?";

    try {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return exercises;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.name());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Exercise exercise = new Exercise(
                            rs.getInt("exercise_id"),
                            rs.getString("exercise_name"),
                            Category.valueOf(rs.getString("category"))
                    );
                    exercises.add(exercise);
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error fetching exercises by category: " + e.getMessage());
    }

    return exercises;
}

}

