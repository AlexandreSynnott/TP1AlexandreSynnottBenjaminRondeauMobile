package cstjean.mobile.dames.test;

import cstjean.mobile.dames.Affichage;
import cstjean.mobile.dames.Damier;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAffichage {
    private Affichage affichage;
    private Damier damier;

    @Before
    public void setUp() {
        affichage = new Affichage();
        damier = new Damier();
    }

    @Test
    public void testGenerate() {
        String resultat = affichage.generate(damier);
        assertNotNull("La génération ne devrait pas retourner null", resultat);
        assertFalse("La génération ne devrait pas retourner une chaîne vide", resultat.isEmpty());

        // Vérifier le format de base
        String[] lignes = resultat.split("\n");
        assertEquals("Devrait avoir 10 lignes", 10, lignes.length);

        for (String ligne : lignes) {
            String[] cases = ligne.split(" ");
            assertEquals("Chaque ligne devrait avoir 10 cases", 10, cases.length);
        }
    }

    @Test
    public void testGenerateAvecPions() {
        // Vérifier que l'affichage contient bien les pions initiaux
        String resultat = affichage.generate(damier);

        // Devrait contenir des pions 'b' et 'n'
        assertTrue("Devrait contenir des pions blancs 'b'", resultat.contains("b"));
        assertTrue("Devrait contenir des pions noirs 'n'", resultat.contains("n"));
    }

    @Test
    public void testGenerateDamierVide() {
        // Vider le damier
        Damier damierVide = new Damier();
        for (int i = 0; i < Damier.TAILLE; i++) {
            for (int j = 0; j < Damier.TAILLE; j++) {
                damierVide.setCase(i, j, null);
            }
        }

        String resultat = affichage.generate(damierVide);
        // Devrait seulement contenir des '-' et des espaces
        assertFalse("Damier vide ne devrait pas contenir de pions",
                resultat.contains("b") || resultat.contains("n") ||
                        resultat.contains("B") || resultat.contains("N"));
    }
}