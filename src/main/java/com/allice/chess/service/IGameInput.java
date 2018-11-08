package com.allice.chess.service;

import com.allice.chess.model.ChessMoveRaw;

public interface IGameInput {
    ChessMoveRaw doInputMove(String prompt);
}
