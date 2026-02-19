package com.example.puzzle.view;

import com.example.puzzle.model.Tile;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * View component for a single puzzle tile.
 */
public class TileView extends StackPane {
    private final ImageView imageView = new ImageView();
    private final Label numberLabel = new Label();
    private Tile tile;

    public TileView() {
        getStyleClass().add("tile");
        setAlignment(Pos.CENTER);

        // Default size, can be adjusted
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        numberLabel.getStyleClass().add("tile-number");

        getChildren().addAll(imageView, numberLabel);
    }

    /**
     * Updates the view with the given tile data.
     * @param tile The tile data.
     * @param showNumberOnImage Whether to show the number overlay when an image is present.
     */
    public void setTile(Tile tile, boolean showNumberOnImage) {
        this.tile = tile;
        if (tile == null || tile.isEmpty()) {
            imageView.setImage(null);
            numberLabel.setText("");
            getStyleClass().remove("movable-tile");
            // Empty tile is effectively invisible but occupies space
            imageView.setVisible(false);
            numberLabel.setVisible(false);
        } else {
            imageView.setVisible(true);
            if (tile.image() != null) {
                imageView.setImage(tile.image());
                imageView.setViewport(tile.viewport());
                numberLabel.setText(String.valueOf(tile.originalIndex()));
                numberLabel.getStyleClass().setAll("tile-number", "tile-number-overlay");
                StackPane.setAlignment(numberLabel, Pos.BOTTOM_RIGHT);
                numberLabel.setVisible(showNumberOnImage);
            } else {
                imageView.setImage(null);
                numberLabel.setText(String.valueOf(tile.originalIndex()));
                numberLabel.getStyleClass().setAll("tile-number", "tile-number-default");
                StackPane.setAlignment(numberLabel, Pos.CENTER);
                numberLabel.setVisible(true);
            }
        }
    }

    public Tile getTile() {
        return tile;
    }

    public void setMovable(boolean movable) {
        if (movable) {
            if (!getStyleClass().contains("movable-tile")) {
                getStyleClass().add("movable-tile");
            }
        } else {
            getStyleClass().remove("movable-tile");
        }
    }
}
