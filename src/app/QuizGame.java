package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class QuizGame extends JFrame {

    private User currentUser;
    private ArrayList<Question> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;

    JLabel questionLabel;
    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup group = new ButtonGroup();
    JButton nextBtn = new JButton("Next");

    public QuizGame(User user) {
        this.currentUser = user;

        setTitle("Multiple Choice Quiz - " + user.getUsername());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // fetch questions from DB
        fetchQuestions();

        // UI setup
        JPanel topPanel = new JPanel();
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(questionLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4,1,10,10));

        for(int i=0;i<4;i++){
            options[i] = new JRadioButton();
            group.add(options[i]);
            centerPanel.add(options[i]);
        }

        add(centerPanel, BorderLayout.CENTER);

        add(nextBtn, BorderLayout.SOUTH);

        nextBtn.addActionListener(e -> nextQuestion());

        if(!questions.isEmpty()){
            showQuestion(currentIndex);
        }

        setVisible(true);
    }

    private void fetchQuestions(){
        String sql = "SELECT * FROM quiz_questions";
        try(Connection conn = DBUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while(rs.next()){
                Question q = new Question(
                    rs.getInt("id"),
                    rs.getString("question"),
                    rs.getString("option1"),
                    rs.getString("option2"),
                    rs.getString("option3"),
                    rs.getString("option4"),
                    rs.getInt("correct_option")
                );
                questions.add(q);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void showQuestion(int index){
        Question q = questions.get(index);
        questionLabel.setText((index+1) + ". " + q.getQuestion());
        options[0].setText(q.getOption1());
        options[1].setText(q.getOption2());
        options[2].setText(q.getOption3());
        options[3].setText(q.getOption4());
        group.clearSelection();
    }

    private void nextQuestion(){
        Question q = questions.get(currentIndex);

        int selected = -1;
        for(int i=0;i<4;i++){
            if(options[i].isSelected()){
                selected = i+1;
            }
        }

        if(selected == q.getCorrectOption()){
            score += 10;
        }

        currentIndex++;
        if(currentIndex < questions.size()){
            showQuestion(currentIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Quiz Finished! Your score: " + score);
            updateScore();
            dispose();
        }
    }

    private void updateScore(){
        String checkSql = "SELECT * FROM scores WHERE user_id=? AND game_name='Quiz'";
        String insertSql = "INSERT INTO scores(user_id, game_name, level, score) VALUES(?, 'Quiz', 2, ?)";
        String updateSql = "UPDATE scores SET level=level+1, score=score+? WHERE user_id=? AND game_name='Quiz'";

        try(Connection conn = DBUtil.getConnection()){
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, currentUser.getId());
            ResultSet rs = checkStmt.executeQuery();

            if(rs.next()){
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, score);
                updateStmt.setInt(2, currentUser.getId());
                updateStmt.executeUpdate();
            } else {
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, currentUser.getId());
                insertStmt.setInt(2, score);
                insertStmt.executeUpdate();
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    // inner class to store questions
    private class Question {
        private int id;
        private String question, option1, option2, option3, option4;
        private int correctOption;

        public Question(int id, String question, String option1, String option2,
                        String option3, String option4, int correctOption){
            this.id = id;
            this.question = question;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.correctOption = correctOption;
        }

        public String getQuestion(){ return question; }
        public String getOption1(){ return option1; }
        public String getOption2(){ return option2; }
        public String getOption3(){ return option3; }
        public String getOption4(){ return option4; }
        public int getCorrectOption(){ return correctOption; }
    }

}
