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
    public void exploser() {
        if (delaiExplosion == 0 || explosion) {
            System.out.println("La mine " + getType() + " explose !");
            utiliser(null);
        } else {
            System.out.println("La mine n'est pas encore prête à exploser.");
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
            exploser();
            System.out.println("La mine explose au contact de " + personnage.getNom() + " !");
            personnage.reduireEnergie(20);
            grille.retirerObjet(personnage.getPosition());
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
