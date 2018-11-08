package com.allice.chess.service;

import com.allice.chess.model.ChessBoardStatus;
import com.allice.chess.model.ChessCell;
import com.allice.chess.model.ChessMove;
import com.allice.chess.model.Color;
import com.allice.chess.model.piece.*;

import java.util.ArrayList;
import java.util.List;

class ChessBoard {
    private ChessCell[][] chessBoard = new ChessCell[8][8];

    private ChessCell cellWhiteKing, cellBlackKing;

    private ChessBoardStatus chessBoardStatus;

    ChessBoard() {
        this.init();
    }

    // initialization
    private void init() {
        // create all cells
        chessBoardStatus = ChessBoardStatus.INPROGRESS;
        Color color = Color.Black;
        for (int y = 0;y<=7;y++){
            for (int x = 0;x<=7;x++) {
                ChessCell cell = new ChessCell(x,y,color);
                chessBoard[x][y] = cell;
                color = color == Color.Black ? Color.White : Color.Black;
            }
            color = color == Color.Black ? Color.White : Color.Black;
        }

        // set up figures
        // White
        for (int x = 0;x<=7;x++) {
            chessBoard[x][1].setPiece(new ChessPiecePawn(Color.White));
        }
        chessBoard[0][0].setPiece(new ChessPieceRock(Color.White));
        chessBoard[1][0].setPiece(new ChessPieceHorse(Color.White));
        chessBoard[2][0].setPiece(new ChessPieceBishop(Color.White));
        chessBoard[3][0].setPiece(new ChessPieceQueen(Color.White));
        chessBoard[4][0].setPiece(new ChessPieceKing(Color.White));
        chessBoard[5][0].setPiece(new ChessPieceBishop(Color.White));
        chessBoard[6][0].setPiece(new ChessPieceHorse(Color.White));
        chessBoard[7][0].setPiece(new ChessPieceRock(Color.White));
        cellWhiteKing = chessBoard[4][0];
        // Black
        for (int x = 0;x<=7;x++) {
            chessBoard[x][6].setPiece(new ChessPiecePawn(Color.Black));
        }
        chessBoard[0][7].setPiece(new ChessPieceRock(Color.Black));
        chessBoard[1][7].setPiece(new ChessPieceHorse(Color.Black));
        chessBoard[2][7].setPiece(new ChessPieceBishop(Color.Black));
        chessBoard[3][7].setPiece(new ChessPieceQueen(Color.Black));
        chessBoard[4][7].setPiece(new ChessPieceKing(Color.Black));
        chessBoard[5][7].setPiece(new ChessPieceBishop(Color.Black));
        chessBoard[6][7].setPiece(new ChessPieceHorse(Color.Black));
        chessBoard[7][7].setPiece(new ChessPieceRock(Color.Black));
        cellBlackKing = chessBoard[4][7];

    }

    // Constructor,that helps to plan next move (it creates copy of existing chessboard)
    private ChessBoard(ChessCell[][] originalChessBoard, Color colorOnMove) {

        ChessCell[][] chessCellsNew = new ChessCell[8][8];
        for (int x = 0; x<=7;x++) {
            for (int y = 0;y<=7;y++) {
                ChessCell originCell = originalChessBoard[x][y];
                ChessCell newCell = new ChessCell(originCell);
                chessCellsNew[x][y] = newCell;
            }
        }

        this.chessBoard = chessCellsNew;
        cellWhiteKing = findKing(Color.White);
        cellBlackKing = findKing(Color.Black);
        checkGameStatus(colorOnMove, true);
    }


    private ChessCell findKing(Color color) {
        for (int y = 0;y<=7;y++){
            for (int x = 0;x<=7;x++) {
                ChessCell cell = chessBoard[x][y];
                if (cell.getPiece() != null
                        && cell.getPiece() instanceof ChessPieceKing
                        && cell.getPiece().getColor() == color) {
                    return cell;
                }
            }
        }
        return null;
    }

    // move from one to other
    // return true if there is legal move has been done, false otherwise
    boolean checkMoveOnChessBoard(Color color, ChessPiece piece, ChessCell cellFrom, ChessCell cellTo) {
        // get cells, that must be empty
        int[][] coordsToCheck = piece.getCoordsMustBeEmptyWithMove(cellFrom, cellTo);
        if (coordsToCheck != null && coordsToCheck.length >0) {
            for (int[] coord : coordsToCheck) {
                if (chessBoard[coord[0]][coord[1]].getPiece() != null ) {
                    return false; // there is a figure in a way
                }
            }
        }

        // if we move the KING, check there is no another KING around
        if (piece instanceof ChessPieceKing) {
            if (isAnotherKingAround(color, cellTo)) {
                return false;
            }
        }

        // check if there is the cell empty or it has a figure of a different color
        if (cellTo.getPiece() == null || cellTo.getPiece().getColor() != color ) {
            return true;
        }
        return false;
    }

