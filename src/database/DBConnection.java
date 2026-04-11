package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection{
    private static Connection connection = null;

    private static final String URL = "jdbc:mysql://localhost:3306/FitnessMS";
    private static final String USER = "root";
    private static final String PASS = System.getenv("DB_PASSWORD");

    private DBConnection() {};

   public static Connection getConnection() {
        try {
            // Only create a new connection if one doesn't exist or was closed
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("🔌 New Database Connection Established.");
            }
        } catch (Exception e) {
            System.out.println("❌ Connection Failed! " + e.getMessage());
        }
        return connection;
    }
}