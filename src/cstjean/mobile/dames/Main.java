package cstjean.mobile.dames;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Affichage a = new Affichage();
        Damier damier = new Damier();
        Historique historique = new Historique();

        System.out.println("--- Damier initial ---");
        System.out.println(a.generate(damier));

        damier.setCase(5, 0,  new Pion(Pion.Couleur.BLANC));
        damier.setCase(4, 1, new Pion(Pion.Couleur.NOIR));
        damier.setCase(3, 2, null); //mettre "b" ou "n" pour une prise impossible

        boolean priseEffectuee = Mouvement.effectuerMouvement(damier, historique,5, 0, 3, 2);
        if(priseEffectuee){
            System.out.println("--- Après prise ---");
        }
        else {
            System.out.println("--- Prise impossible ---");
        }
        System.out.println(a.generate(damier));

        // Exemple : un pion blanc atteint la dernière rangée
        damier.setCase(0, 3, new Pion(Pion.Couleur.BLANC));

        System.out.println("\n--- Avant promotion ---");
        System.out.println(a.generate(damier));

        System.out.println("\n--- Après vérification de la promotion ---");
        Promotion.verifierPromotion(damier);
        System.out.println(a.generate(damier));
    }
}

