package cstjean.mobile.dames;

public class Prise {

    // Effectue une prise si possible
    // Retourne true si la prise a été faite
    public static boolean prendre(Damier damier, int ligneD, int colD, int ligneA, int colA) {
        String[][] p = damier.plateau;
        String depart = p[ligneD][colD];
        String arrivee = p[ligneA][colA];

        // Vérifier qu'il y a une pièce à déplacer et que la case d'arrivée est vide
        if (depart == null) return false;
        if (arrivee != null) return false;

        int dL = ligneA - ligneD;
        int dC = colA - colD;

        // --- Pion blanc ---
        if (depart.equals("b")) {
            // On peut capturer une pièce noire adjacente
            if (Math.abs(dL) == 2 && Math.abs(dC) == 2) {
                int lM = (ligneD + ligneA)/2;
                int cM = (colD + colA)/2;
                String milieu = p[lM][cM];
                if (milieu.equals("n") || milieu.equals("N")) {
                    p[ligneA][colA] = "b";
                    p[ligneD][colD] = null;
                    p[lM][cM] = null; // suppression de la pièce capturée
                    return true;
                }
            }
        }

        // --- Pion noir ---
        if (depart.equals("n")) {
            if (Math.abs(dL) == 2 && Math.abs(dC) == 2) {
                int lM = (ligneD + ligneA)/2;
                int cM = (colD + colA)/2;
                String milieu = p[lM][cM];
                if (milieu.equals("b") || milieu.equals("B")) {
                    p[ligneA][colA] = "n";
                    p[ligneD][colD] = null;
                    p[lM][cM] = null;
                    return true;
                }
            }
        }

        // --- Dame blanche ---
        if (depart.equals("B")) {
            // simple diagonale de 1 case
            if (Math.abs(dL) == 1 && Math.abs(dC) == 1) {
                String milieu = p[ligneD + dL/2][colD + dC/2];
                if (milieu != null && (milieu.equals("n") || milieu.equals("N"))) {
                    p[ligneA][colA] = "B";
                    p[ligneD][colD] = null;
                    p[ligneD + dL/2][colD + dC/2] = null;
                    return true;
                }
            }
        }

        // --- Dame noire ---
        if (depart.equals("N")) {
            if (Math.abs(dL) == 1 && Math.abs(dC) == 1) {
                String milieu = p[ligneD + dL/2][colD + dC/2];
                if (milieu != null && (milieu.equals("b") || milieu.equals("B"))) {
                    p[ligneA][colA] = "N";
                    p[ligneD][colD] = null;
                    p[ligneD + dL/2][colD + dC/2] = null;
                    return true;
                }
            }
        }

        return false; // impossible
    }
}
