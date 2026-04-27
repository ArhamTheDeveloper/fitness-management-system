package models;

public class WorkoutPlanItem {
    private int itemId;
    private int planId;
    private int exerciseId;
    private int targetSets;
    private int targetReps;
    private double targetWeight;
    private int sortOrder;

    public WorkoutPlanItem() {
    }

    public WorkoutPlanItem(int itemId, int planId, int exerciseId, int targetSets, int targetReps, double targetWeight, int sortOrder) {
        this.itemId = itemId;
        this.planId = planId;
        this.exerciseId = exerciseId;
        this.targetSets = targetSets;
        this.targetReps = targetReps;
        this.targetWeight = targetWeight;
        this.sortOrder = sortOrder;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getTargetSets() {
        return targetSets;
    }

    public void setTargetSets(int targetSets) {
        this.targetSets = targetSets;
    }

    public int getTargetReps() {
        return targetReps;
    }

    public void setTargetReps(int targetReps) {
        this.targetReps = targetReps;
    }

    public double getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
