package com.allice.chess.model.piece;

import com.allice.chess.model.Color;

public class ChessPieceRock extends ChessPieceStaright {
    public ChessPieceRock(Color color) {
        super(color);
        rightMoves = new int[][] {
                {1,0},{0,-1},{-1,0},{0,1},
                {2,0},{0,-2},{-2,0},{0,2},
                {3,0},{0,-3},{-3,0},{0,3},
                {4,0},{0,-4},{-4,0},{0,4},
                {5,0},{0,-5},{-5,0},{0,5},
                {6,0},{0,-6},{-6,0},{0,6},
                {7,0},{0,-7},{-7,0},{0,7}
                                };
    }

    @Override
    public String getAsChar() {
        return color == Color.White ? "R" : "r";
    }

}
