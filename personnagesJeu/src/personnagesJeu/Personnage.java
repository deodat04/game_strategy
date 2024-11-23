/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personnagesJeu;
import jeuCombat.Grille;
import jeuCombat.observateurs.AbstractModeleEcoutable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adanded241
 */

public class Personnage extends AbstractModeleEcoutable{
    
    private String nom;
    private int energie;
    private boolean bouclierActif;
    public Position position;
    private List<Armes> armes;
    private static final int energieMax = 100;
    private Grille grille;


    public Personnage(String nom, int energie, Position position, List<Armes> armes,Grille grille) {
        this.nom = nom;
        this.energie = energie > 0 ? energie : 100;
        this.position = position;
        this.bouclierActif = false;
        this.armes = armes;
        this.grille = grille;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Armes> getArmes() {
        return armes;
    }

    public void ajouterArme(Armes arme) {
        armes.add(arme);
    }

    public void deplacer(Direction direction) {
        int oldX = position.getX();
        int oldY = position.getY();
        Position anciennePosition = new Position(oldX, oldY);

        //déplacement en fonction de la direction
        switch (direction) {
            case HAUT:
                if (position.getY() > 0) {
                    position.setY(position.getY() - 1);
                } else {
                    System.out.println(nom + " ne peut pas se déplacer vers HAUT, position hors limite.");
                    return; //empêche le déplacement si hors limite
                }
                break;
            case BAS:
                if (position.getY() < grille.getHauteur() - 1) { //vérifie si le déplacement est dans les limites de la grille
                    position.setY(position.getY() + 1);
                } else {
                    System.out.println(nom + " ne peut pas se déplacer vers BAS, position hors limite.");
                    return; //empêche le déplacement si hors limite
                }
                break;
            case GAUCHE:
                if (position.getX() > 0) {
                    position.setX(position.getX() - 1);
                } else {
                    System.out.println(nom + " ne peut pas se déplacer vers GAUCHE, position hors limite.");
                    return;
                }
                break;
            case DROITE:
                if (position.getX() < grille.getLargeur() - 1) {
                    position.setX(position.getX() + 1);
                } else {
                    System.out.println(nom + " ne peut pas se déplacer vers DROITE, position hors limite.");
                    return;
                }
                break;
        }

        Position nouvellePosition = new Position(position.getX(), position.getY());

        //mise à jour de la grille avec la nouvelle position
        grille.mettreAJourPosition(anciennePosition, nouvellePosition, this);

        System.out.println(nom + " se déplace vers " + direction);
    }



    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public int getEnergie() {
        return energie;
    }

    public void setEnergie(int nouvelleEnergie) {
        this.energie = nouvelleEnergie;
        fireChangement();
    }


//    public void deposerMine(Mine mine) {
//        if (mine != null) {
//            // Ajoute la mine à la liste des objets du personnage
//            objets.add(mine);
//            System.out.println(getNom() + " a ajouté une mine à son inventaire.");
//        }
//    }


    public void tirer(Direction direction) {
        if (!armes.isEmpty()) {
            Armes arme = armes.get(0); //utilisation de la première arme
            arme.utiliser(this);  //tire en utilisant l'arme
            energie -=10;
            System.out.println(nom + " tire en direction de " + direction + " avec " + arme.getType());
        } else {
            System.out.println(nom + " n'a pas d'arme pour tirer.");
        }
    }

    public void activerBouclier() {
        bouclierActif = true;
        energie -=15;
        System.out.println( nom + "active son bouclier hahaha");
    }

    public void rienFaire(){
        System.out.println( nom + "ne fait rien cette fois");

    }

    public void regenererEnergie() {
        this.energie = Math.min(energie + 10, energieMax);
        fireChangement();
    }

    public void reduireEnergie(int energie) {
        this.energie = energie;
        fireChangement();
    }
    
}