package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // URL of your database
    private static final String URL = "jdbc:mysql://localhost:3306/puzzlesim?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    // your MySQL username & password
    private static final String USER = "ashna";  // your MySQL username
    private static final String PASS = "ashna123";  // your MySQL password

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // connect to the database
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("DB connection error: " + e.getMessage());
        }
        return conn;
    }
}

