# Fitness Management System

## Project Description

A Java-based fitness management application that lets users track their workouts, exercises, and progress, and create personalized workout plans. The system provides both a graphical interface (Swing) and a command-line interface for flexibility.

## Group Members and Contributions

| Name           | Student ID  | Section | Contribution                                                                         |
| -------------- | ----------- | ------- | ------------------------------------------------------------------------------------ |
| Muhammad Arham | 023-25-0020 | E       | Overall project setup, architecture, database integration, UI flow, and coordination |
| Luksh Arija    | 023-25-0057 | E       | Workout logging module                                                               |
| Ubaid          | 023-25-0113 | E       | Workout plans module                                                                 |
| Uzair Khan     | 023-25-0136 | E       | Progress tracking module                                                             |

## Project Purpose

**Problem Solved:** Fitness enthusiasts and gym-goers often lack an integrated system to track their workouts, monitor progress over time, and manage personalized workout plans.

**Target Users:** Individual fitness enthusiasts, gym members, personal trainers, and anyone tracking fitness goals.

**Motivation:** This project applies core OOP principles to build a real-world fitness tracking application, demonstrating proper database design, layered architecture, user authentication, and both GUI and CLI interfaces.

## Core Modules & Architecture

The project follows a **layered architecture** with clear separation of concerns:

### 1. Models (`src/models/`)

Entity classes representing core domain objects:

- `User.java` — User profile with fitness goals and metrics
- `Exercise.java` — Exercise definitions with categories (Strength, Cardio, Flexibility)
- `Workout.java` — Individual workout log entries
- `WorkoutPlan.java` — Personalized workout plans
- `WorkoutPlanItem.java` — Individual exercises within a workout plan
- `ProgressEntry.java` — Body measurements and progress tracking
- `Category.java` — Exercise category enumeration

### 2. Database Layer (`src/database/`)

Data access objects (DAOs) implementing CRUD operations:

- `DBConnection.java` — MySQL connection management (reads password from `DB_PASSWORD` environment variable)
- `UserDAO.java` — User registration, login, profile updates
- `ExerciseDAO.java` — Exercise catalog management
- `WorkoutDAO.java` — Workout logging and retrieval
- `WorkoutPlanDAO.java` — Workout plan CRUD operations
- `WorkoutPlanItemDAO.java` — Workout plan items management
- `ProgressEntryDAO.java` — Progress tracking data persistence

### 3. Services (`src/services/`)

Business logic layer:

- `AuthService.java` — User authentication and registration
- `WorkoutService.java` — Workout logging and retrieval
- `WorkoutPlanService.java` — Workout plan creation and management
- `ProgressService.java` — Progress tracking
- `ServiceResult.java` — Standardized result wrapper for service operations

### 4. User Interface (`src/ui/`)

Swing-based presentation layer:

- `FitnessSwingApp.java` — Main GUI application and window management
- `LoginPanel.java` — User authentication UI
- `RegisterPanel.java` — New user registration UI
- `DashboardPanel.java` — Main dashboard with workout tracking
- `WorkoutDialog.java` — Dialog for logging workouts
- `MenuHandler.java` — CLI menu interface
- `AppTheme.java` — Centralized styling and color scheme

## Key OOP Features Demonstrated

- **Encapsulation** — Private fields with public getters/setters; business logic in service classes; DB details hidden in `DBConnection`
- **Inheritance** — Entity classes share common patterns; DAO classes share common methods
- **Polymorphism** — Multiple UI components implementing common interfaces
- **Abstraction** — Abstract data access patterns in DAOs; service interfaces defining contracts
- **Collections** — `ArrayList`/`HashMap` for exercises, workout plans, and progress entries
- **Exception Handling** — Database and SQL exceptions with error messages; input validation
- **Database Usage** — MySQL with proper relational schema, foreign keys, and transaction management

## System Features

