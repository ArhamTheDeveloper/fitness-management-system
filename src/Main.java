import database.DBConnection;
import ui.MenuHandler;
import ui.FitnessSwingApp;

import java.awt.GraphicsEnvironment;
import java.sql.Connection;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        boolean forceCli = args.length > 0 && "cli".equalsIgnoreCase(args[0]);
        boolean forceGui = args.length > 0 && "gui".equalsIgnoreCase(args[0]);
        boolean headless = GraphicsEnvironment.isHeadless();

        if (forceCli || (headless && !forceGui)) {
            if (headless && !forceCli) {
                System.out.println("No graphical environment detected. Starting CLI mode.");
            }
            launchCli();
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                new FitnessSwingApp().show();
            } catch (Exception e) {
                System.out.println("GUI launch failed: " + e.getMessage());
                System.out.println("Falling back to CLI mode.");
                launchCli();
            }
        });
    }

    private static void launchCli() {
        Connection testConn = DBConnection.getConnection();

        if (testConn == null) {
            System.out.println("Connection failed. Check your database settings and MySQL server.");
        }

        new MenuHandler().start();
    }
}