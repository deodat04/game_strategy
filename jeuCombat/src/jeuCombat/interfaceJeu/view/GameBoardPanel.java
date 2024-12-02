package jeuCombat.interfaceJeu.view;


//afficher la grille de jeu, c'est-à-dire la disposition des personnages, bombe, mine et mur
import jeuCombat.interfaceJeu.ControleurInterface;
import personnagesJeu.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static jeuCombat.interfaceJeu.GUI.grilleDeJeu;


public class GameBoardPanel extends JPanel {
    private ControleurInterface controleur;

    public GameBoardPanel(ControleurInterface controleur) {
        this.controleur = controleur;
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("Repaint appelé - Dimensions panneau : " + getWidth() + "x" + getHeight());

        System.out.println("Case size: " + Constantes.CASE_SIZE);


        int caseWidth = getWidth() / grilleDeJeu.getLargeur();
        int caseHeight = getHeight() / grilleDeJeu.getHauteur();
        Constantes.CASE_SIZE = Math.min(caseWidth, caseHeight);
        //Constantes.CASE_SIZE = 150;


        //dessiner la grille
        drawGrid(g);

        System.out.println("Nombre de murs : " + controleur.getMurs().size());

        //dessiner les murs
        drawWalls(g);

        //dessiner les joueurs et leurs éléments
        drawPlayers(g);

        //dessiner les objets d'énergie
        drawEnergyObjects(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= grilleDeJeu.getLargeur(); i++) {
            int x = i * Constantes.CASE_SIZE;
            g.drawLine(x, 0, x, grilleDeJeu.getHauteur() * Constantes.CASE_SIZE);
        }
        for (int j = 0; j <= grilleDeJeu.getHauteur(); j++) {
            int y = j * Constantes.CASE_SIZE;
            g.drawLine(0, y, grilleDeJeu.getLargeur() * Constantes.CASE_SIZE, y);
        }
    }

    private void drawWalls(Graphics g) {
        g.setColor(Color.BLACK);
        for (Mur wall : controleur.getMurs()) {
            g.fillRect(
                    wall.getX() * Constantes.CASE_SIZE,
                    wall.getY() * Constantes.CASE_SIZE,
                    Constantes.CASE_SIZE,
                    Constantes.CASE_SIZE
            );
        }
    }

    private void drawPlayers(Graphics g) {
        List<Personnage> players = controleur.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            Personnage player = players.get(i);

            //Dessiner le joueur
            g.setColor(i == 0 ? Color.RED : Color.BLUE);
            g.fillOval(
                    player.getPosition().getX() * Constantes.CASE_SIZE + Constantes.CASE_SIZE / 4,
                    player.getPosition().getY() * Constantes.CASE_SIZE + Constantes.CASE_SIZE / 4,
                    Constantes.CASE_SIZE / 2,
                    Constantes.CASE_SIZE / 2
            );

            //Dessiner les mines du joueur
            for (Mine mine : player.getMines()) {
                if (mine.getPosition() != null) {
                    g.setColor(Color.RED);
                    g.fillRect(
                            mine.getPosition().getX() * Constantes.CASE_SIZE + Constantes.CASE_SIZE / 4,
                            mine.getPosition().getY() * Constantes.CASE_SIZE + Constantes.CASE_SIZE / 4,
                            Constantes.CASE_SIZE / 2,
                            Constantes.CASE_SIZE / 2
                    );
                }
            }


            //Dessiner les bombes du joueur
            for (Bombe bomb : player.getBombs()) {
                if (bomb.getPosition() != null) {
                    g.setColor(Color.ORANGE);
                    g.fillOval(
                            bomb.getPosition().getX() * Constantes.CASE_SIZE + Constantes.CASE_SIZE / 4,
                            bomb.getPosition().getY() * Constantes.CASE_SIZE + Constantes.CASE_SIZE / 4,
                            Constantes.CASE_SIZE / 2,
                            Constantes.CASE_SIZE / 2
                    );
                }
            }
        }
    }

    private void drawEnergyObjects(Graphics g) {
        g.setColor(Color.GREEN);
        for (ObjetEnergie objetEnergie : controleur.getEnergyObjects()) {
            g.fillOval(
                    objetEnergie.getX() * Constantes.CASE_SIZE + Constantes.CASE_SIZE / 4,
                    objetEnergie.getY() * Constantes.CASE_SIZE + Constantes.CASE_SIZE / 4,
                    Constantes.CASE_SIZE / 4,
                    Constantes.CASE_SIZE / 4
            );
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                grilleDeJeu.getLargeur() * Constantes.CASE_SIZE,
                grilleDeJeu.getHauteur() * Constantes.CASE_SIZE
        );
    }
}
