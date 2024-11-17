package jeuCombat;

import personnagesJeu.*;

import java.util.HashMap;
import java.util.Map;


public class Grille {
    private int largeur;
    private int hauteur;

    private Map<Position, Object> positionObjectMap;

    public Grille(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.positionObjectMap = new HashMap<>();
    }

    public void ajouterObjet(Position position, Object objet) {
        positionObjectMap.put(position, objet);
    }

    public void mettreAJourPosition(Position anciennePosition, Position nouvellePosition, Object objet) {
        //retirer l'objet de l'ancienne position
        positionObjectMap.remove(anciennePosition);

        //ajouter l'objet à la nouvelle position
        positionObjectMap.put(nouvellePosition, objet);
    }

    public boolean estPositionLibre(Position position) {
        return !positionObjectMap.containsKey(position);
    }

    public void afficherGrille() {
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                Position position = new Position(x, y);
                if (positionObjectMap.containsKey(position)) {
                    Object objet = positionObjectMap.get(position);
                    if (objet instanceof JoueurHumain) {
                        System.out.print("H ");
                    } else if (objet instanceof JoueurIA){
                        System.out.print("I ");
                    } else if (objet instanceof Mine) {
                        System.out.print("M ");
                    } else if (objet instanceof Bombe) {
                        System.out.print("B ");
                    }
                } else {
                    // Case vide
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }


    public int getLargeur() {
        return largeur;
    }
    public int getHauteur() {
        return hauteur;
    }

}
