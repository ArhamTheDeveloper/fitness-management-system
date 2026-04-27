package models;

import java.sql.Timestamp;

public class WorkoutPlan {
    private int planId;
    private int userId;
    private String planName;
    private String planDescription;
    private Timestamp createdAt;

    public WorkoutPlan() {
    }

    public WorkoutPlan(int planId, int userId, String planName, String planDescription, Timestamp createdAt) {
        this.planId = planId;
        this.userId = userId;
        this.planName = planName;
        this.planDescription = planDescription;
        this.createdAt = createdAt;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return planName;
    }
}
