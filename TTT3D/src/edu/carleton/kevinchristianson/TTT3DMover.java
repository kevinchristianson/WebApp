package edu.carleton.kevinchristianson;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

/**
 * TTT3DMover's job is to analyze a TTT3DBoard and make choices about what move
 * a player should make next. A TT3DMover object could be used as a key component
 * of the "AI" player in a full-blown 3D tic-tac-toe application.
 *
 * In phase 3 of this assignment, you'll implement the methods stubbed and
 * documented below. In phase 2 (which comes before phase 3, as you might guess),
 * you'll create a JUnit TTT3DMoverTest class with a suitably rich collection of
 * unit tests for the public methods below.
 *
 * @author Jeff Ondich, Kevin Christianson, Isaac Haseley
 * @version 30 March 2017
 */
public class TTT3DMover {

    private Character playerChar;
    /**
     * Because we currently have no implementation of TTT3DMover, a default
     * constructor should suffice.
     */
    public TTT3DMover(Character playerChar) {
        this.playerChar = playerChar;
    }

    public static void main (String[] args) throws IOException, InvalidArgumentException {
        TTT3DBoard board = new TTT3DBoard();
        board.loadFromFile(args[1]);
        TTT3DMover player = new TTT3DMover(board.getWhoseTurn());
        String method = args[0];
        if (method.contains("win")) {
            printResult(board, player.winningMoves(board));
        } else if (method.contains("block")) {
            printResult(board, player.blockingMoves(board));
        } else if (method.contains("force")) {
            printResult(board, player.forcingMoves(board));
        } else {
            throw new InvalidArgumentException(args);
        }
    }

