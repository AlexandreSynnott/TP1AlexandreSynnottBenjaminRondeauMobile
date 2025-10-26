package cstjean.mobile.dames.test;

import cstjean.mobile.dames.Dame;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Perdu;
import cstjean.mobile.dames.Pion;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPerdu {

    private Damier damier;

    @Before
    public void setUp() {
        damier = new Damier();
    }

    @Test
    public void testDamePeutBouger() {
        // Supprime toutes les pièces du damier
        viderDamier();

        // Crée une dame qui peut se déplacer - must be on dark square
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche); // (5,4) is dark square (5+4=9 odd)
        damier.setCase(4, 3, null); // Case libre pour se déplacer (dark square)

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut bouger)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    @Test
    public void testDamePeutPrendre() {
        // Supprime toutes les pièces du damier
        viderDamier();

        // Crée une dame qui peut prendre
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche); // Dark square
        damier.setCase(3, 2, new Pion(Pion.Couleur.NOIR)); // Pion noir à prendre (dark square)
        damier.setCase(1, 0, null); // Case d'arrivée libre (dark square)

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut prendre)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    @Test
    public void testDamePeutPrendreLongueDistance() {
        // Supprime toutes les pièces du damier
        viderDamier();

        // Crée une dame qui peut prendre à longue distance avec plusieurs cases libres après
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(7, 2, dameBlanche); // Dark square
        damier.setCase(4, 5, new Pion(Pion.Couleur.NOIR)); // Pion noir à prendre (dark square)
        damier.setCase(1, 8, null); // Case d'arrivée libre loin derrière (dark square)

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut prendre à longue distance)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    @Test
    public void testDamePeutSeDeplacerDansPlusieursDirections() {
        // Supprime toutes les pièces du damier
        viderDamier();

        // Crée une dame qui peut se déplacer dans plusieurs directions
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 5, dameBlanche); // Dark square (5+5=10 even - wait this is light square!)
        // Correction: (5,5) est une case claire, utilisons (5,4) à la place
        damier.setCase(5, 4, dameBlanche); // Dark square (5+4=9 odd)

        // Cases libres dans les 4 directions diagonales
        damier.setCase(4, 3, null); // Haut-gauche
        damier.setCase(4, 5, null); // Haut-droite
        damier.setCase(6, 3, null); // Bas-gauche
        damier.setCase(6, 5, null); // Bas-droite

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut bouger dans 4 directions)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    @Test
    public void testDameBloqueeDeTousCotes() {
        // Supprime toutes les pièces du damier
        viderDamier();

        // Crée une dame complètement bloquée de tous les côtés
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche); // Dark square

        // Bloquer toutes les directions diagonales
        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR)); // Haut-gauche
        damier.setCase(4, 5, new Pion(Pion.Couleur.NOIR)); // Haut-droite
        damier.setCase(6, 3, new Pion(Pion.Couleur.NOIR)); // Bas-gauche
        damier.setCase(6, 5, new Pion(Pion.Couleur.NOIR)); // Bas-droite

        // Aussi bloquer les prises en remplissant les cases derrière
        damier.setCase(3, 2, new Pion(Pion.Couleur.NOIR)); // Derrière haut-gauche
        damier.setCase(3, 6, new Pion(Pion.Couleur.NOIR)); // Derrière haut-droite
        damier.setCase(7, 2, new Pion(Pion.Couleur.NOIR)); // Derrière bas-gauche
        damier.setCase(7, 6, new Pion(Pion.Couleur.NOIR)); // Derrière bas-droite

        assertTrue("Le joueur blanc doit avoir perdu (dame complètement bloquée)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    @Test
    public void testDamePeutPrendreMaisPasBouger() {
        // Supprime toutes les pièces du damier
        viderDamier();

        // Crée une dame qui ne peut pas se déplacer normalement mais peut prendre
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche); // Dark square

        // Bloquer tous les déplacements simples
        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR)); // Haut-gauche
        damier.setCase(4, 5, new Pion(Pion.Couleur.NOIR)); // Haut-droite
        damier.setCase(6, 3, new Pion(Pion.Couleur.NOIR)); // Bas-gauche
        damier.setCase(6, 5, new Pion(Pion.Couleur.NOIR)); // Bas-droite

        // Mais permettre une prise à longue distance
        damier.setCase(3, 2, null); // Case libre derrière l'ennemi en (4,3)
        // Note: La dame peut prendre le pion en (4,3) et atterrir en (3,2) ou plus loin

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut prendre même si déplacements simples bloqués)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    @Test
    public void testDameContreDame() {
        // Supprime toutes les pièces du damier
        viderDamier();

        // Test une dame contre une autre dame
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        Dame dameNoire = new Dame(Pion.Couleur.NOIR);

        damier.setCase(5, 4, dameBlanche); // Dark square
        damier.setCase(3, 2, dameNoire); // Dark square - dame ennemie à prendre
        damier.setCase(1, 0, null); // Case d'arrivée libre

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut prendre une autre dame)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    @Test
    public void testDamePeutFairePriseMultiple() {
        // Supprime toutes les pièces du damier
        viderDamier();

        // Crée une situation où la dame peut faire une prise multiple
        // Note: La logique actuelle de Perdu ne vérifie pas les prises multiples en un tour,
        // mais vérifie seulement si au moins une prise est possible
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche); // Dark square

        // Plusieurs pions ennemis alignés
        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR)); // Premier pion à prendre
        damier.setCase(2, 1, new Pion(Pion.Couleur.NOIR)); // Deuxième pion à prendre
        damier.setCase(0, 0, null); // Case d'arrivée libre après les deux prises

        assertFalse("Le joueur blanc ne doit pas avoir perdu (dame peut faire une prise multiple)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    @Test
    public void testPionPeutPrendre() {
        // Supprime toutes les pièces du damier
        viderDamier();

        // Crée une situation où le pion blanc peut prendre
        damier.setCase(5, 4, new Pion(Pion.Couleur.BLANC)); // Dark square
        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR)); // Pion noir à prendre (dark square)
        damier.setCase(3, 2, null); // Case d'arrivée libre (dark square)

        assertFalse("Le joueur blanc ne doit pas avoir perdu (peut prendre)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
    }

    @Test
    public void testJoueurBloqueSansPrises() {
        // Supprime toutes les pièces du damier
        viderDamier();

        // Crée une situation où le pion est COMPLÈTEMENT bloqué - pas de captures possibles
        damier.setCase(6, 5, new Pion(Pion.Couleur.BLANC));

        // Block ALL possible moves and captures
        damier.setCase(5, 4, new Pion(Pion.Couleur.NOIR));
        damier.setCase(5, 6, new Pion(Pion.Couleur.NOIR));

        damier.setCase(4, 3, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 7, new Pion(Pion.Couleur.NOIR));

        assertTrue("Le joueur blanc doit avoir perdu (complètement bloqué)",
                Perdu.joueurPerd(damier, Pion.Couleur.BLANC));
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