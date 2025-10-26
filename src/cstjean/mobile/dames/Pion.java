package cstjean.mobile.dames;

/**
 * Classe représentant un pion dans le jeu de dames.
 * Un pion possède une couleur (BLANC ou NOIR).
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class Pion {

    /**
     * Enumération représentant les deux couleurs possibles d'un pion.
     */
    public enum Couleur {
        /** Pion de couleur blanche. */
        BLANC,

        /** Pion de couleur noire. */
        NOIR
    }

    /**
     * Couleur actuelle du pion.
     */
    private Couleur couleur;

    /**
     * Constructeur qui initialise le pion avec la couleur spécifiée.
     *
     * @param couleur la couleur du pion (BLANC ou NOIR)
     */
    public Pion(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * Constructeur par défaut.
     * Initialise le pion à la couleur blanche.
     */
    public Pion() {
        this.couleur = Couleur.BLANC;
    }

    /**
     * Retourne la couleur du pion.
     *
     * @return la couleur du pion
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Définit la couleur du pion.
     *
     * @param couleur la nouvelle couleur du pion
     */
    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * Retourne la représentation du pion sous forme de caractère.
     * 'b' pour un pion blanc et 'n' pour un pion noir.
     *
     * @return 'b' si le pion est blanc, 'n' s’il est noir
     */
    public char getRepresentation() {
        return (couleur == Couleur.NOIR) ? 'n' : 'b';
    }

    /**
     * Retourne une représentation textuelle du pion.
     *
     * @return une chaîne représentant le pion et sa couleur
     */
    @Override
    public String toString() {
        return "Pion[" + couleur + "]";
    }
}
