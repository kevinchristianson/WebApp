package edu.carleton.kevinchristianson;

import java.util.Arrays;

/**
 * TTT3DBoard represents a simple 4x4x4 3D tic-tac-toe game. Each instance
 * stores includes the contents of each of the 64 squares, plus an indicator
 * of whose turn it is ('X' or 'O').
 *
 * To keep things simple, we use the uppercase letters X and O rather than
 * something more complicated (e.g. an enumerated type) to represent players
 * and their moves. Empty squares are represented using a hyphen.
 *
 * FOR DISCUSSION:
 * (1) How do you feel about my use of this to refer to instance variables?
 * (2) Does it make sense to define BOARD_SIZE and EMPTY_SQUARE instead of
 * just using 4 and '-' throughout the code? If so, then why not also define
 * constants for 'X' and 'O'?
 * (3)
 *
 *
 * @author Jeff Ondich
 * @version 30 March 2017
 */
public class TTT3DBoard {
    public final static Character EMPTY_SQUARE = '-';
    public final static int BOARD_SIZE = 4;

    private Character squareValues[];
    private Character whoseTurn;

    /**
     * Initialize an empty game board.
     */
    public TTT3DBoard() {
        int squareArrayLength = BOARD_SIZE * BOARD_SIZE * BOARD_SIZE;
        this.squareValues = new Character[squareArrayLength];
        for (int k = 0; k < squareArrayLength; k++) {
            this.squareValues[k] = EMPTY_SQUARE;
        }
    }

    /**
     * Copy constructor.
     * @param otherBoard the board to be copied
     */
    public TTT3DBoard(TTT3DBoard otherBoard) {
        int squareArrayLength = BOARD_SIZE * BOARD_SIZE * BOARD_SIZE;
        this.squareValues = new Character[squareArrayLength];
        System.arraycopy(otherBoard.squareValues, 0, this.squareValues, 0, squareArrayLength);
    }

    /**
     * @return 'X' or 'O', depending on whose turn it is
     */
    public Character getWhoseTurn() {
        return this.whoseTurn;
    }

    /**
     * @param level the level of the board position
     * @param row the row of the board position
     * @param column the column of the board position
     * @return the value ('X', 'O', or EMPTY_SQUARE) located at the specified
     * position on the game board
     */
    public Character valueInSquare(int level, int row, int column) {
        if (level < 0 || level >= BOARD_SIZE) {
            throw new IllegalArgumentException("Illegal level number"  + level);
        }
        if (row < 0 || row >= BOARD_SIZE) {
            throw new IllegalArgumentException("Illegal row number"  + row);
        }
        if (column < 0 || column >= BOARD_SIZE) {
            throw new IllegalArgumentException("Illegal column number"  + column);
        }
        return this.squareValues[indexForPosition(level, row, column)];
    }

    /**
     * Apply the specified move to this game board.
     * @param move the move to be made
     * @throws IllegalArgumentException if the move position is out of bounds or
     * if it's not currently the move's player's turn
     */
    public void makeMove(TTT3DMove move) {
        if (move.row < 0 || move.row >= BOARD_SIZE) {
            throw new IllegalArgumentException("Illegal row number"  + move.row);
        }
        if (move.column < 0 || move.column >= BOARD_SIZE) {
            throw new IllegalArgumentException("Illegal column number"  + move.column);
        }
        if (move.level < 0 || move.level >= BOARD_SIZE) {
            throw new IllegalArgumentException("Illegal level number"  + move.level);
        }
        if (move.player != this.whoseTurn) {
            throw new IllegalArgumentException("Its not " + move.player + "s turn");
        }

        this.squareValues[indexForPosition(move.row, move.column, move.level)] = this.whoseTurn;
        this.whoseTurn = (this.whoseTurn == 'X' ? 'O' : 'X');
    }

    /**
     * @param level the level of the board position
     * @param row the row of the board position
     * @param column the column of the board position
     * @return the index to be used in the linear array squareValues to represent the
     * 3D position (level, row, column). We're storing the squares in level-major and
     * then row-major order.
     */
    private int indexForPosition(int level, int row, int column) {
        return BOARD_SIZE * BOARD_SIZE * level + BOARD_SIZE * row + column;
    }
}
