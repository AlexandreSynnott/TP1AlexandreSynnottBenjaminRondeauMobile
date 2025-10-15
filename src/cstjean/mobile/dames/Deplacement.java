package cstjean.mobile.dames;

public class Deplacement {

    public static boolean deplacer(Damier damier, int ligneD, int colD, int ligneA, int colA) {
        String[][] p = damier.plateau;
        String piece = p[ligneD][colD];
        if (piece == null || p[ligneA][colA] != null) return false;

        int dL = ligneA - ligneD;
        int dC = colA - colD;

        // Déplacement simple pour toutes les pièces
        if (Math.abs(dL) == 1 && Math.abs(dC) == 1) {
            // Pour pions : vérifier la direction
            if ("b".equals(piece) && dL == -1 ||
                    "n".equals(piece) && dL == 1 ||
                    "B".equals(piece) || "N".equals(piece)) {
                p[ligneA][colA] = piece;
                p[ligneD][colD] = null;
                return true;
            }
        }

        // Capture pour pions
        if (Math.abs(dL) == 2 && Math.abs(dC) == 2) {
            int lM = ligneD + (dL / 2);
            int cM = colD + (dC / 2);
            String milieu = p[lM][cM];

            if (("b".equals(piece) && ("n".equals(milieu) || "N".equals(milieu))) ||
                    ("n".equals(piece) && ("b".equals(milieu) || "B".equals(milieu)))) {
                p[ligneA][colA] = piece;
                p[ligneD][colD] = null;
                p[lM][cM] = null;
                return true;
            }
        }

        return false;
    }
}

