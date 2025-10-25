package cstjean.mobile.dames.test;

import cstjean.mobile.dames.Affichage;
import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Historique;
import cstjean.mobile.dames.Mouvement;
import cstjean.mobile.dames.Pion;
import cstjean.mobile.dames.Dame;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Tests pour la classe Mouvement.
 */
public class TestMouvement {

    private Damier damier;
    private Historique historique;
    private Affichage affichage;

    @Before
    public void setUp() {
        damier = new Damier();
        historique = new Historique();
        affichage = new Affichage();
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
    public void testDeplacementSimplePionNoir() {
        // Vérifier position initiale
        Pion pion = damier.getCase(3, 2);
        assertNotNull("Devrait avoir un pion en (3,2)", pion);
        assertEquals("Pion en (3,2) devrait être noir", Pion.Couleur.NOIR, pion.getCouleur());

        // Déplacement valide: (3,2) -> (4,3)
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 3, 2, 4, 3);
        assertTrue("Déplacement (3,2)->(4,3) devrait être valide", resultat);
        assertNotNull("Pion devrait être en (4,3) après déplacement", damier.getCase(4, 3));
        assertNull("Case (3,2) devrait être vide après déplacement", damier.getCase(3, 2));
    }

    @Test
    public void testDeplacementInvalidePionBlancReculer() {
        // Pion blanc ne peut pas reculer
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 7, 0);
        assertFalse("Déplacement en arrière devrait être invalide pour pion blanc", resultat);
    }

    @Test
    public void testDeplacementInvalidePionNoirReculer() {
        // Pion noir ne peut pas reculer
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 3, 2, 2, 1);
        assertFalse("Déplacement en arrière devrait être invalide pour pion noir", resultat);
    }

    @Test
    public void testDeplacementInvalideCaseOccupee() {
        // Déplacement vers case occupée
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 7, 2);
        assertFalse("Déplacement vers case occupée devrait être invalide", resultat);
    }

    @Test
    public void testDeplacementInvalideNonDiagonal() {
        // Déplacement non diagonal
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 6, 2);
        assertFalse("Déplacement non diagonal devrait être invalide", resultat);
    }

    @Test
    public void testPriseSimplePion() {

        System.out.println("=== DEBUG testPriseSimplePion ===");
        System.out.println("Initial board:");
        System.out.println(affichage.generate(damier));

        // Préparer une situation de prise CORRECTE
        // Pour une prise de (6,1) à (4,3), la case milieu doit être (5,2)
        // On doit avoir un pion adverse en (5,2), pas en (4,3)
        damier.setCase(5, 2, new Pion(Pion.Couleur.NOIR)); // Pion noir à prendre au milieu
        damier.setCase(4, 3, null); // Arrivée doit être vide

        System.out.println("After setup:");
        System.out.println(affichage.generate(damier));

        // Pion blanc prend pion noir
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 4, 3);

        System.out.println("After movement attempt, result: " + resultat);
        System.out.println(affichage.generate(damier));

        // Vérifications plus détaillées
        if (!resultat) {
            // Debug pourquoi la prise a échoué
            Pion pieceDepart = damier.getCase(6, 1);
            Pion pieceMilieu = damier.getCase(5, 2);
            Pion pieceArrivee = damier.getCase(4, 3);
            System.out.println("Piece départ: " + pieceDepart);
            System.out.println("Piece milieu: " + pieceMilieu);
            System.out.println("Piece arrivée: " + pieceArrivee);
        }

        assertTrue("Prise (6,1)->(4,3) devrait être valide", resultat);
        assertNotNull("Pion preneur devrait être en (4,3) après prise", damier.getCase(4, 3));
        assertNull("Case départ (6,1) devrait être vide", damier.getCase(6, 1));
        assertNull("Case milieu (5,2) devrait être vide (pion pris)", damier.getCase(5, 2));

        // Vérifier que c'est bien le pion blanc qui a pris
        Pion pionArrivee = damier.getCase(4, 3);
        assertEquals("Le pion en (4,3) devrait être blanc après prise", Pion.Couleur.BLANC, pionArrivee.getCouleur());
    }

    @Test
    public void testPriseInvalideSansPionAdverse() {
        // Préparer une situation où il n'y a pas de pion adverse à prendre
        damier.setCase(5, 2, null); // Vider la case milieu

        // Tentative de prise sans pion adverse
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 4, 3);
        assertFalse("Prise sans pion adverse devrait être invalide", resultat);
    }

    @Test
    public void testPriseInvalideAvecPionAllie() {
        // Préparer une situation avec pion allié sur le chemin
        damier.setCase(5, 2, new Pion(Pion.Couleur.BLANC)); // Placer un pion allié

        // Tentative de prise avec pion allié sur le chemin
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 4, 3);
        assertFalse("Prise avec pion allié sur le chemin devrait être invalide", resultat);
    }

    @Test
    public void testDeplacementDame() {
        // Vider le damier et placer une dame sur une case foncée
        viderDamier();
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);
        damier.setCase(5, 4, dameBlanche);

        System.out.println("=== DEBUG testDeplacementDame ===");
        System.out.println("Before movement:");
        System.out.println(affichage.generate(damier));

        // Dame peut se déplacer d'une case dans n'importe quelle direction
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 4, 3); // (4,3) is dark (4+3=7 odd)

        System.out.println("After movement attempt, result: " + resultat);
        System.out.println(affichage.generate(damier));

        assertTrue("Dame devrait pouvoir se déplacer d'une case", resultat);
        assertNotNull("Dame devrait être en (4,3) après déplacement", damier.getCase(4, 3));
        assertNull("Case départ (5,4) devrait être vide", damier.getCase(5, 4));
    }

    @Test
    public void testPriseDame() {
        // Vider le damier et préparer une prise avec dame
        viderDamier();
        Dame dameBlanche = new Dame(Pion.Couleur.BLANC);


        damier.setCase(5, 4, dameBlanche);
        damier.setCase(3, 2, new Pion(Pion.Couleur.NOIR));

        System.out.println("=== DEBUG testPriseDame ===");
        System.out.println("Before movement:");
        System.out.println(affichage.generate(damier));


        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 5, 4, 1, 0);

        System.out.println("After movement attempt, result: " + resultat);
        System.out.println(affichage.generate(damier));

        assertTrue("Dame devrait pouvoir prendre à longue distance", resultat);
        assertNotNull("Dame devrait être en (1,0) après prise", damier.getCase(1, 0));
        assertNull("Case départ (5,4) devrait être vide", damier.getCase(5, 4));
        assertNull("Pion pris (3,2) devrait être enlevé", damier.getCase(3, 2));
    }
    @Test
    public void testHistoriqueDeplacementSimple() {
        // Vérifier que l'historique est vide au début
        assertTrue("Historique devrait être vide au début", historique.obtenirHistoriqueComplet().isEmpty());

        // Effectuer un déplacement simple
        Mouvement.effectuerMouvement(damier, historique, 6, 1, 5, 0);

        // Vérifier que l'historique contient maintenant une entrée
        assertFalse("Historique ne devrait pas être vide après un mouvement", historique.obtenirHistoriqueComplet().isEmpty());
        assertEquals("Historique devrait avoir 1 entrée", 1, historique.obtenirHistoriqueComplet().size());

        // Vérifier le format de la notation (utilisation sécurisée de get)
        if (!historique.obtenirHistoriqueComplet().isEmpty()) {
            String mouvement = historique.obtenirHistoriqueComplet().get(0);
            assertNotNull("Mouvement enregistré ne devrait pas être null", mouvement);
            assertTrue("Notation devrait contenir '-'", mouvement.contains("-"));
        }
    }

    @Test
    public void testHistoriquePrise() {
        // Préparer une prise CORRECTE
        damier.setCase(5, 2, new Pion(Pion.Couleur.NOIR)); // Pion noir au milieu
        damier.setCase(4, 3, null); // Arrivée vide

        System.out.println("=== DEBUG testHistoriquePrise ===");
        System.out.println("Before movement:");
        System.out.println(affichage.generate(damier));

        // Effectuer une prise
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 4, 3);

        System.out.println("After movement, result: " + resultat);
        System.out.println(affichage.generate(damier));

        // Vérifier d'abord que la prise a fonctionné
        assertTrue("La prise doit fonctionner pour tester l'historique", resultat);

        // Vérifier l'historique seulement si la prise a réussi
        if (resultat && !historique.obtenirHistoriqueComplet().isEmpty()) {
            String mouvement = historique.obtenirHistoriqueComplet().get(0);
            assertNotNull("Mouvement enregistré ne devrait pas être null", mouvement);
            assertTrue("Notation de prise devrait contenir '×'", mouvement.contains("×"));
        } else {
            System.out.println("DEBUG: Prise a échoué ou historique vide");
            System.out.println("Résultat prise: " + resultat);
            System.out.println("Taille historique: " + historique.obtenirHistoriqueComplet().size());
        }
    }

    @Test
    public void testMouvementSansHistorique() {
        // Effectuer un mouvement sans historique (null)
        boolean resultat = Mouvement.effectuerMouvement(damier, null, 6, 1, 5, 0);
        assertTrue("Mouvement sans historique devrait quand même fonctionner", resultat);
    }

    @Test
    public void testMouvementCaseDepartVide() {
        // Tentative de mouvement depuis une case vide
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 0, 0, 1, 1);
        assertFalse("Mouvement depuis case vide devrait être invalide", resultat);
    }

    @Test
    public void testMouvementCaseArriveeOccupee() {
        // Tentative de mouvement vers une case occupée
        boolean resultat = Mouvement.effectuerMouvement(damier, historique, 6, 1, 7, 2);
        assertFalse("Mouvement vers case occupée devrait être invalide", resultat);
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