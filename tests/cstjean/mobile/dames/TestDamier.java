package cstjean.mobile.dames;

import junit.framework.TestCase;

/**
 * Tests unitaires de la classe {@link Damier}.
 * <p>
 * Ces tests vérifient :
 * <ul>
 *     <li>Que le damier s’initialise correctement (pions noirs et blancs bien placés).</li>
 *     <li>Que les méthodes {@link Damier#getCase(int, int)} et {@link Damier#setCase(int, int, String)} fonctionnent.</li>
 *     <li>Que les positions hors limites sont bien ignorées.</li>
 * </ul>
 * </p>
 *
 * @author
 *     Alexandre Synnott
 */
public class TestDamier extends TestCase {

    /** Damier utilisé pour les tests. */
    private Damier damier;

    @Override
    public void setUp() {
        damier = new Damier();
    }

    /**
     * Vérifie que le damier est bien initialisé :
     * - Les 4 premières lignes contiennent des "n" (pions noirs)
     * - Les 4 dernières lignes contiennent des "b" (pions blancs)
     * - Les cases centrales sont vides
     */
    public void testInitialisation() {
        // Pions noirs (rangées 0 à 3)
        for (int r = 0; r <= 3; r++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                if ((r + c) % 2 == 1) {
                    assertEquals("n", damier.getCase(r, c));
                } else {
                    assertNull(damier.getCase(r, c));
                }
            }
        }

        // Pions blancs (rangées 6 à 9)
        for (int r = 6; r < Damier.TAILLE; r++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                if ((r + c) % 2 == 1) {
                    assertEquals("b", damier.getCase(r, c));
                } else {
                    assertNull(damier.getCase(r, c));
                }
            }
        }

        // Zones centrales (4 et 5) doivent être vides
        for (int r = 4; r <= 5; r++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                assertNull(damier.getCase(r, c));
            }
        }
    }

    /**
     * Teste la modification d’une case avec {@link Damier#setCase(int, int, String)}.
     */
    public void testSetEtGetCase() {
        damier.setCase(4, 3, "b");
        assertEquals("b", damier.getCase(4, 3));

        damier.setCase(4, 3, "n");
        assertEquals("n", damier.getCase(4, 3));
    }

    /**
     * Vérifie que les coordonnées invalides (hors du tableau)
     * sont correctement ignorées.
     */
    public void testCasesInvalides() {
        // Affectation invalide
        damier.setCase(-1, 5, "b");
        damier.setCase(10, 2, "n");
        damier.setCase(2, -3, "b");
        damier.setCase(3, 10, "n");

        // Lecture invalide
        assertNull(damier.getCase(-1, 5));
        assertNull(damier.getCase(10, 2));
        assertNull(damier.getCase(2, -3));
        assertNull(damier.getCase(3, 10));
    }

    /**
     * Vérifie que le plateau est bien un tableau 10x10.
     */
    public void testTailleDuPlateau() {
        assertEquals(10, damier.plateau.length);
        assertEquals(10, damier.plateau[0].length);
    }
}
