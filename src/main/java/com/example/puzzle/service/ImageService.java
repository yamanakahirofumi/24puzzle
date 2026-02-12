package com.example.puzzle.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for processing and splitting images for the puzzle.
 */
public class ImageService {
    private static final int GRID_SIZE = 5;
    private static final int TARGET_SIZE = 500;

    /**
     * Processes and splits an image into 25 fragments.
     * @param image the source image
     * @return a list of 25 image fragments
     */
    public List<Image> splitImage(Image image) {
        double width = image.getWidth();
        double height = image.getHeight();

        double cropX, cropY, cropSize;
        if (width > height) {
            cropX = (width - height) / 2;
            cropY = 0;
            cropSize = height;
        } else {
            cropX = 0;
            cropY = (height - width) / 2;
            cropSize = width;
        }

        // We can use PixelReader to read the cropped area
        PixelReader reader = image.getPixelReader();

        // Divide the cropped area into 5x5 grid
        double tileSize = cropSize / GRID_SIZE;
        List<Image> fragments = new ArrayList<>();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                // For the last tile (empty), we might still want the fragment for when the game is won
                WritableImage fragment = new WritableImage(reader,
                    (int)(cropX + j * tileSize),
                    (int)(cropY + i * tileSize),
                    (int)tileSize,
                    (int)tileSize);
                fragments.add(fragment);
            }
        }

        return fragments;
    }
}
