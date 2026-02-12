package com.example.puzzle;

import com.example.puzzle.model.PuzzleBoard;
import com.example.puzzle.service.ShuffleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShuffleServiceTest {
    @Test
    @DisplayName("Shuffling should change the board state but keep it solvable (implicitly)")
    void shuffle_InitialBoard_ShouldChangeState() {
        PuzzleBoard board = new PuzzleBoard();
        ShuffleService shuffleService = new ShuffleService();

        assertTrue(board.isSolved());
        shuffleService.shuffle(board);

        // It's theoretically possible but extremely unlikely to shuffle back to solved state in 200 moves
        assertFalse(board.isSolved());
    }
}
