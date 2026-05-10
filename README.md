# Fitness Management System

## Project Description

A comprehensive Java-based fitness management application that allows users to track their workouts, exercises, progress, and create personalized workout plans. The system provides both a graphical user interface (Swing) and command-line interface for flexibility in accessing and managing fitness data.

## Group Members

| Name           | Student ID  | Section |
| -------------- | ----------- | ------- |
| Muhammad Arham | 023-25-0020 | E       |
| Luksh Arija    | 023-25-0057 | E       |
| Ubaid          | 023-25-0113 | E       |
| Uzair Khan     | 023-25-0136 | E       |

## Project Purpose

**Problem Solved:** Fitness enthusiasts and gym-goers lack an integrated system to track their workouts, monitor progress over time, and manage personalized workout plans effectively.

**Target Users:**

- Individual fitness enthusiasts
- Gym members
- Personal trainers
- Anyone tracking their fitness goals

**Motivation:** This project applies core OOP principles to build a real-world fitness tracking application, demonstrating proper database design, layered architecture, user authentication, and both GUI and CLI interfaces.

## Core Modules & Architecture

The project follows a **layered architecture pattern** with clear separation of concerns:

### 1. **Models Package** (`src/models/`)

Entity classes representing core domain objects:

- `User.java` - User profile with fitness goals and metrics
- `Exercise.java` - Exercise definitions with categories (Strength, Cardio, Flexibility)
- `Workout.java` / `WorkoutLog.java` - Individual workout sessions and logs
- `WorkoutPlan.java` - Personalized workout plans
- `WorkoutPlanItem.java` - Individual exercises within a workout plan
- `ProgressEntry.java` - Body measurements and progress tracking
- `Category.java` - Exercise category enumeration

### 2. **Database Layer** (`src/database/`)

Data access objects (DAOs) implementing CRUD operations:

- `DBConnection.java` - MySQL connection management
- `UserDAO.java` - User registration, login, profile updates
- `ExerciseDAO.java` - Exercise catalog management
- `WorkoutDAO.java` - Workout logging and retrieval
- `WorkoutPlanDAO.java` - Workout plan CRUD operations
- `WorkoutPlanItemDAO.java` - Workout plan items management
- `ProgressEntryDAO.java` - Progress tracking data persistence

### 3. **Services Layer** (`src/services/`)

Business logic and service operations:

- `AuthService.java` - User authentication and registration
- `WorkoutService.java` - Workout logging and retrieval
- `WorkoutPlanService.java` - Workout plan creation and management
- `ProgressService.java` - Progress tracking and analytics
- `ServiceResult.java` - Standardized result wrapper for service operations

### 4. **User Interface** (`src/ui/`)

Presentation layer with Swing components:

- `FitnessSwingApp.java` - Main GUI application and window management
- `LoginPanel.java` - User authentication UI
- `RegisterPanel.java` - New user registration UI
- `DashboardPanel.java` - Main dashboard with workout tracking
- `WorkoutDialog.java` - Dialog for logging workouts
- `MenuHandler.java` - CLI menu interface
- `AppTheme.java` - Centralized styling and color scheme

## Key OOP Features Demonstrated

✓ **Encapsulation**

- Private fields with public getters/setters
- Business logic encapsulated in service classes
- Database connection details hidden in DBConnection

✓ **Inheritance**

- Entity classes inherit from common base patterns
- DAO classes share common methods

✓ **Polymorphism**

- Multiple UI components implementing common interfaces
- Service classes providing different implementations

✓ **Abstraction**

- Abstract data access patterns in DAOs
- Service interfaces defining contracts

✓ **Collections**

- ArrayList/HashMap usage for storing exercises, workout plans, and progress entries
- Custom collections in workout plans

✓ **Exception Handling**

- Database connection exceptions
- SQL exceptions with proper error messages
- User input validation and error feedback

✓ **Database Usage**

- MySQL relational database with proper schema
- Foreign key relationships
- Transaction management

✓ **File & Configuration Management**

- SQL schema file (schema.sql) for database setup
- Database connection configuration

## System Features

- **User Authentication** - Secure login and registration
- **Workout Logging** - Log completed workouts with sets, reps, and weight
- **Exercise Management** - Pre-defined exercise library with categories
- **Workout Plans** - Create and follow custom workout plans
- **Progress Tracking** - Monitor weight, body measurements, and fitness metrics
- **Dual Interface** - Both GUI (Swing) and CLI modes
- **Data Persistence** - MySQL database with proper schema

## How to Run

### Prerequisites

- **Java Development Kit (JDK)**: Version 11 or higher
- **MySQL Server**: Version 5.7 or higher
- **MySQL Driver Library**: Already included in `lib/` folder

### Step 1: Set Up the Database

1. **Start MySQL Server**

   ```bash
   # On Linux/Mac
   mysql.server start

   # On Windows
   net start MySQL80  # or your MySQL version
   ```

2. **Create the Database**

   ```bash
   mysql -u root -p < src/schema.sql
   ```

   Enter your MySQL root password when prompted. This will:
   - Create the `FitnessMS` database
   - Create all required tables
   - Insert sample exercise data

3. **Configure Database Connection** (if needed)
   - Edit `src/database/DBConnection.java`
   - Update `DB_URL`, `DB_USER`, and `DB_PASSWORD` with your MySQL credentials

### Step 2: Compile the Project

From the project root directory:

```bash
javac -d bin -cp lib/* src/**/*.java
```

Or use your IDE (IntelliJ, Eclipse, VS Code):

- Right-click project → Build Project
- Uses build.gradle or pom.xml if configured

### Step 3: Run the Application

**GUI Mode (Default):**

```bash
java -cp bin:lib/* Main
```

**CLI Mode:**

```bash
java -cp bin:lib/* Main cli
```

**Force GUI Mode (even on headless system):**

```bash
java -cp bin:lib/* Main gui
```

### Step 4: Use the Application

1. **First-time users**: Click "Register" to create an account
2. **Existing users**: Login with your credentials
3. Navigate through the dashboard to:
   - Log workouts
   - Create workout plans
   - Track progress

## Database Schema Overview

- **users** - User profiles and credentials
- **exercises** - Exercise library (Strength, Cardio, Flexibility)
- **workout_logs** - Individual workout sessions
- **workout_plans** - Custom workout plans per user
- **workout_plan_items** - Exercises within plans
- **progress_entries** - Body metrics and measurement tracking

## Project Structure

```
fitness-management-system/
├── src/
│   ├── Main.java                 # Application entry point
│   ├── schema.sql                # Database schema
│   ├── models/                   # Entity classes
│   ├── database/                 # Data access objects (DAOs)
│   ├── services/                 # Business logic
│   └── ui/                       # Swing GUI components
├── bin/                          # Compiled .class files
├── lib/                          # External libraries (MySQL driver)
├── README.md                     # This file
└── .gitignore
```

## Demo & Repository

**Video Demo:** https://youtu.be/Lkay6qZZ4DA
_5-minute demo covering project purpose, architecture, main features, and member contributions._

**GitHub Repository:** https://github.com/ArhamTheDeveloper/fitness-management-system

## Compilation & Execution Commands Quick Reference

```bash
# Compile all sources
javac -d bin -cp lib/* src/**/*.java

# Run GUI version
java -cp bin:lib/* Main

# Run CLI version
java -cp bin:lib/* Main cli
```
