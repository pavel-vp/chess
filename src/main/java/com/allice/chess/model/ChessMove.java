package com.allice.chess.model;

import com.allice.chess.model.piece.ChessPiece;

public class ChessMove {
    private ChessCell cellFrom;
    private ChessCell cellTo;

    public ChessMove(ChessCell chessCelFrom, ChessCell chessCellTo) {
        this.cellFrom = chessCelFrom;
        this.cellTo = chessCellTo;
    }

    public ChessPiece getPiece() {
        return cellFrom.getPiece();
    }

    public ChessCell getCellTo() {
        return cellTo;
    }

    public ChessCell getCellFrom() {
        return cellFrom;
    }
}
