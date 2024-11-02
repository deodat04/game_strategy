package personnagesJeu;

public class Mine extends Armes {
    private boolean visible;
    private int delaiExplosion;
    private boolean explosion;


    public Mine(String type, int durabilite, int munitions, int delaiExplosion, boolean visible, boolean explosion, Position position){
        super(durabilite, type, munitions, position); //appel du constructeur de Armes
        this.visible = visible;
        this.delaiExplosion = delaiExplosion;
        this.explosion = explosion;
    }

    //exploser mine
    public void exploser() {
        if (delaiExplosion == 0) {
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

}
