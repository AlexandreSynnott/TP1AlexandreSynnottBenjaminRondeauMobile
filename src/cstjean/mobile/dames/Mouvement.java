package cstjean.mobile.dames;

/**
 * Classe gérant les mouvements (déplacements et prises) sur le damier.
 */
public class Mouvement {

    /**
     * Tente d'effectuer un mouvement (déplacement simple ou prise) sur le damier.
     *
     * @param damier          le damier de jeu
     * @param historique      l'objet Historique (peut être null pour ne pas enregistrer)
     * @param ligneDepart     ligne de départ (0-indexée)
     * @param colonneDepart   colonne de départ (0-indexée)
     * @param ligneArrivee    ligne d'arrivée (0-indexée)
     * @param colonneArrivee  colonne d'arrivée (0-indexée)
     * @return true si le mouvement est valide et effectué, sinon false.
     */
    public static boolean effectuerMouvement(Damier damier, Historique historique,
                                             int ligneDepart, int colonneDepart,
                                             int ligneArrivee, int colonneArrivee) {

        Pion piece = damier.getCase(ligneDepart, colonneDepart);

        if (piece == null || damier.getCase(ligneArrivee, colonneArrivee) != null) {
            return false;
        }

        // Vérifier d'abord si c'est une prise
        if (estPriseValide(damier, ligneDepart, colonneDepart, ligneArrivee, colonneArrivee)) {
            return effectuerPrise(damier, historique, ligneDepart, colonneDepart,
                    ligneArrivee, colonneArrivee);
        }

        // Sinon, vérifier si c'est un déplacement simple valide
        if (estDeplacementSimpleValide(damier, ligneDepart, colonneDepart, ligneArrivee, colonneArrivee)) {
            return effectuerDeplacementSimple(damier, historique, ligneDepart, colonneDepart,
                    ligneArrivee, colonneArrivee);
        }

        return false;
    }

    private static boolean estDeplacementSimpleValide(Damier damier, int ligneD, int colD,
                                                      int ligneA, int colA) {

        Pion piece = damier.getCase(ligneD, colD);
        if (piece == null) {
            return false;
        }

        int deltaLigneSimple = ligneA - ligneD;
        int deltaColonneSimple = colA - colD;

        // Doit être diagonal
        if (Math.abs(deltaLigneSimple) != Math.abs(deltaColonneSimple)) {
            return false;
        }

        // Déplacement simple : une seule case
        if (Math.abs(deltaLigneSimple) == 1) {
            if (!(piece instanceof Dame)) { // pion normal
                if (piece.getCouleur() == Pion.Couleur.BLANC && deltaLigneSimple == -1) {
                    return true; // Blanc va vers le haut
                } else if (piece.getCouleur() == Pion.Couleur.NOIR && deltaLigneSimple == 1) {
                    return true; // Noir va vers le bas
                }
                return false;
            } else { // dame
                return true;
            }
        }

        return false;
    }

    private static boolean estPriseValide(Damier damier, int ligneDepart, int colonneDepart,
                                          int ligneArrivee, int colonneArrivee) {

        Pion piece = damier.getCase(ligneDepart, colonneDepart);
        if (piece == null) {
            return false;
        }

        int deltaLigne = ligneArrivee - ligneDepart;
        int deltaColonne = colonneArrivee - colonneDepart;

        if (Math.abs(deltaLigne) != Math.abs(deltaColonne)) {
            return false;
        }

        // Pour les pions
        if (!(piece instanceof Dame)) {
            if (Math.abs(deltaLigne) == 2) {
                int ligneMilieu = ligneDepart + deltaLigne / 2;
                int colonneMilieu = colonneDepart + deltaColonne / 2;
                Pion pieceMilieu = damier.getCase(ligneMilieu, colonneMilieu);

                return pieceMilieu != null && pieceMilieu.getCouleur() != piece.getCouleur();
            }
        }

        // Pour les dames
        if (piece instanceof Dame) {
            if (Math.abs(deltaLigne) >= 2) {
                int pas = Math.abs(deltaLigne);
                int dirLigne = deltaLigne / pas;
                int dirColonne = deltaColonne / pas;

                int ligneActuelle = ligneDepart + dirLigne;
                int colonneActuelle = colonneDepart + dirColonne;
                int ennemisTrouves = 0;

                while (ligneActuelle != ligneArrivee && colonneActuelle != colonneArrivee) {
                    Pion pieceCourante = damier.getCase(ligneActuelle, colonneActuelle);

                    if (pieceCourante != null) {
                        if (pieceCourante.getCouleur() == piece.getCouleur()) {
                            return false; // Bloqué par un allié
                        } else {
                            ennemisTrouves++;
                            if (ennemisTrouves > 1) {
                                return false;
                            }
                        }
                    }

                    ligneActuelle += dirLigne;
                    colonneActuelle += dirColonne;
                }

                return ennemisTrouves == 1;
            }
        }

        return false;
    }

