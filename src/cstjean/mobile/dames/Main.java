package cstjean.mobile.dames;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Damier damier = new Damier();
        damier.afficher();

        // Exemple : un pion blanc atteint la dernière rangée
        damier.setCase(0, 3, "b");

        System.out.println("\n--- Avant promotion ---");
        damier.afficher();

        System.out.println("\n--- Après vérification de la promotion ---");
        Promotion.verifierPromotion(damier);
        damier.afficher();
    }
}
