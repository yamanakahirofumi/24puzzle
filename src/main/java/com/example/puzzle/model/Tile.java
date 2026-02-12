package com.example.puzzle.model;

import javafx.scene.image.Image;

/**
 * Represents a single tile in the puzzle.
 */
public class Tile {
    private final int originalIndex;
    private Image imageFragment;

    public Tile(int originalIndex) {
        this.originalIndex = originalIndex;
    }

    public int getOriginalIndex() {
        return originalIndex;
    }

    public Image getImageFragment() {
        return imageFragment;
    }

    public void setImageFragment(Image imageFragment) {
        this.imageFragment = imageFragment;
    }

    /**
     * Returns the number to be displayed on the tile (1-based).
     * For the empty tile, it returns the total number of tiles (25).
     */
    public int getDisplayNumber() {
        return originalIndex + 1;
    }
}
