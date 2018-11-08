package com.allice.chess.model.piece;

import com.allice.chess.model.Color;

public class ChessPieceBishop extends ChessPieceStaright {
    public ChessPieceBishop(Color color) {
        super(color);
        rightMoves = new int[][] {
                {1,1},{1,-1},{-1,-1},{-1,1},
                {2,2},{2,-2},{-2,-2},{-2,2},
                {3,3},{3,-3},{-3,-3},{-3,3},
                {4,4},{4,-4},{-4,-4},{-4,4},
                {5,5},{5,-5},{-5,-5},{-5,5},
                {6,6},{6,-6},{-6,-6},{-6,6},
                {7,7},{7,-7},{-7,-7},{-7,7}
        };

    }

    @Override
    public String getAsChar() {
        return color == Color.White ? "B" : "b";
    }

}
