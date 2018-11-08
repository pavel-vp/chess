package com.allice.chess.model.piece;

import com.allice.chess.model.ChessCell;
import com.allice.chess.model.Color;

public abstract class ChessPieceNotPawn extends ChessPiece {

    protected int[][] rightMoves;

    public ChessPieceNotPawn(Color color) {
        super(color);
    }

    @Override
    public boolean canGoTo(ChessCell cellFrom, ChessCell cellTo) {
        // calculate available move from current cell
        for (int[] move : rightMoves) {
            if (moveInBorder(cellFrom, move)
                    && (cellFrom.getX() + move[0] == cellTo.getX())
                    && (cellFrom.getY() + move[1] == cellTo.getY())) {
                return true;
            }
        }
        return false;
    }

}
