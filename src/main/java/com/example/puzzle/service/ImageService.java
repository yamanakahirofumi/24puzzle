package com.example.puzzle.service;

import com.example.puzzle.model.PuzzleBoard;
import com.example.puzzle.model.Tile;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling image processing and tile generation.
 */
public class ImageService {

    /**
     * Creates tiles for a standard number puzzle (no image).
     */
    public List<Tile> createNumberTiles() {
        List<Tile> tiles = new ArrayList<>();
        int total = PuzzleBoard.SIZE * PuzzleBoard.SIZE;
        for (int i = 1; i <= total; i++) {
            tiles.add(new Tile(i, null, null));
        }
        return tiles;
    }

    /**
     * Creates tiles from an image by defining viewports for each tile.
     * This approach preserves animations in GIFs.
     * @param originalImage The image to split.
     * @return A list of 25 tiles.
     */
    public List<Tile> createTilesFromImage(Image originalImage) {
        double w = originalImage.getWidth();
        double h = originalImage.getHeight();
        double s = Math.min(w, h);
        double offsetX = (w - s) / 2.0;
        double offsetY = (h - s) / 2.0;

        List<Tile> tiles = new ArrayList<>();
        int size = PuzzleBoard.SIZE;
        double tileSize = s / size;

        for (int i = 0; i < size * size; i++) {
            int r = i / size;
            int c = i % size;
            if (i == size * size - 1) {
                // The 25th tile is the empty space.
                tiles.add(new Tile(i + 1, null, null));
            } else {
                // Define the viewport for this tile fragment.
                Rectangle2D viewport = new Rectangle2D(
                    offsetX + c * tileSize,
                    offsetY + r * tileSize,
                    tileSize, tileSize);
                tiles.add(new Tile(i + 1, originalImage, viewport));
            }
        }
        return tiles;
    }
}
