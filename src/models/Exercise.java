package models;

public class Exercise {
    private int exerciseId;
    private String exerciseName;
    private Category category; 

    public Exercise() {}

    public Exercise(int exerciseId, String exerciseName, Category category) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.category = category;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // toString for easy debugging
    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + exerciseId +
                ", name='" + exerciseName + '\'' +
                ", category=" + category +
                '}';
    }
}