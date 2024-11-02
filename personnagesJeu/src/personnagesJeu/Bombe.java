package personnagesJeu;

public class Bombe extends Armes {
    private int rayonExplosion;

    public Bombe(int durabilite, String type, int munitions, int rayonExplosion, Position position){
        super(durabilite, type, munitions, position);
        this.rayonExplosion = rayonExplosion;
    }

    //exploser une bombe
    public void exploser() {
        if (getDurabilite() > 0) {
            System.out.println("La bombe " + getType() + " explose avec un rayon de " + rayonExplosion + " mètres !");
            utiliser(null);
        } else {
            System.out.println("La bombe " + getType() + " est inutilisable.");
        }
    }

    public int getRayonExplosion() {
        return rayonExplosion;
    }



}
