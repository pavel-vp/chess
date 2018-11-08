package com.allice.chess.model.piece;

import com.allice.chess.model.ChessCell;
import com.allice.chess.model.Color;

public class ChessPiecePawn extends ChessPiece {

    public ChessPiecePawn(Color color) {
        super(color);
    }

    public boolean canGoTo(ChessCell cellFrom, ChessCell cellTo) {
        // check borders
        if (cellTo.getX() >= 0 && cellTo.getX() <= 7
                && cellTo.getY() >= 0 && cellTo.getY() <= 7) {

            int direction = color == Color.White ? 1 : -1;

            // not eating move
            if (cellFrom.getX() == cellTo.getX()
                    && cellTo.getPiece() == null) {
                // simple move forward
                if (cellTo.getY() - cellFrom.getY() == direction) {
                    return true;
                }
                if (color == Color.White
                        && cellFrom.getY() == 1
                        && cellTo.getY() == 3) {
                    return true;
                }
                if (color == Color.Black
                        && cellFrom.getY() == 6
                        && cellTo.getY() == 4) {
                    return true;
                }
            } else {
                // check for eating move
                if (Math.abs(cellTo.getX() - cellFrom.getX()) == 1
                    && cellTo.getY() - cellFrom.getY() == direction
                    && cellTo.getPiece() != null
                    && cellTo.getPiece().getColor() != color) {

                    return true;
                }
            }
        }

        return false;
    }

    public int[][] getCoordsMustBeEmptyWithMove(ChessCell cellFrom, ChessCell cellTo) {
        int distY = cellFrom.getY() - cellTo.getY();

        int dist = Math.abs(distY);
        if (dist != 2) {
            return null;
        }

        int[][] emptyCells = new int[dist - 1][2];

        for (int i = 1; i <= dist - 1; i++) {
            emptyCells[i - 1][0] = cellFrom.getX();
            emptyCells[i - 1][1] = cellFrom.getY() + i * (distY == 0 ? 0 : (distY > 0 ? -1 : 1));
        }
        return emptyCells;
    }

    @Override
    public String getAsChar() {
        return color == Color.White ? "P" : "p";
    }

}
