package cstjean.mobile.dames.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite de tests pour l'ensemble du projet de dames.
 * Exécute tous les tests des différentes classes.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestMouvement.class,
        TestHistorique.class,
        TestPerdu.class,
        TestPion.class,
        TestDame.class,
        TestDamier.class,
        TestAffichage.class,
        TestPromotion.class
})
public class TestSuitesDames {
    // Cette classe reste vide, elle sert seulement de conteneur pour les annotations
}