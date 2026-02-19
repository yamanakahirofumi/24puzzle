package com.example.puzzle.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the state of the 5x5 puzzle board.
 */
public class PuzzleBoard {
    public static final int SIZE = 5;
    private final List<Tile> tiles;

    public PuzzleBoard(List<Tile> tiles) {
        if (tiles.size() != SIZE * SIZE) {
            throw new IllegalArgumentException("Invalid number of tiles: " + tiles.size());
        }
        this.tiles = new ArrayList<>(tiles);
    }

    /**
     * Returns an unmodifiable list of the current tiles.
     * The index in the list represents the current position (0 to 24).
     */
    public List<Tile> getTiles() {
        return Collections.unmodifiableList(tiles);
    }

    /**
     * Gets the current position of the empty space.
     * @return 0-based index of the empty space.
     */
    public int getEmptyPosition() {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).isEmpty()) {
                return i;
            }
        }
        throw new IllegalStateException("Empty space not found");
    }

    /**
     * Moves a tile at the given position if it is adjacent to the empty space.
     * @param position 0-based index of the tile to move.
     * @return true if the tile was moved, false otherwise.
     */
    public boolean move(int position) {
        if (position < 0 || position >= tiles.size()) {
            return false;
        }
        int emptyPos = getEmptyPosition();
        if (isAdjacent(position, emptyPos)) {
            Collections.swap(tiles, position, emptyPos);
            return true;
        }
        return false;
    }

    /**
     * Checks if two positions are adjacent on the 5x5 grid.
     */
    private boolean isAdjacent(int p1, int p2) {
        int r1 = p1 / SIZE;
        int c1 = p1 % SIZE;
        int r2 = p2 / SIZE;
        int c2 = p2 % SIZE;
        return (Math.abs(r1 - r2) == 1 && c1 == c2) || (Math.abs(c1 - c2) == 1 && r1 == r2);
    }

    /**
     * Checks if the puzzle is in its goal state.
     */
    public boolean isSolved() {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).originalIndex() != i + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Resets the board to the goal state.
     * Note: This assumes the original tiles list was in order.
     * Since tiles are records and we keep them, we need to sort them by originalIndex.
     */
    public void reset() {
        tiles.sort((t1, t2) -> Integer.compare(t1.originalIndex(), t2.originalIndex()));
    }
}
