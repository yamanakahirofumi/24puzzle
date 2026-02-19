package com.example.puzzle.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * Represents a single tile in the puzzle.
 *
 * @param originalIndex The original position (1-based, 1-25). 25 represents the empty space.
 * @param image The image for this tile (can be the full image if viewport is used).
 * @param viewport The area of the image to display.
 */
public record Tile(int originalIndex, Image image, Rectangle2D viewport) {
    public boolean isEmpty() {
        return originalIndex == 25;
    }
}
