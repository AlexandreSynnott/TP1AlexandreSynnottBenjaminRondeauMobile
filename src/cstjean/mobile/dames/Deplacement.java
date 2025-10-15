package cstjean.mobile.dames;

public class Deplacement {

    // Déplace une pièce sur le damier
    // Retourne true si le déplacement a été effectué
    public static boolean deplacer(Damier damier, int ligneD, int colD, int ligneA, int colA) {
        String[][] p = damier.plateau; // accès direct au plateau
        String piece = p[ligneD][colD];
        if (piece == null) return false; // rien à déplacer
        if (p[ligneA][colA] != null) return false; // case arrivée occupée

        int dL = ligneA - ligneD;
        int dC = colA - colD;

        // --- Pions blancs ---
        if ("b".equals(piece)) {
            if (dL == -1 && Math.abs(dC) == 1) { // déplacement simple
                p[ligneA][colA] = piece;
                p[ligneD][colD] = null;
                return true;
            }
            if (dL == -2 && Math.abs(dC) == 2) { // capture
                int lM = ligneD - 1;
                int cM = colD + dC/2;
                String milieu = p[lM][cM];
                if ("n".equals(milieu) || "N".equals(milieu)) {
                    p[ligneA][colA] = piece;
                    p[ligneD][colD] = null;
                    p[lM][cM] = null;
                    return true;
                }
            }
        }

        // --- Pions noirs ---
        if ("n".equals(piece)) {
            if (dL == 1 && Math.abs(dC) == 1) { // déplacement simple
                p[ligneA][colA] = piece;
                p[ligneD][colD] = null;
                return true;
            }
            if (dL == 2 && Math.abs(dC) == 2) { // capture
                int lM = ligneD + 1;
                int cM = colD + dC/2;
                String milieu = p[lM][cM];
                if ("b".equals(milieu) || "B".equals(milieu)) {
                    p[ligneA][colA] = piece;
                    p[ligneD][colD] = null;
                    p[lM][cM] = null;
                    return true;
                }
            }
        }

        // --- Dames ---
        if ("B".equals(piece) || "N".equals(piece)) {
            if (Math.abs(dL) == 1 && Math.abs(dC) == 1) { // déplacement simple
                p[ligneA][colA] = piece;
                p[ligneD][colD] = null;
                return true;
            }
        }

        return false;
    }
}
