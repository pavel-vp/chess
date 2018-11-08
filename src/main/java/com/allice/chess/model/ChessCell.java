package com.allice.chess.model;

import com.allice.chess.model.piece.ChessPiece;

public class ChessCell {
    private int x;
    private int y;
    private Color color;
    private ChessPiece piece;

    public ChessCell(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public ChessCell(ChessCell originCell) {
        this.x = originCell.getX();
        this.y = originCell.getY();
        this.color = originCell.getColor();
        this.piece = originCell.getPiece();
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }
    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
