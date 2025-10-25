package cstjean.mobile.dames.test;

import cstjean.mobile.dames.Dame;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDame {
    private Damier damier;
    private Dame dameBlanche;
    private Dame dameNoire;

    @Before
    public void setUp() {
        damier = new Damier();
        dameBlanche = new Dame(Pion.Couleur.BLANC);
        dameNoire = new Dame(Pion.Couleur.NOIR);
    }

    @Test
    public void testRepresentation() {
        damier.setCase(0, 3, dameBlanche);
        assertEquals('B', damier.getCase(0, 3).getRepresentation());

        Dame dame = (Dame) damier.getCase(0, 3);
        assertEquals("Dame[BLANC]", dame.toString());
    }

    @Test
    public void testRepresentationNoire() {
        damier.setCase(9, 6, dameNoire);
        assertEquals('N', damier.getCase(9, 6).getRepresentation());

        Dame dame = (Dame) damier.getCase(9, 6);
        assertEquals("Dame[NOIR]", dame.toString());
    }

    @Test
    public void testCreationAvecCouleur() {
        Dame dameCustom = new Dame(Pion.Couleur.NOIR);
        assertEquals(Pion.Couleur.NOIR, dameCustom.getCouleur());

        Dame dameDefault = new Dame();
        assertEquals(Pion.Couleur.BLANC, dameDefault.getCouleur());
    }

    @Test
    public void testSetCouleur() {
        Dame dame = new Dame();
        dame.setCouleur(Pion.Couleur.NOIR);
        assertEquals(Pion.Couleur.NOIR, dame.getCouleur());
        assertEquals('N', dame.getRepresentation());
    }
}