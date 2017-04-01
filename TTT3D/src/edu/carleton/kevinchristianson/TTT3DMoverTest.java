package edu.carleton.kevinchristianson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
* @author Kevin Christianson and Isaac Haseley
*/
class TTT3DMoverTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    /**
     * Creates boards with the following criteria and checks that winningMoves returns correct options:
     * win by 2D horizontal, 2D vertical, 2D diagonal, 3D vertical, 3D diagonal, only one option, multiple options,
     * intersecting options, empty board, full board, no winning moves
     */
    @org.junit.jupiter.api.Test
    void winningMoves() {
        char startPlayer = 'X';
        TTT3DBoard empty = createBoardstate(new int[] {}, startPlayer);
        TTT3DBoard vertical = createBoardstate(new int[] {0, 4, 8}, startPlayer);
        TTT3DBoard vertical_blocked = createBoardstate(new int[] {0, 2, 4, 6, 8, 12},  startPlayer);
        TTT3DBoard horizontal = createBoardstate(new int[] {0, 4, 1, 5, 2, 6}, startPlayer);
        TTT3DBoard horizontal_blocked = createBoardstate(new int[] {0, 4, 1, 5, 2, 3}, startPlayer);
        TTT3DBoard diagonal = createBoardstate(new int[] {0, 1, 5, 2, 10, 3}, startPlayer);
        TTT3DBoard diagonal_blocked = createBoardstate(new int[] {0, 1, 5, 2, 10, 15}, startPlayer);
        TTT3DBoard vertical3D = createBoardstate(new int[] {0, 1, 16, 2, 32,3 }, startPlayer);
        TTT3DBoard vertical3d_blocked = createBoardstate(new int[] {0, 1, 16, 2, 32, 48}, startPlayer);
        TTT3DBoard horizontal3D = createBoardstate(new int[] {0, 1, 17, 2, 34, 3}, startPlayer);
        TTT3DBoard horizontal3d_blocked = createBoardstate(new int[] {0, 1, 17, 2, 34, 51}, startPlayer);
        TTT3DBoard diagonal3D = createBoardstate(new int[] {0, 1, 21, 2, 42, 3}, startPlayer);
        TTT3DBoard diagonal3d_blocked = createBoardstate(new int[] {0, 1, 21, 2, 42, 63}, startPlayer);

        //boards where there are more than one option available
        TTT3DBoard vertical_diagonal = createBoardstate(new int[] {0, 10, 4, 11, 8, 14, 3, 15, 6, 1, 9, 2}, startPlayer);
        TTT3DBoard vertical_diagonal_blocked = createBoardstate(new int[] {0, 10, 4, 11, 8, 14, 3, 15, 6, 1, 9, 12}, startPlayer);

        TTT3DMover player = new TTT3DMover();
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), player.winningMoves(empty)), true);
        ArrayList<TTT3DMove> expected = new ArrayList<>();
        expected.add(new TTT3DMove(0,3,0,'X'));
        assertEquals(movesAreEqual(expected, player.winningMoves(vertical)), true);
        /* TO DO:
        Set whose turn it is
        Consider whether we need board states to be valid/possible
        Add empty board case
        Add full board case
        Move cases to instance variable for class

         */
    }

    @org.junit.jupiter.api.Test
    void blockingMoves() {
        /*
        Same as winningMoves
        */
    }

    @org.junit.jupiter.api.Test
    void forcingMoves() {
    }

    @org.junit.jupiter.api.Test
    void bestMove() {
    }

    /**
     @param moves a list of coordinates of moves to be made in order
     @param firstPlayer character of which player moves first

     Creates a TTT3DBoard object with specified firstPlayer starting, and iterates through the passed in array making
     all specified moves in order, switching off between X and Yc

     @return TTT3DBoard returns the completed board of the moves made
    */
    TTT3DBoard createBoardstate(int[] moves, char firstPlayer) {
        TTT3DBoard board = new TTT3DBoard();
        char currentPlayer = firstPlayer;
        for (int i = 0; i < moves.length; i++) {
            board.makeMove(new TTT3DMove((moves[i] / 16), ((moves[i] % 16) / 4), (moves[i] % 4), currentPlayer));
            if (currentPlayer == 'X') {
                currentPlayer = 'O';
            } else {
                currentPlayer = 'X';
            }
        }
        return board;
    }

    boolean movesAreEqual(List<TTT3DMove> moves1, List<TTT3DMove> moves2) {
        if (moves1.size() != moves2.size()) {
            return false;
        }
        for (int i = 0; i < moves1.size(); i++) {
            int level = moves1.get(i).level;
            int row = moves1.get(i).row;
            int col = moves1.get(i).column;
            char player = moves1.get(i).player;
            boolean foundMatch = false;
            for (int j = 0; j < moves2.size(); j++) {
                int level2 = moves2.get(j).level;
                int row2 = moves2.get(j).row;
                int col2 = moves2.get(j).column;
                char player2 = moves2.get(j).player;
                if (level == level2 && row == row2 && col == col2 && player == player2) {
                    foundMatch = true;
                }
            }
            if (!foundMatch) {
                return false;
            }
        }
        return true;
    }
}