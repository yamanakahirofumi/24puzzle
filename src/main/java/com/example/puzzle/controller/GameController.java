package com.example.puzzle.controller;

import com.example.puzzle.model.PuzzleBoard;
import com.example.puzzle.model.Tile;
import com.example.puzzle.service.ImageService;
import com.example.puzzle.service.ShuffleService;
import com.example.puzzle.view.TileView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the 24-Puzzle game.
 */
public class GameController {
    @FXML private GridPane puzzleGrid;
    @FXML private Label moveCountLabel;
    @FXML private Label timerLabel;
    @FXML private CheckBox showNumbersCheckbox;
    @FXML private ImageView previewImageView;

    private final ImageService imageService = new ImageService();
    private final ShuffleService shuffleService = new ShuffleService();
    private PuzzleBoard board;
    private final List<TileView> tileViews = new ArrayList<>();

    private int moveCount = 0;
    private int secondsElapsed = 0;
    private Timeline timer;
    private boolean isGameActive = false;

    @FXML
    public void initialize() {
        setupBoard(imageService.createNumberTiles());
        setupTimer();
    }

    private void setupBoard(List<Tile> tiles) {
        board = new PuzzleBoard(tiles);
        puzzleGrid.getChildren().clear();
        tileViews.clear();

        for (int i = 0; i < PuzzleBoard.SIZE * PuzzleBoard.SIZE; i++) {
            TileView tileView = new TileView();
            final int pos = i;
            tileView.setOnMouseClicked(e -> handleTileClick(pos));
            tileViews.add(tileView);
            puzzleGrid.add(tileView, i % PuzzleBoard.SIZE, i / PuzzleBoard.SIZE);
        }
        updateUI();
    }

    private void handleTileClick(int pos) {
        if (!isGameActive) return;
        if (board.move(pos)) {
            moveCount++;
            updateUI();
            checkWin();
        }
    }

    private void updateUI() {
        List<Tile> tiles = board.getTiles();
        int emptyPos = board.getEmptyPosition();
        boolean showNumbers = showNumbersCheckbox.isSelected();

        for (int i = 0; i < tileViews.size(); i++) {
            tileViews.get(i).setTile(tiles.get(i), showNumbers);
            tileViews.get(i).setMovable(isAdjacent(i, emptyPos));
        }
        moveCountLabel.setText(String.valueOf(moveCount));
    }

    private boolean isAdjacent(int p1, int p2) {
        int size = PuzzleBoard.SIZE;
        int r1 = p1 / size, c1 = p1 % size;
        int r2 = p2 / size, c2 = p2 % size;
        return (Math.abs(r1 - r2) == 1 && c1 == c2) || (Math.abs(c1 - c2) == 1 && r1 == r2);
    }

    @FXML
    private void handleShuffle() {
        shuffleService.shuffle(board, 200);
        moveCount = 0;
        secondsElapsed = 0;
        isGameActive = true;
        timerLabel.setText("00:00");
        timer.playFromStart();
        updateUI();
    }

    @FXML
    private void handleReset() {
        board.reset();
        moveCount = 0;
        secondsElapsed = 0;
        isGameActive = false;
        timer.stop();
        timerLabel.setText("00:00");
        updateUI();
    }

    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );
        File file = fileChooser.showOpenDialog(puzzleGrid.getScene().getWindow());
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                setupBoard(imageService.createTilesFromImage(image));
                previewImageView.setImage(image);
                handleReset();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load image");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleToggleNumbers() {
        updateUI();
    }

    private void setupTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsElapsed++;
            int mins = secondsElapsed / 60;
            int secs = secondsElapsed % 60;
            timerLabel.setText(String.format("%02d:%02d", mins, secs));
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    private void checkWin() {
        if (board.isSolved()) {
            isGameActive = false;
            timer.stop();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations!");
            alert.setHeaderText("You solved the puzzle!");
            alert.setContentText("Moves: " + moveCount + "\nTime: " + timerLabel.getText());
            alert.showAndWait();
        }
    }

    /**
     * Handles keyboard events to move tiles using arrow keys.
     * Maps arrow keys to move the empty space in that direction.
     */
    public void handleKeyPressed(KeyEvent event) {
        if (!isGameActive) return;
        int emptyPos = board.getEmptyPosition();
        int size = PuzzleBoard.SIZE;
        int r = emptyPos / size;
        int c = emptyPos % size;
        int targetPos = -1;

        switch (event.getCode()) {
            case UP:
                if (r > 0) targetPos = emptyPos - size;
                break;
            case DOWN:
                if (r < size - 1) targetPos = emptyPos + size;
                break;
            case LEFT:
                if (c > 0) targetPos = emptyPos - 1;
                break;
            case RIGHT:
                if (c < size - 1) targetPos = emptyPos + 1;
                break;
            default:
                break;
        }

        if (targetPos != -1) {
            handleTileClick(targetPos);
        }
    }
}
