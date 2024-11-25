package main.com.quizapp.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;
import main.com.quizapp.dao.DatabaseHelper;
import main.com.quizapp.dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text errorMessage;

    private UserDAO userDao;
    private Labeled errorLabel;


    public LoginController() {
        try {
            this.userDao = new UserDAO(main.com.quizapp.dao.DatabaseHelper.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    public void handleSignup() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Ensure fields are not empty
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }


        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try {

            Connection connection = DatabaseHelper.getConnection();
            UserDAO userDAO = new UserDAO(connection);


            if (userDAO.createUser(username, hashedPassword)) {
                errorLabel.setText("Signup successful! Please log in.");
            } else {
                errorLabel.setText("Signup failed. Try a different username.");
            }


            connection.close();
        } catch (SQLException e) {
            errorLabel.setText("Error: " + e.getMessage());
        }
    }
}
