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

    Character playerChar;
    /**
     * Because we currently have no implementation of TTT3DMover, a default
     * constructor should suffice.
     */
    public TTT3DMover() {
    }

    public static void main (String[] args) throws IOException, InvalidArgumentException {
        TTT3DMover player = new TTT3DMover();
        TTT3DBoard board = new TTT3DBoard();
        board.loadFromFile(args[1]);
        player.playerChar = board.getWhoseTurn();
        String method = args[0];
        if(method.contains("win")){
            printResult(board, player.winningMoves(board));
        }else if(method.contains("block")){
            printResult(board, player.blockingMoves(board));
        }else if(method.contains("force")) {
            printResult(board, player.forcingMoves(board));
        }else{
            throw new InvalidArgumentException(args);
        }
    }

    private static void printResult(TTT3DBoard board, List<TTT3DMove> moves){
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
        return new ArrayList<TTT3DMove>();
    }
    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return a (possibly empty) list of moves that the non-current player could take
     * to win the game in a single turn. That is, these are positions where the current
     * player should play to avoid losing on the opponent's next turn.
     */
    public List<TTT3DMove> blockingMoves(TTT3DBoard board) {
        return new ArrayList<TTT3DMove>();
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

        /*
        For each empty square:
            Create a board with that square filled
            Check if there are 2+ winning moves
        */

        return new ArrayList<TTT3DMove>();
    }

    /**
     * @param board a 3D tic-tac-toe board, including existing X and O positions
     *              as well as a marker for whose turn comes next
     * @return the move that this object determines would be the best choice for the
     * board's current player.
     */
    public TTT3DMove bestMove(TTT3DBoard board) {
        return new TTT3DMove(0, 0, 0, board.getWhoseTurn());
    }
}