    private static boolean effectuerDeplacementSimple(Damier damier, Historique historique,
                                                      int ligneDepart, int colonneDepart,
                                                      int ligneArrivee, int colonneArrivee) {

        Pion piece = damier.getCase(ligneDepart, colonneDepart);

        damier.setCase(ligneArrivee, colonneArrivee, piece);
        damier.setCase(ligneDepart, colonneDepart, null);

        if (historique != null) {
            int numeroDepart = coordonneesVersNumeroManoury(ligneDepart, colonneDepart);
            int numeroArrivee = coordonneesVersNumeroManoury(ligneArrivee, colonneArrivee);
            String notation = numeroDepart + "-" + numeroArrivee;

            if (piece.getCouleur() == Pion.Couleur.NOIR) {
                notation = "(" + notation + ")";
            }
            historique.ajouterMouvement(notation);
        }

        return true;
    }

    private static boolean effectuerPrise(Damier damier, Historique historique,
                                          int ligneDepart, int colonneDepart,
                                          int ligneArrivee, int colonneArrivee) {

        Pion piece = damier.getCase(ligneDepart, colonneDepart);
        int deltaLigne = ligneArrivee - ligneDepart;
        int deltaColonne = colonneArrivee - colonneDepart;

        if (!(piece instanceof Dame)) { // pion normal
            int ligneMilieu = ligneDepart + deltaLigne / 2;
            int colonneMilieu = colonneDepart + deltaColonne / 2;

            damier.setCase(ligneArrivee, colonneArrivee, piece);
            damier.setCase(ligneDepart, colonneDepart, null);
            damier.setCase(ligneMilieu, colonneMilieu, null);

        } else { // dame
            int pas = Math.abs(deltaLigne);
            int dirLigne = deltaLigne / pas;
            int dirColonne = deltaColonne / pas;

            int ligneActuelle = ligneDepart + dirLigne;
            int colonneActuelle = colonneDepart + dirColonne;
            int ligneEnnemi = -1;
            int colonneEnnemi = -1;

            while (ligneActuelle != ligneArrivee && colonneActuelle != colonneArrivee) {
                if (damier.getCase(ligneActuelle, colonneActuelle) != null) {
                    ligneEnnemi = ligneActuelle;
                    colonneEnnemi = colonneActuelle;
                    break;
                }
                ligneActuelle += dirLigne;
                colonneActuelle += dirColonne;
            }

            damier.setCase(ligneArrivee, colonneArrivee, piece);
            damier.setCase(ligneDepart, colonneDepart, null);
            damier.setCase(ligneEnnemi, colonneEnnemi, null);
        }

        if (historique != null) {
            int numeroDepart = coordonneesVersNumeroManoury(ligneDepart, colonneDepart);
            int numeroArrivee = coordonneesVersNumeroManoury(ligneArrivee, colonneArrivee);
            String notation = numeroDepart + "×" + numeroArrivee;

            assert piece != null;
            if (piece.getCouleur() == Pion.Couleur.NOIR) {
                notation = "(" + notation + ")";
            }
            historique.ajouterMouvement(notation);
        }

        return true;
    }

    public static int coordonneesVersNumeroManoury(int ligne, int colonne) {
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
}
