package cstjean.mobile.dames.test;

import cstjean.mobile.dames.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestHistorique {

    private Damier damier;
    private Historique historique;

    @Before
    public void setUp() {
        damier = new Damier();
        historique = new Historique();
    }

    @Test
    public void testDeplacementSimplePionBlanc() {
        // Vérifier position initiale
        Pion pion = damier.getCase(6, 1);
        assertNotNull("Devrait avoir un pion en (6,1)", pion);
        assertEquals("Pion en (6,1) devrait être blanc", Pion.Couleur.BLANC, pion.getCouleur());

        // Déplacement valide: (6,1) -> (5,0)
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 5, 0);
        assertTrue("Déplacement (6,1)->(5,0) devrait être valide", resultat);
        assertNotNull("Pion devrait être en (5,0) après déplacement", damier.getCase(5, 0));
        assertNull("Case (6,1) devrait être vide après déplacement", damier.getCase(6, 1));
    }

    @Test
    public void testHistoriqueVideAuDebut() {
        // Vérifier que l'historique est vide au début
        assertTrue("Historique devrait être vide au début",
                historique.obtenirHistoriqueComplet().isEmpty());
    }

    @Test
    public void testAjouterMouvement() {
        // Ajouter un mouvement à l'historique
        historique.ajouterMouvement("31-27");

        // Vérifier que l'historique contient le mouvement
        assertEquals("Historique devrait avoir 1 mouvement",
                1, historique.obtenirHistoriqueComplet().size());
        assertEquals("Mouvement devrait être '31-27'",
                "31-27", historique.obtenirHistoriqueComplet().getFirst());
    }

    @Test
    public void testHistoriqueApresDeplacement() {
        // Effectuer un déplacement
        Mouvement.effectuerMouvement(damier, historique, 6, 1, 5, 0);

        // Vérifier que l'historique n'est plus vide
        assertFalse("Historique ne devrait pas être vide après un mouvement",
                historique.obtenirHistoriqueComplet().isEmpty());

        // Vérifier qu'il y a exactement un mouvement
        assertEquals("Historique devrait avoir 1 mouvement",
                1, historique.obtenirHistoriqueComplet().size());

        // Vérifier que le mouvement a été enregistré
        String mouvement = historique.obtenirHistoriqueComplet().getFirst();
        assertNotNull("Mouvement enregistré ne devrait pas être null", mouvement);
        assertTrue("Mouvement devrait contenir '-'", mouvement.contains("-"));
    }

    @Test
    public void testHistoriqueApresPrise() {
        // Préparer une prise
        damier.setCase(5, 2, new Pion(Pion.Couleur.NOIR));
        damier.setCase(4, 3, null);

        // Effectuer une prise
        Mouvement.effectuerMouvement(damier, historique, 6, 1, 4, 3);

        // Vérifier l'historique
        assertFalse("Historique ne devrait pas être vide après une prise",
                historique.obtenirHistoriqueComplet().isEmpty());

        String mouvement = historique.obtenirHistoriqueComplet().getFirst();
        assertNotNull("Mouvement de prise ne devrait pas être null", mouvement);
        assertTrue("Mouvement de prise devrait contenir '×'", mouvement.contains("×"));
    }

    @Test
    public void testMultipleMouvements() {
        // Effectuer plusieurs mouvements
        Mouvement.effectuerMouvement(damier, historique, 6, 1, 5, 0);
        Mouvement.effectuerMouvement(damier, historique, 3, 2, 4, 3);

        // Vérifier que l'historique contient 2 mouvements
        assertEquals("Historique devrait avoir 2 mouvements",
                2, historique.obtenirHistoriqueComplet().size());

        // Vérifier l'ordre des mouvements
        String premierMouvement = historique.obtenirHistoriqueComplet().get(0);
        String deuxiemeMouvement = historique.obtenirHistoriqueComplet().get(1);

        assertNotNull("Premier mouvement ne devrait pas être null", premierMouvement);
        assertNotNull("Deuxième mouvement ne devrait pas être null", deuxiemeMouvement);
    }

    @Test
    public void testEffacerHistorique() {
        // Ajouter des mouvements
        historique.ajouterMouvement("31-27");
        historique.ajouterMouvement("42-38");

        // Vérifier qu'il y a des mouvements
        assertFalse("Historique ne devrait pas être vide",
                historique.obtenirHistoriqueComplet().isEmpty());

        // Effacer l'historique
        historique.effacerHistorique();

        // Vérifier que l'historique est vide
        assertTrue("Historique devrait être vide après effacement",
                historique.obtenirHistoriqueComplet().isEmpty());
    }

    @Test
    public void testNotationManouryBlanc() {
        // Effectuer un déplacement blanc
        Mouvement.effectuerMouvement(damier, historique, 6, 1, 5, 0);

        String mouvement = historique.obtenirHistoriqueComplet().getFirst();

        // Vérifier que la notation pour les blancs n'a pas de parenthèses
        assertFalse("Notation pour blancs ne devrait pas avoir de parenthèses",
                mouvement.startsWith("(") && mouvement.endsWith(")"));
    }

    @Test
    public void testNotationManouryNoir() {
        // Effectuer un déplacement noir
        Mouvement.effectuerMouvement(damier, historique, 3, 2, 4, 3);

        String mouvement = historique.obtenirHistoriqueComplet().getFirst();

        // Vérifier que la notation pour les noirs a des parenthèses
        assertTrue("Notation pour noirs devrait avoir des parenthèses",
                mouvement.startsWith("(") && mouvement.endsWith(")"));
    }

    @Test
    public void testAfficherHistorique() {
        // Ajouter quelques mouvements
        historique.ajouterMouvement("31-27");
        historique.ajouterMouvement("(42-38)");
        historique.ajouterMouvement("27×18");

        // Cette méthode devrait s'exécuter sans erreur
        // On ne peut pas facilement tester l'affichage console en JUnit,
        // mais on peut s'assurer qu'elle ne lance pas d'exception
        try {
            historique.afficherHistorique();
        } catch (Exception e) {
            fail("afficherHistorique ne devrait pas lancer d'exception: " + e.getMessage());
        }
    }

    @Test
    public void testObtenirHistoriqueComplet() {
        // Ajouter des mouvements
        historique.ajouterMouvement("31-27");
        historique.ajouterMouvement("42-38");

        // Obtenir l'historique complet
        java.util.LinkedList<String> historiqueComplet = historique.obtenirHistoriqueComplet();

        // Vérifier que c'est la même instance (ou une copie, selon l'implémentation)
        assertNotNull("Historique complet ne devrait pas être null", historiqueComplet);
        assertEquals("Historique complet devrait avoir 2 mouvements",
                2, historiqueComplet.size());

        // Vérifier le contenu
        assertTrue("Devrait contenir '31-27'", historiqueComplet.contains("31-27"));
        assertTrue("Devrait contenir '42-38'", historiqueComplet.contains("42-38"));
    }
}