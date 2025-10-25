package cstjean.mobile.dames;

import junit.framework.TestCase;

import java.util.LinkedList;

/**
 * Test unitaire de la classe {@link Pion}.
 *
 * <p>
 * Vérifie la création des pions, la modification de leur couleur et la
 * représentation textuelle de pions et dames.
 * </p>
 *
 * <p>
 * Ces tests permettent de s’assurer que les constructeurs, les getters/setters
 * et les méthodes polimorphiques {@link Pion#getRepresentation()} et
 * {@link Pion#toString()} fonctionnent correctement.
 * </p>
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class TestPion extends TestCase {

    /** Pion créé avec la couleur par défaut (blanc). */
    private Pion pionBlanc;

    /** Pion créé explicitement en noir. */
    private Pion pionNoir;

    /**
     * Prépare l’environnement de test avant chaque méthode.
     * Initialise deux objets {@link Pion} :
     * <ul>
     *   <li>pionBlanc : couleur par défaut (BLANC)</li>
     *   <li>pionNoir : couleur spécifiée (NOIR)</li>
     * </ul>
     */
    @Override
    public void setUp() {
        pionBlanc = new Pion();         // Défaut = blanc
        pionNoir = new Pion(Pion.Couleur.NOIR);
    }

    /**
     * Vérifie que le constructeur sans paramètre initialise bien un pion blanc
     * et que le constructeur avec paramètre définit correctement la couleur
     * spécifiée.
     */
    public void testCreer() {
        assertEquals(Pion.Couleur.NOIR, pionNoir.getCouleur());
        assertEquals(Pion.Couleur.BLANC, pionBlanc.getCouleur());
    }

    /**
     * Vérifie que la méthode {@link Pion#setCouleur(Pion.Couleur)} applique
     * correctement la couleur donnée.
     */
    public void testChangementCouleur() {
        pionBlanc.setCouleur(Pion.Couleur.NOIR);
        pionNoir.setCouleur(Pion.Couleur.BLANC);

        assertEquals(Pion.Couleur.NOIR, pionBlanc.getCouleur());
        assertEquals(Pion.Couleur.BLANC, pionNoir.getCouleur());
    }

    /**
     * Affiche dans la console la présentation textuelle de plusieurs pions
     * pour inspection visuelle.
     *
     * <p>
     * Construit une liste mixte de pions puis appelle {@link Pion#toString()}
     * sur chacun d’eux.
     * </p>
     */
    public void testPresentationPion() {
        LinkedList<Pion> pions = new LinkedList<>();
        pions.add(pionNoir);
        pions.add(pionBlanc);
        pions.add(pionNoir);
        pions.add(pionBlanc);
        pions.add(pionNoir);
        pions.add(pionBlanc);

        for (Pion pion : pions) {
            System.out.println(pion.toString());
        }
    }

    /**
     * Vérifie la représentation des objets {@link Dame} (sous-classe de {@link Pion}).
     * <ul>
     *   <li>Dame noire : 'D'</li>
     *   <li>Dame blanche : 'd'</li>
     * </ul>
     */
    public void testDameRepresentation() {
        Dame dameNoire = new Dame(Pion.Couleur.NOIR);
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);

        assertEquals('D', dameNoire.getRepresentation());
        assertEquals('d', dameBlanche.getRepresentation());
    }

}
