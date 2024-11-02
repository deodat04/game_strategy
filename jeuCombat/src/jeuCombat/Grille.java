package jeuCombat;

import personnagesJeu.Personnage;
import personnagesJeu.Position;

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

    public void mettreAJourPosition(Position anciennePosition, Position nouvellePosition) {
        Object objet = positionObjectMap.remove(anciennePosition);
        if (objet != null) {
            positionObjectMap.put(nouvellePosition, objet);
        }
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
                    if (objet instanceof Personnage) {
                        System.out.print("P ");
                    } else {
                        System.out.print("O ");
                    }
                } else {
                    // Case vide
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

}
