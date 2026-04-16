package models;

public class User {
    private int userId;
    private String username;
    private String password;
    private double currentWeight;
    private String fitnessGoal;
    
    public User() {}

    public User(int userId, String username, String password, double currentWeight, String fitnessGoal) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.currentWeight = currentWeight;
        this.fitnessGoal = fitnessGoal;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }

    // toString for easy debugging
    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", name='" + username + '\'' +
                ", goal='" + fitnessGoal + '\'' +
                '}';
    }
}