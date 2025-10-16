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

        // top panel - welcome
        JPanel topPanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Choose a game to play:");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);

        // center panel - game buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 3, 20, 20));

        JButton ticTacToeBtn = new JButton("Tic Tac Toe");
        JButton mazeBtn = new JButton("Maze");
        JButton quizBtn = new JButton("Quiz");
        JButton cardMemoryBtn = new JButton("Card Memory");
        JButton pacmanBtn = new JButton("Pac-Man Riddles");
        JButton leaderboardBtn = new JButton("Leaderboard");

        centerPanel.add(ticTacToeBtn);
        centerPanel.add(mazeBtn);
        centerPanel.add(quizBtn);
        centerPanel.add(cardMemoryBtn);
        centerPanel.add(pacmanBtn);
        centerPanel.add(leaderboardBtn);

        add(centerPanel, BorderLayout.CENTER);

        // button actions
        ticTacToeBtn.addActionListener(e -> playTicTacToe());
        mazeBtn.addActionListener(e -> playMaze());
        quizBtn.addActionListener(e -> playQuiz());
        cardMemoryBtn.addActionListener(e -> playCardMemory());
        pacmanBtn.addActionListener(e -> playPacMan());
        leaderboardBtn.addActionListener(e -> showLeaderboard());

        setVisible(true);
    }

    // MainGameWindow.java
private void playTicTacToe() {
    // open the actual Tic Tac Toe game window
    new TicTacToeGame(currentUser);
}

    private void playMaze() {
        JOptionPane.showMessageDialog(this, "Maze is starting...");
    }

    private void playQuiz() {
        JOptionPane.showMessageDialog(this, "Quiz is starting...");
    }

    private void playCardMemory() {
        JOptionPane.showMessageDialog(this, "Card Memory is starting...");
    }

    private void playPacMan() {
        JOptionPane.showMessageDialog(this, "Pac-Man Riddles is starting...");
    }

    private void showLeaderboard() {
        JOptionPane.showMessageDialog(this, "Leaderboard coming soon!");
    }

    // for testing
    public static void main(String[] args) {
        // just test with a dummy user
        User dummy = new User(1, "ash", "ash@example.com", "1234");
        new MainGameWindow(dummy);
    }
}
