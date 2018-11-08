package com.allice.chess;

import com.allice.chess.model.ChessCell;
import com.allice.chess.model.piece.ChessPieceQueen;
import com.allice.chess.model.piece.ChessPieceRock;
import com.allice.chess.model.Color;
import org.junit.Test;

public class ChessPieceMoveTest {

    @Test
    public void move_rock_getcoordsempty_test() {
        // set
        ChessCell cellFrom = new ChessCell( 5,5, Color.Black);
        ChessCell cellTo = new ChessCell( 3,5, Color.Black);
        ChessPieceRock rock = new ChessPieceRock(Color.Black);
        // check
        int[][] coords = rock.getCoordsMustBeEmptyWithMove(cellFrom, cellTo);
        // test
        assert coords.length == 1;
        assert coords[0][0] == 4;
        assert coords[0][1] == 5;
    }

    @Test
    public void move_queen_getcoordsempty_test() {
        // set
        ChessCell cellFrom = new ChessCell( 7,5, Color.Black);
        ChessCell cellTo = new ChessCell( 5,3, Color.Black);
        ChessPieceQueen queen = new ChessPieceQueen(Color.Black);
        // check
        int[][] coords = queen.getCoordsMustBeEmptyWithMove(cellFrom, cellTo);
        // test
        assert coords.length == 1;
        assert coords[0][0] == 6;
        assert coords[0][1] == 4;
    }


}
