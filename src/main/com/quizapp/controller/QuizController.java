package main.com.quizapp.controller;

import javafx.event.ActionEvent;
import main.com.quizapp.dao.DatabaseHelper;
import main.com.quizapp.dao.QuizDAO;
import main.com.quizapp.model.Quiz;
import main.com.quizapp.model.Question;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class QuizController {

    @FXML
    private Text quizTitle;

    @FXML
    private Text questionText;

    @FXML
    private RadioButton option1, option2, option3, option4;

    @FXML
    private ToggleGroup optionsGroup;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private QuizDAO quizDao;
    private int score = 0;

    public QuizController() {
        try {
            this.quizDao = new QuizDAO(DatabaseHelper.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database.", e);
        }
    }


    @FXML
    public void initialize() {
        try {
            Connection connection = DatabaseHelper.getConnection();
            quizDao = new QuizDAO(connection);


            List<Quiz> quizzes = quizDao.getAllQuizzes();
            if (!quizzes.isEmpty()) {
                Quiz quiz = quizzes.get(0);
                quizTitle.setText(quiz.getTitle());


                questions = quizDao.getQuestionsByQuizId(quiz.getId());
                if (!questions.isEmpty()) {
                    showNextQuestion();
                } else {
                    displayNoQuestionsMessage();
                }
            } else {
                quizTitle.setText("No Quizzes Available");
                questionText.setText("Please contact the administrator.");
            }
        } catch (SQLException e) {
            quizTitle.setText("Error Loading Quiz");
            questionText.setText("An error occurred: " + e.getMessage());
        }
    }


    private void showNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionText.setText(question.getQuestionText());
            String[] options = question.getOptions();
            option1.setText(options[0]);
            option2.setText(options[1]);
            option3.setText(options[2]);
            option4.setText(options[3]);
            optionsGroup.selectToggle(null);
        } else {

            displayQuizCompletionMessage();
        }
    }

    private void displayNoQuestionsMessage() {
        quizTitle.setText("No Questions Found");
        questionText.setText("Please contact the administrator to add questions.");
        disableOptions();
    }

    private void displayQuizCompletionMessage() {
        quizTitle.setText("Quiz Completed!");
        questionText.setText("Your score: " + score + " / " + questions.size());
        disableOptions();
    }

    private void disableOptions() {
        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);
    }

    @FXML
    public void handleSubmit() {
        RadioButton selectedOption = (RadioButton) optionsGroup.getSelectedToggle();
        if (selectedOption != null) {
            String selectedAnswer = selectedOption.getText();
            Question currentQuestion = questions.get(currentQuestionIndex);

            if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                score++;
            }
            currentQuestionIndex++;
            showNextQuestion();
        } else {
            questionText.setText("Please select an answer before proceeding.");
        }
    }
}
