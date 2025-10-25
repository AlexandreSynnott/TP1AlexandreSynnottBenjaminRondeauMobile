package test;

import cstjean.mobile.dames.Affichage;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import junit.framework.TestCase;

public class TestAffichage  extends TestCase {
    private Affichage a = new Affichage();
private Damier damier = new  Damier();
private Pion pion;

public void testGenerate(){
    a.generate(damier);
}
}
