package personnagesJeu;

public class Armes {

    private int durabilite ;
    private String type;
    private Position position;
    private int munitions;

  public Armes(int durabilite , String type, int munitions, Position position) {
      this.durabilite = durabilite;
      this.type = type;
      this.munitions = this.munitions;
      this.position = position;
  }

    public void utiliser(Personnage personnage) {
        if (durabilite > 0) {
            durabilite--;
            munitions--;
            System.out.println("Utilisation de l'arme " + type + " sur " + personnage.getNom());
        } else {
            System.out.println("L'arme " + type + " est inutilisable, durabilité épuisée.");
        }
    }

    public int getDurabilite() {
        return durabilite;
    }

    public String getType() {
        return type;
    }

    public int getMunitions() {
        return munitions;
    }
}
