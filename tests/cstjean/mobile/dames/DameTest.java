package cstjean.mobile.dames;

import junit.framework.TestCase;

/**
 * Test unitaire de la classe {@link Dame}.
 *
 * <p>
 * Vérifie que les constructeurs fonctionnent correctement
 * et que les méthodes {@link Dame#getRepresentation()} et {@link Dame#toString()}
 * retournent les valeurs attendues selon la couleur du pion.
 *
 * <p>
 * Couverture attendue : 100% des branches liées aux constructeurs et aux représentations.
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */

public class DameTest extends TestCase {

    /**
     * Teste les constructeurs et les méthodes de représentation d'une Dame.
     *
     * <p>
     * Vérifie :
     * <ul>
     *   <li>Constructeur avec couleur NOIR</li>
     *   <li>Constructeur avec couleur BLANC</li>
     *   <li>Constructeur par défaut (BLANC)</li>
     *   <li>Représentation graphique {@link Dame#getRepresentation()}</li>
     *   <li>Représentation textuelle {@link Dame#toString()}</li>
     * </ul>
     */

    public void testConstructorsAndMethods() {
        // Constructeur avec couleur NOIR
        Dame dameNoir = new Dame(Pion.Couleur.NOIR);
        assertEquals('D', dameNoir.getRepresentation());
        assertEquals("Dame[NOIR]", dameNoir.toString());

        // Constructeur avec couleur BLANC
        Dame dameBlanc = new Dame(Pion.Couleur.BLANC);
        assertEquals('d', dameBlanc.getRepresentation());
        assertEquals("Dame[BLANC]", dameBlanc.toString());

        // Constructeur par défaut (supposé BLANC)
        Dame dameDefaut = new Dame();
        assertEquals('d', dameDefaut.getRepresentation());
        assertEquals("Dame[BLANC]", dameDefaut.toString());
    }
}
