package models;

import java.sql.Timestamp;

public class Workout {
	private int logId;
	private int userId;
	private int exerciseId;
	private int sets;
	private int reps;
	private double weightLifted;
	private Timestamp logDate;

	public Workout() {}

	public Workout(int logId, int userId, int exerciseId, int sets, int reps, double weightLifted, Timestamp logDate) {
		this.logId = logId;
		this.userId = userId;
		this.exerciseId = exerciseId;
		this.sets = sets;
		this.reps = reps;
		this.weightLifted = weightLifted;
		this.logDate = logDate;
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public int getSets() {
		return sets;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public double getWeightLifted() {
		return weightLifted;
	}

	public void setWeightLifted(double weightLifted) {
		this.weightLifted = weightLifted;
	}

	public Timestamp getLogDate() {
		return logDate;
	}

	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}

	// toString for easy debugging
	@Override
	public String toString() {
		return "Workout{" +
				"logId=" + logId +
				", userId=" + userId +
				", exerciseId=" + exerciseId +
				", sets=" + sets +
				", reps=" + reps +
				", weightLifted=" + weightLifted +
				", logDate=" + logDate +
				'}';
	}
}
