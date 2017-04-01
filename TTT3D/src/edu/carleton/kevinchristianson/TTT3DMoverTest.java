package edu.carleton.kevinchristianson;

import static org.junit.jupiter.api.Assertions.*;

/**
<<<<<<< HEAD
 * Created by kevinchristianson on 3/30/17.
 * @author kevinchristianson and Isaac Haseley
=======
 * @author Kevin Christianson & Isaac Haseley
>>>>>>> origin/master
 */
class TTT3DMoverTest {
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }
    /**
     creates boards with the following criteria and checks that the winningMoves method returns correct options:
     win by 2D horizontal, 2D vertical, 2D diagonal, 3D vertical, 3D diagonal, only one option, multiple options, intersecting options
     */
    @org.junit.jupiter.api.Test
    void winningMoves() {
        TTT3DBoard empty = createBoardstate(new int[] {}, new int[] {});
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

        //boards where there are more than one option available
        TTT3DBoard vertical_diagonal = createBoardstate(new int[] {0, 4, 8, 3, 6, 9}, new int[] {});
        TTT3DBoard vertical_diagonal_blocked = createBoardstate(new int[] {0, 4, 8, 3, 6, 9}, new int[] {12});

        /* Cases:
            Vertical, horizontal, diagonal in 2D
            Vertical, horizontal, diagonal in 3D
            One winning move, completes two different 4-in-a-rows
            Multiple options
        */

        /* TO DO:
        Set whose turn it is
        Consider whether we need board states to be valid/possible
        Add empty board case
        Add full board case
        Move cases to instance variable for class

            In Board:
        Initialize whoseTurn with a constructor argument
        Has somebody won yet?
        What should makeMove do if move is illegal? -> Maybe add another throws
        Adjust documentation
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

    TTT3DBoard createBoardstate(int[] xs, int[] os) {
        TTT3DBoard board = new TTT3DBoard();
        for (int i = 0; i < xs.length; i++) {
            board.makeMove(new TTT3DMove((xs[i]/16), ((xs[i] % 16) / 4),  (xs[i] % 4), 'X'));
        }
        for (int i = 0; i < os.length; i++) {
            board.makeMove(new TTT3DMove((os[i]/16), ((os[i] % 16) / 4),  (os[i] % 4), 'O'));
        }
        return board;
    }
}