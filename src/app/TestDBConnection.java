package app;

import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connected to database: " + conn.getCatalog());
            } else {
                System.out.println("❌ Connection failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

