package com.example.puzzle.view;

import com.example.puzzle.model.Tile;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Visual representation of a puzzle tile.
 */
public class TileView extends StackPane {
    private Tile tile;
    private final ImageView imageView;
    private final Label numberLabel;

    public TileView(Tile tile, double size) {
        this.tile = tile;
        this.getStyleClass().add("tile");

        this.setPrefSize(size, size);
        this.setMinSize(size, size);
        this.setMaxSize(size, size);

        imageView = new ImageView();
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        numberLabel = new Label(String.valueOf(tile.getDisplayNumber()));
        numberLabel.getStyleClass().add("tile-number");
        StackPane.setAlignment(numberLabel, Pos.BOTTOM_RIGHT);

        this.getChildren().addAll(imageView, numberLabel);
        update();
    }

    public void update() {
        if (tile.getOriginalIndex() == 24) { // Empty tile index
            imageView.setImage(null);
            numberLabel.setVisible(false);
            this.setStyle("-fx-background-color: #333;"); // Default empty color
        } else {
            imageView.setImage(tile.getImageFragment());
            this.setStyle("");
            // Number visibility is controlled by GameController binding
        }
    }

    public void setTile(Tile tile) {
        this.tile = tile;
        update();
    }

    public Tile getTile() {
        return tile;
    }

    public void setShowNumber(boolean show) {
        if (tile.getOriginalIndex() != 24) {
            numberLabel.setVisible(show);
        }
    }
}
