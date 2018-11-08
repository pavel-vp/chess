package com.allice.chess.model.piece;

import com.allice.chess.model.ChessCell;
import com.allice.chess.model.Color;

public abstract class ChessPieceStaright extends ChessPieceNotPawn {
    public ChessPieceStaright(Color color) {
        super(color);
    }

    public int[][] getCoordsMustBeEmptyWithMove(ChessCell cellFrom, ChessCell cellTo) {
        int distX = cellFrom.getX() - cellTo.getX();
        int distY = cellFrom.getY() - cellTo.getY();

        int dist = Math.max(Math.abs(distX), Math.abs(distY));
        if (dist == 1) {
            return null;
        }

        int[][] emptyCells = new int[dist - 1][2];

        for (int i = 1; i <= dist - 1; i++) {
            emptyCells[i - 1][0] = cellFrom.getX() + i * (distX == 0 ? 0 : (distX > 0 ? -1 : 1));
            emptyCells[i - 1][1] = cellFrom.getY() + i * (distY == 0 ? 0 : (distY > 0 ? -1 : 1));
        }
        return emptyCells;
    }

}
