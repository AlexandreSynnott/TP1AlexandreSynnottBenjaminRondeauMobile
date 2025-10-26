package cstjean.mobile.dames.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Historique;
import cstjean.mobile.dames.Mouvement;
import cstjean.mobile.dames.Pion;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test pour la classe {@link cstjean.mobile.dames.Historique}.
 * Elle vérifie le bon fonctionnement de l'historique des mouvements
 * et l'enregistrement des actions effectuées sur le damier.
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class TestHistorique {

    /**
     * Instance du damier utilisée pour les tests.
     */
    private Damier damier;

    /**
     * Instance de l'historique utilisée pour les tests.
     */
    private Historique historique;

    /**
     * Initialise le damier et l'historique avant chaque test.
     */
    @Before
    public void setUp() {
        damier = new Damier();
        historique = new Historique();
    }

    /**
     * Teste le déplacement simple d'un pion blanc.
     */
    @Test
    public void testDeplacementSimplePionBlanc() {
        Pion pion = damier.getCase(6, 1);
        assertNotNull("Devrait avoir un pion en (6,1)", pion);
        assertEquals("Pion en (6,1) devrait être blanc", Pion.Couleur.BLANC, pion.getCouleur());

        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 5, 0);
        assertTrue("Déplacement (6,1)->(5,0) devrait être valide", resultat);
        assertNotNull("Pion devrait être en (5,0) après déplacement", damier.getCase(5, 0));
        assertNull("Case (6,1) devrait être vide après déplacement", damier.getCase(6, 1));
    }

    /**
     * Vérifie que l'historique est vide au démarrage.
     */
    @Test
    public void testHistoriqueVideAuDebut() {
        assertTrue("Historique devrait être vide au début",
                historique.obtenirHistoriqueComplet().isEmpty());
    }

    /**
     * Teste l'ajout d'un mouvement dans l'historique.
     */
    @Test
    public void testAjouterMouvement() {
        historique.ajouterMouvement("31-27");
        assertEquals("Historique devrait avoir 1 mouvement",
                1, historique.obtenirHistoriqueComplet().size());
        assertEquals("Mouvement devrait être '31-27'",
                "31-27", historique.obtenirHistoriqueComplet().getFirst());
    }

    /**
     * Vérifie que l'historique se met à jour après un déplacement.
     */
    @Test
    public void testHistoriqueApresDeplacement() {
        Mouvement.effectuerMouvement(damier, historique, 6, 1, 5, 0);

        assertFalse("Historique ne devrait pas être vide après un mouvement",
                historique.obtenirHistoriqueComplet().isEmpty());
        assertEquals("Historique devrait avoir 1 mouvement",
                1, historique.obtenirHistoriqueComplet().size());

        String mouvement = historique.obtenirHistoriqueComplet().getFirst();
        assertNotNull("Mouvement enregistré ne devrait pas être null", mouvement);
        assertTrue("Mouvement devrait contenir '-'", mouvement.contains("-"));
    }

    /**
     * Vérifie que les prises sont bien enregistrées dans l'historique.
     */
    @Test
    public void testHistoriqueApresPrise() {
        damier.setCase(5, 2, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 3, null);

        Mouvement.effectuerMouvement(damier, historique, 6, 1, 4, 3);

        assertFalse("Historique ne devrait pas être vide après une prise",
                historique.obtenirHistoriqueComplet().isEmpty());

        String mouvement = historique.obtenirHistoriqueComplet().getFirst();
        assertNotNull("Mouvement de prise ne devrait pas être null", mouvement);
        assertTrue("Mouvement de prise devrait contenir '×'", mouvement.contains("×"));
    }

    /**
     * Vérifie l'historique après plusieurs mouvements.
     */
    @Test
    public void testMultipleMouvements() {
        Mouvement.effectuerMouvement(damier, historique, 6, 1, 5, 0);
        Mouvement.effectuerMouvement(damier, historique, 3, 2, 4, 3);

        assertEquals("Historique devrait avoir 2 mouvements",
                2, historique.obtenirHistoriqueComplet().size());

        String premierMouvement = historique.obtenirHistoriqueComplet().get(0);
        String deuxiemeMouvement = historique.obtenirHistoriqueComplet().get(1);

        assertNotNull("Premier mouvement ne devrait pas être null", premierMouvement);
        assertNotNull("Deuxième mouvement ne devrait pas être null", deuxiemeMouvement);
    }

    /**
     * Vérifie la méthode d'effacement de l'historique.
     */
    @Test
    public void testEffacerHistorique() {
        historique.ajouterMouvement("31-27");
        historique.ajouterMouvement("42-38");

        assertFalse("Historique ne devrait pas être vide",
                historique.obtenirHistoriqueComplet().isEmpty());

        historique.effacerHistorique();
        assertTrue("Historique devrait être vide après effacement",
                historique.obtenirHistoriqueComplet().isEmpty());
    }

    /**
     * Vérifie la notation Manoury pour un mouvement blanc.
     */
    @Test
    public void testNotationManouryBlanc() {
        Mouvement.effectuerMouvement(damier, historique, 6, 1, 5, 0);
        String mouvement = historique.obtenirHistoriqueComplet().getFirst();
        assertFalse("Notation pour blancs ne devrait pas avoir de parenthèses",
                mouvement.startsWith("(") && mouvement.endsWith(")"));
    }

    /**
     * Vérifie la notation Manoury pour un mouvement noir.
     */
    @Test
    public void testNotationManouryNoir() {
        Mouvement.effectuerMouvement(damier, historique, 3, 2, 4, 3);
        String mouvement = historique.obtenirHistoriqueComplet().getFirst();
        assertTrue("Notation pour noirs devrait avoir des parenthèses",
                mouvement.startsWith("(") && mouvement.endsWith(")"));
    }

    /**
     * Vérifie que l'affichage de l'historique ne lance pas d'exception.
     */
    @Test
    public void testAfficherHistorique() {
        historique.ajouterMouvement("31-27");
        historique.ajouterMouvement("(42-38)");
        historique.ajouterMouvement("27×18");

        try {
            historique.afficherHistorique();
        } catch (Exception e) {
            fail("afficherHistorique ne devrait pas lancer d'exception: " + e.getMessage());
        }
    }

    /**
     * Vérifie que la méthode obtenirHistoriqueComplet fonctionne correctement.
     */
    @Test
    public void testObtenirHistoriqueComplet() {
        historique.ajouterMouvement("31-27");
        historique.ajouterMouvement("42-38");

        java.util.LinkedList<String> historiqueComplet = historique.obtenirHistoriqueComplet();

        assertNotNull("Historique complet ne devrait pas être null", historiqueComplet);
        assertEquals("Historique complet devrait avoir 2 mouvements",
                2, historiqueComplet.size());
        assertTrue("Devrait contenir '31-27'", historiqueComplet.contains("31-27"));
        assertTrue("Devrait contenir '42-38'", historiqueComplet.contains("42-38"));
    }
}
