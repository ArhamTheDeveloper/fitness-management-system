package models;

import java.sql.Timestamp;

public class ProgressEntry {
    private int entryId;
    private int userId;
    private Double weight;
    private Double bodyFatPercentage;
    private Double chestCm;
    private Double waistCm;
    private Double hipsCm;
    private String notes;
    private Timestamp entryDate;

    public ProgressEntry() {
    }

    public ProgressEntry(int entryId, int userId, Double weight, Double bodyFatPercentage, 
                         Double chestCm, Double waistCm, Double hipsCm, String notes, Timestamp entryDate) {
        this.entryId = entryId;
        this.userId = userId;
        this.weight = weight;
        this.bodyFatPercentage = bodyFatPercentage;
        this.chestCm = chestCm;
        this.waistCm = waistCm;
        this.hipsCm = hipsCm;
        this.notes = notes;
        this.entryDate = entryDate;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(Double bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    public Double getChestCm() {
        return chestCm;
    }

    public void setChestCm(Double chestCm) {
        this.chestCm = chestCm;
    }

    public Double getWaistCm() {
        return waistCm;
    }

    public void setWaistCm(Double waistCm) {
        this.waistCm = waistCm;
    }

    public Double getHipsCm() {
        return hipsCm;
    }

    public void setHipsCm(Double hipsCm) {
        this.hipsCm = hipsCm;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public String toString() {
        return "ProgressEntry{" +
                "entryId=" + entryId +
                ", userId=" + userId +
                ", weight=" + weight +
                ", bodyFatPercentage=" + bodyFatPercentage +
                ", entryDate=" + entryDate +
                '}';
    }
}
