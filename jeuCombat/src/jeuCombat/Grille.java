package jeuCombat;

import personnagesJeu.*;

import java.util.*;


public class Grille {
    private int largeur;
    private int hauteur;
    private final Map<Position, List<Object>> positionObjectMap;
    private List<Personnage> personnages;

    public Grille(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.positionObjectMap = new HashMap<>();
        this.personnages = new ArrayList<>();
    }

    public void ajouterPersonnage(Personnage personnage) {
        personnages.add(personnage);
    }

    public List<Object> getObjetsParPosition(Position position) {
        return positionObjectMap.getOrDefault(position, new ArrayList<>());
    }


    public void ajouterObjet(Position position, Object objet) {
        positionObjectMap.computeIfAbsent(position, k -> new ArrayList<>()).add(objet);
    }

    public void mettreAJourPosition(Position anciennePosition, Position nouvellePosition, Object objet) {
        // Vérifie si l'objet existe déjà dans la nouvelle position
        if (positionObjectMap.containsKey(nouvellePosition)) {
            List<Object> objetsDansNouvellePosition = positionObjectMap.get(nouvellePosition);
            // Si l'objet est déjà dans la nouvelle position, on ne l'ajoute pas à nouveau
            if (objetsDansNouvellePosition.contains(objet)) {
                //System.out.println("L'objet est déjà présent à la nouvelle position.");
                return; // Ne rien faire si l'objet est déjà à la nouvelle position
            }
        }
        retirerObjet(anciennePosition, objet);

        ajouterObjet(nouvellePosition, objet);
    }


    public void retirerObjet(Position position, Object objet) {
        if (positionObjectMap.containsKey(position)) {
            List<Object> objets = positionObjectMap.get(position);
            objets.removeIf(o -> o.equals(objet)); // Retirer l'objet spécifique de la liste
            if (objets.isEmpty()) {
                positionObjectMap.remove(position); // Si la case devient vide, on la supprime
            }
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
                    List<Object> objets = positionObjectMap.get(position);
                    // Affichage de chaque objet dans la case
                    for (Object objet : objets) {
                        if (objet instanceof JoueurHumain) {
                            System.out.print("H ");  // Affiche 'H' pour un joueur humain
                        } else if (objet instanceof JoueurIA) {
                            System.out.print("I ");  // Affiche 'I' pour un joueur IA
                        } else if (objet instanceof Mine) {
                            System.out.print("M ");  // Affiche 'M' pour une mine
                        } else if (objet instanceof Bombe) {
                            System.out.print("B ");  // Affiche 'B' pour une bombe
                        }
                    }
                } else {
                    // Case vide
                    System.out.print(". ");
                }
            }
            System.out.println();  // Nouvelle ligne à la fin de chaque ligne de la grille
        }
        System.out.println();  // Nouvelle ligne après l'affichage de la grille
    }



    public int getLargeur() {
        return largeur;
    }
    public int getHauteur() {
        return hauteur;
    }

    public List<Personnage> getPersonnages() {
        return this.personnages;
    }


    public List<Personnage> getPersonnagesDansRayon(Position centreExplosion, int rayonExplosion, Grille grille) {
        List<Personnage> personnagesDansRayon = new ArrayList<>();
        int x = centreExplosion.getX();
        int y = centreExplosion.getY();

        // Parcourir les cases dans le rayon d'explosion
        for (int dx = -rayonExplosion; dx <= rayonExplosion; dx++) {
            for (int dy = -rayonExplosion; dy <= rayonExplosion; dy++) {
                int newX = x + dx;
                int newY = y + dy;

                // Vérifier que la case est valide (dans les limites de la grille)
                if (newX >= 0 && newY >= 0 && newX < grille.getLargeur() && newY < grille.getHauteur()) {
                    Position positionActuelle = new Position(newX, newY);

                    // Récupérer les objets à cette position
                    List<Object> objetsSurCase = grille.getObjetsSurPosition(positionActuelle);

                    // Vérifier s'il y a des personnages dans cette case
                    for (Object objet : objetsSurCase) {
                        if (objet instanceof Personnage) {
                            Personnage personnage = (Personnage) objet;
                            personnagesDansRayon.add(personnage);
                            System.out.println("Personnage trouvé : " + personnage.getNom() + " à la position " + positionActuelle);
                        }
                    }
                }
            }
        }

        return personnagesDansRayon;
    }

    public List<Object> getObjetsSurPosition(Position position) {
        return positionObjectMap.getOrDefault(position, new ArrayList<>());
    }

    public List<Object> getObjets(Position position) {
        return positionObjectMap.getOrDefault(position, new ArrayList<>());
    }

    public Set<Position> getToutesLesPositions() {
        return positionObjectMap.keySet();
    }

    public boolean estDansLesLimites(Position position) {
        return position.getX() >= 0 && position.getX() < largeur && position.getY() >= 0 && position.getY() < hauteur;
    }


    public Personnage getPersonnageA(Position position) {
        if (!estDansLesLimites(position)) {
            System.out.println("Position hors limites : " + position);
            return null;
        }

        Object objet = positionObjectMap.get(position);
        if (objet == null) {
            System.out.println("Aucun objet trouvé à la position : " + position);
            return null;
        }

        if (objet instanceof Personnage) {
            System.out.println("Personnage trouvé : " + ((Personnage) objet).getNom() + " à la position " + position);
            return (Personnage) objet;
        }

        System.out.println("L'objet à la position n'est pas un personnage : " + objet);
        return null;
    }
}
