package edu.carleton.kevinchristianson;

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
     * Tests winningMoves method in mover
     */
    @org.junit.jupiter.api.Test
    void winningMoves() {
        winningAndBlockingTester('X');
    }

    /**
     * Tests blockingMoves method in mover
     */
    @org.junit.jupiter.api.Test
    void blockingMoves() {
        winningAndBlockingTester('O');
    }

    /**
     * Tests forcingMoves method in mover
     */
    @org.junit.jupiter.api.Test
    void forcingMoves() {

        // ******BOARDS******

        TTT3DBoard empty = createBoardState(new int[] {});

        // Full board
        int[] full_board = new int[64];
        for (int i = 0; i < 64; i++) {
            full_board[i] = i;
        }
        TTT3DBoard full = createBoardState(full_board);

        // Board representing the early stages of a game
        TTT3DBoard early = createBoardState(new int[] {0, 5, 7, 2});

        
    }

    /**
     * Tests bestMove method in mover
     */
    @org.junit.jupiter.api.Test
    void bestMove() {
    }

    /**
     * @param curPlayer 'X' to test winningMoves, 'O' to test blockingMoves
     * Creates a variety of board states, checks that mover returns the correct options
     */
    void winningAndBlockingTester(char curPlayer) {

        // ******BOARDS******

        TTT3DBoard empty = createBoardState(new int[] {});
        TTT3DBoard vertical = createBoardState(new int[] {0, 1, 4, 2, 8, 3});
        TTT3DBoard vertical_blocked = createBoardState(new int[] {0, 2, 4, 6, 8, 12});
        TTT3DBoard horizontal = createBoardState(new int[] {0, 4, 1, 5, 2, 6});
        TTT3DBoard horizontal_blocked = createBoardState(new int[] {0, 4, 1, 5, 2, 3});
        TTT3DBoard diagonal = createBoardState(new int[] {0, 1, 5, 2, 10, 3});
        TTT3DBoard diagonal_blocked = createBoardState(new int[] {0, 1, 5, 2, 10, 15});
        TTT3DBoard vertical3D = createBoardState(new int[] {0, 1, 16, 2, 32,3 });
        TTT3DBoard vertical3D_blocked = createBoardState(new int[] {0, 1, 16, 2, 32, 48});
        TTT3DBoard horizontal3D = createBoardState(new int[] {0, 1, 17, 2, 34, 3});
        TTT3DBoard horizontal3D_blocked = createBoardState(new int[] {0, 1, 17, 2, 34, 51});
        TTT3DBoard diagonal3D = createBoardState(new int[] {0, 1, 21, 2, 42, 3});
        TTT3DBoard diagonal3D_blocked = createBoardState(new int[] {0, 1, 21, 2, 42, 63});

        // Board representing the early stages of a game
        TTT3DBoard early = createBoardState(new int[] {0, 5, 7, 2});

        // Board with multiple winning options
        TTT3DBoard multiple = createBoardState(new int[] {0, 16, 1, 26, 2, 30, 4, 32, 8, 48, 5, 24, 10, 29});

        // Full board
        int[] full_board = new int[64];
        for (int i = 0; i < 64; i++) {
            full_board[i] = i;
        }
        TTT3DBoard full = createBoardState(full_board);

        // Board with intersecting options, blocked version
        TTT3DBoard vertical_diagonal = createBoardState(new int[] {0, 10, 4, 11, 8, 14, 3, 15, 6, 1, 9, 2});
        TTT3DBoard vertical_diagonal_blocked = createBoardState(new int[] {0, 10, 4, 11, 8, 14, 3, 15, 6, 1, 9, 12});

        // ******TESTS******

        // Create instance of TTT3DMover
        TTT3DMover player = new TTT3DMover();

        // Test empty board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), blockOrWin(player, empty, curPlayer)), true);

        // Test vertical board
        ArrayList<TTT3DMove> expected = new ArrayList<>();
        expected.add(new TTT3DMove(0,3,0, curPlayer));
        assertEquals(movesAreEqual(expected, blockOrWin(player, vertical, curPlayer)), true);

        // Test blocked vertical board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), blockOrWin(player, vertical_blocked, curPlayer)), true);

        // Test horizontal board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(0, 0, 3, curPlayer));
        assertEquals(movesAreEqual(expected, blockOrWin(player, horizontal, curPlayer)), true);

        // Test blocked horizontal board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), blockOrWin(player, horizontal_blocked, curPlayer)), true);

        // Test diagonal board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(0, 3, 3, curPlayer));
        assertEquals(movesAreEqual(expected, blockOrWin(player, diagonal, curPlayer)), true);

        // Test blocked diagonal board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), blockOrWin(player, diagonal_blocked, curPlayer)), true);

        // Test 3D vertical board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(3, 0, 0, curPlayer));
        assertEquals(movesAreEqual(expected, blockOrWin(player, vertical3D, curPlayer)), true);

        // Test blocked 3D vertical board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), blockOrWin(player, vertical3D_blocked, curPlayer)), true);

        // Test 3D horizontal board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(3, 0, 3, curPlayer));
        assertEquals(movesAreEqual(expected, blockOrWin(player, horizontal3D, curPlayer)), true);

        // Test blocked 3D horizontal board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), blockOrWin(player, horizontal3D_blocked, curPlayer)), true);

        // Test 3D diagonal board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(3, 3, 3, curPlayer));
        assertEquals(movesAreEqual(expected, blockOrWin(player, diagonal3D, curPlayer)), true);

        // Test blocked 3D diagonal board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), blockOrWin(player, diagonal3D_blocked, curPlayer)), true);

        // Test vertical and diagonal board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(0, 3, 0, curPlayer));
        assertEquals(movesAreEqual(expected, blockOrWin(player, vertical_diagonal, curPlayer)), true);


        // Test both vertical and diagonal blocked board
        assertEquals(movesAreEqual(new ArrayList<TTT3DMove>(), blockOrWin(player, vertical_diagonal_blocked, curPlayer)), true);

        // Test early board
        assertEquals(movesAreEqual(new ArrayList<>(), blockOrWin(player, early, curPlayer)), true);

        // Test multiple board
        expected = new ArrayList<>();
        expected.add(new TTT3DMove(0, 3, 0, curPlayer));
        expected.add(new TTT3DMove(0, 3, 3, curPlayer));
        expected.add(new TTT3DMove(0, 0, 3, curPlayer));
        assertEquals(movesAreEqual(expected, blockOrWin(player, multiple, curPlayer)), true);

        // Test full board
        assertEquals(movesAreEqual(new ArrayList<>(), blockOrWin(player, full, curPlayer)), true);
    }

    /**
     * Prevents repeat code by allowing us to use the same boards to test both blockingMoves and winningMoves
     * @param player an instance of mover
     * @param board the board state to be tested
     * @param curPlayer the player seeking a move, either 'X' or 'O'
     * @return the list of moves, either blocking or winning, that mover finds
     */
    List<TTT3DMove> blockOrWin(TTT3DMover player, TTT3DBoard board, char curPlayer) {
        if (curPlayer == 'X') {
            return player.winningMoves(board);
        } else {
            return player.blockingMoves(board);
        }
    }

    /**
     * @param moves a list of coordinates of moves to be made for alternating players
     * @return The completed board
     */
    TTT3DBoard createBoardState(int[] moves) {
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
                if (moves1.get(i).level == moves2.get(j).level && moves1.get(i).row == moves2.get(j).row &&
                        moves1.get(i).column == moves2.get(j).column && moves1.get(i).player == moves2.get(j).player) {
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