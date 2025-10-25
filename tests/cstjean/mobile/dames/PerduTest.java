/**package cstjean.mobile.dames;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Perdu.

public class PerduTest {

    @Test
    void testJoueurBlancSansPions() {
        Damier damier = new Damier();

        // Aucun pion blanc sur le damier
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {
                damier.setCase(l, c, null);
            }
        }
        damier.setCase(4, 6, "n");

        assertTrue(Perdu.joueurAPerdu(damier, "b"), "Le joueur blanc doit avoir perdu (aucun pion)");
    }

    @Test
    void testJoueurNoirSansPions() {
        Damier damier = new Damier();

        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {
                damier.setCase(l, c, null);
            }
        }
        damier.setCase(3, 2, "b");

        assertTrue(Perdu.joueurAPerdu(damier, "n"), "Le joueur noir doit avoir perdu (aucun pion)");
    }

    @Test
    void testJoueurBlancBloque() {
        Damier damier = new Damier();

        // Un pion blanc bloqué par des noirs
        damier.setCase(4, 6, "n");
        damier.setCase(5, 5, "n");
        damier.setCase(5, 7, "n");
        damier.setCase(6, 6, "b");

        assertTrue(Perdu.joueurAPerdu(damier, "b"), "Le joueur blanc doit avoir perdu (aucun déplacement possible)");
    }

    @Test
    void testJoueurPeutEncoreBouger() {
        Damier damier = new Damier();

        // Un pion blanc avec un espace libre
        damier.setCase(5, 5, "b");
        damier.setCase(4, 4, null);
        damier.setCase(4, 6, null);

        assertFalse(Perdu.joueurAPerdu(damier, "b"), "Le joueur blanc ne doit pas avoir perdu (peut bouger)");
    }

}
 */