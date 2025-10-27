package cstjean.mobile.dames.test;

import static cstjean.mobile.dames.Mouvement.coordonneesVersNumeroManoury;
import static org.junit.Assert.*;

import cstjean.mobile.dames.Dame;
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

    /** Prépare un damier et un historique avant chaque test. */
    @Before
    public void setUp() {
        damier = new Damier();
        historique = new Historique();
    }

    /** Vérifie un déplacement simple d'un pion blanc. */
    @Test
    public void testDeplacementSimplePionBlanc() {
        Pion pion = damier.getCase(6, 1);
        assertNotNull(pion);
        assertEquals(Pion.Couleur.BLANC, pion.getCouleur());

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 5, 0);
        assertTrue(resultat);
        assertNotNull(damier.getCase(5, 0));
        assertNull(damier.getCase(6, 1));
    }
    @Test
    public void testDeplacementSimpleInvalideDirectionPion() {
        viderDamier();
        // Crée un pion blanc sur une case noire valide
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        damier.setCase(6, 1, pionBlanc);

        // Tente de déplacer le pion blanc VERS LE BAS (deltaLigneSimple = +1)
        // Mouvement invalide pour un pion blanc
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 7, 2);

        assertFalse("Un pion blanc ne peut pas aller vers le bas", resultat);
    }
    @Test
    public void testDeplacementCaseArriveeOccupee() {
        // Place un pion blanc sur une case (5,4)
        Pion pion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pion);

        // Place un autre pion blanc sur la case d'arrivée (6,5)
        Pion autrePion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(6, 5, autrePion);

        // Essaye de déplacer le pion blanc vers une case occupée (6,5)
        boolean result = Mouvement.effectuerMouvement(damier, historique, 5, 4, 6, 5);

        // Le mouvement devrait échouer car la case d'arrivée est occupée
        assertFalse(result);
    }

    @Test
    public void testEffectuerPrise_PionNoir_AjouteParenthesesNotation() {
        Damier damier = new Damier();
        Historique historique = new Historique();

        // Place un pion noir et un pion blanc pour qu'il y ait une prise valide
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(2, 3, pionNoir);
        damier.setCase(3, 4, new Pion(Pion.Couleur.BLANC));

        // Le pion noir prend le pion blanc (vers 4,5)
        Mouvement.effectuerMouvement(damier, historique, 2, 3, 4, 5);

        // Récupère le dernier mouvement ajouté sans mentionner LinkedList
        var mouvements = historique.obtenirHistoriqueComplet();
        String dernierMouvement = mouvements.get(mouvements.size() - 1);

        // Vérifie que la notation du pion noir est entourée de parenthèses
        assertTrue("La notation devrait être entourée de parenthèses pour un pion noir",
                dernierMouvement.startsWith("(") && dernierMouvement.endsWith(")"));
    }

    @Test
    public void testPionNoirDeplacementValide() {
        Damier damier = new Damier();
        Historique historique = new Historique();

        // Nettoyer le damier
        for (int i = 0; i < Damier.TAILLE; i++) {
            for (int j = 0; j < Damier.TAILLE; j++) {
                damier.setCase(i, j, null);
            }
        }

        // Placer un pion noir à (5,4)
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(5, 4, pionNoir);

        // S'assurer que la case d'arrivée est vide
        damier.setCase(6, 5, null);

        // Tester le déplacement
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 6, 5);
        assertTrue("Le pion noir devrait pouvoir se déplacer d'une case en bas à droite", resultat);
    }

    @Test
    public void testPionBlancDeplacementInvalideVersLeBas() {
        Damier damier = new Damier();
        Historique historique = new Historique();

        // Nettoyer le damier
        for (int i = 0; i < Damier.TAILLE; i++) {
            for (int j = 0; j < Damier.TAILLE; j++) {
                damier.setCase(i, j, null);
            }
        }

        // Placer un pion blanc à (4,4)
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        damier.setCase(4, 4, pionBlanc);

        // Case d’arrivée vers le bas (5,5)
        damier.setCase(5, 5, null);

        // Ce déplacement est invalide pour un pion blanc
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 4, 4, 5, 5);
        assertFalse("Le pion blanc ne peut pas se déplacer vers le bas", resultat);
    }
    /** Vérifie un déplacement simple d'un pion noir. */
    @Test
    public void testDeplacementSimplePionNoir() {
        Pion pion = damier.getCase(3, 2);
        assertNotNull(pion);
        assertEquals(Pion.Couleur.NOIR, pion.getCouleur());

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 3, 2, 4, 3);
        assertTrue(resultat);
        assertNotNull(damier.getCase(4, 3));
        assertNull(damier.getCase(3, 2));
    }

    /** Vérifie qu'un pion blanc ne peut pas reculer. */
    @Test
    public void testDeplacementInvalidePionBlancReculer() {
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 6, 1, 7, 0));
    }

    /** Vérifie qu'un pion noir ne peut pas reculer. */
    @Test
    public void testDeplacementInvalidePionNoirReculer() {
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 3, 2, 2, 1));
    }


    /** Vérifie qu'un pion ne peut pas aller sur une case déjà occupée. */
    @Test
    public void testDeplacementInvalideCaseOccupee() {
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 6, 1, 7, 2));
    }

    /** Teste qu'un pion ne peut pas se déplacer horizontalement ou verticalement. */
    @Test
    public void testDeplacementInvalideNonDiagonal() {
        viderDamier();
        Pion pion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(6, 1, pion);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 6, 1, 6, 2));  // Horizontal
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 6, 1, 7, 1));  // Vertical
    }


    /** Teste un déplacement simple d'une dame dans toutes les directions. */
    @Test
    public void testDeplacementSimpleDame() {
        viderDamier();
        Dame dame = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dame);

        assertTrue(Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3));
        damier.setCase(5, 4, dame); damier.setCase(4, 3, null);
        assertTrue(Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 5));
        damier.setCase(5, 4, dame); damier.setCase(4, 5, null);
        assertTrue(Mouvement.effectuerMouvement(damier, historique, 5, 4, 6, 3));
        damier.setCase(5, 4, dame); damier.setCase(6, 3, null);
        assertTrue(Mouvement.effectuerMouvement(damier, historique, 5, 4, 6, 5));
    }

    /** Teste qu'une dame ne peut pas faire un déplacement simple de plus d'une case. */
    @Test
    public void testDeplacementDameInvalidePlusDuneCase() {
        viderDamier();
        Dame dame = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dame);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2));
    }

    /** Teste une prise simple par une dame. */
    @Test
    public void testPriseSimpleDame() {
        viderDamier();
        Dame dame = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(5, 4, dame);
        damier.setCase(4, 3, pionNoir);
        damier.setCase(3, 2, null);

        assertTrue(Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2));
    }

    /** Teste une prise à longue distance par une dame. */
    @Test
    public void testPriseLongueDistanceDame() {
        viderDamier();
        Dame dame = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(7, 2, dame);
        damier.setCase(4, 5, pionNoir);
        damier.setCase(1, 8, null);
        assertTrue(Mouvement.effectuerMouvement(damier, historique, 7, 2, 1, 8));
    }

    /** Teste qu'une dame ne peut pas prendre si bloquée par un allié. */
    @Test
    public void testPriseDameBloqueeParAllie() {
        viderDamier();
        Dame dame = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dame);
        damier.setCase(4, 3, pionBlanc);
        damier.setCase(3, 2, pionNoir);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 2, 1));
    }

    /** Teste qu'une dame ne peut pas prendre s'il y a plus d'un ennemi sur le chemin. */
    @Test
    public void testPriseDameDeuxEnnemis() {
        viderDamier();
        Dame dame = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir1 = new Pion(Pion.Couleur.NOIR);
        Pion pionNoir2 = new Pion(Pion.Couleur.NOIR);
        damier.setCase(5, 4, dame);
        damier.setCase(4, 3, pionNoir1);
        damier.setCase(3, 2, pionNoir2);
        damier.setCase(2, 1, null);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 2, 1));
    }

    /** Teste une prise simple par un pion. */
    @Test
    public void testPriseSimplePion() {
        viderDamier();
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(5, 4, pionBlanc);
        damier.setCase(4, 3, pionNoir);
        damier.setCase(3, 2, null);
        assertTrue(Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2));
    }

    /** Teste qu'un pion ne peut pas prendre un allié. */
    @Test
    public void testPrisePionAllieInvalide() {
        viderDamier();
        Pion pionBlanc1 = new Pion(Pion.Couleur.BLANC);
        Pion pionBlanc2 = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pionBlanc1);
        damier.setCase(4, 3, pionBlanc2);
        damier.setCase(3, 2, null);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2));
    }

    /** Teste qu'un mouvement échoue si la case de départ est vide. */
    @Test
    public void testMouvementCaseDepartVide() {
        viderDamier();
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3));
    }

    /** Teste qu'un mouvement échoue si la case d'arrivée est occupée. */
    @Test
    public void testMouvementCaseArriveeOccupee() {
        viderDamier();
        Pion pion1 = new Pion(Pion.Couleur.BLANC);
        Pion pion2 = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pion1);
        damier.setCase(4, 3, pion2);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3));
    }

    /** Teste l'enregistrement dans l'historique pour un déplacement simple. */
    @Test
    public void testHistoriqueDeplacementSimple() {
        viderDamier();
        Pion pion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pion);
        Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3);
        assertEquals(1, historique.obtenirHistoriqueComplet().size());
        assertTrue(historique.obtenirHistoriqueComplet().getFirst().contains("-"));
    }

    /** Teste l'enregistrement dans l'historique pour une prise. */
    @Test
    public void testHistoriquePrise() {
        viderDamier();
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(5, 4, pionBlanc);
        damier.setCase(4, 3, pionNoir);
        damier.setCase(3, 2, null);
        Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2);
        assertTrue(historique.obtenirHistoriqueComplet().getFirst().contains("×"));
    }

    /** Teste qu'un mouvement ne modifie pas l'historique si historique est null. */
    @Test
    public void testMouvementSansHistorique() {
        viderDamier();
        Pion pion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pion);
        int tailleAvant = historique.obtenirHistoriqueComplet().size();
        Mouvement.effectuerMouvement(damier, null, 5, 4, 4, 3);
        assertEquals(tailleAvant, historique.obtenirHistoriqueComplet().size());
    }

    /** Teste qu'une dame ne peut pas prendre plusieurs ennemis à la fois. */
    @Test
    public void testPriseDameMultipleEnnemis() {
        viderDamier();
        Dame dame = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir1 = new Pion(Pion.Couleur.NOIR);
        Pion pionNoir2 = new Pion(Pion.Couleur.NOIR);
        damier.setCase(5, 4, dame);
        damier.setCase(4, 3, pionNoir1);
        damier.setCase(3, 2, pionNoir2);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 2, 1));
    }

    /** Teste la conversion des coordonnées en numéro Manoury. */
    @Test
    public void testCoordonneesVersNumeroManoury() {
        assertEquals(1, coordonneesVersNumeroManoury(0, 1));
        assertEquals(50, coordonneesVersNumeroManoury(9, 8));
        assertEquals(-1, coordonneesVersNumeroManoury(0, 0));
        assertEquals(-1, coordonneesVersNumeroManoury(-1, 0));
        assertEquals(-1, coordonneesVersNumeroManoury(10, 5));
    }

    // =========================================================
    // === TESTS SUPPLÉMENTAIRES POUR COUVERTURE À 100 % =======
    // =========================================================

    /** Teste un mouvement totalement invalide (ni prise ni déplacement). */
    @Test
    public void testMouvementInvalideNiPriseNiDeplacement() {
        viderDamier();
        Pion pion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pion);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 2, 1));
    }

    /** Teste un déplacement invalide d’un pion sur deux cases sans prise. */
    @Test
    public void testDeplacementInvalidePionDeuxCases() {
        viderDamier();
        Pion pion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pion);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2));
    }

    /** Teste une tentative de prise invalide d’un pion (distance ≠ 2). */
    @Test
    public void testPrisePionDistanceInvalide() {
        viderDamier();
        Pion pion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pion);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 2, 1));
    }

    /** Teste une tentative de prise par une dame sans ennemi sur la diagonale. */
    @Test
    public void testPriseDameSansEnnemi() {
        viderDamier();
        Dame dame = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dame);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 2, 1));
    }

    /** Vide complètement le damier. */
    private void viderDamier() {
        for (int i = 0; i < Damier.TAILLE; i++)
            for (int j = 0; j < Damier.TAILLE; j++)
                damier.setCase(i, j, null);
    }
}
