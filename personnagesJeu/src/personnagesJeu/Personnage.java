/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personnagesJeu;
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
    private Position position;
    private List<Armes> armes;
    private static final int energieMax = 100;


    public Personnage(String nom, int energie, Position position, List<Armes> armes) {
        this.nom = nom;
        this.energie = energie;
        this.position = position;
        this.bouclierActif = false;
        this.armes = armes;
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
        switch (direction) {
            case HAUT:
                position.setY(position.getY() - 1);
                break;
            case BAS:
                position.setY(position.getY() + 1);
                break;
            case GAUCHE:
                position.setX(position.getX() - 1);
                break;
            case DROITE:
                position.setX(position.getX() + 1);
                break;
        }
        energie -=5;
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


    public void deposerMine(Direction direction) {
        Mine mine = new Mine("Mine", 1, 10, 5, true, false);
        energie -=20;
        System.out.println(nom + " dépose une mine en position " + position);
        fireChangement();
    }

    public void tirer(Direction direction) {
        if (!armes.isEmpty()) {
            Armes arme = armes.get(0); // Utilisation de la première arme
            arme.utiliser(this);  // Tire en utilisant l'arme
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
        this.energie = Math.min(energie + 10, energieMax); //régénération limitée
        fireChangement();
    }

    public void actualiserMine() {
        for (Armes arme : armes) {
            if (arme instanceof Mine) {
                Mine mine = (Mine) arme;
                mine.reduireDelai();
                if (mine.estPreteAExploser()) {
                    mine.exploser();
                    fireChangement();
                }
            }
        }
    }
    
}