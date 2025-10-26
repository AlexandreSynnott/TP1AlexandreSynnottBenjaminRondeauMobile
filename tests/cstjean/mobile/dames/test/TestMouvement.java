package cstjean.mobile.dames.test;

import static cstjean.mobile.dames.Mouvement.coordonneesVersNumeroManoury;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

    /**
     * Prépare un damier, un historique et un affichage avant chaque test.
     */
    @Before
    public void setUp() {
        damier = new Damier();
        historique = new Historique();
    }

    /**
     * Vérifie un déplacement simple d'un pion blanc.
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
     * Vérifie un déplacement simple d'un pion noir.
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
     * Vérifie qu'un pion blanc ne peut pas reculer.
     */
    @Test
    public void testDeplacementInvalidePionBlancReculer() {
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 7, 0);
        assertFalse("Déplacement en arrière devrait être invalide pour pion blanc", resultat);
    }

    /**
     * Vérifie qu'un pion noir ne peut pas reculer.
     */
    @Test
    public void testDeplacementInvalidePionNoirReculer() {
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 3, 2, 2, 1);
        assertFalse("Déplacement en arrière devrait être invalide pour pion noir", resultat);
    }

    /**
     * Vérifie qu'un pion ne peut pas aller sur une case déjà occupée.
     */
    @Test
    public void testDeplacementInvalideCaseOccupee() {
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 7, 2);
        assertFalse("Déplacement vers case occupée devrait être invalide", resultat);
    }

    /**
     * Vérifie qu'un pion ne peut pas se déplacer horizontalement ou verticalement.
     */
    @Test
    public void testDeplacementInvalideNonDiagonal() {
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 6, 2);
        assertFalse("Déplacement non diagonal devrait être invalide", resultat);
    }

    /**
     * Teste un déplacement simple d'une dame dans toutes les directions.
     */
    @Test
    public void testDeplacementSimpleDame() {
        viderDamier();

        Dame dame = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dame); // Case foncée (5+4=9 impairs)

        // Test déplacement en haut à gauche
        boolean resultat1 = Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3);
        assertTrue("Dame devrait pouvoir se déplacer en haut à gauche", resultat1);

        // Remettre la dame pour tester autre direction
        damier.setCase(5, 4, dame);
        damier.setCase(4, 3, null);

        // Test déplacement en haut à droite
        boolean resultat2 = Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 5);
        assertTrue("Dame devrait pouvoir se déplacer en haut à droite", resultat2);

        // Remettre la dame pour tester autre direction
        damier.setCase(5, 4, dame);
        damier.setCase(4, 5, null);

        // Test déplacement en bas à gauche
        boolean resultat3 = Mouvement.effectuerMouvement(damier, historique, 5, 4, 6, 3);
        assertTrue("Dame devrait pouvoir se déplacer en bas à gauche", resultat3);

        // Remettre la dame pour tester autre direction
        damier.setCase(5, 4, dame);
        damier.setCase(6, 3, null);

        // Test déplacement en bas à droite
        boolean resultat4 = Mouvement.effectuerMouvement(damier, historique, 5, 4, 6, 5);
        assertTrue("Dame devrait pouvoir se déplacer en bas à droite", resultat4);
    }

    /**
     * Teste qu'une dame ne peut pas faire un déplacement simple de plus d'une case.
     */
    @Test
    public void testDeplacementDameInvalidePlusDuneCase() {
        viderDamier();

        Dame dame = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dame);

        // Tentative de déplacement de 2 cases sans prise
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2);
        assertFalse("Dame ne devrait pas pouvoir se déplacer de 2 cases sans prise", resultat);
    }

    /**
     * Teste une prise simple par une dame.
     */
    @Test
    public void testPriseSimpleDame() {
        viderDamier();

        Dame dame = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);

        damier.setCase(5, 4, dame);
        damier.setCase(4, 3, pionNoir);
        damier.setCase(3, 2, null); // Case d'arrivée libre

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2);
        assertTrue("Dame devrait pouvoir prendre un pion", resultat);
        assertNotNull("Dame devrait être sur la case d'arrivée", damier.getCase(3, 2));
        assertNull("Pion pris devrait être retiré", damier.getCase(4, 3));
        assertNull("Case de départ devrait être vide", damier.getCase(5, 4));
    }

    /**
     * Teste une prise à longue distance par une dame.
     */
    @Test
    public void testPriseLongueDistanceDame() {
        viderDamier();

        Dame dame = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);

        damier.setCase(7, 2, dame);
        damier.setCase(4, 5, pionNoir); // Pion à prendre loin de la dame
        damier.setCase(1, 8, null); // Case d'arrivée libre loin derrière

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 7, 2, 1, 8);
        assertTrue("Dame devrait pouvoir prendre à longue distance", resultat);
        assertNotNull("Dame devrait être sur la case d'arrivée", damier.getCase(1, 8));
        assertNull("Pion pris devrait être retiré", damier.getCase(4, 5));
        assertNull("Case de départ devrait être vide", damier.getCase(7, 2));
    }

    /**
     * Teste qu'une dame ne peut pas prendre si bloquée par un allié.
     */
    @Test
    public void testPriseDameBloqueeParAllie() {
        viderDamier();

        Dame dame = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);

        damier.setCase(5, 4, dame);
        damier.setCase(4, 3, pionBlanc); // Allié bloque le chemin
        damier.setCase(3, 2, pionNoir); // Pion ennemi derrière l'allié

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 2, 1);
        assertFalse("Dame ne devrait pas pouvoir prendre si bloquée par un allié", resultat);
    }

    /**
     * Teste qu'une dame ne peut pas prendre s'il y a plus d'un ennemi sur le chemin.
     */
    @Test
    public void testPriseDameDeuxEnnemis() {
        viderDamier();

        Dame dame = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir1 = new Pion(Pion.Couleur.NOIR);
        Pion pionNoir2 = new Pion(Pion.Couleur.NOIR);

        damier.setCase(5, 4, dame);
        damier.setCase(4, 3, pionNoir1); // Premier ennemi
        damier.setCase(3, 2, pionNoir2); // Deuxième ennemi
        damier.setCase(2, 1, null); // Case d'arrivée libre

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 2, 1);
        assertFalse("Dame ne devrait pas pouvoir prendre s'il y a deux ennemis", resultat);
    }

    /**
     * Teste une prise simple par un pion.
     */
    @Test
    public void testPriseSimplePion() {
        viderDamier();

        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);

        damier.setCase(5, 4, pionBlanc);
        damier.setCase(4, 3, pionNoir);
        damier.setCase(3, 2, null); // Case d'arrivée libre

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2);
        assertTrue("Pion devrait pouvoir prendre", resultat);
        assertNotNull("Pion devrait être sur la case d'arrivée", damier.getCase(3, 2));
        assertNull("Pion pris devrait être retiré", damier.getCase(4, 3));
        assertNull("Case de départ devrait être vide", damier.getCase(5, 4));
    }

    /**
     * Teste qu'un pion ne peut pas prendre un allié.
     */
    @Test
    public void testPrisePionAllieInvalide() {
        viderDamier();

        Pion pionBlanc1 = new Pion(Pion.Couleur.BLANC);
        Pion pionBlanc2 = new Pion(Pion.Couleur.BLANC);

        damier.setCase(5, 4, pionBlanc1);
        damier.setCase(4, 3, pionBlanc2); // Allié, pas ennemi
        damier.setCase(3, 2, null);

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2);
        assertFalse("Pion ne devrait pas pouvoir prendre un allié", resultat);
    }

    /**
     * Teste qu'un mouvement échoue si la case de départ est vide.
     */
    @Test
    public void testMouvementCaseDepartVide() {
        viderDamier();

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3);
        assertFalse("Mouvement devrait échouer si case départ vide", resultat);
    }

    /**
     * Teste qu'un mouvement échoue si la case d'arrivée est occupée.
     */
    @Test
    public void testMouvementCaseArriveeOccupee() {
        viderDamier();

        Pion pion1 = new Pion(Pion.Couleur.BLANC);
        Pion pion2 = new Pion(Pion.Couleur.BLANC);

        damier.setCase(5, 4, pion1);
        damier.setCase(4, 3, pion2); // Case d'arrivée occupée

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3);
        assertFalse("Mouvement devrait échouer si case d'arrivée occupée", resultat);
    }

    /**
     * Teste l'enregistrement dans l'historique pour un déplacement simple.
     */
    @Test
    public void testHistoriqueDeplacementSimple() {
        viderDamier();

        Pion pion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pion);

        Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3);

        assertEquals("Historique devrait avoir 1 entrée", 1, historique.obtenirHistoriqueComplet().size());
        String mouvement = historique.obtenirHistoriqueComplet().getFirst();
        assertTrue("Mouvement devrait contenir '-'", mouvement.contains("-"));
    }

    /**
     * Teste l'enregistrement dans l'historique pour une prise.
     */
    @Test
    public void testHistoriquePrise() {
        viderDamier();

        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);

        damier.setCase(5, 4, pionBlanc);
        damier.setCase(4, 3, pionNoir);
        damier.setCase(3, 2, null);

        Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2);

        assertEquals("Historique devrait avoir 1 entrée", 1, historique.obtenirHistoriqueComplet().size());
        String mouvement = historique.obtenirHistoriqueComplet().getFirst();
        assertTrue("Mouvement de prise devrait contenir '×'", mouvement.contains("×"));
    }

    /**
     * Teste que l'historique fonctionne quand il est null.
     */
    @Test
    public void testMouvementSansHistorique() {
        viderDamier();

        Pion pion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pion);

        boolean resultat = Mouvement.effectuerMouvement(damier, null, 5, 4, 4, 3);
        assertTrue("Mouvement devrait fonctionner sans historique", resultat);
    }

    /**
     * Teste la conversion des coordonnées en numéro Manoury.
     */
    @Test
    public void testCoordonneesVersNumeroManoury() {
        // Test quelques cases noires connues
        assertEquals(1, coordonneesVersNumeroManoury(0, 1)); // Première case noire
        assertEquals(50, coordonneesVersNumeroManoury(9, 8)); // Dernière case noire

        // Test case blanche (devrait retourner -1)
        assertEquals(-1, coordonneesVersNumeroManoury(0, 0));

        // Test coordonnées invalides
        assertEquals(-1, coordonneesVersNumeroManoury(-1, 0));
        assertEquals(-1, coordonneesVersNumeroManoury(10, 5));
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