    private static void printResult(TTT3DBoard board, List<TTT3DMove> moves) {
        Character[] squareValues = board.getSquareValues();
        for (TTT3DMove move : moves) {
            int index = 16 * move.level + 4 * move.row + move.column;
            squareValues[index] = '*';
        }
        for (int row = 0; row < 4; row++) {
            for (int level = 0; level < 4; level++) {
                for (int column = 0; column < 4; column++) {
                    System.out.print(squareValues[16 * level + 4 * row + column]);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return a (possibly empty) list of moves that the current player can take
     * to win the game in a single turn.
     */
    public List<TTT3DMove> winningMoves(TTT3DBoard board) {
        int[][] winConditions =
            // 2D Verticals
            {{0, 4, 8, 12}, {1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {16, 20, 24, 28},
            {17, 21, 25, 29}, {18, 22, 26, 30}, {19, 23, 27, 31}, {32, 36, 40, 44}, {33, 37, 41, 45},
            {34, 38, 42, 46}, {35, 39, 43, 47}, {48, 52, 56, 60}, {49, 53, 57, 61}, {50, 54, 58, 62},
            {51, 55, 59, 63},
            // 2D Horizontals
            {0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}, {16, 17, 18, 19},
            {20, 21, 22, 23}, {24, 25, 26, 27}, {28, 29, 30, 31}, {32, 33, 34, 35}, {36, 37, 38, 39},
            {40, 41, 42, 43}, {44, 45, 46, 47}, {48, 49, 50, 51}, {52, 53, 54, 55}, {56, 57, 58, 59},
            {60, 61, 62, 63},
            // 2D Diagonals
            {0, 5, 10, 15}, {3, 6, 9, 12}, {16, 21, 26, 31}, {19, 22, 25, 28}, {32, 37, 42, 47},
            {35, 38, 41, 44}, {48, 53, 58, 63}, {51, 54, 57, 60},
            // 3D Verticals
            {0, 16, 32, 48}, {1, 17, 33, 49}, {2, 18, 34, 50}, {3, 19, 35, 51}, {4, 20, 36, 52},
            {5, 21, 37, 53}, {6, 22, 38, 54}, {7, 23, 39, 55}, {8, 24, 40, 56}, {9, 25, 41, 57},
            {10, 26, 42, 58}, {11, 27, 43, 59}, {12, 28, 44, 60}, {13, 29, 45, 61}, {14, 30, 46, 62},
            {15, 31, 47, 63},
            // 3D Short Diagonals
            {0, 17, 34, 51}, {4, 21, 38, 55}, {8, 25, 42, 59}, {12, 29, 46, 63},
            {0, 20, 40, 60}, {1, 21, 41, 61}, {2, 22, 42, 62}, {3, 23, 43, 63}, {12, 24, 36, 48},
            {13, 25, 37, 49}, {14, 26, 38, 50}, {15, 27, 39, 51}, {15, 30, 45, 60}, {11, 26, 41, 56},
            {7, 22, 37, 52}, {3, 18, 33, 48},
            // 3D Long Diagonals
            {0, 21, 42, 63}, {3, 22, 41, 60}, {12, 25, 38, 51}, {15, 26, 37, 48}};
        List<TTT3DMove> results = new ArrayList<>();
        for (int[] streak : winConditions) {
            int moveCount = 0;
            boolean blocked = false;
            for (int index : streak) {
                if (board.getSquareValue(index).equals(this.playerChar)) {
                    moveCount++;
                }
                else if (!board.getSquareValue(index).equals('-')) {
                    blocked = true;
                }
            }
            if (moveCount == 3 && !blocked) {
                int winLoc = streak[0];
                for (int index : streak) {
                    if (board.getSquareValue(index).equals('-')) {
                        winLoc = index;
                    }
                }
                TTT3DMove winningMove = new TTT3DMove(winLoc / 16, (winLoc % 16) / 4, winLoc % 4, this.playerChar);
                results.add(winningMove);
            }
        }
        return results;
    }

    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return a (possibly empty) list of moves that the non-current player could take
     * to win the game in a single turn. That is, these are positions where the current
     * player should play to avoid losing on the opponent's next turn.
     */
    public List<TTT3DMove> blockingMoves(TTT3DBoard board) {
        this.playerChar = charSwitch(this.playerChar);
        List<TTT3DMove> results = winningMoves(board);
        this.playerChar = charSwitch(this.playerChar);
        for (TTT3DMove move : results){
            move.player = charSwitch(move.player);
        }
        return results;
    }

    private char charSwitch(char player){
        if (player == 'X') {
            player = 'O';
        }
        else {
            player = 'X';
        }
        return player;
    }

    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return a (possibly empty) list of moves that the current player could take
     * to force a win. A move is "forcing" if it results in at least two different
     * ways for the current player to win on the next move. In other words, after a
     * forcing move, the opponent will be forced to make two different blocking moves
     * in a single turn to avoid losing.
     */
    public List<TTT3DMove> forcingMoves(TTT3DBoard board) {
        List<TTT3DMove> results = new ArrayList<>();
        Character[] moves = board.getSquareValues();
        TTT3DBoard tempBoard;
        for (int i = 0; i < 64; i++) {
            if (moves[i].equals('-')) {
                tempBoard = new TTT3DBoard(board);
                tempBoard.makeMove(new TTT3DMove((i / 16), ((i % 16) / 4), (i % 4), this.playerChar));
                if (winningMoves(tempBoard).size() > 1) {
                    results.add(new TTT3DMove((i / 16), ((i % 16) / 4), (i % 4), this.playerChar));
                }
            }
        }
        return results;
    }

    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return the move that this object determines would be the best choice for the
     * board's current player.
     */
    public TTT3DMove bestMove(TTT3DBoard board) {
        if(winningMoves(board).size() > 0) return winningMoves(board).get(0);
        else if (blockingMoves(board).size() > 0) return blockingMoves(board).get(0);
        else if (forcingMoves(board).size() > 0) return forcingMoves(board).get(0);
        else{
            Character[] moves = board.getSquareValues();
            for (int i = 0; i < 64; i++){
                if(moves[i] == '-') return new TTT3DMove((i / 16), ((i % 16) / 4), (i % 4), this.playerChar);
            }
        }
        return new TTT3DMove(0,0,0,'-');
    }
}