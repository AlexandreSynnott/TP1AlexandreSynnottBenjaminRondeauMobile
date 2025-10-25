package cstjean.mobile.dames;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Suite de tests regroupant {@link TestPion} et {@link TestDamier}.
 *
 * <p>
 * Permet d’exécuter d’un seul coup tous les tests unitaires du projet Dames.
 * </p>
 *
 * <p>
 * Cette classe sert de point d’entrée pour exécuter l’ensemble des tests
 * sans avoir à lancer chaque classe de test individuellement.
 * </p>
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class TestSuitesDames extends TestCase {

    /**
     * Construit et retourne la suite de tests pour l’ensemble du projet.
     *
     * <p>
     * Ajoute {@link TestPion} et {@link TestDamier} à la suite.
     * </p>
     *
     * @return la suite de tests contenant toutes les classes de test
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestPion.class);
        suite.addTestSuite(TestDamier.class);
        return suite;
    }
}
