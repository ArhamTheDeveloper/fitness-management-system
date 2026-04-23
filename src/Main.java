import database.DBConnection;
import ui.MenuHandler;
import ui.FitnessSwingApp;

import java.sql.Connection;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && "cli".equalsIgnoreCase(args[0])) {
            Connection testConn = DBConnection.getConnection();

            if (testConn == null) {
                System.out.println("Connection failed. Check your database settings and MySQL server.");
            }

            new MenuHandler().start();
            return;
        }

        SwingUtilities.invokeLater(() -> new FitnessSwingApp().show());
    }
}