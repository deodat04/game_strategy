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

import java.util.ArrayList;
import java.util.List;


public class JeuCombat {
    public static void main(String[] args) {
        Grille grille = new Grille(10,10);
        List<Personnage> personnages = new ArrayList<>();
        ControleurJeu partie = new ControleurJeu(grille, personnages, new ArrayList<>());
        partie.partieManuel();

//        //initialisation des personnages, armes et positions
//        Position positionJ1 = new Position(0, 0);
//        Position positionJ2 = new Position(5, 5);
//        Position positionA1 = new Position(2, 6);
//        Position positionA2 = new Position(5, 8);
//        Position positionA3 = new Position(7, 3);
//        Position positionA4 = new Position(9, 8);
//
//        Grille grille = new Grille(10,10);
//
//        Bombe bombe1 = new Bombe(5, "Bim", 5, 4, positionA1);
//        Bombe bombe2 = new Bombe(5, "Bam", 5, 4, positionA2);
//        Mine mine1 = new Mine("Badaboum", 3, 5, 2, false, false, positionA3);
//        Mine mine2 = new Mine("boomm", 3, 5, 2, false, false, positionA4);
//
//        JoueurHumain joueur1 = new JoueurHumain("JohnDoe", 50, positionJ1, List.of(bombe1,mine1), grille);
//        JoueurIA joueur2 = new JoueurIA("Solara", 50, positionJ2, List.of(bombe2, mine2), grille);
//
//        Strategie strategieJoueur1 = new StrategieOffensive();
//        Strategie strategieJoueur2 = new StrategieDefensive();
//
//        ControleurJeu partie = new ControleurJeu(grille, List.of(joueur1, joueur2), List.of(strategieJoueur1, strategieJoueur2));
//
//        //ajouter des objets sur la grille
//        grille.ajouterObjet(positionJ1, joueur1);
//        grille.ajouterObjet(positionJ2, joueur2);
//        grille.ajouterObjet(positionA1, bombe1);
//        grille.ajouterObjet(positionA2, bombe2);
//        grille.ajouterObjet(positionA3, mine1);
//        grille.ajouterObjet(positionA4, mine2);
//
//
//        //affichage de la configuration initiale
//        afficherConfiguration(partie);
//
//        //démarrage de la partie
//        partie.startPartie();
    }

//    private static void afficherConfiguration(ControleurJeu controleurJeu) {
//        System.out.println("Configuration initiale de la partie :");
//        controleurJeu.getGrille().afficherGrille();
//        for (Personnage personnage : controleurJeu.getPersonnages()) {
//            System.out.println("Personnage: " + personnage.getNom() + " à la position " + personnage.getPosition());
//            System.out.println("Energie restante : " + personnage.getEnergie());
//        }
//    }

}
