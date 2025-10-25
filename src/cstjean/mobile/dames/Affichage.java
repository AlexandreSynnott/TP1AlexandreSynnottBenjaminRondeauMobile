package cstjean.mobile.dames;

/**
 * Classe responsable de générer une représentation textuelle d'un damier.
 */
public class Affichage {

    /**
     * Génère une chaîne représentant le damier sous forme textuelle.
     */
    public String generate(Damier damier) {
        StringBuilder builder = new StringBuilder();

        for (int row = 0; row < Damier.TAILLE; row++) {
            for (int col = 0; col < Damier.TAILLE; col++) {
                if ((row + col) % 2 == 1) {
                    // case foncée : contient un pion où est vide
                    Pion pion = damier.getCase(row, col);
                    if (pion == null) {
                        builder.append("-");
                    } else {
                        builder.append(pion.getRepresentation());
                    }
                } else {
                    // case claire : juste pour le repérage, mais neutre
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