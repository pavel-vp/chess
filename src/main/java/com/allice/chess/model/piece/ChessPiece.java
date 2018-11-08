package com.allice.chess.model.piece;

import com.allice.chess.model.ChessCell;
import com.allice.chess.model.Color;

public abstract class ChessPiece {
    protected Color color;

    public ChessPiece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract boolean canGoTo(ChessCell cellFrom, ChessCell cellTo);

    protected boolean moveInBorder(ChessCell cellFrom, int[] move) {
        int nextX = cellFrom.getX() + move[0];
        int nextY = cellFrom.getY() + move[1];
        return nextX >= 0 && nextX <= 7 &&
                nextY >= 0 && nextY <= 7;
    }

    public abstract int[][] getCoordsMustBeEmptyWithMove(ChessCell cellFrom, ChessCell cellTo);

    public abstract String getAsChar();
}
