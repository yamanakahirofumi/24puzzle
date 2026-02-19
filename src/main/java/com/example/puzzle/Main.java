package com.example.puzzle;

import com.example.puzzle.controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the 24-Puzzle application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = loader.load();

        GameController controller = loader.getController();

        Scene scene = new Scene(root);
        // Register key event handler from the controller
        scene.setOnKeyPressed(controller::handleKeyPressed);

        primaryStage.setTitle("24-Puzzle");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
