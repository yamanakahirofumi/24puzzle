package com.example.puzzle.service;

import com.example.puzzle.model.PuzzleBoard;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service for shuffling the puzzle board using random walk.
 */
public class ShuffleService {
    private static final int SHUFFLE_MOVES = 200;
    private final Random random = new Random();

    /**
     * Shuffles the board by performing random valid moves.
     * Starts from the current state and ensures it remains solvable.
     * @param board the board to shuffle
     */
    public void shuffle(PuzzleBoard board) {
        int movesDone = 0;
        int lastPos = -1;

        while (movesDone < SHUFFLE_MOVES) {
            List<Integer> validMoves = getValidMoves(board);
            // Avoid immediate undo move to make it more random
            if (validMoves.size() > 1 && lastPos != -1) {
                final int finalLastPos = lastPos;
                validMoves.removeIf(pos -> pos == finalLastPos);
            }

            int nextPos = validMoves.get(random.nextInt(validMoves.size()));
            int currentEmpty = board.getEmptyPos();
            if (board.moveTile(nextPos)) {
                lastPos = currentEmpty;
                movesDone++;
            }
        }
    }

    private List<Integer> getValidMoves(PuzzleBoard board) {
        List<Integer> validMoves = new ArrayList<>();
        int emptyPos = board.getEmptyPos();
        int r = emptyPos / PuzzleBoard.SIZE;
        int c = emptyPos % PuzzleBoard.SIZE;

        // Up
        if (r > 0) validMoves.add(emptyPos - PuzzleBoard.SIZE);
        // Down
        if (r < PuzzleBoard.SIZE - 1) validMoves.add(emptyPos + PuzzleBoard.SIZE);
        // Left
        if (c > 0) validMoves.add(emptyPos - 1);
        // Right
        if (c < PuzzleBoard.SIZE - 1) validMoves.add(emptyPos + 1);

        return validMoves;
    }
}
