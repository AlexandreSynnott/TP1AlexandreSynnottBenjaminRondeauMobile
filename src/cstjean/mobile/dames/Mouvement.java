package cstjean.mobile.dames;

import java.util.ArrayList;
import java.util.List;
/**
 * Classe gérant les mouvements (déplacements et prises) sur le damier.
 *
 *
 * @author Alexandre Synnott Benjamin Rondeau
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

    /**
     * Vérifie si un déplacement simple (une case diagonale) est valide pour une pièce.
     *
     * <p>
     * Un déplacement simple est autorisé :
     * - pour un pion blanc : diagonale vers le haut
     * - pour un pion noir : diagonale vers le bas
     * - pour une dame : diagonale dans toutes les directions
     *
     * @param damier le damier de jeu
     * @param ligneD la ligne de départ (0-indexée)
     * @param colD   la colonne de départ (0-indexée)
     * @param ligneA la ligne d'arrivée (0-indexée)
     * @param colA   la colonne d'arrivée (0-indexée)
     * @return true si le déplacement simple est valide, sinon false
     */
    private static boolean estDeplacementSimpleValide(Damier damier, int ligneD, int colD,
                                                      int ligneA, int colA) {

        Pion piece = damier.getCase(ligneD, colD);

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

    /**
     * Vérifie si une prise (capture) est valide pour une pièce.
     *
     * <p>
     * Pour un pion : une prise est valide s'il y a un pion adverse sur la case
     * intermédiaire et que la case d'arrivée est vide.
     * Pour une dame : elle peut capturer sur plusieurs cases en diagonale,
     * mais seulement un adversaire à la fois et sans obstacles.
     *
     * @param damier        le damier de jeu
     * @param ligneDepart   la ligne de départ (0-indexée)
     * @param colonneDepart la colonne de départ (0-indexée)
     * @param ligneArrivee  la ligne d'arrivée (0-indexée)
     * @param colonneArrivee la colonne d'arrivée (0-indexée)
     * @return true si la prise est valide, sinon false
     */
    private static boolean estPriseValide(Damier damier, int ligneDepart, int colonneDepart,
                                          int ligneArrivee, int colonneArrivee) {

        Pion piece = damier.getCase(ligneDepart, colonneDepart);

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

    /**
     * Effectue un déplacement simple d'une pièce sur le damier et met à jour l'historique.
     *
     * @param damier        le damier de jeu
     * @param historique    l'objet Historique (peut être null)
     * @param ligneDepart   la ligne de départ (0-indexée)
     * @param colonneDepart la colonne de départ (0-indexée)
     * @param ligneArrivee  la ligne d'arrivée (0-indexée)
     * @param colonneArrivee la colonne d'arrivée (0-indexée)
     * @return true si le déplacement a été effectué, sinon false
     */
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

    /**
     * Effectue une prise (capture) d'une pièce sur le damier et met à jour l'historique.
     *
     * <p>
     * Pour les pions, la prise est sur une case adjacente.
     * Pour les dames, la prise peut se faire sur plusieurs cases en diagonale
     * mais seulement un adversaire à la fois.
     *
     * @param damier        le damier de jeu
     * @param historique    l'objet Historique (peut être null)
     * @param ligneDepart   la ligne de départ (0-indexée)
     * @param colonneDepart la colonne de départ (0-indexée)
     * @param ligneArrivee  la ligne d'arrivée (0-indexée)
     * @param colonneArrivee la colonne d'arrivée (0-indexée)
     * @return true si la prise a été effectuée, sinon false
     */
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

    /**
     * Convertit les coordonnées (ligne, colonne) du damier en numéro Manoury.
     *
     * <p>
     * Le numéro Manoury correspond à la notation utilisée en jeu de dames
     * pour identifier les cases valides (uniquement les cases foncées).
     *
     * @param ligne   la ligne de la case (0-indexée)
     * @param colonne la colonne de la case (0-indexée)
     * @return le numéro Manoury correspondant à la case, ou -1 si la case est invalide
     */

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
    /**
     * Retourne la liste des coordonnées de toutes les cases atteignables
     * par un pion à la position (ligne, colonne), compte tenu de l'état actuel du damier.
     *
     * @param damier le damier de jeu
     * @param ligne  la ligne du pion
     * @param colonne la colonne du pion
     * @return liste de paires [ligne, colonne] pour chaque déplacement valide
     */

    public static List<int[]> deplacementsValides(Damier damier, int ligne, int colonne) {
        List<int[]> deplacements = new ArrayList<>();
        Pion piece = damier.getCase(ligne, colonne);
        if (piece == null) {
            return deplacements;
        }

        // Parcourir tout le damier pour voir où le pion peut aller
        for (int l = 0; l < Damier.TAILLE; l++) {
            for (int c = 0; c < Damier.TAILLE; c++) {
                if (damier.getCase(l, c) == null) {
                    // Vérifie si déplacement simple ou prise valide
                    if (estDeplacementSimpleValide(damier, ligne, colonne, l, c) ||
                            estPriseValide(damier, ligne, colonne, l, c)) {
                        deplacements.add(new int[]{l, c});
                    }
                }
            }
        }

        return deplacements;
    }
}
