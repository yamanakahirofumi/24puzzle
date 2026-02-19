package com.example.puzzle.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PuzzleBoardTest {
    private PuzzleBoard board;

    @BeforeEach
    void setUp() {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            tiles.add(new Tile(i, null, null));
        }
        board = new PuzzleBoard(tiles);
    }

    @Test
    void testInitialState() {
        assertTrue(board.isSolved());
        assertEquals(24, board.getEmptyPosition());
    }

    @Test
    void testMove() {
        // Empty space at 24 (index of tile 25). Adjacent: 19 (above), 23 (left)
        assertTrue(board.move(23));
        assertEquals(23, board.getEmptyPosition());
        assertFalse(board.isSolved());

        assertTrue(board.move(24));
        assertEquals(24, board.getEmptyPosition());
        assertTrue(board.isSolved());
    }

    @Test
    void testInvalidMove() {
        assertFalse(board.move(0)); // Not adjacent to 24
        assertEquals(24, board.getEmptyPosition());
    }

    @Test
    void testReset() {
        board.move(23);
        assertFalse(board.isSolved());
        board.reset();
        assertTrue(board.isSolved());
    }
}
