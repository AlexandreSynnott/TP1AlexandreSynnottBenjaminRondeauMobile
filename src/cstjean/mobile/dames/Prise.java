package cstjean.mobile.dames;

public class Prise {

    /**
     * Tente d'effectuer une prise (capture) sur le damier et, si fourni,
     * @param damier                 le damier de jeu
     * @param historique             l'objet Historique (peut être null pour ne pas enregistrer)
     * @param ligneDepart           ligne de départ (0-indexée)
     * @param colonneDepart         colonne de départ (0-indexée)
     * @param ligneArrivee          ligne d'arrivée (0-indexée)
     * @param colonneArrivee        colonne d'arrivée (0-indexée)
     * @return true si la prise est valide et effectuée, sinon false
     */
    public static boolean prendre(Damier damier, Historique historique,
                                  int ligneDepart, int colonneDepart,
                                  int ligneArrivee, int colonneArrivee) {

        String[][] plateauDeJeu = damier.plateau;
        String pieceSelectionnee = plateauDeJeu[ligneDepart][colonneDepart];

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

                // Effectue la prise
                plateauDeJeu[ligneArrivee][colonneArrivee] = pieceSelectionnee;
                plateauDeJeu[ligneDepart][colonneDepart] = null;
                plateauDeJeu[ligneMilieu][colonneMilieu] = null;

                // Enregistre dans l'historique en notation Manoury :
                if (historique != null) {
                    int numeroDepart = coordonneesVersNumeroManoury(ligneDepart, colonneDepart);
                    int numeroArrivee = coordonneesVersNumeroManoury(ligneArrivee, colonneArrivee);
                    String signePrise = "×";
                    String notationManoury = numeroDepart + signePrise + numeroArrivee;

                    if (estPieceNoire(pieceSelectionnee)) {
                        notationManoury = "(" + notationManoury + ")";
                    }
                    historique.ajouterMouvement(notationManoury);
                }

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
                            return false;
                        }
                    }

                    ligneActuelle += directionLignes;
                    colonneActuelle += directionColonnes;
                }

                if (nombreEnnemisRencontres == 1) {
                    plateauDeJeu[ligneArrivee][colonneArrivee] = pieceSelectionnee;
                    plateauDeJeu[ligneDepart][colonneDepart] = null;
                    plateauDeJeu[ligneEnnemi][colonneEnnemi] = null;

                    // Enregistre dans l'historique en notation Manoury :
                    if (historique != null) {
                        int numeroDepart = coordonneesVersNumeroManoury(ligneDepart, colonneDepart);
                        int numeroArrivee = coordonneesVersNumeroManoury(ligneArrivee, colonneArrivee);
                        String signePrise = "×";
                        String notationManoury = numeroDepart + signePrise + numeroArrivee;

                        if (estPieceNoire(pieceSelectionnee)) {
                            notationManoury = "(" + notationManoury + ")";
                        }
                        historique.ajouterMouvement(notationManoury);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Convertit des coordonnées (ligne, colonne) 0-indexées en numéro Manoury (1..50)
     * pour les cases noires numérotées de gauche à droite, de haut en bas.
     *
     * @param ligne  index de ligne 0..9
     * @param colonne index de colonne 0..9
     * @return numéro Manoury (1..50) ou -1 si la case n'est pas une case noire valide
     */
    private static int coordonneesVersNumeroManoury(int ligne, int colonne) {
        int compteurManoury = 0;
        for (int r = 0; r < Damier.TAILLE; r++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                if ((r + c) % 2 == 1) {
                    compteurManoury++;
                    if (r == ligne && c == colonne) {
                        return compteurManoury;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Indique si une pièce est noire (pion ou dame).
     */
    private static boolean estPieceNoire(String piece) {
        return piece != null && (piece.equals("n") || piece.equals("N"));
    }
}
