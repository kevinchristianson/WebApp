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
     * win by 2D horizontal, 2D vertical, 2D diagonal, 3D vertical, 3D diagonal, blocked versions of these,
     * multiple options, intersecting options, empty board, full board
     */
    @org.junit.jupiter.api.Test
    void winningMoves() {
        winningAndBlockingTester('X');
    }

    @org.junit.jupiter.api.Test
    void blockingMoves() {
        winningAndBlockingTester('O');
    }

    @org.junit.jupiter.api.Test
    void forcingMoves() {
    }

    @org.junit.jupiter.api.Test
    void bestMove() {
    }


    void winningAndBlockingTester(char curPlayer){
        TTT3DBoard empty = createBoardstate(new int[] {});
        TTT3DBoard vertical = createBoardstate(new int[] {0, 1, 4, 2, 8, 3});
        TTT3DBoard vertical_blocked = createBoardstate(new int[] {0, 2, 4, 6, 8, 12});
        TTT3DBoard horizontal = createBoardstate(new int[] {0, 4, 1, 5, 2, 6});
        TTT3DBoard horizontal_blocked = createBoardstate(new int[] {0, 4, 1, 5, 2, 3});
        TTT3DBoard diagonal = createBoardstate(new int[] {0, 1, 5, 2, 10, 3});
        TTT3DBoard diagonal_blocked = createBoardstate(new int[] {0, 1, 5, 2, 10, 15});
        TTT3DBoard vertical3D = createBoardstate(new int[] {0, 1, 16, 2, 32,3 });
        TTT3DBoard vertical3D_blocked = createBoardstate(new int[] {0, 1, 16, 2, 32, 48});
        TTT3DBoard horizontal3D = createBoardstate(new int[] {0, 1, 17, 2, 34, 3});
        TTT3DBoard horizontal3D_blocked = createBoardstate(new int[] {0, 1, 17, 2, 34, 51});
        TTT3DBoard diagonal3D = createBoardstate(new int[] {0, 1, 21, 2, 42, 3});
        TTT3DBoard diagonal3D_blocked = createBoardstate(new int[] {0, 1, 21, 2, 42, 63});

        // Intersecting options, blocked version
        TTT3DBoard vertical_diagonal = createBoardstate(new int[] {0, 10, 4, 11, 8, 14, 3, 15, 6, 1, 9, 2});
        TTT3DBoard vertical_diagonal_blocked = createBoardstate(new int[] {0, 10, 4, 11, 8, 14, 3, 15, 6, 1, 9, 12});

        //create instance of TTT3DMover
        TTT3DMover player = new TTT3DMover();

        // test empty board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), player.winningMoves(empty)), true);

        // test vertical board
        ArrayList<TTT3DMove> expected = new ArrayList<>();
        expected.add(new TTT3DMove(0,3,0,curPlayer));
        assertEquals(movesAreEqual(expected, player.winningMoves(vertical)), true);

        //test blocked vertical board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), player.winningMoves(vertical_blocked)), true);

        //test horizontal board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(0, 0, 3, curPlayer));
        assertEquals(movesAreEqual(expected, player.winningMoves(horizontal)), true);

        //test blocked horizontal board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), player.winningMoves(horizontal_blocked)), true);

        //test diagonal board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(0, 3, 3, curPlayer));
        assertEquals(movesAreEqual(expected, player.winningMoves(diagonal)), true);

        //test blocked diagonal board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), player.winningMoves(diagonal_blocked)), true);

        //test 3D vertical board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(3, 0, 0, curPlayer));
        assertEquals(movesAreEqual(expected, player.winningMoves(vertical3D)), true);

        //test blocked 3D vertical board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), player.winningMoves(vertical3D_blocked)), true);

        //test 3D horizontal board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(3, 0, 3, curPlayer));
        assertEquals(movesAreEqual(expected, player.winningMoves(horizontal3D)), true);

        //test blocked 3D horizontal board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), player.winningMoves(horizontal3D_blocked)), true);

        //test 3D diagonal board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(3, 3, 3, curPlayer));
        assertEquals(movesAreEqual(expected, player.winningMoves(diagonal3D)), true);

        //test blocked 3D diagonal board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), player.winningMoves(diagonal3D_blocked)), true);

        //test vertical and diagonal board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(0, 3, 0, curPlayer));
        assertEquals(movesAreEqual(expected, player.winningMoves(vertical_diagonal)), true);


        //test both vertical and diagonal blocked board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), player.winningMoves(vertical_diagonal_blocked)), true);

        /*
        Boards for:
            A move or two total
            Multiple winning moves
            Full board
         */
    }

    /**
     * @param moves a list of coordinates of moves to be made for alternating players
     * @return The completed board
     */
    TTT3DBoard createBoardstate(int[] moves) {
        TTT3DBoard board = new TTT3DBoard();
        char currentPlayer = 'X';
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


    /**
     * @param moves1 a list of moves
     * @param moves2 a list of moves
     * @return true if the lists of moves are equal, false otherwise
     */
    boolean movesAreEqual(List<TTT3DMove> moves1, List<TTT3DMove> moves2) {
        if (moves1.size() != moves2.size()) {
            return false;
        }
        for (int i = 0; i < moves1.size(); i++) {
            boolean foundMatch = false;
            for (int j = 0; j < moves2.size(); j++) {
                if(moves1.get(i).level == moves2.get(j).level && moves1.get(i).row == moves2.get(j).row &&
                        moves1.get(i).column == moves2.get(j).column && moves1.get(i).player == moves2.get(j).player){
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