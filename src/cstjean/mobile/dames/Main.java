package cstjean.mobile.dames;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Affichage a = new Affichage();
        Damier damier = new Damier();
        System.out.println(a.generate(damier));

        System.out.println("\n--- Avant promotion ---");
       System.out.println(a.generate(damier));

        System.out.println("\n--- Après vérification de la promotion ---");
        Promotion.verifierPromotion(damier);
        System.out.println(a.generate(damier));
    }
}
