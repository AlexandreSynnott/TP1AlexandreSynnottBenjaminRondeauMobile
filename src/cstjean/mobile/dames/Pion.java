package cstjean.mobile.dames;


public class Pion {

    public enum Couleur { BLANC, NOIR }

    private Couleur couleur;

    public Pion(Couleur couleur) {
        this.couleur = couleur;
    }

    public Pion() {
        this.couleur = Couleur.BLANC;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }


    public char getRepresentation() {
        return (couleur == Couleur.NOIR) ? 'n' : 'b';
    }
    @Override
    public String toString() {
        return "Pion[" + couleur + "]";
    }
}
