package com.allice.chess.model;

public class ChessMoveRaw {
    private int[] coordFrom;
    private int[] coordTo;

    public ChessMoveRaw(int[] coordFrom, int[] coordTo) {
        this.coordFrom = coordFrom;
        this.coordTo = coordTo;
    }

    public int[] getCoordFrom() {
        return coordFrom;
    }

    public int[] getCoordTo() {
        return coordTo;
    }
}
