package edu.carleton.kevinchristianson;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kevinchristianson on 3/30/17.
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
        Character[] array0 = new Character[64];
        TTT3DBoard vertical = createBoardstate(array0);

        /* Cases:
            Vertical, horizontal, diagonal in 2D
            Vertical, horizontal, diagonal in 3D
            One winning move, completes two different 3-in-a-rows
            Multiple options
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

    TTT3DBoard createBoardstate(Character[] input){
        TTT3DBoard board = new TTT3DBoard();
        for(int i = 0; i < input.length; i++){
            if(input[i] == 'X' || input[i] == 'O') board.makeMove(new TTT3DMove(((int)i/16), ((int)i/4),(i % 4), input[i]));
        }
        return board;
    }
}