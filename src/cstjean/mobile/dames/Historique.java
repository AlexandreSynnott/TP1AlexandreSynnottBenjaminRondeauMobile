package cstjean.mobile.dames;

import java.util.LinkedList;

/**
 * Classe Historique : enregistre les mouvements effectués
 * dans une partie de dames selon la notation Manoury.
 *
 * <p>
 * Elle permet aussi de revenir à un état précédent du jeu.
 */
public class Historique {

    /**
     * Liste des notations des mouvements effectués sur le damier.
     *
     * <p>
     * Chaque élément correspond à un mouvement au format Manoury
     * (ex. "12-16" pour un déplacement ou "22×17" pour une prise).
     */
    private LinkedList<String> listeDesMouvements = new LinkedList<>();

    /**
     * Ajoute un mouvement à l'historique en notation Manoury.
     *
     * @param mouvementNotation la notation du mouvement à ajouter
     */
    public void ajouterMouvement(String mouvementNotation) {
        this.listeDesMouvements.add(mouvementNotation);
    }

    /**
     * Retourne la liste complète des mouvements joués.
     *
     * <p>
     * Chaque élément correspond à un mouvement au format Manoury.
     *
     * @return la liste des mouvements en {@link LinkedList}
     */
    public LinkedList<String> obtenirHistoriqueComplet() {
        return this.listeDesMouvements;
    }

    /**
     * Efface tout l'historique des mouvements.
     */
    public void effacerHistorique() {
        this.listeDesMouvements.clear();
    }

    /**
     * Affiche tous les mouvements au format lisible.
     */
    public void afficherHistorique() {
        System.out.println("Historique des mouvements :");
        for (String mouvement : listeDesMouvements) {
            System.out.println("• " + mouvement);
        }
    }

    /**
     * Annule le dernier mouvement enregistré.
     *
     * <p>
     * Cette méthode supprime le dernier mouvement de la liste
     * et le retourne pour que le jeu puisse revenir à l'état précédent.
     * </p>
     *
     * @return le dernier mouvement supprimé, ou {@code null} si la liste est vide
     */
    public String annulerDernierMouvement() {
        if (!listeDesMouvements.isEmpty()) {
            return listeDesMouvements.removeLast();
        }
        return null;
    }

    /**
     * Retourne le dernier mouvement sans l’enlever.
     *
     * <p>
     * Cela permet de consulter le mouvement le plus récent
     * sans modifier l’historique.
     *
     * @return le dernier mouvement ou {@code null} si aucun
     */
    public String consulterDernierMouvement() {
        if (!listeDesMouvements.isEmpty()) {
            return listeDesMouvements.getLast();
        }
        return null;
    }

    /**
     * Indique s’il est possible de revenir en arrière.
     *
     * <p>
     * Utile pour vérifier avant d’appeler {@link #annulerDernierMouvement()}.
     *
     * @return {@code true} s’il y a au moins un mouvement enregistré
     */
    public boolean peutRevenirArriere() {
        return !listeDesMouvements.isEmpty();
    }
}
