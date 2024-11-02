/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jeuCombat;

/**
 *
 * @author adanded241
 */

import personnagesJeu.*;
import personnagesJeu.Personnage;

import java.util.List;


public class JeuCombat {
    public static void main(String[] args) {
        // Initialisation des personnages
        Position position1 = new Position(0, 0);
        Position position2 = new Position(5, 5);

        JoueurHumain joueur1 = new JoueurHumain("Joueur 1", 50, position1, List.of(new Bombe(5, "Bombe", 10, 3), new Mine("MineHumain", 4, 10, 3, false, false)));
        JoueurIA joueur2 = new JoueurIA("IA", 50, position2, List.of(new Bombe(5, "Bombe", 10, 4), new Mine("MineIA", 3, 5, 2, false, false)));

        Grille grille = new Grille(10,10);

        Strategie strategieJoueur1 = new StrategieOffensive();
        Strategie strategieJoueur2 = new StrategieDefensive();

        ControleurJeu partie = new ControleurJeu(grille, List.of(joueur1, joueur2), List.of(strategieJoueur1, strategieJoueur2));

        // Affichage de la configuration initiale
        afficherConfiguration(partie);

        // Démarrage de la partie
        partie.startPartie();
    }

    private static void afficherConfiguration(ControleurJeu controleurJeu) {
        System.out.println("Configuration initiale de la partie :");
        controleurJeu.getGrille().afficherGrille();
        for (Personnage personnage : controleurJeu.getPersonnages()) {
            System.out.println("Personnage: " + personnage.getNom() + " à la position " + personnage.getPosition());
            System.out.println("Energie restante : " + personnage.getEnergie());
        }
    }
}
