package cstjean.mobile.dames;

/**
 * Classe représentant une dame dans le jeu de dames.
 * Hérite de {@link Pion} et surcharge la représentation graphique.
 *
 * @author Alexandre
 * @author Benjamin
 */
public class Dame extends Pion {

    /**
     * Constructeur avec couleur spécifiée.
     *
     * @param couleur La couleur du pion (NOIR ou BLANC)
     */
    public Dame(Couleur couleur) {
        super(couleur);
    }

    /** Constructeur par défaut (BLANC). */
    public Dame() {
        super();
    }

    /**
     * Retourne le caractère représentant la dame selon sa couleur.
     *
     * @return 'D' pour NOIR, 'd' pour BLANC
     */
    @Override
    public char getRepresentation() {
        return (getCouleur() == Couleur.NOIR) ? 'D' : 'd';
    }

    /**
     * Représentation textuelle de la dame.
     *
     * @return "Dame[COULEUR]"
     */
    @Override
    public String toString() {
        return "Dame[" + getCouleur() + "]";
    }
}
