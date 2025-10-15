package cstjean.mobile.dames;

public class Damier {

    public final String[][] plateau; // 10x10
    public static final int TAILLE = 10;
    private final Pion[] cases;

    public Damier() {
        plateau = new String[TAILLE][TAILLE];
        cases = new Pion[50];
        initialiser();

        cases = new Pion[50];

        for (int i = 0; i < 50; i++) {
            if (i < 20) {
                cases[i] = new Pion(Pion.Couleur.NOIR);
            } else if (i >= 30) {
                cases[i] = new Pion(Pion.Couleur.BLANC);
            }
        }
    }
    private boolean verifierPosition(int position) {
        return position >= 1 && position <= 50;
    }
    /**
     * Retourne le pion à une position donnée.
     *
     * @param position La position (1..50) à consulter
     * @return Le pion à cette position ou null si vide ou invalide
     */
    public Pion getPion(int position) {
        if (verifierPosition(position)) {
            return cases[position - 1];
        }
        return null;
    }
    // Initialise les positions de départ du damier
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
                    plateau[r][c] = "n"; // pion noir
                }
            }
        }

        // Joueur blanc (en bas) : rangées 6 à 9.
        for (int r = 6; r < TAILLE; r++) {
            for (int c = 0; c < TAILLE; c++) {
                if ((r + c) % 2 == 1) {
                    plateau[r][c] = "b"; // pion blanc
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

