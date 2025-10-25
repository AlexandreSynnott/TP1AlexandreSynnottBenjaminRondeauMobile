package cstjean.mobile.dames;

public class Damier {

    public static final int TAILLE = 10;
    public final String[][] plateau; // Représente les cases visuelles
    private Pion[] cases = new Pion[50]; // Représente les 50 cases jouables

    public Damier() {
        plateau = new String[TAILLE][TAILLE];
        initialiser();
    }

    private boolean verifierPosition(int position) {
        return position >= 1 && position <= 50;
    }

    // Initialise les positions de départ du damier
    private void initialiser() {
        for (int r = 0; r < TAILLE; r++) {
            for (int c = 0; c < TAILLE; c++) {
                plateau[r][c] = null;
            }
        }

        // Joueur noir (en haut)
        for (int r = 0; r <= 3; r++) {
            for (int c = 0; c < TAILLE; c++) {
                if ((r + c) % 2 == 1) {
                    plateau[r][c] = "n";
                }
            }
        }

        // Joueur blanc (en bas)
        for (int r = 6; r < TAILLE; r++) {
            for (int c = 0; c < TAILLE; c++) {
                if ((r + c) % 2 == 1) {
                    plateau[r][c] = "b";
                }
            }
        }
    }

    public String getCase(int ligne, int colonne) {
        if (ligne < 0 || ligne >= TAILLE || colonne < 0 || colonne >= TAILLE)
            return null;
        return plateau[ligne][colonne];
    }

    public void setCase(int ligne, int colonne, String valeur) {
        if (ligne < 0 || ligne >= TAILLE || colonne < 0 || colonne >= TAILLE)
            return;
        plateau[ligne][colonne] = valeur;
    }


}
