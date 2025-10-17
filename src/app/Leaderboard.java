package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Leaderboard extends JFrame {

    public Leaderboard() {
        setTitle("Leaderboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Username");
        model.addColumn("Game");
        model.addColumn("Level");
        model.addColumn("Score");

        // fetch data from DB
        try(Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT u.username, s.game_name, s.level, s.score " +
                         "FROM scores s JOIN users u ON s.user_id = u.id " +
                         "ORDER BY s.score DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getString("username"),
                        rs.getString("game_name"),
                        rs.getInt("level"),
                        rs.getInt("score")
                });
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        // create table
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // for testing
    public static void main(String[] args) {
        new Leaderboard();
    }
}
