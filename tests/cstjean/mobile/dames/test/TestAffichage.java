package cstjean.mobile.dames.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import cstjean.mobile.dames.Affichage;
import cstjean.mobile.dames.Damier;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de tests unitaires pour la classe {@link Affichage}.
 *
 * <p>
 * Cette classe vérifie que la génération textuelle du damier
 * est correcte, qu’elle affiche bien les pions, et qu’elle
 * respecte la structure 10x10 du jeu de dames.
 * </p>
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class TestAffichage {

    /** Instance utilisée pour tester la génération de l'affichage. */
    private Affichage affichage;

    /** Damier utilisé pour effectuer les tests. */
    private Damier damier;

    /**
     * Initialise les objets avant chaque test.
     *
     * <p>
     * Cette méthode est exécutée automatiquement avant chaque
     * méthode de test grâce à l’annotation {@code @Before}.
     * </p>
     */
    @Before
    public void setUp() {
        affichage = new Affichage();
        damier = new Damier();
    }

    /**
     * Vérifie que la méthode {@code generate()} retourne une
     * représentation valide du damier (non nulle et avec 10x10 cases).
     */
    @Test
    public void testGenerate() {
        String resultat = affichage.generate(damier);
        assertNotNull("La génération ne devrait pas retourner null", resultat);
        assertFalse("La génération ne devrait pas retourner une chaîne vide", resultat.isEmpty());

        // Vérifier le format de base
        String[] lignes = resultat.split("\n");
        assertEquals("Devrait avoir 10 lignes", 10, lignes.length);

        for (String ligne : lignes) {
            String[] casesLigne = ligne.split(" ");
            assertEquals("Chaque ligne devrait avoir 10 cases", 10, casesLigne.length);
        }
    }

    /**
     * Vérifie que l’affichage initial contient bien des pions blancs et noirs.
     */
    @Test
    public void testGenerateAvecPions() {
        String resultat = affichage.generate(damier);

        assertTrue("Devrait contenir des pions blancs 'b'", resultat.contains("b"));
        assertTrue("Devrait contenir des pions noirs 'n'", resultat.contains("n"));
    }

    /**
     * Vérifie que la génération du damier vide ne contient aucun pion.
     */
    @Test
    public void testGenerateDamierVide() {
        Damier damierVide = new Damier();

        // On vide toutes les cases
        for (int i = 0; i < Damier.TAILLE; i++) {
            for (int j = 0; j < Damier.TAILLE; j++) {
                damierVide.setCase(i, j, null);
            }
        }

        String resultat = affichage.generate(damierVide);

        // Vérifie qu’il n’y a aucun pion (b, n, B, N)
        assertFalse("Le damier vide ne devrait pas contenir de pions",
                resultat.contains("b") || resultat.contains("n") ||
                        resultat.contains("B") || resultat.contains("N"));
    }
}
