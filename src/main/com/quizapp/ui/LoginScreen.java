package main.com.quizapp.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScreen {
    public Scene createLoginScene() {
        // Create UI components
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        // Set up the login button's action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            // Call login logic here
        });

        VBox layout = new VBox(10, usernameField, passwordField, loginButton);
        return new Scene(layout, 300, 200);
    }
}
