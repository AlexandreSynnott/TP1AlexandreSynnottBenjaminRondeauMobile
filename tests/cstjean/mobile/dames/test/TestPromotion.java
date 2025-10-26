package cstjean.mobile.dames.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cstjean.mobile.dames.Affichage;
import cstjean.mobile.dames.Dame;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import cstjean.mobile.dames.Promotion;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests unitaires pour la classe {@link Promotion}.
 * Vérifie la promotion des pions en dames selon les règles du jeu.
 */
public class TestPromotion {

    /** Damier utilisé pour les tests. */
    private Damier damier;

    /** Affichage pour visualiser le damier (optionnel pour tests). */
    private Affichage affichage;

    /**
     * Initialise le damier et l'affichage avant chaque test.
     */
    @Before
    public void setUp() {
        damier = new Damier();
        affichage = new Affichage();
    }

    /**
     * Vérifie qu'un pion blanc sur la ligne de promotion est promu en dame.
     */
    @Test
    public void testPromotionPionBlanc() {
        viderDamier();

        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        damier.setCase(0, 1, pionBlanc);

        Promotion.verifierPromotion(damier);

        assertTrue("Le pion blanc en (0,1) devrait être promu en dame",
                damier.getCase(0, 1) instanceof Dame);
        assertEquals("La dame devrait être blanche",
                Pion.Couleur.BLANC, damier.getCase(0, 1).getCouleur());
    }

    /**
     * Vérifie qu'un pion noir sur la ligne de promotion est promu en dame.
     */
    @Test
    public void testPromotionPionNoir() {
        viderDamier();

        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(9, 8, pionNoir);

        Promotion.verifierPromotion(damier);

        assertTrue("Le pion noir en (9,8) devrait être promu en dame",
                damier.getCase(9, 8) instanceof Dame);
        assertEquals("La dame devrait être noire",
                Pion.Couleur.NOIR, damier.getCase(9, 8).getCouleur());
    }

    /**
     * Vérifie qu'un pion hors ligne de promotion ne devient pas une dame.
     */
    @Test
    public void testPasDePromotionHorsLigne() {
        viderDamier();

        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 5, pionBlanc);

        Promotion.verifierPromotion(damier);

        assertFalse("Le pion blanc en (5,5) ne devrait pas être promu",
                damier.getCase(5, 5) instanceof Dame);
        assertTrue("Le pion blanc en (5,5) devrait rester un pion",
                damier.getCase(5, 5) instanceof Pion);
    }

    /**
     * Vérifie qu'une dame déjà existante sur la ligne de promotion reste inchangée.
     */
    @Test
    public void testDameNonPromue() {
        viderDamier();

        Dame dameExistante = new Dame(Pion.Couleur.BLANC);
        damier.setCase(0, 1, dameExistante);

        Promotion.verifierPromotion(damier);

        assertTrue("La dame en (0,1) devrait rester une dame",
                damier.getCase(0, 1) instanceof Dame);
        assertEquals("La dame devrait rester blanche",
                Pion.Couleur.BLANC, damier.getCase(0, 1).getCouleur());
    }

    /**
     * Méthode utilitaire pour vider le damier avant chaque test.
     */
    private void viderDamier() {
        for (int i = 0; i < Damier.TAILLE; i++) {
            for (int j = 0; j < Damier.TAILLE; j++) {
                damier.setCase(i, j, null);
            }
        }
    }
}
