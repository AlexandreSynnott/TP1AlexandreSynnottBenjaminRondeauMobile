package cstjean.mobile.dames.test;

import cstjean.mobile.dames.Dame;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import junit.framework.TestCase;

public class TestDame extends TestCase {
    private Damier damier = new  Damier();
    private Dame dame  = new Dame();

    public void testRepresentation() {

        damier.setCase(0, 3, new Dame(Pion.Couleur.BLANC));
        dame = (Dame) damier.getCase(0,3);
        System.out.println(dame.getRepresentation());
        System.out.println(dame.toString());

    }
}
