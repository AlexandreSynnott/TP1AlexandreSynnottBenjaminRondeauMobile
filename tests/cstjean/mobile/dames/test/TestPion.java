package cstjean.mobile.dames.test;

import static org.junit.Assert.assertEquals;

import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import org.junit.Test;

/**
 * Classe de test pour la classe {@link Pion}.
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class TestPion {

    /** Damier utilisé pour les tests. */
    private Damier damier = new Damier();

    /** Pion utilisé pour les tests. */
    private Pion pion = new Pion();

    /**
     * Teste la représentation d'un pion blanc dans le damier.
     */
    @Test
    public void testRepresentation() {
        damier.setCase(0, 3, new Pion(Pion.Couleur.BLANC));
        assertEquals('b', damier.getCase(0, 3).getRepresentation());

        pion = damier.getCase(0, 3);
        assertEquals("Pion[BLANC]", pion.toString());
    }
}
