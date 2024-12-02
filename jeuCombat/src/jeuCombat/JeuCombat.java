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

    }
}
