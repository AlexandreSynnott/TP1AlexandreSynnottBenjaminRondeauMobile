package cstjean.mobile.dames.test;

import static org.junit.Assert.assertEquals;

import cstjean.mobile.dames.Dame;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test pour la classe {@link Dame}.
 *
 * <p>
 * Vérifie les différentes fonctionnalités liées aux dames :
 * création, couleur, représentation et affichage texte.
 * </p>
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class TestDame {

    /** Damier utilisé pour les tests. */
    private Damier damier;

    /** Dame blanche utilisée pour les tests. */
    private Dame dameBlanche;

    /** Dame noire utilisée pour les tests. */
    private Dame dameNoire;

    /**
     * Initialise les objets avant chaque test.
     */
    @Before
    public void setUp() {
        damier = new Damier();
        dameBlanche = new Dame(Pion.Couleur.BLANC);
        dameNoire = new Dame(Pion.Couleur.NOIR);
    }

    /**
     * Vérifie la représentation et le toString() d'une dame blanche.
     */
    @Test
    public void testRepresentation() {
        damier.setCase(0, 3, dameBlanche);
        assertEquals('B', damier.getCase(0, 3).getRepresentation());

        Dame dame = (Dame) damier.getCase(0, 3);
        assertEquals("Dame[BLANC]", dame.toString());
    }

    /**
     * Vérifie la représentation et le toString() d'une dame noire.
     */
    @Test
    public void testRepresentationNoire() {
        damier.setCase(9, 6, dameNoire);
        assertEquals('N', damier.getCase(9, 6).getRepresentation());

        Dame dame = (Dame) damier.getCase(9, 6);
        assertEquals("Dame[NOIR]", dame.toString());
    }

    /**
     * Vérifie la création des dames avec et sans couleur spécifiée.
     */
    @Test
    public void testCreationAvecCouleur() {
        Dame dameCustom = new Dame(Pion.Couleur.NOIR);
        assertEquals(Pion.Couleur.NOIR, dameCustom.getCouleur());

        Dame dameDefault = new Dame();
        assertEquals(Pion.Couleur.BLANC, dameDefault.getCouleur());
    }

    /**
     * Vérifie la modification de la couleur d'une dame.
     */
    @Test
    public void testSetCouleur() {
        Dame dame = new Dame();
        dame.setCouleur(Pion.Couleur.NOIR);
        assertEquals(Pion.Couleur.NOIR, dame.getCouleur());
        assertEquals('N', dame.getRepresentation());
    }
}
