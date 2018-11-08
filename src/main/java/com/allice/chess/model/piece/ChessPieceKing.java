package com.allice.chess.model.piece;

import com.allice.chess.model.ChessCell;
import com.allice.chess.model.Color;

public class ChessPieceKing extends ChessPieceNotPawn {

    public ChessPieceKing(Color color) {
        super(color);
        rightMoves = new int[][] {{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1},{0,1}};
    }

    public int[][] getCoordsMustBeEmptyWithMove(ChessCell cellFrom, ChessCell cellTo) {
        return null;
    }

    @Override
    public boolean canGoTo(ChessCell cellFrom, ChessCell cellTo) {
        // calculate available move from current cell
        for (int[] move : rightMoves) {
            if (moveInBorder(cellFrom, move)
                    && (cellFrom.getX() + move[0] == cellTo.getX())
                    && (cellFrom.getY() + move[1] == cellTo.getY())
                    && !(cellTo.getPiece() instanceof ChessPieceKing)
                    ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getAsChar() {
        return color == Color.White ? "K" : "k";
    }

}
