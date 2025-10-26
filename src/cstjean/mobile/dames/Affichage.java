package cstjean.mobile.dames;

/**
 * Classe responsable de générer une représentation textuelle d'un damier.
 *
 *
 * @author Alexandre Synnott et Benjamin Rondeau
 */

public class Affichage {

    /**
     * Génère une représentation textuelle du damier.
     * Chaque case est représentée par un caractère :
     * - les cases foncées vides sont représentées par '-'
     * - les cases foncées avec un pion affichent le symbole du pion
     * - les cases claires sont représentées par '-'
     *
     * @param damier Le damier dont on souhaite générer la représentation textuelle.
     * @return Une chaîne de caractères représentant le damier.
     */
    public String generate(Damier damier) {
        StringBuilder builder = new StringBuilder();

        for (int row = 0; row < Damier.TAILLE; row++) {
            for (int col = 0; col < Damier.TAILLE; col++) {
                if ((row + col) % 2 == 1) {
                    // case foncée : contient un pion ou est vide
                    Pion pion = damier.getCase(row, col);
                    if (pion == null) {
                        builder.append("-");
                    } else {
                        builder.append(pion.getRepresentation());
                    }
                } else {
                    // case claire : neutre
                    builder.append("-");
                }

                if (col < Damier.TAILLE - 1) {
                    builder.append(" ");
                }
            }
            builder.append("\n");
        }

        return builder.toString().trim();
    }
}
