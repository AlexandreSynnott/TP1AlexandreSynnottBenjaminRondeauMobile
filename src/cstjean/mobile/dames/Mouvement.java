package cstjean.mobile.dames;

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
     * @return true si le mouvement est valide et effectué, sinon false
     */
    public static boolean effectuerMouvement(Damier damier, Historique historique,
                                             int ligneDepart, int colonneDepart,
                                             int ligneArrivee, int colonneArrivee) {

        Pion[][] plateau = damier.plateau;
        Pion piece = plateau[ligneDepart][colonneDepart];

        if (piece == null || plateau[ligneArrivee][colonneArrivee] != null)
            return false;

        int deltaLigne = ligneArrivee - ligneDepart;
        int deltaColonne = colonneArrivee - colonneDepart;

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

    /**
     * Vérifie si un déplacement simple est valide.
     */
    private static boolean estDeplacementSimpleValide(Damier damier, int ligneD, int colD,
                                                      int ligneA, int colA) {
        Pion piece = damier.plateau[ligneD][colD];
        if (piece == null) return false;

        int dL = ligneA - ligneD;
        int dC = colA - colD;

        // Doit être diagonal
        if (Math.abs(dL) != Math.abs(dC)) return false;

        // Déplacement simple : une seule case
        if (Math.abs(dL) == 1) {
            // Pour pions : vérifier la direction
            if (piece instanceof Pion && !(piece instanceof Dame)) {
                if (piece.getCouleur() == Pion.Couleur.BLANC && dL == -1) {
                    return true; // Blanc va vers le haut
                } else if (piece.getCouleur() == Pion.Couleur.NOIR && dL == 1) {
                    return true; // Noir va vers le bas
                }
                return false; // Direction invalide pour un pion
            } else if (piece instanceof Dame) {
                return true; // Les dames peuvent aller dans toutes les directions
            }
        }

        return false;
    }

    /**
     * Vérifie si une prise est valide.
     */
    private static boolean estPriseValide(Damier damier, int ligneDepart, int colonneDepart,
                                          int ligneArrivee, int colonneArrivee) {
        Pion piece = damier.plateau[ligneDepart][colonneDepart];
        if (piece == null) return false;

        int deltaLigne = ligneArrivee - ligneDepart;
        int deltaColonne = colonneArrivee - colonneDepart;

        // Doit être diagonal
        if (Math.abs(deltaLigne) != Math.abs(deltaColonne)) return false;

        // Pour les pions : prise de 2 cases
        if (piece instanceof Pion && !(piece instanceof Dame)) {
            if (Math.abs(deltaLigne) == 2 && Math.abs(deltaColonne) == 2) {
                int ligneMilieu = ligneDepart + deltaLigne / 2;
                int colonneMilieu = colonneDepart + deltaColonne / 2;
                Pion pieceMilieu = damier.plateau[ligneMilieu][colonneMilieu];

                return pieceMilieu != null && pieceMilieu.getCouleur() != piece.getCouleur();
            }
        }

        // Pour les dames : prise à longue portée
        if (piece instanceof Dame) {
            if (Math.abs(deltaLigne) >= 2) {
                int pas = Math.abs(deltaLigne);
                int dirLigne = deltaLigne / pas;
                int dirColonne = deltaColonne / pas;

                int ligneActuelle = ligneDepart + dirLigne;
                int colonneActuelle = colonneDepart + dirColonne;
                int ennemisTrouves = 0;
                int ligneEnnemi = -1;
                int colonneEnnemi = -1;

                while (ligneActuelle != ligneArrivee && colonneActuelle != colonneArrivee) {
                    Pion pieceCourante = damier.plateau[ligneActuelle][colonneActuelle];

                    if (pieceCourante != null) {
                        boolean estEnnemi = pieceCourante.getCouleur() != piece.getCouleur();
                        boolean estAllie = pieceCourante.getCouleur() == piece.getCouleur();

                        if (estAllie) return false; // Bloqué par un allié
                        if (estEnnemi) {
                            ennemisTrouves++;
                            if (ennemisTrouves > 1) return false; // Plus d'un ennemi
                            ligneEnnemi = ligneActuelle;
                            colonneEnnemi = colonneActuelle;
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

    /**
     * Effectue un déplacement simple.
     */
    private static boolean effectuerDeplacementSimple(Damier damier, Historique historique,
                                                      int ligneDepart, int colonneDepart,
                                                      int ligneArrivee, int colonneArrivee) {
        Pion piece = damier.plateau[ligneDepart][colonneDepart];

        damier.plateau[ligneArrivee][colonneArrivee] = piece;
        damier.plateau[ligneDepart][colonneDepart] = null;

        // Enregistrement dans l'historique
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

    /**
     * Effectue une prise.
     */
    private static boolean effectuerPrise(Damier damier, Historique historique,
                                          int ligneDepart, int colonneDepart,
                                          int ligneArrivee, int colonneArrivee) {
        Pion piece = damier.plateau[ligneDepart][colonneDepart];
        int deltaLigne = ligneArrivee - ligneDepart;
        int deltaColonne = colonneArrivee - colonneDepart;

        // Pour les pions : prise simple
        if (piece instanceof Pion && !(piece instanceof Dame)) {
            int ligneMilieu = ligneDepart + deltaLigne / 2;
            int colonneMilieu = colonneDepart + deltaColonne / 2;

            damier.plateau[ligneArrivee][colonneArrivee] = piece;
            damier.plateau[ligneDepart][colonneDepart] = null;
            damier.plateau[ligneMilieu][colonneMilieu] = null;
        }
        // Pour les dames : prise à longue portée
        else if (piece instanceof Dame) {
            int pas = Math.abs(deltaLigne);
            int dirLigne = deltaLigne / pas;
            int dirColonne = deltaColonne / pas;

            int ligneActuelle = ligneDepart + dirLigne;
            int colonneActuelle = colonneDepart + dirColonne;
            int ligneEnnemi = -1;
            int colonneEnnemi = -1;

            // Trouver la position de l'ennemi
            while (ligneActuelle != ligneArrivee && colonneActuelle != colonneArrivee) {
                if (damier.plateau[ligneActuelle][colonneActuelle] != null) {
                    ligneEnnemi = ligneActuelle;
                    colonneEnnemi = colonneActuelle;
                    break;
                }
                ligneActuelle += dirLigne;
                colonneActuelle += dirColonne;
            }

            damier.plateau[ligneArrivee][colonneArrivee] = piece;
            damier.plateau[ligneDepart][colonneDepart] = null;
            damier.plateau[ligneEnnemi][colonneEnnemi] = null;
        }

        // Enregistrement dans l'historique
        if (historique != null) {
            int numeroDepart = coordonneesVersNumeroManoury(ligneDepart, colonneDepart);
            int numeroArrivee = coordonneesVersNumeroManoury(ligneArrivee, colonneArrivee);
            String notation = numeroDepart + "×" + numeroArrivee;

            if (piece.getCouleur() == Pion.Couleur.NOIR) {
                notation = "(" + notation + ")";
            }
            historique.ajouterMouvement(notation);
        }

        return true;
    }

    /**
     * Convertit des coordonnées en numéro Manoury.
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
}