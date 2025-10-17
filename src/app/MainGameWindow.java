package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGameWindow extends JFrame {

    private User currentUser;

    public MainGameWindow(User user) {
        this.currentUser = user;

        setTitle("Puzzle Simulator - Welcome " + user.getUsername());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // top panel - welcome + back button
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Choose a game to play:");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> goBack());
        topPanel.add(backBtn, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        // center panel - 3 game buttons + leaderboard
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 20, 20));

        JButton ticTacToeBtn = new JButton("Tic Tac Toe");
        JButton quizBtn = new JButton("Quiz");
        JButton mazeBtn = new JButton("Maze");
        JButton leaderboardBtn = new JButton("Leaderboard");

        centerPanel.add(ticTacToeBtn);
        centerPanel.add(quizBtn);
        centerPanel.add(mazeBtn);
        centerPanel.add(leaderboardBtn);

        add(centerPanel, BorderLayout.CENTER);

        // button actions
        ticTacToeBtn.addActionListener(e -> playTicTacToe());
        quizBtn.addActionListener(e -> playQuiz());
        mazeBtn.addActionListener(e -> playMaze());
        leaderboardBtn.addActionListener(e -> showLeaderboard());

        setVisible(true);
    }

    private void playTicTacToe() {
        new TicTacToeGame(currentUser);
    }

    private void playQuiz() {
        new QuizGame(currentUser);
    }


    private void showLeaderboard() {
    new Leaderboard();
}

    private void goBack() {
        this.dispose(); // close main window
        new LoginForm(); // go back to login page
    }

    // for testing
    public static void main(String[] args) {
        User dummy = new User(1, "ash", "ash@example.com", "1234");
        new MainGameWindow(dummy);
    }
}
