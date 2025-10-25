package cstjean.mobile.dames.test;

import cstjean.mobile.dames.Dame;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import junit.framework.TestCase;

public class TestDamier extends TestCase {

    private Damier damier = new Damier();

    public void testDamier() {
        damier.getCase(1,1);
        damier.setCase(1,1,new Dame(Pion.Couleur.NOIR));
        assertEquals('N', damier.getCase(1,1).getRepresentation());
    }
}
