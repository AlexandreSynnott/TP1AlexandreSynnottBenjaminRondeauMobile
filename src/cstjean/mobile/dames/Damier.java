package cstjean.mobile.dames;

/**
 * Classe représentant un damier de jeu de dames.
 *
 * <p>
 * Le damier est un tableau 10x10 de cases, chaque case pouvant contenir un objet {@link Pion}
 * ou être vide (null). Les cases foncées sont celles où l'on peut placer des pions,
 * et les cases claires sont neutres.
 *
 * <p>
 * La disposition initiale place les pions noirs en haut et les pions blancs en bas.
 *
 *
 * @author Alexandre Synnott et Benjamin Rondeau
 */
public class Damier {

    /** Plateau de jeu : tableau 10x10 de Pion. */
    private final Pion[][] plateau;

    /** Taille du damier (10x10). */
    public static final int TAILLE = 10;

    /**
     * Constructeur du damier.
     *
     * <p>
     * Crée un plateau vide puis initialise les positions de départ des pions.
     */
    public Damier() {
        plateau = new Pion[TAILLE][TAILLE];
        initialiser();
    }

    /**
     * Initialise les positions de départ du damier avec des objets {@link Pion}.
     *
     * <p>
     * Les cases foncées des rangées 0 à 3 contiennent les pions noirs.
     * Les cases foncées des rangées 6 à 9 contiennent les pions blancs.
     * Les autres cases sont null (vides).
     */
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
                if ((r + c) % 2 == 1) { // cases foncées
                    plateau[r][c] = new Pion(Pion.Couleur.BLANC); // pion blanc
                }
            }
        }
    }

    /**
     * Retourne le {@link Pion} présent à une case spécifique du damier.
     *
     * @param ligne   La ligne de la case (0 à 9)
     * @param colonne La colonne de la case (0 à 9)
     * @return Le {@link Pion} présent à cette case, ou null si la case est vide ou hors limites.
     */
    public Pion getCase(int ligne, int colonne) {
        if (ligne < 0 || ligne >= TAILLE || colonne < 0 || colonne >= TAILLE) {
            return null;
        }
        return plateau[ligne][colonne];
    }

    /**
     * Place un {@link Pion} à une case spécifique du damier.
     *
     * <p>
     * Si la case est hors limites, l'opération est ignorée.
     *
     * @param ligne   La ligne de la case (0 à 9)
     * @param colonne La colonne de la case (0 à 9)
     * @param valeur  Le {@link Pion} à placer, ou null pour vider la case.
     */
    public void setCase(int ligne, int colonne, Pion valeur) {
        if (ligne < 0 || ligne >= TAILLE || colonne < 0 || colonne >= TAILLE) {
            return;
        }
        plateau[ligne][colonne] = valeur;
    }
}
