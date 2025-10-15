package cstjean.mobile.dames;

/**
 * Classe responsable de générer une représentation textuelle d'un damier.
 *
 * <p>
 * Chaque case noire du damier est représentée par un pion ("n" ou "b") ou un tiret s'il est vide.
 * La numérotation des cases suit le système Manoury (1 à 50 sur les cases noires) — le compteur
 * Manoury est maintenu ici pour cohérence même s'il n'est pas affiché.
 * </p>
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class Affichage {

    /**
     * Génère une chaîne représentant le damier sous forme textuelle.
     *
     * @param damier Le damier à afficher
     * @return La représentation textuelle du damier
     */
    public String generate(Damier damier) {
        StringBuilder builder = new StringBuilder();
        int manoury = 1;

        for (int row = 0; row < Damier.TAILLE; row++) {
            for (int col = 0; col < Damier.TAILLE; col++) {
                if ((row + col) % 2 == 1) {
                    // case foncée : contient un pion ("n" ou "b") ou null si vide
                    String valeur = damier.getCase(row, col);
                    builder.append(valeur == null ? "-" : valeur);
                    manoury++;
                } else {

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
