package services;

import database.WorkoutPlanDAO;
import database.WorkoutPlanItemDAO;
import java.util.List;
import models.WorkoutPlan;
import models.WorkoutPlanItem;

public class WorkoutPlanService {
    private final WorkoutPlanDAO workoutPlanDAO;
    private final WorkoutPlanItemDAO workoutPlanItemDAO;

    public WorkoutPlanService(WorkoutPlanDAO workoutPlanDAO, WorkoutPlanItemDAO workoutPlanItemDAO) {
        this.workoutPlanDAO = workoutPlanDAO;
        this.workoutPlanItemDAO = workoutPlanItemDAO;
    }

    public ServiceResult<WorkoutPlan> createPlan(int userId, String planName, String planDescription) {
        if (userId <= 0) {
            return ServiceResult.failure("Invalid user session.");
        }
        if (planName == null || planName.isBlank()) {
            return ServiceResult.failure("Plan name cannot be empty.");
        }

        WorkoutPlan plan = new WorkoutPlan();
        plan.setUserId(userId);
        plan.setPlanName(planName.trim());
        plan.setPlanDescription(planDescription == null ? null : planDescription.trim());

        int planId = workoutPlanDAO.createWorkoutPlan(plan);
        if (planId <= 0) {
            return ServiceResult.failure("Failed to create workout plan.");
        }

        WorkoutPlan createdPlan = workoutPlanDAO.getWorkoutPlanById(planId);
        if (createdPlan == null) {
            return ServiceResult.failure("Workout plan created, but failed to load details.");
        }

        return ServiceResult.success("Workout plan created successfully.", createdPlan);
    }

    public List<WorkoutPlan> getPlansByUserId(int userId) {
        return workoutPlanDAO.getWorkoutPlansByUserId(userId);
    }

    public int getTotalPlansCountByUserId(int userId) {
        if (userId <= 0) {
            return 0;
        }

        return workoutPlanDAO.getWorkoutPlansByUserId(userId).size();
    }

    public ServiceResult<WorkoutPlan> getPlanByIdForUser(int userId, int planId) {
        if (userId <= 0) {
            return ServiceResult.failure("Invalid user session.");
        }
        if (planId <= 0) {
            return ServiceResult.failure("Select a valid workout plan.");
        }

        WorkoutPlan plan = workoutPlanDAO.getWorkoutPlanById(planId);
        if (plan == null || plan.getUserId() != userId) {
            return ServiceResult.failure("Workout plan not found.");
        }

        return ServiceResult.success("Workout plan loaded.", plan);
    }

    public ServiceResult<Void> addPlanItem(int userId, int planId, int exerciseId, int targetSets, int targetReps, double targetWeight, int sortOrder) {
        ServiceResult<WorkoutPlan> planResult = getPlanByIdForUser(userId, planId);
        if (!planResult.isSuccess()) {
            return ServiceResult.failure(planResult.getMessage());
        }
        if (exerciseId <= 0) {
            return ServiceResult.failure("Please select a valid exercise.");
        }
        if (targetSets <= 0) {
            return ServiceResult.failure("Target sets must be greater than 0.");
        }
        if (targetReps <= 0) {
            return ServiceResult.failure("Target reps must be greater than 0.");
        }
        if (targetWeight < 0) {
            return ServiceResult.failure("Target weight cannot be negative.");
        }
        if (sortOrder <= 0) {
            return ServiceResult.failure("Sort order must be greater than 0.");
        }

        WorkoutPlanItem item = new WorkoutPlanItem();
        item.setPlanId(planId);
        item.setExerciseId(exerciseId);
        item.setTargetSets(targetSets);
        item.setTargetReps(targetReps);
        item.setTargetWeight(targetWeight);
        item.setSortOrder(sortOrder);

        boolean created = workoutPlanItemDAO.addWorkoutPlanItem(item);
        if (!created) {
            return ServiceResult.failure("Failed to add workout plan item.");
        }

        return ServiceResult.success("Workout plan item added successfully.", null);
    }

    public ServiceResult<List<WorkoutPlanItem>> getPlanItemsByPlanIdForUser(int userId, int planId) {
        ServiceResult<WorkoutPlan> planResult = getPlanByIdForUser(userId, planId);
        if (!planResult.isSuccess()) {
            return ServiceResult.failure(planResult.getMessage());
        }

        return ServiceResult.success("Workout plan items loaded.", workoutPlanItemDAO.getWorkoutPlanItemsByPlanId(planId));
    }

    public ServiceResult<Void> deletePlan(int userId, int planId) {
        ServiceResult<WorkoutPlan> planResult = getPlanByIdForUser(userId, planId);
        if (!planResult.isSuccess()) {
            return ServiceResult.failure(planResult.getMessage());
        }

        boolean deleted = workoutPlanDAO.deleteWorkoutPlan(planId);
        if (!deleted) {
            return ServiceResult.failure("Failed to delete workout plan.");
        }

        return ServiceResult.success("Workout plan deleted successfully.", null);
    }
}
