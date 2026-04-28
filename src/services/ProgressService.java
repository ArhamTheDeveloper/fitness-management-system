package services;

import database.ProgressEntryDAO;
import java.util.List;
import models.ProgressEntry;

public class ProgressService {
    private final ProgressEntryDAO progressEntryDAO;

    public ProgressService(ProgressEntryDAO progressEntryDAO) {
        this.progressEntryDAO = progressEntryDAO;
    }

    public ServiceResult<Void> logProgressEntry(int userId, Double weight, Double bodyFatPercentage, 
                                                Double chestCm, Double waistCm, Double hipsCm, String notes) {
        if (userId <= 0) {
            return ServiceResult.failure("Invalid user session.");
        }

        if (weight == null && bodyFatPercentage == null && chestCm == null && 
            waistCm == null && hipsCm == null && (notes == null || notes.isBlank())) {
            return ServiceResult.failure("Please enter at least one measurement or note.");
        }

        if (weight != null && weight <= 0) {
            return ServiceResult.failure("Weight must be greater than 0.");
        }

        if (bodyFatPercentage != null && (bodyFatPercentage < 0 || bodyFatPercentage > 100)) {
            return ServiceResult.failure("Body fat percentage must be between 0 and 100.");
        }

        if (chestCm != null && chestCm < 0) {
            return ServiceResult.failure("Chest measurement cannot be negative.");
        }

        if (waistCm != null && waistCm < 0) {
            return ServiceResult.failure("Waist measurement cannot be negative.");
        }

        if (hipsCm != null && hipsCm < 0) {
            return ServiceResult.failure("Hips measurement cannot be negative.");
        }

        ProgressEntry entry = new ProgressEntry();
        entry.setUserId(userId);
        entry.setWeight(weight);
        entry.setBodyFatPercentage(bodyFatPercentage);
        entry.setChestCm(chestCm);
        entry.setWaistCm(waistCm);
        entry.setHipsCm(hipsCm);
        entry.setNotes(notes == null || notes.isBlank() ? null : notes.trim());

        boolean saved = progressEntryDAO.addProgressEntry(entry);
        if (!saved) {
            return ServiceResult.failure("Failed to save progress entry.");
        }

        return ServiceResult.success("Progress entry logged successfully.", null);
    }

    public ServiceResult<List<ProgressEntry>> getProgressEntriesByUserId(int userId) {
        if (userId <= 0) {
            return ServiceResult.failure("Invalid user session.");
        }

        List<ProgressEntry> entries = progressEntryDAO.getProgressEntriesByUserId(userId);
        return ServiceResult.success("Progress entries loaded.", entries);
    }

    public ServiceResult<ProgressEntry> getLatestProgressEntry(int userId) {
        if (userId <= 0) {
            return ServiceResult.failure("Invalid user session.");
        }

        ProgressEntry entry = progressEntryDAO.getLatestProgressEntryByUserId(userId);
        if (entry == null) {
            return ServiceResult.failure("No progress entries found.");
        }

        return ServiceResult.success("Latest progress entry loaded.", entry);
    }

    public ServiceResult<Void> deleteProgressEntry(int userId, int entryId) {
        if (userId <= 0) {
            return ServiceResult.failure("Invalid user session.");
        }

        if (entryId <= 0) {
            return ServiceResult.failure("Select a valid progress entry.");
        }

        boolean deleted = progressEntryDAO.deleteProgressEntry(entryId);
        if (!deleted) {
            return ServiceResult.failure("Failed to delete progress entry.");
        }

        return ServiceResult.success("Progress entry deleted successfully.", null);
    }
}
