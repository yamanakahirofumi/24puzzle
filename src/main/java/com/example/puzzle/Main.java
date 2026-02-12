package com.example.puzzle;

import com.example.puzzle.controller.GameController;
import com.example.puzzle.model.PuzzleBoard;
import com.example.puzzle.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the 24-Puzzle application.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        PuzzleBoard board = new PuzzleBoard();
        MainView view = new MainView();
        new GameController(board, view);

        Scene scene = new Scene(view, 800, 600);
        String css = getClass().getResource("/com/example/puzzle/css/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("24-Puzzle Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
