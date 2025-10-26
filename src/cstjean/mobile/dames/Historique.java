package cstjean.mobile.dames;

import java.util.LinkedList;

/**
 * Classe Historique : enregistre les mouvements effectués
 * dans une partie de dames selon la notation Manoury.
 */
public class Historique {

    /**
     * Liste des notations des mouvements effectués sur le damier.
     *
     * <p>
     * Chaque élément correspond à un mouvement au format Manoury
     * (par exemple "12-16" pour un déplacement ou "22×17" pour une prise).
     */
    private LinkedList<String> listeDesMouvements = new LinkedList<>();

    /**
     * Ajoute un mouvement à l'historique en notation Manoury.
     *
     * <p>
     * Exemple : "31-27" pour un déplacement simple ou "27×18" pour une prise.
     *
     * @param mouvementNotation la notation du mouvement à ajouter à l'historique
     */
    public void ajouterMouvement(String mouvementNotation) {
        this.listeDesMouvements.add(mouvementNotation);
    }

    /**
     * Retourne la liste complète des mouvements joués.
     *
     * <p>
     * Chaque élément de la liste correspond à un mouvement au format Manoury,
     * par exemple "12-16" pour un déplacement ou "22×17" pour une prise.
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
}
