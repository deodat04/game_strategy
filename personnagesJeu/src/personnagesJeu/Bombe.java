package personnagesJeu;

import jeuCombat.Grille;

public class Bombe extends Armes {
    private int rayonExplosion;
    private boolean utilisee;

    public Bombe(int durabilite, String type, int munitions, int rayonExplosion, boolean utilisee, Position position){
        super(durabilite, type, munitions, position);
        this.rayonExplosion = rayonExplosion;
        this.utilisee = utilisee;
    }

    //exploser une bombe
    public void exploser(Grille grille, Personnage personnage) {
        System.out.println("La bombe " + getType() + " explose avec un rayon de " + rayonExplosion + " mètres !");
        grille.retirerObjet(personnage.getPosition());

        // Appliquer des dégâts aux personnages dans le rayon de l'explosion.
        for (Personnage p : grille.getPersonnagesDansRayon(personnage.getPosition(), rayonExplosion)) {
            System.out.println("Dégâts infligés à " + p.getNom());
            p.reduireEnergie(30);
        }
    }

    public int getRayonExplosion() {
        return rayonExplosion;
    }

    public void detonnerSiNecessaire(Personnage personnage, Grille grille) {
        System.out.println("Bombe détectée au contact de " + personnage.getNom() + " !");
        exploser(grille, personnage);
    }
    
    public boolean estUtilisee() {
        return utilisee;
    }

    public void setUtilisee(boolean utilisee) {
        this.utilisee = utilisee;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
