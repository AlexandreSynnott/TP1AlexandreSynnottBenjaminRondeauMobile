package cstjean.mobile.dames;

public class Promotion {

    // Vérifie et applique la promotion sur le damier
    public static void verifierPromotion(Damier damier) {
        int taille = Damier.TAILLE;

        // --- Promotion des pions blancs (rangée 0) ---
        for (int c = 0; c < taille; c++) {
            String piece = damier.getCase(0, c);
            if ("b".equals(piece)) {
                damier.setCase(0, c, "B"); // devient dame blanche
            }
        }

        // --- Promotion des pions noirs (rangée 9) ---
        for (int c = 0; c < taille; c++) {
            String piece = damier.getCase(taille - 1, c);
            if ("n".equals(piece)) {
                damier.setCase(taille - 1, c, "N"); // devient dame noire
            }
        }
    }
}
