package com.allice.chess.model.piece;

import com.allice.chess.model.ChessCell;
import com.allice.chess.model.Color;

public class ChessPieceHorse extends ChessPieceNotPawn {
    public ChessPieceHorse(Color color) {
        super(color);
        rightMoves = new int[][] {{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};
    }

    public int[][] getCoordsMustBeEmptyWithMove(ChessCell cellFrom, ChessCell cellTo) {
        return null;
    }

    @Override
    public String getAsChar() {
        return color == Color.White ? "H" : "h";
    }
}
