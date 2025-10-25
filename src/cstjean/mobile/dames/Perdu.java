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
        return !peutSeDeplacer(damier, couleur);
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
        for (int l = 0; l < Damier.TAILLE; l++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                Pion pion = damier.getCase(l, c);
                if (pion != null && pion.getCouleur() == couleur) {
                    if (peutBouger(damier, l, c) || peutPrendre(damier, l, c)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean peutBouger(Damier damier, int l, int c) {
        Pion pion = damier.getCase(l, c);
        if (pion == null) return false;

        // Directions de déplacement selon le type de pièce
        int[][] directions;

        if (pion instanceof Dame) {
            // Les dames peuvent se déplacer dans les 4 directions
            directions = new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        } else {
            // Les pions ne peuvent avancer que dans 2 directions
            if (pion.getCouleur() == Pion.Couleur.BLANC) {
                directions = new int[][]{{-1, -1}, {-1, 1}}; // Vers le haut
            } else {
                directions = new int[][]{{1, -1}, {1, 1}}; // Vers le bas
            }
        }

        // Vérifier chaque direction possible
        for (int[] dir : directions) {
            int newL = l + dir[0];
            int newC = c + dir[1];

            if (estDansDamier(newL, newC) && damier.getCase(newL, newC) == null) {
                return true;
            }
        }

        return false;
    }

    private static boolean peutPrendre(Damier damier, int l, int c) {
        Pion pion = damier.getCase(l, c);
        if (pion == null) return false;

        Pion.Couleur ennemi = (pion.getCouleur() == Pion.Couleur.BLANC) ?
                Pion.Couleur.NOIR : Pion.Couleur.BLANC;

        // Directions de prise (toujours 2 cases)
        int[][] directions = new int[][]{{-2, -2}, {-2, 2}, {2, -2}, {2, 2}};

        for (int[] dir : directions) {
            int newL = l + dir[0];
            int newC = c + dir[1];
            int midL = l + dir[0] / 2;
            int midC = c + dir[1] / 2;

            if (estDansDamier(newL, newC) && damier.getCase(newL, newC) == null) {
                Pion pionMilieu = damier.getCase(midL, midC);
                if (pionMilieu != null && pionMilieu.getCouleur() == ennemi) {
                    return true;
                }
            }
        }

        // Vérifier les prises pour les dames (longue distance)
        if (pion instanceof Dame) {
            int[][] dameDirections = new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

            for (int[] dir : dameDirections) {
                int currentL = l + dir[0];
                int currentC = c + dir[1];
                boolean ennemiTrouve = false;

                while (estDansDamier(currentL, currentC)) {
                    Pion currentPion = damier.getCase(currentL, currentC);

                    if (currentPion == null) {
                        if (ennemiTrouve) {
                            return true; // On peut prendre l'ennemi et atterrir ici
                        }
                    } else if (currentPion.getCouleur() == pion.getCouleur()) {
                        break; // Bloqué par un allié
                    } else {
                        if (ennemiTrouve) {
                            break; // Plus d'un ennemi sur le chemin
                        } else {
                            ennemiTrouve = true;
                        }
                    }

                    currentL += dir[0];
                    currentC += dir[1];
                }
            }
        }

        return false;
    }

    private static boolean estDansDamier(int l, int c) {
        return l >= 0 && l < Damier.TAILLE && c >= 0 && c < Damier.TAILLE;
    }
}