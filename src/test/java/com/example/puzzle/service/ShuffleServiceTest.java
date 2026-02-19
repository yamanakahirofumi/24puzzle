package com.example.puzzle.service;

import com.example.puzzle.model.PuzzleBoard;
import com.example.puzzle.model.Tile;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ShuffleServiceTest {
    @Test
    void testShuffle() {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            tiles.add(new Tile(i, null, null));
        }
        PuzzleBoard board = new PuzzleBoard(tiles);
        ShuffleService shuffleService = new ShuffleService();

        // Shuffle with enough moves to likely change the board
        shuffleService.shuffle(board, 200);

        assertFalse(board.isSolved(), "Board should not be in solved state after shuffle");
    }
}
