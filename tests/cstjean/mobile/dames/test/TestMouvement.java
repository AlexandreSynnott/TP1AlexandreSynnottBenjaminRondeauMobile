package cstjean.mobile.dames.test;

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
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests pour la classe {@link Mouvement}.
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class TestMouvement {

    /** Damier de la partie pour les tests. */
    private Damier damier;

    /** Historique des mouvements pour les tests. */
    private Historique historique;

    /** Initialisation avant chaque test. */
    @Before
    public void setUp() {
        damier = new Damier();
        historique = new Historique();
    }

    /** Vide toutes les cases du damier. */
    private void viderDamier() {
        for (int i = 0; i < Damier.TAILLE; i++) {
            for (int j = 0; j < Damier.TAILLE; j++) {
                damier.setCase(i, j, null);
            }
        }
    }

    @Test
    public void testEffectuerDeplacementSimple() {
        viderDamier();
        Pion pion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pion);
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3);
        assertTrue(resultat);
        assertEquals(pion, damier.getCase(4, 3));
        assertNull(damier.getCase(5, 4));
        assertEquals(1, historique.obtenirHistoriqueComplet().size());
        assertTrue(historique.obtenirHistoriqueComplet().getFirst().contains("-"));
    }

    @Test
    public void testEffectuerPrisePion() {
        viderDamier();
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(5, 4, pionBlanc);
        damier.setCase(4, 3, pionNoir);
        damier.setCase(3, 2, null);
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 3, 2);
        assertTrue(resultat);
        assertEquals(pionBlanc, damier.getCase(3, 2));
        assertNull(damier.getCase(4, 3));
        assertNull(damier.getCase(5, 4));
        assertTrue(historique.obtenirHistoriqueComplet().getFirst().contains("×"));
    }

    @Test
    public void testEffectuerPriseDame() {
        viderDamier();
        Dame dame = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(7, 0, dame);
        damier.setCase(5, 2, pionNoir);
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 7, 0, 4, 3);
        assertTrue(resultat);
        assertEquals(dame, damier.getCase(4, 3));
        assertNull(damier.getCase(5, 2));
        assertNull(damier.getCase(7, 0));
    }

    @Test
    public void testDeplacementsValidesPion() {
        viderDamier();
        Pion pion = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pion);
        List<int[]> deplacements = Mouvement.deplacementsValides(damier, 5, 4);
        assertTrue(deplacements.stream().anyMatch(d -> d[0] == 4 && d[1] == 3));
        assertTrue(deplacements.stream().anyMatch(d -> d[0] == 4 && d[1] == 5));
    }

    @Test
    public void testDeplacementsValidesDame() {
        viderDamier();
        Dame dame = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.setCase(5, 4, dame);
        damier.setCase(3, 2, pionNoir);
        damier.setCase(2, 1, null);
        List<int[]> deplacements = Mouvement.deplacementsValides(damier, 5, 4);
        assertTrue(deplacements.stream().anyMatch(d -> d[0] == 4 && d[1] == 3));
        assertTrue(deplacements.stream().anyMatch(d -> d[0] == 2 && d[1] == 1));
    }

    @Test
    public void testEffectuerMouvementCaseVide() {
        viderDamier();
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3));
    }

    @Test
    public void testEffectuerMouvementCaseArriveeOccupee() {
        viderDamier();
        Pion pion1 = new Pion(Pion.Couleur.BLANC);
        Pion pion2 = new Pion(Pion.Couleur.NOIR);
        damier.setCase(5, 4, pion1);
        damier.setCase(4, 3, pion2);
        assertFalse(Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3));
    }

    @Test
    public void testCoordonneesVersNumeroManoury() {
        assertEquals(1, Mouvement.coordonneesVersNumeroManoury(0, 1));
        assertEquals(50, Mouvement.coordonneesVersNumeroManoury(9, 8));
        assertEquals(-1, Mouvement.coordonneesVersNumeroManoury(0, 0));
    }

    @Test
    public void testEffectuerMouvementAucunDeplacementNiPrise() {
        viderDamier();
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        damier.setCase(5, 4, pionBlanc);

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 5, 6);
        assertFalse(resultat);
        assertEquals(pionBlanc, damier.getCase(5, 4));
        assertNull(damier.getCase(5, 6));
    }

    @Test
    public void testPriseDameBloqueeParAllie() {
        viderDamier();
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);

        damier.setCase(5, 4, dameBlanche);
        damier.setCase(4, 3, pionBlanc);
        damier.setCase(3, 2, pionNoir);

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 2, 1);
        assertFalse(resultat);
    }

    @Test
    public void testPriseDameDeuxEnnemis() {
        viderDamier();
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        Pion pionNoir1 = new Pion(Pion.Couleur.NOIR);
        Pion pionNoir2 = new Pion(Pion.Couleur.NOIR);

        damier.setCase(5, 4, dameBlanche);
        damier.setCase(4, 3, pionNoir1);
        damier.setCase(3, 2, pionNoir2);
        damier.setCase(2, 1, null);

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 2, 1);
        assertFalse(resultat);
    }

    @Test
    public void testPrisePionNoirHistorique() {
        viderDamier();
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        Pion pionBlanc = new Pion(Pion.Couleur.BLANC);

        damier.setCase(2, 3, pionNoir);
        damier.setCase(3, 4, pionBlanc);
        damier.setCase(4, 5, null);

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 2, 3, 4, 5);
        assertTrue(resultat);

        LinkedList<String> coups = historique.obtenirHistoriqueComplet();
        assertEquals(1, coups.size());
        assertTrue(coups.get(0).startsWith("("));
    }

    @Test
    public void testDeplacementsValides_caseVide() {
        viderDamier();
        List<int[]> coups = Mouvement.deplacementsValides(damier, 5, 0);
        assertNotNull(coups);
        assertTrue(coups.isEmpty());
    }
}
