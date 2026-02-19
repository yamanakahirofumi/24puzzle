package com.example.puzzle.service;

import com.example.puzzle.model.PuzzleBoard;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service for shuffling the puzzle board while ensuring solvability.
 */
public class ShuffleService {
    private final Random random = new Random();

    /**
     * Shuffles the board using a random walk from the goal state.
     * @param board The board to shuffle.
     * @param moves The number of random moves to perform.
     */
    public void shuffle(PuzzleBoard board, int moves) {
        board.reset();
        int count = 0;
        int lastPos = -1;
        while (count < moves) {
            int emptyPos = board.getEmptyPosition();
            List<Integer> candidates = getAdjacentPositions(emptyPos);

            // Randomly pick a candidate
            int nextPos = candidates.get(random.nextInt(candidates.size()));

            // Try to avoid immediate reversal
            if (nextPos != lastPos || candidates.size() == 1) {
                if (board.move(nextPos)) {
                    lastPos = emptyPos;
                    count++;
                }
            }
        }
    }

    private List<Integer> getAdjacentPositions(int pos) {
        int size = PuzzleBoard.SIZE;
        int r = pos / size;
        int c = pos % size;
        List<Integer> list = new ArrayList<>();
        if (r > 0) list.add(pos - size);
        if (r < size - 1) list.add(pos + size);
        if (c > 0) list.add(pos - 1);
        if (c < size - 1) list.add(pos + 1);
        return list;
    }
}
