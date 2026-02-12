package com.example.puzzle.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * Main layout for the 24-Puzzle application.
 */
public class MainView extends BorderPane {
    private final GridPane puzzleGrid;
    private final Label moveCountLabel;
    private final Label timerLabel;
    private final Button startButton;
    private final Button resetButton;
    private final Button imageSelectButton;
    private final CheckBox showNumbersCheckBox;

    public MainView() {
        this.setPadding(new Insets(20));

        // Puzzle Grid
        puzzleGrid = new GridPane();
        puzzleGrid.setHgap(2);
        puzzleGrid.setVgap(2);
        puzzleGrid.setStyle("-fx-background-color: #000;");
        this.setCenter(puzzleGrid);

        // Control Panel
        VBox controlPanel = new VBox(15);
        controlPanel.setPadding(new Insets(0, 0, 0, 20));
        controlPanel.setPrefWidth(200);

        moveCountLabel = new Label("Moves: 0");
        moveCountLabel.setFont(new Font(18));

        timerLabel = new Label("Time: 00:00");
        timerLabel.setFont(new Font(18));

        startButton = new Button("Start / Shuffle");
        startButton.setMaxWidth(Double.MAX_VALUE);

        resetButton = new Button("Reset");
        resetButton.setMaxWidth(Double.MAX_VALUE);

        imageSelectButton = new Button("Select Image");
        imageSelectButton.setMaxWidth(Double.MAX_VALUE);

        showNumbersCheckBox = new CheckBox("Show Numbers");
        showNumbersCheckBox.setSelected(true);

        controlPanel.getChildren().addAll(
            timerLabel,
            moveCountLabel,
            startButton,
            resetButton,
            imageSelectButton,
            showNumbersCheckBox
        );

        this.setRight(controlPanel);
    }

    public GridPane getPuzzleGrid() {
        return puzzleGrid;
    }

    public Label getMoveCountLabel() {
        return moveCountLabel;
    }

    public Label getTimerLabel() {
        return timerLabel;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public Button getImageSelectButton() {
        return imageSelectButton;
    }

    public CheckBox getShowNumbersCheckBox() {
        return showNumbersCheckBox;
    }
}
