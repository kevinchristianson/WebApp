package edu.carleton.kevinchristianson;

import static org.junit.jupiter.api.Assertions.*;

/**
* @author kevinchristianson and Isaac Haseley
*/
class TTT3DMoverTest {

    private TTT3DBoard vertical = createBoardstate(new int[] {0, 4, 8}, new int[] {});

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

        TTT3DBoard vertical = createBoardstate(new int[] {0, 4, 8}, new int[] {});
        TTT3DBoard vertical_blocked = createBoardstate(new int[] {0, 4, 8}, new int[] {12});
        TTT3DBoard horizontal = createBoardstate(new int[] {0, 1, 2}, new int[] {});
        TTT3DBoard horizontal_blocked = createBoardstate(new int[] {0, 1, 2}, new int[] {3});
        TTT3DBoard diagonal = createBoardstate(new int[] {0, 5, 10}, new int[] {});
        TTT3DBoard diagonal_blocked = createBoardstate(new int[] {0, 5, 10}, new int[] {15});
        TTT3DBoard vertical3D = createBoardstate(new int[] {0, 16, 32}, new int[] {});
        TTT3DBoard vertical3d_blocked = createBoardstate(new int[] {0, 16, 32}, new int[] {48});
        TTT3DBoard horizontal3D = createBoardstate(new int[] {0, 17, 34}, new int[] {});
        TTT3DBoard horizontal3d_blocked = createBoardstate(new int[] {0, 17, 34}, new int[] {51});
        TTT3DBoard diagonal3D = createBoardstate(new int[] {0, 21, 42}, new int[] {});
        TTT3DBoard diagonal3d_blocked = createBoardstate(new int[] {0, 21, 42}, new int[] {63});

        // Boards where there are more than one option available
        TTT3DBoard vertical_diagonal = createBoardstate(new int[] {0, 4, 8, 3, 6, 9}, new int[] {});
        TTT3DBoard vertical_diagonal_blocked = createBoardstate(new int[] {0, 4, 8, 3, 6, 9}, new int[] {12});

        // Empty and Full boards
        TTT3DBoard empty = createBoardstate(new int[] {}, new int[] {});

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
            board.makeMove(new TTT3DMove((moves[i]/16), ((moves[i] % 16) / 4),  (moves[i] % 4), currentPlayer));
        }
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
        return board;
    }
}