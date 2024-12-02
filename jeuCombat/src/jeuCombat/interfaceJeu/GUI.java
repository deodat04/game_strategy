package jeuCombat.interfaceJeu;

//affichage de la grille de jeu, des boutons de direction et des actions disponibles pour chaque joueur.

import jeuCombat.Grille;
import jeuCombat.interfaceJeu.view.ActionPanel;
import jeuCombat.interfaceJeu.view.GameBoardPanel;
import jeuCombat.interfaceJeu.view.ConfigurationPanel;
import personnagesJeu.Constantes;
import personnagesJeu.Personnage;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private ControleurInterface controleur;
    private static JTextArea logArea = new JTextArea();
    static JPanel gamePanel = new JPanel();
    public static Grille grilleDeJeu = new Grille(5, 5);
    static List<Personnage> personnages = new ArrayList<>();
    private static Personnage personnage;


    public GUI(ControleurInterface controleur, Personnage personnage) {
        this.controleur = controleur;
        this.personnage = personnage;

        //ajouter mur
        controleur.ajouterMur(2, 3);

        //ajouter energie
        controleur.ajouterObjetEnergie(1, 3);

        setTitle("Jeu Combat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);

        showConfigurationPanel();
    }

    /**
     * Affiche le panneau de configuration.
     */
    private void showConfigurationPanel() {
        ControleurInterface controleur = new ControleurInterface(personnages,gamePanel,logArea,grilleDeJeu);
        ConfigurationPanel configurationPanel = new ConfigurationPanel(grilleDeJeu,this::onConfigurationValidated);
        configurationPanel.setControleur(controleur);
        setContentPane(configurationPanel);
        revalidate();
        repaint();
    }

    /**
     * Méthode appelée lorsque la configuration est validée.
     */
    private void onConfigurationValidated() {
        showGameScreen();
    }

    /**
     * Affiche l'écran principal de la grille de jeu et des actions.
     */
    private void showGameScreen() {
        GameBoardPanel gameBoardPanel = new GameBoardPanel(controleur);
        ActionPanel actionPanel = new ActionPanel(controleur, logArea);

        //disposer les panneaux
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(actionPanel, BorderLayout.WEST);
        mainPanel.add(gameBoardPanel, BorderLayout.CENTER);

        //appliquer le panneau principal comme contenu
        setContentPane(mainPanel);
        revalidate();
        repaint();
    }


    public static void main(String[] args) {
        //création d'un contrôleur et lancement de l'interface
        ControleurInterface controleur = new ControleurInterface(personnages, gamePanel, logArea, grilleDeJeu);
        new GUI(controleur,personnage).setVisible(true);
    }
}
