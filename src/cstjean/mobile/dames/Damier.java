package cstjean.mobile.dames;

public class Damier {

    public final String[][] plateau; // 10x10
    public static final int TAILLE = 10;

    public Damier() {
        plateau = new String[TAILLE][TAILLE];
        initialiser();
    }

    // Initialise les positions de départ du damier
    private void initialiser() {
        // Vider le damier
        for (int r = 0; r < TAILLE; r++) {
            for (int c = 0; c < TAILLE; c++) {
                plateau[r][c] = null;
            }
        }

        // Joueur noir (en haut) : rangées 0 à 3
        for (int r = 0; r <= 3; r++) {
            for (int c = 0; c < TAILLE; c++) {
                if ((r + c) % 2 == 1) { // cases foncées
                    plateau[r][c] = "n"; // pion noir
                }
            }
        }

        // Joueur blanc (en bas) : rangées 6 à 9
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

    // Affichage console simple
    public void afficher() {
        System.out.println("   0 1 2 3 4 5 6 7 8 9");
        for (int r = 0; r < TAILLE; r++) {
            System.out.print(r + " ");
            for (int c = 0; c < TAILLE; c++) {
                if ((r + c) % 2 == 0)
                    System.out.print(" ."); // case claire
                else {
                    String valeur = plateau[r][c];
                    if (valeur == null) System.out.print(" _"); // case foncée vide
                    else System.out.print(" " + valeur);
                }
            }
            System.out.println();
        }
    }
}

