package cstjean.mobile.dames;

/**
 * Classe responsable de générer une représentation textuelle d'un damier.
 *
 * <p>
 * Chaque case noire du damier est représentée par un pion ou un tiret s'il est vide.
 * La numérotation des cases suit le système Manoury (1 à 50 sur les cases noires).
 * </p>
 *
 * @author Alexandre Synnott
 * @author Benjamin Rondeau
 */
public class Affichage {

    /**
     * Génère une chaîne représentant le damier sous forme textuelle.
     *
     * <p>
     * Parcourt les lignes et colonnes du damier. Les cases noires contiennent un pion ou un
     * tiret si elles sont vides. La numérotation des cases suit le système Manoury.
     *
     * @param damier Le damier à afficher
     * @return La représentation textuelle du damier
     */
    public String generate(Damier damier) {
        StringBuilder builder = new StringBuilder();
        int manoury = 1;

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if ((row + col) % 2 == 1) {
                    Pion pion = damier.getPion(manoury);
                    builder.append(pion == null ? "-" : pion.getRepresentation());
                    manoury++;
                } else {
                    builder.append("-");
                }
            }
            builder.append("\n");
        }
        return builder.toString().trim();
    }
}
