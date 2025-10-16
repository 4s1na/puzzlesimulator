import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TicTacToeGame extends JFrame {

    private JButton[][] buttons = new JButton[3][3];
    private boolean playerX = true; // X starts
    private User currentUser;

    public TicTacToeGame(User user) {
        this.currentUser = user;

        setTitle("Tic Tac Toe - Player: " + user.getUsername());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3,3));

        // create buttons
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                add(buttons[i][j]);
                buttons[i][j].addActionListener(new ButtonListener(i,j));
            }
        }

        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        int row, col;
        public ButtonListener(int row, int col){
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e){
            if(buttons[row][col].getText().equals("")){
                buttons[row][col].setText(playerX ? "X" : "O");
                playerX = !playerX;
                checkWinner();
            }
        }
    }

    private void checkWinner(){
        String winner = null;

        // check rows
        for(int i=0; i<3; i++){
            if(!buttons[i][0].getText().equals("") &&
               buttons[i][0].getText().equals(buttons[i][1].getText()) &&
               buttons[i][1].getText().equals(buttons[i][2].getText())){
                winner = buttons[i][0].getText();
            }
        }

        // check columns
        for(int i=0; i<3; i++){
            if(!buttons[0][i].getText().equals("") &&
               buttons[0][i].getText().equals(buttons[1][i].getText()) &&
               buttons[1][i].getText().equals(buttons[2][i].getText())){
                winner = buttons[0][i].getText();
            }
        }

        // check diagonals
        if(!buttons[0][0].getText().equals("") &&
           buttons[0][0].getText().equals(buttons[1][1].getText()) &&
           buttons[1][1].getText().equals(buttons[2][2].getText())){
            winner = buttons[0][0].getText();
        }

        if(!buttons[0][2].getText().equals("") &&
           buttons[0][2].getText().equals(buttons[1][1].getText()) &&
           buttons[1][1].getText().equals(buttons[2][0].getText())){
            winner = buttons[0][2].getText();
        }

        if(winner != null){
            JOptionPane.showMessageDialog(this, winner + " wins!");
            updateScore();
            dispose(); // close game after win
        } else if(isBoardFull()){
            JOptionPane.showMessageDialog(this, "Draw!");
            dispose();
        }
    }

    private boolean isBoardFull(){
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(buttons[i][j].getText().equals(""))
                    return false;
        return true;
    }

    // update user score in DB
    private void updateScore(){
        String checkSql = "SELECT * FROM scores WHERE user_id=? AND game_name='Tic Tac Toe'";
        String insertSql = "INSERT INTO scores(user_id, game_name, level, score) VALUES(?, 'Tic Tac Toe', 2, 10)";
        String updateSql = "UPDATE scores SET level=level+1, score=score+10 WHERE user_id=? AND game_name='Tic Tac Toe'";

        try(Connection conn = DBUtil.getConnection()){
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, currentUser.getId());
            ResultSet rs = checkStmt.executeQuery();

            if(rs.next()){
                // already exists → update level & score
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, currentUser.getId());
                updateStmt.executeUpdate();
            } else {
                // first time → insert
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, currentUser.getId());
                insertStmt.executeUpdate();
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

}

