package cstjean.mobile.dames;

/**
 * Classe responsable de gérer la promotion des pions en dames.
 *
 * <p>
 * Lorsqu'un pion atteint la dernière rangée du camp adverse,
 * il est automatiquement promu en une pièce de type {@link Dame}.
 * Cette classe vérifie les deux camps (blanc et noir) après chaque tour.
 * </p>
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class Promotion {

    /**
     * Vérifie et effectue la promotion des pions en dames sur le damier.
     *
     * <p>
     * Si un pion blanc atteint la rangée 0, il devient une {@link Dame} blanche.
     * Si un pion noir atteint la dernière rangée, il devient une {@link Dame} noire.
     * </p>
     *
     * @param damier le damier actuel sur lequel vérifier les promotions
     */
    public static void verifierPromotion(Damier damier) {
        int taille = Damier.TAILLE;

        // --- Promotion des pions blancs (rangée 0) ---
        for (int c = 0; c < taille; c++) {
            Pion piece = damier.getCase(0, c);
            if (piece != null && !(piece instanceof Dame) &&
                    piece.getCouleur() == Pion.Couleur.BLANC) {
                damier.setCase(0, c, new Dame(Pion.Couleur.BLANC));
            }
        }

        // --- Promotion des pions noirs (rangée du bas) ---
        for (int c = 0; c < taille; c++) {
            Pion piece = damier.getCase(taille - 1, c);
            if (piece != null && !(piece instanceof Dame) &&
                    piece.getCouleur() == Pion.Couleur.NOIR) {
                damier.setCase(taille - 1, c, new Dame(Pion.Couleur.NOIR));
            }
        }
    }
}
