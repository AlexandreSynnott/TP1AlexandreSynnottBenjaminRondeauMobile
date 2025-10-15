package cstjean.mobile.dames;

public class Prise {

    public static boolean prendre(Damier damier, int ligneD, int colD, int ligneA, int colA) {
        String[][] p = damier.plateau;
        String piece = p[ligneD][colD];
        if (piece == null || p[ligneA][colA] != null) return false;

        int dL = ligneA - ligneD;
        int dC = colA - colD;

        // --- Capture pour pions ---
        if ((piece.equals("b") || piece.equals("n")) && Math.abs(dL) == 2 && Math.abs(dC) == 2) {
            int lM = ligneD + dL/2;
            int cM = colD + dC/2;
            String milieu = p[lM][cM];

            if ((piece.equals("b") && (milieu.equals("n") || milieu.equals("N"))) ||
                    (piece.equals("n") && (milieu.equals("b") || milieu.equals("B")))) {
                p[ligneA][colA] = piece;
                p[ligneD][colD] = null;
                p[lM][cM] = null;
                return true;
            }
        }

        // --- Capture pour dames ---
        if ((piece.equals("B") || piece.equals("N")) && Math.abs(dL) == 1 && Math.abs(dC) == 1) {
            int lM = ligneD + dL/2;
            int cM = colD + dC/2;
            String milieu = p[lM][cM];

            if ((piece.equals("B") && (milieu.equals("n") || milieu.equals("N"))) ||
                    (piece.equals("N") && (milieu.equals("b") || milieu.equals("B")))) {
                p[ligneA][colA] = piece;
                p[ligneD][colD] = null;
                p[lM][cM] = null;
                return true;
            }
        }

        return false;
    }
}

