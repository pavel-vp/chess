package com.allice.chess;

import com.allice.chess.model.*;
import com.allice.chess.service.Game;
import com.allice.chess.service.IGameInput;
import com.allice.chess.service.IGameOutput;
import com.allice.chess.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public static void main(String[] args)  {
        // initialization
        Game game = new Game();
        game.setPlayerType(Color.White, PlayerType.HUMAN);
        game.setPlayerType(Color.Black, PlayerType.COMPUTER);

        final BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));

        game.startProcess(new IGameInput() {
            @Override
            public ChessMoveRaw doInputMove(String prompt) {
                // ask for move for the player
                System.out.println(prompt);
                String line = null;
                try {
                    line = buffer.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

                int[][] coordsMove = Utils.stringToMove(line);
                if (coordsMove == null || coordsMove.length != 2 ||
                        coordsMove[0].length != 2 ||
                        coordsMove[1].length != 2 ||
                        coordsMove[0][0] < 0 || coordsMove[0][1] > 7 ||
                        coordsMove[1][0] < 0 || coordsMove[1][1] > 7
                        ) {
                    System.out.println("cant parse (XY-XY, from-to) !");
                    return null;
                }
                return new ChessMoveRaw(coordsMove[0], coordsMove[1]);
            }
        }, new IGameOutput() {
            @Override
            public void doOutput(String s) {
                System.out.println(s);
            }
        });


    }

}
