import database.DBConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Initializing FlexDB...");

        // Testing the connection
        Connection testConn = DBConnection.getConnection();

        if (testConn != null) {
            System.out.println("✅ JDBC is working! Connection to MySQL successful.");
        } else {
            System.out.println("⚠️ Connection failed. Check your password and MySQL server.");
        }
    }
}