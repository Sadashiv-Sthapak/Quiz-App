package main.com.quizapp.dao;

import main.com.quizapp.model.Quiz;
import main.com.quizapp.model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private final Connection connection;


    public QuizDAO(Connection connection) {
        this.connection = connection;
    }


    public List<Quiz> getAllQuizzes() throws SQLException {
        String sql = "SELECT * FROM quizzes";
        List<Quiz> quizzes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Create Quiz object and add it to the list
                quizzes.add(new Quiz(rs.getInt("id"), rs.getString("title")));
            }
        }
        return quizzes;
    }


    public List<Question> getQuestionsByQuizId(int quizId) throws SQLException {
        String sql = "SELECT * FROM questions WHERE quiz_id = ?";
        List<Question> questions = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quizId); // Set quiz ID parameter
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    questions.add(new Question(
                            rs.getInt("id"),
                            rs.getString("question_text"),
                            new String[]{
                                    rs.getString("option1"),
                                    rs.getString("option2"),
                                    rs.getString("option3"),
                                    rs.getString("option4")
                            },
                            rs.getString("correct_answer")
                    ));
                }
            }
        }
        return questions;
    }
}
