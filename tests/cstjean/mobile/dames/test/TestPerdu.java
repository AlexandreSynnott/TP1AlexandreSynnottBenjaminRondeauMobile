package cstjean.mobile.dames.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cstjean.mobile.dames.Dame;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Perdu;
import cstjean.mobile.dames.Pion;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests pour la classe {@link Perdu}.
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class TestPerdu {

    /** Damier utilisé pour les tests. */
    private Damier damier;

    /**
     * Prépare un damier avant chaque test.
     */
    @Before
    public void setUp() {
        damier = new Damier();
    }
    @Test
    public void testDameAvecDeuxEnnemisSurLaDiagonale() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(6, 6, dameBlanche);

        // Deux pions ennemis sur la même diagonale
        damier.setCase(5, 5, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 4, new Pion(Pion.Couleur.NOIR));

        // Case libre après, pour que la boucle while parcourt
        damier.setCase(3, 3, null);

        assertTrue("Le joueur noir ne doit pas avoir perdu (Dame blanche ne peut pas sauter deux ennemis d’affilée)",
                Perdu.joueurPerd(damier, Pion.Couleur.NOIR) == false);
    }

    /**
     * Vérifie qu'une dame peut se déplacer.
     */
    @Test
    public void testDamePeutBouger() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche);
        damier.setCase(4, 3, null);

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut bouger)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }
    @Test
    public void testDamePeutPrendreLongueDiagonaleAvecCaseVideAvant() {
        Damier damier = new Damier(); // crée un damier vide

        // Place une dame blanche à (4,4)
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(4, 4, dameBlanche);

        // Pion noir à (2,2) sur la diagonale
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(2, 2, pionNoir);

        // Case vide après le pion ennemi (1,1)
        damier.setCase(1, 1, null);

        // Vérifie que la dame peut prendre
        boolean peutPrendre = Perdu.joueurPerd(damier, Pion.Couleur.NOIR) == false;

        // Message du test
        String message = "La dame devrait pouvoir prendre un pion ennemi en diagonale.";

        // Assertion
        assertTrue(message, peutPrendre);
    }

    /**
     * Vérifie qu'une dame peut prendre un pion.
     */
    @Test
    public void testDamePeutPrendre() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche);
        damier.setCase(3, 2, new Pion(Pion.Couleur.NOIR));
        damier.setCase(1, 0, null);

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut prendre)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    /**
     * Vérifie qu'une dame peut prendre à longue distance.
     */
    @Test
    public void testDamePeutPrendreLongueDistance() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(7, 2, dameBlanche);
        damier.setCase(4, 5, new Pion(Pion.Couleur.NOIR));
        damier.setCase(1, 8, null);

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut prendre à longue distance)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    /**
     * Vérifie qu'une dame peut se déplacer dans plusieurs directions.
     */
    @Test
    public void testDamePeutSeDeplacerDansPlusieursDirections() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche);

        damier.setCase(4, 3, null);
        damier.setCase(4, 5, null);
        damier.setCase(6, 3, null);
        damier.setCase(6, 5, null);

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut bouger dans 4 directions)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    /**
     * Vérifie qu'une dame complètement bloquée fait perdre le joueur.
     */
    @Test
    public void testDameBloqueeDeTousCotes() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche);

        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 5, new Pion(Pion.Couleur.NOIR));
        damier.setCase(6, 3, new Pion(Pion.Couleur.NOIR));
        damier.setCase(6, 5, new Pion(Pion.Couleur.NOIR));

        damier.setCase(3, 2, new Pion(Pion.Couleur.NOIR));
        damier.setCase(3, 6, new Pion(Pion.Couleur.NOIR));
        damier.setCase(7, 2, new Pion(Pion.Couleur.NOIR));
        damier.setCase(7, 6, new Pion(Pion.Couleur.NOIR));

        assertTrue("Le joueur blanc doit avoir perdu (dame complètement bloquée)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }
    @Test
    public void testDameRencontreDeuxPionsEnnemiSurDiagonale() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 5, dameBlanche);

        // Premier pion ennemi
        damier.setCase(4, 4, new Pion(Pion.Couleur.NOIR));
        // Deuxième pion ennemi sur la même diagonale
        damier.setCase(3, 3, new Pion(Pion.Couleur.NOIR));

        // Case vide après le deuxième pion
        damier.setCase(2, 2, null);

        // Vérifie que le joueur noir ne perd pas (la dame ne peut pas sauter deux ennemis d'affilée)
        assertFalse("La dame rencontre deux pions ennemis sur la diagonale, doit bloquer la prise",
                Perdu.joueurPerd(damier, Pion.Couleur.NOIR));
    }
    @Test
    public void testDameRencontrePionAllieLongueDiagonale() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 5, dameBlanche);

        // Pion allié sur la diagonale
        damier.setCase(3, 3, new Pion(Pion.Couleur.BLANC));

        // Case après pion allié vide
        damier.setCase(2, 2, null);

        // Le joueur noir ne doit pas perdre, la dame ne peut pas sauter le pion allié
        assertFalse("La dame rencontre un pion allié sur la diagonale longue, ne peut pas prendre",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));

    }

    @Test
    public void testDameRencontreDeuxEnnemisConsecutifs() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 5, dameBlanche);

        // Deux pions ennemis consécutifs
        damier.setCase(4, 4, new Pion(Pion.Couleur.NOIR));
        damier.setCase(3, 3, new Pion(Pion.Couleur.NOIR));

        // Case vide après le second pion
        damier.setCase(2, 2, null);

        // La dame ne peut pas sauter deux pions d’affilée
        assertFalse("La dame rencontre deux pions ennemis consécutifs, ne peut pas prendre",
                Perdu.joueurPerd(damier, Pion.Couleur.NOIR));
    }

    @Test
    public void testDameCaseVideAvantEnnemi() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 5, dameBlanche);

        // Case vide avant le pion ennemi
        damier.setCase(4, 4, null);
        damier.setCase(3, 3, new Pion(Pion.Couleur.NOIR));

        // La dame peut prendre car ennemi trouvé après case vide
        assertFalse("La dame a une case vide avant le pion ennemi, doit pouvoir prendre",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    /**
     * Vérifie qu'une dame peut prendre même si elle ne peut pas se déplacer normalement.
     */
    @Test
    public void testDamePeutPrendreMaisPasBouger() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche);

        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 5, new Pion(Pion.Couleur.NOIR));
        damier.setCase(6, 3, new Pion(Pion.Couleur.NOIR));
        damier.setCase(6, 5, new Pion(Pion.Couleur.NOIR));

        damier.setCase(3, 2, null);

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut prendre même si déplacements simples bloqués)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    /**
     * Vérifie qu'une dame peut prendre une autre dame.
     */
    @Test
    public void testDameContreDame() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        Dame dameNoire = new Dame(Pion.Couleur.NOIR);

        damier.setCase(5, 4, dameBlanche);
        damier.setCase(3, 2, dameNoire);
        damier.setCase(1, 0, null);

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut prendre une autre dame)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    /**
     * Vérifie qu'une dame peut faire une multiple prise.
     */
    @Test
    public void testDamePeutFairePriseMultiple() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche);
        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR));
        damier.setCase(2, 1, new Pion(Pion.Couleur.NOIR));
        damier.setCase(0, 0, null);

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut faire une prise multiple)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    /**
     * Vérifie qu'un pion peut prendre un pion adverse.
     */
    @Test
    public void testPionPeutPrendre() {
        viderDamier();

        damier.setCase(5, 4, new Pion(Pion.Couleur.BLANC));
        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR));
        damier.setCase(3, 2, null);

        assertFalse("Le joueur blanc ne doit pas avoir perdu (peut prendre)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    /**
     * Vérifie qu'un joueur bloque sans possibilité de prise perd la partie.
     */
    @Test
    public void testJoueurBloqueSansPrises() {
        viderDamier();

        damier.setCase(6, 5, new Pion(Pion.Couleur.BLANC));
        damier.setCase(5, 4, new Pion(Pion.Couleur.NOIR));
        damier.setCase(5, 6, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 7, new Pion(Pion.Couleur.NOIR));

        assertTrue("Le joueur blanc doit avoir perdu (complètement bloqué)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }
    @Test
    public void testJoueurPerdSansPions() {
        viderDamier();

        // Le joueur blanc n'a plus de pions
        assertTrue("Le joueur blanc doit avoir perdu (sans pions)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }
    @Test
    public void testJoueurPerdSansDeplacement() {
        viderDamier();

        // Le joueur blanc a encore des pions, mais il ne peut pas se déplacer
        damier.setCase(6, 5, new Pion(Pion.Couleur.BLANC));
        damier.setCase(5, 4, new Pion(Pion.Couleur.NOIR));
        damier.setCase(5, 6, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 7, new Pion(Pion.Couleur.NOIR));

        assertTrue("Le joueur blanc doit avoir perdu (pion bloqué sans mouvement)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }
    @Test
    public void testDamePeutPrendreLongueDistanceDiagonale() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);

        damier.setCase(4, 4, dameBlanche);   // Dame blanche
        damier.setCase(2, 2, pionNoir);      // Pion noir sur la diagonale
        damier.setCase(1, 1, null);          // Case vide après le pion ennemi

        assertTrue("La dame peut capturer le pion ennemi à longue distance",
                Perdu.joueurPerd(damier, Pion.Couleur.NOIR) == false); // Le blanc ne perd pas
    }
    @Test
    public void testDamePeutPrendreLongueDistance_LigneNonCouvert() {
        viderDamier();

        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 5, dameBlanche);

        // Pion ennemi sur la diagonale
        damier.setCase(4, 4, new Pion(Pion.Couleur.NOIR));

        // Case vide après le pion ennemi pour déclencher "if (ennemiTrouve) return true;"
        damier.setCase(3, 3, null);

        assertFalse("La dame peut capturer le pion ennemi à longue distance (ennemiTrouve exécuté)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }



    @Test
    public void testJoueurPerdAvecPionsBloquesSansPrises() {
        viderDamier();

        // Le joueur blanc a des pions mais aucun mouvement possible (aucune prise)
        damier.setCase(6, 5, new Pion(Pion.Couleur.BLANC));
        damier.setCase(5, 4, new Pion(Pion.Couleur.NOIR));
        damier.setCase(5, 6, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 7, new Pion(Pion.Couleur.NOIR));
        damier.setCase(3, 2, new Pion(Pion.Couleur.NOIR));

        assertTrue("Le joueur blanc doit avoir perdu (pion bloqué sans possibilité de prise ou de déplacement)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
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