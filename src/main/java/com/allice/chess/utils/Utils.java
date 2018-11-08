package com.allice.chess.utils;

public class Utils {

    // parse line from XY-XY fromat to coords
    public static int[][] stringToMove(String line) {
        try {
            String[] s = line.split("-");
            int[] from = convertAlphaToCoord(s[0]);
            int[] to = convertAlphaToCoord(s[1]);
            return new int[][] {{from[0], from[1]}, {to[0], to[1]}};
        } catch (Exception e){
            // bad format
        }
        return null;
    }

    public static int[] convertAlphaToCoord(String s) {
        int x = s.toUpperCase().charAt(0) - ("A".charAt(0));
        int y = s.toUpperCase().charAt(1) - ("1".charAt(0));
        return new int[] {x, y};
    }

    public static String convertCoordToAlpha(int[] coord) {

        char x = (char) (coord[0] + ("A".charAt(0)));
        char y = (char) (coord[1] + ("1".charAt(0)));
        return String.valueOf(x) + String.valueOf(y);
    }
}
