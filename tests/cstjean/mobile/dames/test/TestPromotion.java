package cstjean.mobile.dames.test;

import cstjean.mobile.dames.Affichage;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import cstjean.mobile.dames.Dame;
import cstjean.mobile.dames.Promotion;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPromotion {
    private Damier damier;
    private Affichage affichage;

    @Before
    public void setUp() {
        damier = new Damier();
        affichage = new Affichage();
    }

    @Test
    public void testPromotionPionBlanc() {
        // Vider le damier pour un test propre
        viderDamier();

        // Placer un pion blanc sur la ligne de promotion (rangée 0)
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        damier.setCase(0, 1, pionBlanc);

        Promotion.verifierPromotion(damier);

        assertTrue("Le pion blanc en (0,1) devrait être promu en dame",
                damier.getCase(0, 1) instanceof Dame);
        assertEquals("La dame devrait être blanche",
                Pion.Couleur.BLANC, damier.getCase(0, 1).getCouleur());
    }

    @Test
    public void testPromotionPionNoir() {
        // Vider le damier pour un test propre
        viderDamier();

        // Placer un pion noir sur la ligne de promotion (rangée 9)
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(9, 8, pionNoir);

        Promotion.verifierPromotion(damier);

        assertTrue("Le pion noir en (9,8) devrait être promu en dame",
                damier.getCase(9, 8) instanceof Dame);
        assertEquals("La dame devrait être noire",
                Pion.Couleur.NOIR, damier.getCase(9, 8).getCouleur());
    }

    @Test
    public void testPasDePromotionHorsLigne() {
        // Vider le damier pour un test propre
        viderDamier();

        // Placer un pion blanc pas sur la ligne de promotion
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 5, pionBlanc);

        Promotion.verifierPromotion(damier);

        assertFalse("Le pion blanc en (5,5) ne devrait pas être promu",
                damier.getCase(5, 5) instanceof Dame);
        assertTrue("Le pion blanc en (5,5) devrait rester un pion",
                damier.getCase(5, 5) instanceof Pion);
    }

    @Test
    public void testDameNonPromue() {
        // Vider le damier pour un test propre
        viderDamier();

        // Placer une dame sur la ligne de promotion - elle ne devrait pas être changée
        Dame dameExistante = new Dame(Pion.Couleur.BLANC);
        damier.setCase(0, 1, dameExistante);

        Promotion.verifierPromotion(damier);

        // La dame devrait rester une dame
        assertTrue("La dame en (0,1) devrait rester une dame",
                damier.getCase(0, 1) instanceof Dame);
        assertEquals("La dame devrait rester blanche",
                Pion.Couleur.BLANC, damier.getCase(0, 1).getCouleur());
    }

    /**
     * Méthode utilitaire pour vider le damier.
     */
    private void viderDamier() {
        for (int i = 0; i < Damier.TAILLE; i++) {
            for (int j = 0; j < Damier.TAILLE; j++) {
                damier.setCase(i, j, null);
            }
        }
    }
}