    private boolean isAnotherKingAround(Color color, ChessCell cellTo) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (!(x == 0 && y == 0)) {
                    if (isAnotherKingInCell(color, cellTo.getX() + x, cellTo.getY() + y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isAnotherKingInCell(Color color, int x, int y) {
        if (x >=0 && x <= 7 && y >=0 && y<=7) {
            ChessPiece piece = chessBoard[x][y].getPiece();
            if (piece != null
                    && piece instanceof ChessPieceKing
                    && piece.getColor() != color) {
                return true;
            }
        }
        return false;
    }

    // move from one to other
    public boolean move(Color color, ChessPiece piece, ChessCell cellFrom, ChessCell cellTo) {
        // change position of the figure
        ChessCell chessCell = chessBoard[cellTo.getX()][cellTo.getY()];
        {
            ChessPiece oldPieceTo = cellTo.getPiece();
            cellFrom.setPiece(null);
            chessCell.setPiece(piece);
            // if after the move there is a CHECK to king of this color
            if (checkOfCHECK(color, findKing(color))) {
                // return figures back
                cellFrom.setPiece(piece);
                chessCell.setPiece(oldPieceTo);
                return false;
            }

        }
        // check if there is a king's move
        if (piece instanceof ChessPieceKing) {
            if (color == Color.White) {
                cellWhiteKing = chessCell;
            } else {
                cellBlackKing = chessCell;
            }
        }
        // check if there is a pawn's move and there is a last or first Y - transorf pawn to Queen
        if (piece instanceof ChessPiecePawn
                && (chessCell.getY() == 0 || chessCell.getY() == 7)) {
            ChessPiece newQueen = new ChessPieceQueen(piece.getColor());
            chessCell.setPiece(newQueen);

        }
        return true;
    }

    private boolean checkCHECKonWhite(boolean skipCheckCheckMate) {
        // check if there is a CHECK state
        boolean isCheck = checkOfCHECK(Color.White, cellWhiteKing);
        if (isCheck) {
            if (skipCheckCheckMate) {
                chessBoardStatus = ChessBoardStatus.WHITECHECK;
                return true;
            }
            // check all available moves of all White fugures to prevent CHECK
            boolean canEscapeCheckMate = isCanEscapeCheckMate(Color.White);
            if (!canEscapeCheckMate) {
                chessBoardStatus = ChessBoardStatus.BLACKWON;
                return true;
            } else {
                chessBoardStatus = ChessBoardStatus.WHITECHECK;
                return true;
            }
        }
        return false;
    }

    private boolean checkCHECKonBlack(boolean skipCheckCheckMate) {
        boolean isCheck = checkOfCHECK(Color.Black, cellBlackKing);
        if (isCheck) {
            if (skipCheckCheckMate) {
                chessBoardStatus = ChessBoardStatus.BLACKCHECK;
                return true;
            }
            // check all available moves of all White fugures to prevent CHECK
            boolean canEscapeCheckMate = isCanEscapeCheckMate(Color.Black);
            if (!canEscapeCheckMate) {
                chessBoardStatus = ChessBoardStatus.WHITEWON;
                return true;
            } else {
                chessBoardStatus = ChessBoardStatus.BLACKCHECK;
                return true;
            }
        }
        return false;
    }

    // check game status
    public void checkGameStatus(Color colorOnMove, boolean skipCheckCheckMate) {
        // first, check the CHECK and CHECKMATE states
        if (colorOnMove == Color.White) {
            if (checkCHECKonWhite(skipCheckCheckMate)) {
                return;
            }
            if (checkCHECKonBlack(skipCheckCheckMate)) {
                return;
            }
        } else {
            if (checkCHECKonBlack(skipCheckCheckMate)) {
                return;
            }
            if (checkCHECKonWhite(skipCheckCheckMate)) {
                return;
            }
        }
        // check DRAW state
        if (!skipCheckCheckMate && getAvailableMoves(colorOnMove).size() == 0) {
            chessBoardStatus = ChessBoardStatus.DRAW;
            return;
        }
        // if there are only two kings alive
        int cnt = 0;
        for (int x = 0; x <= 7; x++) {
            for (int y= 0; y <= 7; y++) {
                if (chessBoard[x][y].getPiece() != null) {
                    cnt++;
                }
            }
        }
        if (cnt == 2) {
            chessBoardStatus = ChessBoardStatus.DRAW;
            return;
        }

        chessBoardStatus = ChessBoardStatus.INPROGRESS;
    }

    public List<ChessMove> getAvailableMoves(Color colorToMove) {
        List<ChessMove> result = new ArrayList<>();
        // for all of the pieces of that color
        for (int x = 0; x<=7; x++) {
            for (int y = 0; y<=7; y++) {
                ChessPiece piece = chessBoard[x][y].getPiece();
                if (piece != null && piece.getColor() == colorToMove) {
                    // for this piece try to move it for every cell in the board
                    for (int x2 = 0; x2<=7; x2++) {
                        for (int y2 = 0; y2 <= 7; y2++) {
                            ChessMove move = new ChessMove(chessBoard[x][y], chessBoard[x2][y2]);
                            if (piece.canGoTo(move.getCellFrom(), move.getCellTo())
                                    && checkMoveOnChessBoard(colorToMove, piece, move.getCellFrom(), move.getCellTo())) {

                                // try move on test board
                                ChessBoard chessBoardNew = new ChessBoard(this.chessBoard, colorToMove);
                                // make a move on a new chessboard
                                ChessCell cellFromNew = chessBoardNew.getChessCellByCoord(new int[] { move.getCellFrom().getX(), move.getCellFrom().getY()});
                                ChessCell cellToNew = chessBoardNew.getChessCellByCoord(new int[] { move.getCellTo().getX(), move.getCellTo().getY()});
                                boolean resultMove = chessBoardNew.move(colorToMove, move.getPiece(), cellFromNew, cellToNew);
                                if (resultMove) {
                                    result.add(move);
                                }

                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    private boolean isCanEscapeCheckMate(Color colorToMove) {
        List<ChessMove> moves = getAvailableMoves(colorToMove);
        for (ChessMove move : moves) {
            ChessBoard chessBoardNew = new ChessBoard(this.chessBoard, colorToMove);
            // make a move on a new chessboard
            ChessCell cellFromNew = chessBoardNew.getChessCellByCoord(new int[] { move.getCellFrom().getX(), move.getCellFrom().getY()});
            ChessCell cellToNew = chessBoardNew.getChessCellByCoord(new int[] { move.getCellTo().getX(), move.getCellTo().getY()});
            boolean result = chessBoardNew.move(colorToMove, move.getPiece(), cellFromNew, cellToNew);
            if (result)  {
                chessBoardNew.checkGameStatus(colorToMove, true);
                // if there is no check anymore - then return true immediately
                ChessBoardStatus status = chessBoardNew.getChessBoardStatus();
                if (colorToMove == Color.White &&
                        (status == ChessBoardStatus.INPROGRESS || status == ChessBoardStatus.BLACKCHECK || status == ChessBoardStatus.WHITEWON)) {
                    return true;
                }
                if (colorToMove == Color.Black &&
                        (status == ChessBoardStatus.INPROGRESS || status == ChessBoardStatus.WHITECHECK || status == ChessBoardStatus.BLACKWON)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkOfCHECK(Color colorOfKing, ChessCell cellWithKing) {
        // we need to go through all of the OTHER color figures and check if ONE of them can reach this cell with legal move
        for (int x = 0; x<=7; x++) {
            for (int y = 0; y<=7; y++) {
                ChessPiece piece = chessBoard[x][y].getPiece();
                if (piece != null && piece.getColor() != colorOfKing) {
                    if (canEatToCell(piece, chessBoard[x][y], cellWithKing)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canEatToCell(ChessPiece piece, ChessCell cellFrom, ChessCell cellTo) {
        // check, if the piece can eat a figure in cell
        boolean pieceCanGo = piece.canGoTo(cellFrom, cellTo);
        if (!pieceCanGo) {
            return false;
        }
        return checkMoveOnChessBoard(piece.getColor(), piece, cellFrom, cellTo);
    }


    public ChessBoardStatus getChessBoardStatus() {
        return chessBoardStatus;
    }

    public ChessCell getChessCellByCoord(int[] coord) {
        return chessBoard[coord[0]][coord[1]];
    }

    String[] getBoardAsStrings() {
        String[] board = new String[10];
        board[0]=" ABCDEFGH ";
        for (int y = 7; y>=0; y--) {
            StringBuilder sb = new StringBuilder();
            sb.append(y + 1);
            for (int x=0; x<=7; x++) {
                ChessCell cell = chessBoard[x][y];
                if (cell.getPiece() == null) {
                    sb.append(" ");
                } else {
                    sb.append(cell.getPiece().getAsChar());
                }
            }
            sb.append(y + 1);
            board[8-y]= sb.toString();
        }
        board[9]=" ABCDEFGH ";
        return board;
    }

}
