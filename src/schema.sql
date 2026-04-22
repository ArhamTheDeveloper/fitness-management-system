CREATE DATABASE FitnessMS;

USE FitnessMS;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    current_weight DOUBLE,
    fitness_goal VARCHAR(100)
);

CREATE TABLE exercises (
    exercise_id INT AUTO_INCREMENT PRIMARY KEY,
    exercise_name VARCHAR(100) NOT NULL,
    category ENUM('Strength', 'Cardio', 'Flexibility') NOT NULL
);

CREATE TABLE workout_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    exercise_id INT,
    sets INT,
    reps INT,
    weight_lifted DOUBLE,
    log_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (exercise_id) REFERENCES exercises(exercise_id)
);

INSERT INTO exercises (exercise_name, category) VALUES 
('Pushups', 'Strength'),
('Squats', 'Strength'),
('Bench Press', 'Strength'),
('Deadlift', 'Strength'),
('Pull-ups', 'Strength'),
('Running', 'Cardio'),
('Cycling', 'Cardio'),
('Swimming', 'Cardio'),
('Jump Rope', 'Cardio'),
('Yoga', 'Flexibility');

ALTER TABLE exercises
MODIFY category ENUM('STRENGTH', 'CARDIO', 'FLEXIBILITY') NOT NULL;