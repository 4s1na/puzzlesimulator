package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToeGame extends JFrame {

    private JButton[][] buttons = new JButton[3][3];
    private boolean userTurn = true; // user starts first
    private User currentUser; // your user class
    private Random random = new Random();

    public TicTacToeGame(User user) {
        this.currentUser = user;

        setTitle("Tic Tac Toe - User vs Computer");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        Font btnFont = new Font("Arial", Font.BOLD, 60);

        // create 3x3 grid buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(btnFont);
                buttons[i][j] = btn;
                add(btn);

                int row = i;
                int col = j;

                btn.addActionListener(e -> {
                    if (userTurn && btn.getText().equals("")) {
                        btn.setText("X");
                        userTurn = false;

                        if (checkWinner("X")) {
                            JOptionPane.showMessageDialog(this, "You Win!");
                            updateScore(); // update DB score
                            dispose();
                        } else if (isBoardFull()) {
                            JOptionPane.showMessageDialog(this, "Draw!");
                            dispose();
                        } else {
                            computerMove();
                        }
                    }
                });
            }
        }

        setVisible(true);
    }

    private void computerMove() {
        // simple AI: block or random
        int[] move = findWinningMove("O");
        if (move == null) move = findWinningMove("X"); // block user
        if (move == null) move = randomMove(); // random
        buttons[move[0]][move[1]].setText("O");
        userTurn = true;

        if (checkWinner("O")) {
            JOptionPane.showMessageDialog(this, "Computer Wins!");
            dispose();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "Draw!");
            dispose();
        }
    }

    private int[] findWinningMove(String player) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    buttons[i][j].setText(player);
                    boolean win = checkWinner(player);
                    buttons[i][j].setText("");
                    if (win) return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private int[] randomMove() {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!buttons[row][col].getText().equals(""));
        return new int[]{row, col};
    }

    private boolean checkWinner(String player) {
        // check rows, cols, diagonals
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(player) &&
                buttons[i][1].getText().equals(player) &&
                buttons[i][2].getText().equals(player)) return true;

            if (buttons[0][i].getText().equals(player) &&
                buttons[1][i].getText().equals(player) &&
                buttons[2][i].getText().equals(player)) return true;
        }

        if (buttons[0][0].getText().equals(player) &&
            buttons[1][1].getText().equals(player) &&
            buttons[2][2].getText().equals(player)) return true;

        if (buttons[0][2].getText().equals(player) &&
            buttons[1][1].getText().equals(player) &&
            buttons[2][0].getText().equals(player)) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().equals("")) return false;
        return true;
    }

    private void updateScore() {
        // TODO: add DB update code here like in QuizGame
        // increase level or score for currentUser
    }

}
