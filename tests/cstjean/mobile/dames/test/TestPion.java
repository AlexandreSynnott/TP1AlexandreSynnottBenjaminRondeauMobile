package cstjean.mobile.dames.test;

import cstjean.mobile.dames.Damier;
import cstjean.mobile.dames.Pion;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPion {


        private Damier damier = new  Damier();
        private Pion pion = new  Pion();
        @Test
        public void testRepresentation() {

            damier.setCase(0, 3, new Pion(Pion.Couleur.BLANC));
            assertEquals('b', damier.getCase(0,3).getRepresentation());

            pion =  damier.getCase(0,3);
            assertEquals("Pion[BLANC]",pion.toString());

        }
    }