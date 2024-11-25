package main.com.quizapp.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.println(getClass().getResource("/fxml/quiz.fxml"));

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/quiz.fxml"));
            Parent root = fxmlLoader.load();

            primaryStage.setWidth(800);
            primaryStage.setHeight(600);

            primaryStage.setTitle("Quiz Application");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            // Log any errors to help debug issues
            e.printStackTrace();
            System.err.println("Error loading FXML file or initializing the application.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
