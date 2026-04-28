package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.ProgressEntry;

public class ProgressEntryDAO {
    public boolean addProgressEntry(ProgressEntry entry) {
        String sql = "INSERT INTO progress_entries (user_id, weight, body_fat_percentage, chest_cm, waist_cm, hips_cm, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, entry.getUserId());
                stmt.setObject(2, entry.getWeight());
                stmt.setObject(3, entry.getBodyFatPercentage());
                stmt.setObject(4, entry.getChestCm());
                stmt.setObject(5, entry.getWaistCm());
                stmt.setObject(6, entry.getHipsCm());
                stmt.setString(7, entry.getNotes());

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error adding progress entry: " + e.getMessage());
        }

        return false;
    }

    public List<ProgressEntry> getProgressEntriesByUserId(int userId) {
        String sql = "SELECT entry_id, user_id, weight, body_fat_percentage, chest_cm, waist_cm, hips_cm, notes, entry_date " +
                "FROM progress_entries WHERE user_id = ? ORDER BY entry_date DESC";
        List<ProgressEntry> entries = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return entries;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        entries.add(new ProgressEntry(
                                rs.getInt("entry_id"),
                                rs.getInt("user_id"),
                                rs.getObject("weight") != null ? rs.getDouble("weight") : null,
                                rs.getObject("body_fat_percentage") != null ? rs.getDouble("body_fat_percentage") : null,
                                rs.getObject("chest_cm") != null ? rs.getDouble("chest_cm") : null,
                                rs.getObject("waist_cm") != null ? rs.getDouble("waist_cm") : null,
                                rs.getObject("hips_cm") != null ? rs.getDouble("hips_cm") : null,
                                rs.getString("notes"),
                                rs.getTimestamp("entry_date")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching progress entries by userId: " + e.getMessage());
        }

        return entries;
    }

    public ProgressEntry getLatestProgressEntryByUserId(int userId) {
        String sql = "SELECT entry_id, user_id, weight, body_fat_percentage, chest_cm, waist_cm, hips_cm, notes, entry_date " +
                "FROM progress_entries WHERE user_id = ? ORDER BY entry_date DESC LIMIT 1";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return null;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new ProgressEntry(
                                rs.getInt("entry_id"),
                                rs.getInt("user_id"),
                                rs.getObject("weight") != null ? rs.getDouble("weight") : null,
                                rs.getObject("body_fat_percentage") != null ? rs.getDouble("body_fat_percentage") : null,
                                rs.getObject("chest_cm") != null ? rs.getDouble("chest_cm") : null,
                                rs.getObject("waist_cm") != null ? rs.getDouble("waist_cm") : null,
                                rs.getObject("hips_cm") != null ? rs.getDouble("hips_cm") : null,
                                rs.getString("notes"),
                                rs.getTimestamp("entry_date")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching latest progress entry by userId: " + e.getMessage());
        }

        return null;
    }

    public boolean deleteProgressEntry(int entryId) {
        String sql = "DELETE FROM progress_entries WHERE entry_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, entryId);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting progress entry: " + e.getMessage());
        }

        return false;
    }
}
