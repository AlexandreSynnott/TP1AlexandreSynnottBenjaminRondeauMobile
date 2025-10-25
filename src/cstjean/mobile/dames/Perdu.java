package cstjean.mobile.dames;

/**
 * Classe responsable de vérifier si un joueur a perdu la partie.
 */
public class Perdu {

    /**
     * Vérifie si un joueur a perdu.
     * Le joueur perd s'il n'a plus de pions ou s'il ne peut plus se déplacer.
     *
     * @param damier le damier actuel
     * @param couleur la couleur du joueur ("b" pour blanc, "n" pour noir)
     * @return true si le joueur a perdu, false sinon
     */
    public static boolean joueurAPerdu(Damier damier, String couleur) {
        // Vérifie si le joueur a encore des pièces
        if (!aDesPions(damier, couleur)) {
            return true;
        }

        // Vérifie si le joueur peut se déplacer
        if (!peutSeDeplacer(damier, couleur)) {
            return true;
        }

        return false;
    }

    /**
     * Vérifie si le joueur possède encore au moins une pièce sur le damier.
     */
    private static boolean aDesPions(Damier damier, String couleur) {
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                if (couleur.equals(damier.getCase(ligne, colonne))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si le joueur peut se déplacer (déplacement simple ou prise possible).
     */
    private static boolean peutSeDeplacer(Damier damier, String couleur) {
        int direction = couleur.equals("b") ? -1 : 1; // les blancs montent, les noirs descendent

        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                if (couleur.equals(damier.getCase(ligne, colonne))) {

                    // Test déplacement simple diagonal gauche/droite
                    int nouvelleLigne = ligne + direction;

                    // Déplacement vers la gauche
                    if (colonne - 1 >= 0 && estCaseVide(damier, nouvelleLigne, colonne - 1)) {
                        return true;
                    }

                    // Déplacement vers la droite
                    if (colonne + 1 < 8 && estCaseVide(damier, nouvelleLigne, colonne + 1)) {
                        return true;
                    }

                    // Vérifie si une prise est possible (saut par-dessus une pièce adverse)
                    if (prisePossible(damier, ligne, colonne, couleur)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si une prise est possible pour un pion donné.
     */
    private static boolean prisePossible(Damier damier, int ligne, int colonne, String couleur) {
        String ennemi = couleur.equals("b") ? "n" : "b";
        int[] directions = {-1, 1};

        for (int dL : directions) {
            for (int dC : directions) {
                int milieuL = ligne + dL;
                int milieuC = colonne + dC;
                int sautL = ligne + (2 * dL);
                int sautC = colonne + (2 * dC);

                if (sautL >= 0 && sautL < 8 && sautC >= 0 && sautC < 8) {
                    if (ennemi.equals(damier.getCase(milieuL, milieuC)) && estCaseVide(damier, sautL, sautC)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si une case est vide (aucun pion).
     */
    private static boolean estCaseVide(Damier damier, int ligne, int colonne) {
        return ligne >= 0 && ligne < 8 && colonne >= 0 && colonne < 8 && damier.getCase(ligne, colonne) == null;
    }
}
