package cstjean.mobile.dames.test;

import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import cstjean.mobile.dames.Perdu;
import junit.framework.TestCase;


public class TestPerdu extends TestCase {

    private Damier damier;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        damier = new Damier();
    }

    public void testJoueurBlancSansPions() {
        // Supprime toutes les pièces du damier
        for (int l = 0; l < Damier.TAILLE; l++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                damier.setCase(l, c, null);
            }
        }
        damier.setCase(4, 6, new Pion(Pion.Couleur.NOIR));

        assertTrue("Le joueur blanc doit avoir perdu (aucun pion)",
                Perdu.joueurAPerdu(damier, Pion.Couleur.BLANC));
    }


    public void testJoueurNoirSansPions() {
        for (int l = 0; l < Damier.TAILLE; l++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                damier.setCase(l, c, null);
            }
        }
        damier.setCase(3, 2, new Pion(Pion.Couleur.BLANC));

        assertTrue("Le joueur noir doit avoir perdu (aucun pion)",
                Perdu.joueurAPerdu(damier, Pion.Couleur.NOIR));
    }

    public void testJoueurBlancBloque() {
        damier.setCase(4, 6, new Pion(Pion.Couleur.NOIR));
        damier.setCase(5, 5, new Pion(Pion.Couleur.NOIR));
        damier.setCase(5, 7, new Pion(Pion.Couleur.NOIR));
        damier.setCase(6, 6, new Pion(Pion.Couleur.BLANC));

        assertTrue("Le joueur blanc doit avoir perdu (aucun déplacement possible)",
                Perdu.joueurAPerdu(damier, Pion.Couleur.BLANC));
    }


    public void testJoueurPeutEncoreBouger() {
        damier.setCase(5, 5, new Pion(Pion.Couleur.BLANC));
        damier.setCase(4, 4, null);
        damier.setCase(4, 6, null);

        assertFalse("Le joueur blanc ne doit pas avoir perdu (peut bouger)",
                Perdu.joueurAPerdu(damier, Pion.Couleur.BLANC));
    }
}
