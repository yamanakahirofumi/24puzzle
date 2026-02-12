package com.example.puzzle.model;

import javafx.scene.image.Image;
import java.util.Arrays;

/**
 * Core logic for the 5x5 puzzle board.
 */
public class PuzzleBoard {
    public static final int SIZE = 5;
    public static final int TOTAL_TILES = SIZE * SIZE;
    public static final int EMPTY_TILE_INDEX = TOTAL_TILES - 1;

    private final Tile[] tiles;
    private int emptyPos;

    public PuzzleBoard() {
        this.tiles = new Tile[TOTAL_TILES];
        for (int i = 0; i < TOTAL_TILES; i++) {
            tiles[i] = new Tile(i);
        }
        this.emptyPos = EMPTY_TILE_INDEX;
    }

    public int getEmptyPos() {
        return emptyPos;
    }

    public Tile getTile(int pos) {
        return tiles[pos];
    }

    /**
     * Tries to move a tile at the given position to the empty position.
     * @param pos the position of the tile to move
     * @return true if the move was successful, false otherwise
     */
    public boolean moveTile(int pos) {
        if (isAdjacent(pos, emptyPos)) {
            swap(pos, emptyPos);
            emptyPos = pos;
            return true;
        }
        return false;
    }

    private boolean isAdjacent(int pos1, int pos2) {
        int r1 = pos1 / SIZE;
        int c1 = pos1 % SIZE;
        int r2 = pos2 / SIZE;
        int c2 = pos2 % SIZE;

        return (Math.abs(r1 - r2) == 1 && c1 == c2) || (r1 == r2 && Math.abs(c1 - c2) == 1);
    }

    private void swap(int i, int j) {
        Tile temp = tiles[i];
        tiles[i] = tiles[j];
        tiles[j] = temp;
    }

    /**
     * Checks if the board is in the goal state.
     */
    public boolean isSolved() {
        for (int i = 0; i < TOTAL_TILES; i++) {
            if (tiles[i].getOriginalIndex() != i) {
                return false;
            }
        }
        return true;
    }

    /**
     * Resets the board to the goal state.
     */
    public void reset() {
        for (int i = 0; i < TOTAL_TILES; i++) {
            tiles[i] = new Tile(i);
        }
        this.emptyPos = EMPTY_TILE_INDEX;
    }

    /**
     * Sets the image fragment for a tile.
     */
    public void setTileImage(int originalIndex, Image fragment) {
        for (Tile tile : tiles) {
            if (tile.getOriginalIndex() == originalIndex) {
                tile.setImageFragment(fragment);
                break;
            }
        }
    }
}
