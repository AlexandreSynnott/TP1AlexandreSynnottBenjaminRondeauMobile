package cstjean.mobile.dames.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import cstjean.mobile.dames.Dame;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test pour la classe {@link Damier}.
 *
 * <p>
 * Vérifie le comportement du damier, notamment :
 * - la gestion des cases valides et invalides,
 * - l'initialisation des pions,
 * - et la cohérence des rangées vides et pleines.
 * </p>
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class TestDamier {

    /** Instance du damier utilisée pour les tests. */
    private Damier damier;

    /**
     * Initialise le damier avant chaque test.
     */
    @Before
    public void setUp() {
        damier = new Damier();
    }

    /**
     * Vérifie que l’ajout et la récupération d’une pièce
     * sur le damier fonctionnent correctement.
     */
    @Test
    public void testSetGetCase() {
        damier.setCase(1, 1, new Pion(Pion.Couleur.NOIR));
        assertEquals('n', damier.getCase(1, 1).getRepresentation());

        damier.setCase(1, 1, new Dame(Pion.Couleur.NOIR));
        assertEquals('N', damier.getCase(1, 1).getRepresentation());
    }

    /**
     * Vérifie le comportement des cases en dehors des limites valides du damier.
     */
    @Test
    public void testSetGetCaseCoordonneesInvalides() {
        damier.setCase(-1, 0, new Pion(Pion.Couleur.BLANC));
        assertNull("Case (-1,0) devrait retourner null", damier.getCase(-1, 0));

        damier.setCase(10, 5, new Pion(Pion.Couleur.BLANC));
        assertNull("Case (10,5) devrait retourner null", damier.getCase(10, 5));

        damier.setCase(5, -1, new Pion(Pion.Couleur.BLANC));
        assertNull("Case (5,-1) devrait retourner null", damier.getCase(5, -1));

        damier.setCase(5, 10, new Pion(Pion.Couleur.BLANC));
        assertNull("Case (5,10) devrait retourner null", damier.getCase(5, 10));
    }

    /**
     * Vérifie l’initialisation complète du damier :
     * pions noirs en haut et pions blancs en bas.
     */
    @Test
    public void testInitialisationDamier() {
        // Vérifie les pions noirs (rangées 0 à 3)
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

        // Vérifie les pions blancs (rangées 6 à 9)
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

    /**
     * Vérifie que les rangées centrales du damier (4 et 5)
     * sont bien initialisées comme vides.
     */
    @Test
    public void testCasesVidesMilieu() {
        for (int r = 4; r <= 5; r++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                assertNull("Case (" + r + "," + c + ") devrait être vide", damier.getCase(r, c));
            }
        }
    }
}
