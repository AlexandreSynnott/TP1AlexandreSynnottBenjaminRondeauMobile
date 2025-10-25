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
     * @param couleur la couleur du joueur (BLANC ou NOIR)
     * @return true si le joueur a perdu, false sinon
     */
    public static boolean joueurAPerdu(Damier damier, Pion.Couleur couleur) {
        if (!aDesPions(damier, couleur)) {
            return true;
        }
        if (!peutSeDeplacer(damier, couleur)) {
            return true;
        }
        return false;
    }

    private static boolean aDesPions(Damier damier, Pion.Couleur couleur) {
        for (int l = 0; l < Damier.TAILLE; l++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                Pion pion = damier.getCase(l, c);
                if (pion != null && pion.getCouleur() == couleur) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean peutSeDeplacer(Damier damier, Pion.Couleur couleur) {
        int direction = (couleur == Pion.Couleur.BLANC) ? -1 : 1; // BLANC monte, NOIR descend
        for (int l = 0; l < Damier.TAILLE; l++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                Pion pion = damier.getCase(l, c);
                if (pion != null && pion.getCouleur() == couleur) {

                    // Vérifie déplacement diagonal simple
                    int nl = l + direction;
                    if (nl >= 0 && nl < Damier.TAILLE) {
                        if (c - 1 >= 0 && damier.getCase(nl, c - 1) == null) return true;
                        if (c + 1 < Damier.TAILLE && damier.getCase(nl, c + 1) == null) return true;
                    }

                    // Vérifie si une prise est possible
                    if (prisePossible(damier, l, c, couleur)) return true;
                }
            }
        }
        return false;
    }

    private static boolean prisePossible(Damier damier, int l, int c, Pion.Couleur couleur) {
        int[] directions = {-1, 1};
        Pion.Couleur ennemi = (couleur == Pion.Couleur.BLANC) ? Pion.Couleur.NOIR : Pion.Couleur.BLANC;

        for (int dl : directions) {
            for (int dc : directions) {
                int ml = l + dl;
                int mc = c + dc;
                int sl = l + 2*dl;
                int sc = c + 2*dc;

                if (sl >= 0 && sl < Damier.TAILLE && sc >= 0 && sc < Damier.TAILLE) {
                    Pion milieu = damier.getCase(ml, mc);
                    if (milieu != null && milieu.getCouleur() == ennemi && damier.getCase(sl, sc) == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
