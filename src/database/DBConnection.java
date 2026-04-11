package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection{
    private static final String URL = "jdbc:mysql://localhost:3306/FitnessMS";
    private static final String USER = "root";
    private static final String PASS = System.getenv("DB_PASSWORD");

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL,USER,PASS);
        } catch(Exception e){
            System.out.println("❌ Connection Failed! Error: " + e.getMessage());
            return null;
        }
    }
}