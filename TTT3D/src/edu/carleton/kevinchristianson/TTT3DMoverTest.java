package edu.carleton.kevinchristianson;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
* @author kevinchristianson and Isaac Haseley
*/
class TTT3DMoverTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    /**
     * creates boards with the following criteria and checks that the winningMoves method returns correct options:
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
        assertEquals(new ArrayList<TTT3DMove>(), player.winningMoves(empty));

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
}