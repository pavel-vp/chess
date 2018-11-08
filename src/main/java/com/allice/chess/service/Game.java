package com.allice.chess.service;

import com.allice.chess.model.*;
import com.allice.chess.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private ChessBoard chessBoard;
    private Color colorOnMove;

    private Map<Color, PlayerType> playerTypeMap = new HashMap<>();

    private int moveCount = 0;

    public Game() {
        this.chessBoard = new ChessBoard();
        colorOnMove = Color.White;
        playerTypeMap.put(Color.White, PlayerType.COMPUTER);
        playerTypeMap.put(Color.Black, PlayerType.COMPUTER);
    }

    private Color getColorOnMove() {
        return colorOnMove;
    }

    private void switchColorOnMove() {
        colorOnMove = colorOnMove == Color.White ? Color.Black : Color.White;
    }

    public void setPlayerType(Color color, PlayerType playerType) {
        playerTypeMap.put(color, playerType);
    }

    private PlayerType getPlayerType() {
        return playerTypeMap.get(colorOnMove);
    }

    private ChessMove guessAndGetNextMove() {
        // I use this method to imitate computer movement, but it just get all of the available movements. So, it is rather stupid right now :)
        List<ChessMove> moves = chessBoard.getAvailableMoves(colorOnMove);
        int num = (int) (Math.random() * moves.size());
        return moves.get(num);
    }

    public void startProcess(IGameInput gameInput, IGameOutput gameOutput) {

        gameOutput.doOutput("ChessGame. Moving format = e2-e4, first part - cell from (X,Y), second - cell to. Chess board is 8x8, Whites take first 2 lines.");
        // main game loop
        while (getStatus() == ChessBoardStatus.INPROGRESS ||
                getStatus() == ChessBoardStatus.WHITECHECK ||
                getStatus() == ChessBoardStatus.BLACKCHECK) {

            // print board
            for(String s : getBoardAsStrings()) {
                gameOutput.doOutput(s);
            }
            if (getStatus() == ChessBoardStatus.WHITECHECK ||
                    getStatus() == ChessBoardStatus.BLACKCHECK) {
                gameOutput.doOutput("Warning ! Game status is " + getStatus());
            }

            ChessMove move = null;
            if (getPlayerType() == PlayerType.HUMAN) {
                ChessMoveRaw moveRaw = gameInput.doInputMove("Enter your move (" + colorOnMove + ") :");
                if (moveRaw != null) {
                    ChessCell cellFrom = getChessCellByCoord(moveRaw.getCoordFrom());
                    ChessCell cellTo = getChessCellByCoord(moveRaw.getCoordTo());
                    move = new ChessMove(cellFrom, cellTo);
                }
            } else {
                move = guessAndGetNextMove();
                gameOutput.doOutput(
                    "Computer move (" + colorOnMove + ") : " +
                        Utils.convertCoordToAlpha(new int[] {move.getCellFrom().getX(),move.getCellFrom().getY()}) +"-" +
                        Utils.convertCoordToAlpha(new int[] {move.getCellTo().getX(),move.getCellTo().getY()}) );
            }
            // check if the move is right
            boolean result = move(move);
            if (!result) {
                gameOutput.doOutput("wrong move !");
            } else {
                gameOutput.doOutput("move's counter:"+moveCount);
            }
        }
        // print board
        for(String s : getBoardAsStrings()) {
            gameOutput.doOutput(s);
        }
        gameOutput.doOutput("Game checkBoardStatus is " + getStatus());

    }


    private boolean move(ChessMove move) {
        boolean isLegalMove = true;
        if (move != null &&
                move.getPiece() != null &&
                move.getPiece().canGoTo(move.getCellFrom(), move.getCellTo())) {

            if (!chessBoard.checkMoveOnChessBoard(getColorOnMove(), move.getPiece(), move.getCellFrom(), move.getCellTo())) {
                isLegalMove = false;
            } else {
                // real movement
                if (!chessBoard.move(getColorOnMove(), move.getPiece(), move.getCellFrom(), move.getCellTo())) {
                    isLegalMove = false;
                }
            }
        } else {
            return false;
        }

        // check end of the game
        chessBoard.checkGameStatus(getColorOnMove(), false);
        if (!isLegalMove) {
            return false;
        }
        // change player
        switchColorOnMove();
        moveCount++;
        return true;

    }


    private ChessBoardStatus getStatus() {
        return chessBoard.getChessBoardStatus();
    }

    private String[] getBoardAsStrings() {
        return chessBoard.getBoardAsStrings();
    }

    private ChessCell getChessCellByCoord(int[] coord) {
        return chessBoard.getChessCellByCoord(coord);
    }

}
