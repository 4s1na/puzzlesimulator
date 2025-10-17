package app;

import java.sql.*;

public class ScoreDAO {

    public static void updateScore(int userId, String gameName, int pointsWon) {
        try (Connection conn = DBUtil.getConnection()) {
            String selectSql = "SELECT * FROM scores WHERE user_id=? AND game_name=?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, userId);
            selectStmt.setString(2, gameName);
            ResultSet rs = selectStmt.executeQuery();

            if(rs.next()) {
                int currentScore = rs.getInt("score");
                int currentLevel = rs.getInt("level");

                String updateSql = "UPDATE scores SET score=?, level=? WHERE id=?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, currentScore + pointsWon);
                updateStmt.setInt(2, currentLevel + 1);
                updateStmt.setInt(3, rs.getInt("id"));
                updateStmt.executeUpdate();
            } else {
                String insertSql = "INSERT INTO scores (user_id, game_name, level, score) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, userId);
                insertStmt.setString(2, gameName);
                insertStmt.setInt(3, 1);
                insertStmt.setInt(4, pointsWon);
                insertStmt.executeUpdate();
            }

        } catch(SQLException e) {
            System.out.println("Error updating score: " + e.getMessage());
        }
    }
}
