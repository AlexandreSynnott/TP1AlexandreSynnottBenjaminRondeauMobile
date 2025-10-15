package cstjean.mobile.dames;

public class Prise {

    /**
     * Tente d'effectuer une prise (capture) sur le damier.
     *
     * @param damier           le damier de jeu
     * @param ligneDepart      ligne de départ
     * @param colonneDepart    colonne de départ
     * @param ligneArrivee     ligne d'arrivée
     * @param colonneArrivee   colonne d'arrivée
     * @return true si la prise est valide et effectuée, sinon false
     */
    public static boolean prendre(Damier damier, int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee) {
        String[][] plateauDeJeu = damier.plateau;
        String pieceSelectionnee = plateauDeJeu[ligneDepart][colonneDepart];

        // Vérifie qu'il y a bien une pièce à déplacer et que la case d'arrivée est libre
        if (pieceSelectionnee == null || plateauDeJeu[ligneArrivee][colonneArrivee] != null) return false;

        int differenceLignes = ligneArrivee - ligneDepart;
        int differenceColonnes = colonneArrivee - colonneDepart;

        // --- Capture pour les pions ---
        if ((pieceSelectionnee.equals("b") || pieceSelectionnee.equals("n"))
                && Math.abs(differenceLignes) == 2 && Math.abs(differenceColonnes) == 2) {

            int ligneMilieu = ligneDepart + differenceLignes / 2;
            int colonneMilieu = colonneDepart + differenceColonnes / 2;
            String pieceAuMilieu = plateauDeJeu[ligneMilieu][colonneMilieu];

            if ((pieceSelectionnee.equals("b") && (pieceAuMilieu != null) && (pieceAuMilieu.equals("n") || pieceAuMilieu.equals("N"))) ||
                    (pieceSelectionnee.equals("n") && (pieceAuMilieu != null) && (pieceAuMilieu.equals("b") || pieceAuMilieu.equals("B")))) {

                plateauDeJeu[ligneArrivee][colonneArrivee] = pieceSelectionnee;
                plateauDeJeu[ligneDepart][colonneDepart] = null;
                plateauDeJeu[ligneMilieu][colonneMilieu] = null;
                return true;
            }
        }

        // --- Capture pour les dames (déplacement diagonal à longue portée) ---
        if (pieceSelectionnee.equals("B") || pieceSelectionnee.equals("N")) {
            // Doit se déplacer en diagonale et d'au moins 2 cases.
            if (Math.abs(differenceLignes) >= 2 && Math.abs(differenceLignes) == Math.abs(differenceColonnes)) {

                int nombreDePas = Math.abs(differenceLignes);
                int directionLignes = differenceLignes / nombreDePas;   // -1 ou +1
                int directionColonnes = differenceColonnes / nombreDePas; // -1 ou +1

                int ligneActuelle = ligneDepart + directionLignes;
                int colonneActuelle = colonneDepart + directionColonnes;

                int nombreEnnemisRencontres = 0;
                int ligneEnnemi = -1;
                int colonneEnnemi = -1;

                // Parcourt toutes les cases entre la position de départ et la destination.
                while (ligneActuelle != ligneArrivee && colonneActuelle != colonneArrivee) {
                    String contenuCase = plateauDeJeu[ligneActuelle][colonneActuelle];

                    if (contenuCase != null) {
                        // Détermine si la pièce rencontrée est un ennemie ou un alliée.
                        boolean estEnnemi = (pieceSelectionnee.equals("B") && (contenuCase.equals("n") || contenuCase.equals("N")))
                                || (pieceSelectionnee.equals("N") && (contenuCase.equals("b") || contenuCase.equals("B")));
                        boolean estAllie = (pieceSelectionnee.equals("B") && (contenuCase.equals("b") || contenuCase.equals("B")))
                                || (pieceSelectionnee.equals("N") && (contenuCase.equals("n") || contenuCase.equals("N")));

                        if (estAllie) {
                            // Bloquée par une pièce alliée -> prise impossible.
                            return false;
                        } else if (estEnnemi) {
                            nombreEnnemisRencontres++;
                            // Plus d’un ennemi sur le trajet -> mouvement invalide.
                            if (nombreEnnemisRencontres > 1) return false;
                            ligneEnnemi = ligneActuelle;
                            colonneEnnemi = colonneActuelle;
                        } else {
                            // Valeur inattendue (sécurité).
                            return false;
                        }
                    }

                    ligneActuelle += directionLignes;
                    colonneActuelle += directionColonnes;
                }

                // Pour qu'une prise soit valide : une seule pièce ennemie doit avoir été trouvée sur le trajet.
                if (nombreEnnemisRencontres == 1) {
                    plateauDeJeu[ligneArrivee][colonneArrivee] = pieceSelectionnee;
                    plateauDeJeu[ligneDepart][colonneDepart] = null;
                    plateauDeJeu[ligneEnnemi][colonneEnnemi] = null;
                    return true;
                }
            }
        }

        return false;
    }
}
