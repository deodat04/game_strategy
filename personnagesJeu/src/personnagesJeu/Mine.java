package personnagesJeu;

import jeuCombat.Grille;

public class Mine extends Armes {
    private boolean visible;
    private int delaiExplosion;
    private boolean explosion;
    private boolean utilisee;


    public Mine(String type, int durabilite, int munitions, int delaiExplosion, boolean visible, boolean explosion, boolean utilisee, Position position){
        super(durabilite, type, munitions, position);
        this.visible = visible;
        this.delaiExplosion = delaiExplosion;
        this.explosion = explosion;
        this.utilisee = utilisee;
    }

    //exploser mine
    public void exploser(Grille grille) {
        if (!explosion) {
            System.out.println("La mine " + getType() + " explose à la position " + position + " !");
            grille.retirerObjet(position, this);

            for (Personnage personnage : grille.getPersonnagesDansRayon(position, 1,grille)) {
                System.out.println("Dégâts infligés à " + personnage.getNom());
                personnage.reduireEnergie(20);
            }
            explosion = true;
        } else {
            System.out.println("La mine a déjà explosé.");
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean estPreteAExploser(){
        return explosion;
    }

    public int getDelaiExplosion(){
        return delaiExplosion;
    }

    public void reduireDelai(){
        if (delaiExplosion > 0) {
            delaiExplosion--;
        }
    }

    public void detonnerSiNecessaire(Personnage personnage, Grille grille) {
        if (estPreteAExploser()) {
            exploser(grille);
            System.out.println("La mine explose au contact de " + personnage.getNom() + " !");
            personnage.reduireEnergie(20);
            grille.retirerObjet(personnage.getPosition(),this);
        } else {
            System.out.println("La mine n'est pas encore prête à exploser.");
        }
    }



    public void miseAJourDelai(Grille grille) {
        if (delaiExplosion > 0) {
            reduireDelai();
            System.out.println("La mine " + getType() + " a un délai d'explosion réduit à " + delaiExplosion + ".");
        }
        if (delaiExplosion == 0 && !explosion) {
            explosion = true;
            exploser(grille);
        }
    }



    public boolean isUtilisee() {
        return utilisee;
    }

    public void setUtilisee(boolean utilisee) {
        this.utilisee = utilisee;
    }

    public Position getPosition() {
        return position;
    }

    public Position setPosition(Position position) {
       return this.position = position;
    }
}
