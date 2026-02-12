package com.example.puzzle;

import com.example.puzzle.model.PuzzleBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleBoardTest {
    private PuzzleBoard board;

    @BeforeEach
    void setUp() {
        board = new PuzzleBoard();
    }

    @Test
    @DisplayName("Initial board should be solved")
    void isSolved_InitialState_ShouldBeTrue() {
        assertTrue(board.isSolved());
    }

    @Test
    @DisplayName("Moving an adjacent tile should update the board")
    void moveTile_AdjacentTile_ShouldMove() {
        // Empty tile is at index 24 (4,4). Adjacent is 23 (4,3) or 19 (3,4).
        int emptyPos = board.getEmptyPos();
        assertEquals(24, emptyPos);

        int targetPos = 23;
        int originalIndexOfTarget = board.getTile(targetPos).getOriginalIndex();

        assertTrue(board.moveTile(targetPos));
        assertEquals(targetPos, board.getEmptyPos());
        assertEquals(originalIndexOfTarget, board.getTile(24).getOriginalIndex());
        assertFalse(board.isSolved());
    }

    @Test
    @DisplayName("Moving a non-adjacent tile should fail")
    void moveTile_NonAdjacentTile_ShouldNotMove() {
        assertFalse(board.moveTile(0));
        assertEquals(24, board.getEmptyPos());
        assertTrue(board.isSolved());
    }

    @Test
    @DisplayName("Resetting the board should return it to solved state")
    void reset_AfterMoves_ShouldBeSolved() {
        board.moveTile(23);
        assertFalse(board.isSolved());

        board.reset();
        assertTrue(board.isSolved());
        assertEquals(24, board.getEmptyPos());
    }
}
