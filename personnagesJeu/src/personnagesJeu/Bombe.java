package personnagesJeu;

import jeuCombat.Grille;

import java.util.List;

public class Bombe extends Armes {
    private int rayonExplosion;
    private boolean utilisee;
    private Personnage poseur;

    public Bombe(int durabilite, String type, int munitions, int rayonExplosion, boolean utilisee, Position position){
        super(durabilite, type, munitions, position);
        this.rayonExplosion = rayonExplosion;
        this.utilisee = utilisee;
    }

    //exploser une bombe
    public void exploser(Grille grille, Personnage personnage) {
        System.out.println("La bombe " + getType() + " explose avec un rayon de " + rayonExplosion + " mètres !");
        grille.retirerObjet(personnage.getPosition(), this);

        //find personnages dans rayon explosion
        List<Personnage> personnagesDansRayon = grille.getPersonnagesDansRayon(personnage.getPosition(), rayonExplosion, grille);

        for (Personnage p : personnagesDansRayon) {
            //exclure le poseur de la bombe
            if (p.equals(this.getPoseur())) {
                System.out.println(p.getNom() + " est le poseur de la bombe et n'est pas affecté.");
                continue;
            }
            //if personnage dans la zone ou est le déclencheur
            if (p.equals(personnage)) {
                System.out.println(p.getNom() + " a déclenché la bombe et subit aussi des dégâts !");
            } else {
                System.out.println("Dégâts infligés à " + p.getNom());
            }
            p.reduireEnergie(30);
            System.out.println("Il reste " + p.getEnergie() + " à " + p.getNom());

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

    public Personnage getPoseur() {
        return this.poseur;
    }

    // Setter pour définir le poseur
    public void setPoseur(Personnage poseur) {
        this.poseur = poseur;
    }

}
