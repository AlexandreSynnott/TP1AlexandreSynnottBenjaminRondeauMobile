package cstjean.mobile.dames;

public class Damier {
    public final Pion[][] plateau; // 10x10 - now stores Pion objects
    public static final int TAILLE = 10;

    public Damier() {
        plateau = new Pion[TAILLE][TAILLE];
        initialiser();
    }
    // Initialise les positions de départ du damier avec des objets Pion
    private void initialiser() {
        // Vider le damier
        for (int r = 0; r < TAILLE; r++) {
            for (int c = 0; c < TAILLE; c++) {
                plateau[r][c] = null;
            }
        }

        // Joueur noir (en haut) : rangées 0 à 3.
        for (int r = 0; r <= 3; r++) {
            for (int c = 0; c < TAILLE; c++) {
                if ((r + c) % 2 == 1) { // cases foncées
                    plateau[r][c] = new Pion(Pion.Couleur.NOIR); // pion noir
                }
            }
        }

        // Joueur blanc (en bas) : rangées 6 à 9.
        for (int r = 6; r < TAILLE; r++) {
            for (int c = 0; c < TAILLE; c++) {
                if ((r + c) % 2 == 1) {
                    plateau[r][c] = new Pion(Pion.Couleur.BLANC); // pion blanc
                }
            }
        }
    }

    public Pion getCase(int ligne, int colonne) {
        if (ligne < 0 || ligne >= TAILLE || colonne < 0 || colonne >= TAILLE)
            return null;
        return plateau[ligne][colonne];
    }

    public void setCase(int ligne, int colonne, Pion valeur) {
        if (ligne < 0 || ligne >= TAILLE || colonne < 0 || colonne >= TAILLE)
            return;
        plateau[ligne][colonne] = valeur;
    }
}