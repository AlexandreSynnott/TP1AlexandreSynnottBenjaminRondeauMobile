package cstjean.mobile.dames.test;

import cstjean.mobile.dames.Dame;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDamier {
    private Damier damier;

    @Before
    public void setUp() {
        damier = new Damier();
    }

    @Test
    public void testSetGetCase() {
        damier.setCase(1, 1, new Pion(Pion.Couleur.NOIR));
        assertEquals('n', damier.getCase(1, 1).getRepresentation());

        damier.setCase(1, 1, new Dame(Pion.Couleur.NOIR));
        assertEquals('N', damier.getCase(1, 1).getRepresentation());
    }

    @Test
    public void testSetGetCaseCoordonneesInvalides() {
        // Test avec coordonnées hors limites
        damier.setCase(-1, 0, new Pion(Pion.Couleur.BLANC));
        assertNull("Case (-1,0) devrait retourner null", damier.getCase(-1, 0));

        damier.setCase(10, 5, new Pion(Pion.Couleur.BLANC));
        assertNull("Case (10,5) devrait retourner null", damier.getCase(10, 5));

        damier.setCase(5, -1, new Pion(Pion.Couleur.BLANC));
        assertNull("Case (5,-1) devrait retourner null", damier.getCase(5, -1));

        damier.setCase(5, 10, new Pion(Pion.Couleur.BLANC));
        assertNull("Case (5,10) devrait retourner null", damier.getCase(5, 10));
    }

    @Test
    public void testInitialisationDamier() {
        // Vérifier que le damier est correctement initialisé
        // Pions noirs en haut (rangées 0-3)
        for (int r = 0; r <= 3; r++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                if ((r + c) % 2 == 1) {
                    Pion pion = damier.getCase(r, c);
                    assertNotNull("Case (" + r + "," + c + ") devrait avoir un pion", pion);
                    assertEquals("Pion en (" + r + "," + c + ") devrait être noir",
                            Pion.Couleur.NOIR, pion.getCouleur());
                }
            }
        }

        // Pions blancs en bas (rangées 6-9)
        for (int r = 6; r < Damier.TAILLE; r++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                if ((r + c) % 2 == 1) {
                    Pion pion = damier.getCase(r, c);
                    assertNotNull("Case (" + r + "," + c + ") devrait avoir un pion", pion);
                    assertEquals("Pion en (" + r + "," + c + ") devrait être blanc",
                            Pion.Couleur.BLANC, pion.getCouleur());
                }
            }
        }
    }

    @Test
    public void testCasesVidesMilieu() {
        // Les rangées du milieu (4 et 5) devraient être vides
        for (int r = 4; r <= 5; r++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                assertNull("Case (" + r + "," + c + ") devrait être vide", damier.getCase(r, c));
            }
        }
    }
}