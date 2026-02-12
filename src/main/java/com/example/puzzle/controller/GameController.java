package com.example.puzzle.controller;

import com.example.puzzle.model.PuzzleBoard;
import com.example.puzzle.model.Tile;
import com.example.puzzle.service.ImageService;
import com.example.puzzle.service.ShuffleService;
import com.example.puzzle.view.MainView;
import com.example.puzzle.view.TileView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.logging.Logger;

/**
 * Controller for the 24-Puzzle game.
 */
public class GameController {
    private static final Logger logger = Logger.getLogger(GameController.class.getName());

    private final PuzzleBoard board;
    private final MainView view;
    private final ShuffleService shuffleService;
    private final ImageService imageService;
    private final TileView[] tileViews = new TileView[PuzzleBoard.TOTAL_TILES];

    private int moves;
    private int seconds;
    private Timeline timer;
    private boolean gameActive;

    public GameController(PuzzleBoard board, MainView view) {
        this.board = board;
        this.view = view;
        this.shuffleService = new ShuffleService();
        this.imageService = new ImageService();

        initialize();
    }

    private void initialize() {
        setupGrid();

        view.getStartButton().setOnAction(e -> startGame());
        view.getResetButton().setOnAction(e -> resetGame());
        view.getImageSelectButton().setOnAction(e -> selectImage());
        view.getShowNumbersCheckBox().selectedProperty().addListener((obs, oldVal, newVal) -> updateTileNumbers());

        setupTimer();
        updateTileNumbers();
    }

    private void setupGrid() {
        view.getPuzzleGrid().getChildren().clear();
        for (int i = 0; i < PuzzleBoard.TOTAL_TILES; i++) {
            Tile tile = board.getTile(i);
            TileView tileView = new TileView(tile, 100);
            final int pos = i;
            tileView.setOnMouseClicked(e -> handleTileClick(pos));
            tileViews[i] = tileView;
            view.getPuzzleGrid().add(tileView, i % PuzzleBoard.SIZE, i / PuzzleBoard.SIZE);
        }
    }

    private void handleTileClick(int pos) {
        if (!gameActive) return;

        if (board.moveTile(pos)) {
            moves++;
            view.getMoveCountLabel().setText("Moves: " + moves);
            refreshGrid();
            checkWin();
        }
    }

    private void refreshGrid() {
        boolean showNumbers = view.getShowNumbersCheckBox().isSelected();
        for (int i = 0; i < PuzzleBoard.TOTAL_TILES; i++) {
            Tile tile = board.getTile(i);
            TileView tileView = tileViews[i];
            tileView.setTile(tile);
            tileView.setShowNumber(showNumbers);

            // Highlight movable tiles
            tileView.getStyleClass().remove("movable-tile");
            if (isMovable(i)) {
                tileView.getStyleClass().add("movable-tile");
            }
        }
    }

    private boolean isMovable(int pos) {
        int emptyPos = board.getEmptyPos();
        int r1 = pos / PuzzleBoard.SIZE;
        int c1 = pos % PuzzleBoard.SIZE;
        int r2 = emptyPos / PuzzleBoard.SIZE;
        int c2 = emptyPos % PuzzleBoard.SIZE;
        return (Math.abs(r1 - r2) == 1 && c1 == c2) || (r1 == r2 && Math.abs(c1 - c2) == 1);
    }

    private void startGame() {
        board.reset();
        shuffleService.shuffle(board);
        moves = 0;
        seconds = 0;
        gameActive = true;
        view.getMoveCountLabel().setText("Moves: 0");
        view.getTimerLabel().setText("Time: 00:00");
        timer.playFromStart();
        refreshGrid();
        logger.info("Game started and board shuffled.");
    }

    private void resetGame() {
        board.reset();
        moves = 0;
        seconds = 0;
        gameActive = false;
        timer.stop();
        view.getMoveCountLabel().setText("Moves: 0");
        view.getTimerLabel().setText("Time: 00:00");
        refreshGrid();
        logger.info("Game reset to initial state.");
    }

    private void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Puzzle Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        File selectedFile = fileChooser.showOpenDialog(view.getScene().getWindow());

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                var fragments = imageService.splitImage(image);
                for (int i = 0; i < fragments.size(); i++) {
                    board.setTileImage(i, fragments.get(i));
                }
                refreshGrid();
                logger.info("Image loaded and applied: " + selectedFile.getName());
            } catch (Exception e) {
                logger.severe("Error loading image: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not load image");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void setupTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            int mins = seconds / 60;
            int secs = seconds % 60;
            view.getTimerLabel().setText(String.format("Time: %02d:%02d", mins, secs));
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateTileNumbers() {
        boolean show = view.getShowNumbersCheckBox().isSelected();
        view.getPuzzleGrid().getChildren().forEach(node -> {
            if (node instanceof TileView) {
                ((TileView) node).setShowNumber(show);
            }
        });
    }

    private void checkWin() {
        if (board.isSolved()) {
            gameActive = false;
            timer.stop();
            logger.info("Game won! Moves: " + moves + ", Time: " + seconds + "s");
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Congratulations!");
                alert.setHeaderText("You solved the puzzle!");
                alert.setContentText("Moves: " + moves + "\nTime: " + view.getTimerLabel().getText().substring(6));
                alert.showAndWait();
            });
        }
    }
}
