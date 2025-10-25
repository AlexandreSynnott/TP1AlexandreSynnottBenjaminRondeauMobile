package cstjean.mobile.dames;

/**
 * Classe représentant un pion du jeu de dames.
 * Un pion a une couleur : BLANC ou NOIR.
 * Permet la création de pions avec une couleur spécifique ou par défaut (BLANC).
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class Pion {

    /**
     * Enumération des couleurs possibles pour un pion.
     */
    public enum Couleur { BLANC, NOIR }

    /** Couleur actuelle du pion. */
    private Couleur couleur;

    /**
     * Constructeur avec couleur spécifiée.
     *
     * @param couleur La couleur du pion (BLANC ou NOIR)
     */
    public Pion(Couleur couleur) {
        this.couleur = couleur;
    }

    /** Constructeur par défaut : pion blanc. */
    public Pion() {
        this.couleur = Couleur.BLANC;
    }

    /**
     * Retourne la couleur actuelle du pion.
     *
     * @return La couleur du pion
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Modifie la couleur du pion.
     *
     * @param couleur La nouvelle couleur du pion
     */
    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * Retourne le caractère représentant le pion.
     * 'P' pour NOIR, 'p' pour BLANC.
     * Méthode polymorphique pouvant être surchargée par Dame.
     *
     * @return Caractère représentant le pion
     */
    public char getRepresentation() {
        return (couleur == Couleur.NOIR) ? 'n' : 'b';
    }

    /**
     * Représentation textuelle du pion.
     *
     * @return Chaîne au format "Pion[COULEUR]"
     */
    @Override
    public String toString() {
        return "Pion[" + couleur + "]";
    }
}