- User authentication — secure login and registration
- Workout logging — log completed workouts with sets, reps, and weight
- Exercise catalog — pre-defined exercise library organized by category
- Workout plans — create and follow custom workout plans
- Progress tracking — monitor weight, body measurements, and fitness metrics
- Dual interface — both GUI (Swing) and CLI modes
- Data persistence — MySQL database with proper relational schema

## How to Run

### Prerequisites

- **Java Development Kit (JDK):** Version 11 or higher
- **MySQL Server:** Version 5.7 or higher
- **MySQL Connector/J:** Already included in the `lib/` folder

### Step 1: Set Up the Database

Start your MySQL server, then run the schema script:

```bash
# Linux / Mac
mysql -u root -p < src/schema.sql

# Windows (Command Prompt)
mysql -u root -p < src\schema.sql
```

Enter your MySQL root password when prompted. This creates the `FitnessMS` database, all tables, and inserts sample exercise data.

### Step 2: Set the Database Password

`DBConnection.java` reads your MySQL password from an environment variable to avoid hardcoding credentials.

```bash
# Linux / Mac (add to ~/.bashrc or ~/.zshrc to make it permanent)
export DB_PASSWORD=your_mysql_password

# Windows (Command Prompt)
set DB_PASSWORD=your_mysql_password

# Windows (PowerShell)
$env:DB_PASSWORD="your_mysql_password"
```

If your MySQL username is not `root`, also update `DB_USER` in `src/database/DBConnection.java`.

### Step 3: Compile the Project

From the project root directory:

```bash
# Linux / Mac
find src -name "*.java" > sources.txt
javac -d bin -cp "lib/*" @sources.txt

# Windows (Command Prompt)
dir /s /b src\*.java > sources.txt
javac -d bin -cp "lib/*" @sources.txt

# Windows (PowerShell)
Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName } | Set-Content sources.txt
javac -d bin -cp "lib/*" @sources.txt
```

Make sure the `bin/` directory exists before compiling:

```bash
mkdir bin   # Linux / Mac
mkdir bin   # Windows
```

### Step 4: Run the Application

**GUI Mode (default):**

```bash
# Linux / Mac
java -cp "bin:lib/*" Main

# Windows
java -cp "bin;lib/*" Main
```

**CLI Mode:**

```bash
# Linux / Mac
java -cp "bin:lib/*" Main cli

# Windows
java -cp "bin;lib/*" Main cli
```

**Force GUI even on a headless system:**

```bash
# Linux / Mac
java -cp "bin:lib/*" Main gui

# Windows
java -cp "bin;lib/*" Main gui
```

### Step 5: Use the Application

1. **First-time users:** Click "Register" to create an account
2. **Returning users:** Login with your credentials
3. From the dashboard you can log workouts, manage workout plans, and track progress

## Database Schema

| Table               | Purpose                                          |
| ------------------- | ------------------------------------------------ |
| users               | User profiles and credentials                    |
| exercises           | Exercise library (Strength, Cardio, Flexibility) |
| workout_logs        | Individual workout sessions                      |
| workout_plans       | Custom workout plans per user                    |
| workout_plan_items  | Exercises within each plan                       |
| progress_entries    | Body metrics and measurement tracking            |

## Project Structure

```
fitness-management-system/
├── src/
│   ├── Main.java                 # Application entry point
│   ├── schema.sql                # Database schema and sample data
│   ├── models/                   # Entity classes
│   ├── database/                 # Data access objects (DAOs)
│   ├── services/                 # Business logic
│   └── ui/                       # Swing GUI components
├── bin/                          # Compiled .class files (create before compiling)
├── lib/                          # MySQL Connector/J (included)
└── README.md
```

## Demo & Repository

**Video Demo:** https://youtu.be/Lkay6qZZ4DA  
*5-minute walkthrough covering project purpose, architecture, main features, and member contributions.*

**GitHub Repository:** https://github.com/ArhamTheDeveloper/fitness-management-system
