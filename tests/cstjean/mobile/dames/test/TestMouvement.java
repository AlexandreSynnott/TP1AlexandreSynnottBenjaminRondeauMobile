package cstjean.mobile.dames.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cstjean.mobile.dames.Affichage;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Historique;
import cstjean.mobile.dames.Mouvement;
import cstjean.mobile.dames.Pion;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests pour la classe {@link Mouvement}.
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class TestMouvement {

    /** Damier utilisé pour les tests. */
    private Damier damier;

    /** Historique des mouvements utilisés pour les tests. */
    private Historique historique;

    /** Affichage du damier pour les tests. */
    private Affichage affichage;

    /**
     * Prépare un damier, un historique et un affichage avant chaque test.
     */
    @Before
    public void setUp() {
        damier = new Damier();
        historique = new Historique();
        affichage = new Affichage();
    }

    /**
     * Vérifie un déplacement simple d’un pion blanc.
     */
    @Test
    public void testDeplacementSimplePionBlanc() {
        Pion pion = damier.getCase(6, 1);
        assertNotNull("Devrait avoir un pion en (6,1)", pion);
        assertEquals("Pion en (6,1) devrait être blanc", Pion.Couleur.BLANC, pion.getCouleur());

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 5, 0);
        assertTrue("Déplacement (6,1)->(5,0) devrait être valide", resultat);
        assertNotNull("Pion devrait être en (5,0)", damier.getCase(5, 0));
        assertNull("Case (6,1) devrait être vide", damier.getCase(6, 1));
    }

    /**
     * Vérifie un déplacement simple d’un pion noir.
     */
    @Test
    public void testDeplacementSimplePionNoir() {
        Pion pion = damier.getCase(3, 2);
        assertNotNull("Devrait avoir un pion en (3,2)", pion);
        assertEquals("Pion en (3,2) devrait être noir", Pion.Couleur.NOIR, pion.getCouleur());

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 3, 2, 4, 3);
        assertTrue("Déplacement (3,2)->(4,3) devrait être valide", resultat);
        assertNotNull("Pion devrait être en (4,3)", damier.getCase(4, 3));
        assertNull("Case (3,2) devrait être vide", damier.getCase(3, 2));
    }

    /**
     * Vérifie qu’un pion blanc ne peut pas reculer.
     */
    @Test
    public void testDeplacementInvalidePionBlancReculer() {
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 7, 0);
        assertFalse("Déplacement en arrière devrait être invalide pour pion blanc", resultat);
    }

    /**
     * Vérifie qu’un pion noir ne peut pas reculer.
     */
    @Test
    public void testDeplacementInvalidePionNoirReculer() {
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 3, 2, 2, 1);
        assertFalse("Déplacement en arrière devrait être invalide pour pion noir", resultat);
    }

    /**
     * Vérifie qu’un pion ne peut pas aller sur une case déjà occupée.
     */
    @Test
    public void testDeplacementInvalideCaseOccupee() {
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 7, 2);
        assertFalse("Déplacement vers case occupée devrait être invalide", resultat);
    }

    /**
     * Vérifie qu’un pion ne peut pas se déplacer horizontalement ou verticalement.
     */
    @Test
    public void testDeplacementInvalideNonDiagonal() {
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 6, 2);
        assertFalse("Déplacement non diagonal devrait être invalide", resultat);
    }

    /**
     * Vide complètement le damier.
     */
    private void viderDamier() {
        for (int i = 0; i < Damier.TAILLE; i++) {
            for (int j = 0; j < Damier.TAILLE; j++) {
                damier.setCase(i, j, null);
            }
        }
    }
}
