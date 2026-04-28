package services;

import database.ExerciseDAO;
import database.WorkoutDAO;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import models.Exercise;
import models.Workout;

public class WorkoutService {
    private final ExerciseDAO exerciseDAO;
    private final WorkoutDAO workoutDAO;

    public WorkoutService(ExerciseDAO exerciseDAO, WorkoutDAO workoutDAO) {
        this.exerciseDAO = exerciseDAO;
        this.workoutDAO = workoutDAO;
    }

    public List<Exercise> getAllExercises() {
        return exerciseDAO.getAllExercises();
    }

    public ServiceResult<Void> addWorkoutLog(int userId, int exerciseId, int sets, int reps, double weightLifted) {
        if (userId <= 0) {
            return ServiceResult.failure("Invalid user session.");
        }
        if (exerciseId <= 0) {
            return ServiceResult.failure("Please select a valid exercise.");
        }
        if (sets <= 0) {
            return ServiceResult.failure("Sets must be greater than 0.");
        }
        if (reps <= 0) {
            return ServiceResult.failure("Reps must be greater than 0.");
        }
        if (weightLifted < 0) {
            return ServiceResult.failure("Weight lifted cannot be negative.");
        }

        Workout workout = new Workout();
        workout.setUserId(userId);
        workout.setExerciseId(exerciseId);
        workout.setSets(sets);
        workout.setReps(reps);
        workout.setWeightLifted(weightLifted);

        boolean saved = workoutDAO.addWorkoutLog(workout);
        if (!saved) {
            return ServiceResult.failure("Failed to save workout log.");
        }

        return ServiceResult.success("Workout log added successfully.", null);
    }

    public ServiceResult<Workout> getWorkoutLogByIdForUser(int userId, int logId) {
        if (userId <= 0) {
            return ServiceResult.failure("Invalid user session.");
        }
        if (logId <= 0) {
            return ServiceResult.failure("Please select a valid workout log.");
        }

        Workout workout = workoutDAO.getWorkoutLogById(logId);
        if (workout == null || workout.getUserId() != userId) {
            return ServiceResult.failure("Workout log not found.");
        }

        return ServiceResult.success("Workout log loaded.", workout);
    }

    public ServiceResult<Void> updateWorkoutLog(int userId, int logId, int exerciseId, int sets, int reps, double weightLifted) {
        ServiceResult<Workout> existingResult = getWorkoutLogByIdForUser(userId, logId);
        if (!existingResult.isSuccess()) {
            return ServiceResult.failure(existingResult.getMessage());
        }
        if (exerciseId <= 0) {
            return ServiceResult.failure("Please select a valid exercise.");
        }
        if (sets <= 0) {
            return ServiceResult.failure("Sets must be greater than 0.");
        }
        if (reps <= 0) {
            return ServiceResult.failure("Reps must be greater than 0.");
        }
        if (weightLifted < 0) {
            return ServiceResult.failure("Weight lifted cannot be negative.");
        }

        Workout workout = existingResult.getData();
        workout.setExerciseId(exerciseId);
        workout.setSets(sets);
        workout.setReps(reps);
        workout.setWeightLifted(weightLifted);

        boolean updated = workoutDAO.updateWorkoutLog(workout);
        if (!updated) {
            return ServiceResult.failure("Failed to update workout log.");
        }

        return ServiceResult.success("Workout log updated successfully.", null);
    }

    public ServiceResult<Void> deleteWorkoutLog(int userId, int logId) {
        ServiceResult<Workout> existingResult = getWorkoutLogByIdForUser(userId, logId);
        if (!existingResult.isSuccess()) {
            return ServiceResult.failure(existingResult.getMessage());
        }

        boolean deleted = workoutDAO.deleteWorkoutLog(logId);
        if (!deleted) {
            return ServiceResult.failure("Failed to delete workout log.");
        }

        return ServiceResult.success("Workout log deleted successfully.", null);
    }

    public List<Workout> getAllWorkoutLogsByUserId(int userId) {
        return workoutDAO.getWorkoutLogsByUserId(userId);
    }

    public List<Workout> getRecentWorkoutLogsByUserId(int userId) {
        return workoutDAO.getRecentWorkoutLogsByUserId(userId);
    }

    public ServiceResult<List<Workout>> getWorkoutLogsByDateRange(int userId, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return ServiceResult.failure("Start date and end date are required.");
        }
        if (startDate.isAfter(endDate)) {
            return ServiceResult.failure("Start date cannot be after end date.");
        }

        Timestamp startTs = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTs = Timestamp.valueOf(endDate.plusDays(1).atStartOfDay().minusSeconds(1));
        List<Workout> workouts = workoutDAO.getWorkoutLogsByUserIdAndDateRange(userId, startTs, endTs);

        return ServiceResult.success("Workout logs loaded.", workouts);
    }

    public int getTotalWorkoutCountByUserId(int userId) {
        return workoutDAO.getTotalWorkoutCountByUserId(userId);
    }

    public int getWorkoutCountByUserIdSince(int userId, Timestamp since) {
        if (userId <= 0 || since == null) {
            return 0;
        }

        return workoutDAO.getWorkoutCountByUserIdSince(userId, since);
    }
}