package com.allice.chess;

import com.allice.chess.utils.Utils;
import org.junit.Test;

import java.util.Arrays;

public class ChessUtilTest {

    @Test
    public void util_format_test() {
        String s = "D2-D4";
        int[][] coords = Utils.stringToMove(s);

        assert coords[0][0] == 3;
        assert coords[0][1] == 1;
        assert coords[1][0] == 3;
        assert coords[1][1] == 3;
    }

    @Test
    public void util_coordtoaplpha_test() {
        int[] coord = new int[] {2,3};
        String s = Utils.convertCoordToAlpha(coord);

        assert s.equals("C4");
    }

    @Test
    public void util_alphatocoord_test() {
        String s = "F4";
        int[] coord = Utils.convertAlphaToCoord(s);
        assert coord[0] == 5;
        assert coord[1] == 3;
    }
}
