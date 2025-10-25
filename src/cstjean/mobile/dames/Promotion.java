package cstjean.mobile.dames;

public class Promotion {

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

        // --- Promotion des pions noirs (rangée 9) ---
        for (int c = 0; c < taille; c++) {
            Pion piece = damier.getCase(taille - 1, c);
            if (piece != null && !(piece instanceof Dame) &&
                    piece.getCouleur() == Pion.Couleur.NOIR) {
                damier.setCase(taille - 1, c, new Dame(Pion.Couleur.NOIR));
            }
        }
    }
}