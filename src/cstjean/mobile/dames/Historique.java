package cstjean.mobile.dames;

import java.util.LinkedList;

/**
 * Classe Historique : enregistre les mouvements effectués
 * dans une partie de dames selon la notation Manoury.
 */
public class Historique {

    // Liste contenant les mouvements joués
    private LinkedList<String> listeDesMouvements = new LinkedList<>();

    /**
     * Ajoute un mouvement à l'historique en notation Manoury.
     * Exemple : "31-27" ou "27x18"
     * @param mouvementNotation description du mouvement
     */
    public void ajouterMouvement(String mouvementNotation) {
        this.listeDesMouvements.add(mouvementNotation);
    }

    /**
     * Retourne la liste complète des mouvements joués.
     * @return la liste des mouvements en LinkedList
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
