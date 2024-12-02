package jeuCombat.interfaceJeu.view;

import jeuCombat.Grille;
import jeuCombat.interfaceJeu.ControleurInterface;
import personnagesJeu.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

//Classe ConfigurationPanel pour la configuration initiale des joueurs

public class ConfigurationPanel extends JPanel {
    private JTextField nbJoueursField;
    private JPanel joueurPanel;
    private JButton ajouterJoueurButton;
    private JButton validerButton;

    private List<JoueurHumain> joueurs;
    private Grille grille;
    private Runnable onValidated;

    private ControleurInterface controleur;

    public ConfigurationPanel(Grille grille, Runnable onValidated) {
        this.joueurs = new ArrayList<>();
        this.grille = grille;
        this.onValidated = onValidated;

        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.add(new JLabel("Nombre de joueurs :"));

        nbJoueursField = new JTextField();
        topPanel.add(nbJoueursField);

        ajouterJoueurButton = new JButton("Ajouter Joueur");
        ajouterJoueurButton.addActionListener(e -> ajouterJoueur());
        topPanel.add(ajouterJoueurButton);

        validerButton = new JButton("Valider Configuration");
        validerButton.setEnabled(false);
        validerButton.addActionListener(this::validerConfiguration);
        topPanel.add(validerButton);

        add(topPanel, BorderLayout.NORTH);

        joueurPanel = new JPanel();
        joueurPanel.setLayout(new BoxLayout(joueurPanel, BoxLayout.Y_AXIS));
        add(joueurPanel, BorderLayout.CENTER);
    }

    public void setControleur(ControleurInterface controleur) {
        this.controleur = controleur;
    }

    public void ajouterJoueur(String nom, int x, int y, List<Armes> armes) {
        controleur.ajouterJoueur(nom, x, y, armes);
    }

    private void ajouterJoueur() {
        try {
            int nbJoueurs = Integer.parseInt(nbJoueursField.getText());
            if (nbJoueurs <= 0) {
                JOptionPane.showMessageDialog(this, "Le nombre de joueurs doit être positif.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (int i = 0; i < nbJoueurs; i++) {
                JPanel joueurFormPanel = new JPanel(new GridLayout(4, 2));
                joueurFormPanel.add(new JLabel("Nom du joueur :"));
                JTextField nomField = new JTextField();
                joueurFormPanel.add(nomField);

                joueurFormPanel.add(new JLabel("Position (x) :"));
                JTextField xPositionField = new JTextField();
                joueurFormPanel.add(xPositionField);

                joueurFormPanel.add(new JLabel("Position (y) :"));
                JTextField yPositionField = new JTextField();
                joueurFormPanel.add(yPositionField);

                joueurFormPanel.add(new JLabel("Nb Mines :"));
                JTextField nbMinesField = new JTextField();
                joueurFormPanel.add(nbMinesField);

                joueurFormPanel.add(new JLabel("Type Mine :"));
                JTextField typeMineField = new JTextField();
                joueurFormPanel.add(typeMineField);

                joueurFormPanel.add(new JLabel("Nb Bombes :"));
                JTextField nbBombesField = new JTextField();
                joueurFormPanel.add(nbBombesField);

                joueurFormPanel.add(new JLabel("Type Bombe :"));
                JTextField typeBombeField = new JTextField();
                joueurFormPanel.add(typeBombeField);

                joueurFormPanel.add(new JLabel("Choix du fusil :"));
                JComboBox<String> fusilComboBox = new JComboBox<>(new String[]{
                        "Fusil d'assaut", "Fusil de précision", "Mitrailleuse"});
                joueurFormPanel.add(fusilComboBox);

                joueurPanel.add(joueurFormPanel);
                joueurPanel.revalidate();
            }
            validerButton.setEnabled(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validerConfiguration(ActionEvent e) {
        Component[] components = joueurPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel joueurFormPanel = (JPanel) component;
                try {
                    JTextField nomField = (JTextField) joueurFormPanel.getComponent(1);
                    JTextField xField = (JTextField) joueurFormPanel.getComponent(3);
                    JTextField yField = (JTextField) joueurFormPanel.getComponent(5);
                    JTextField nbMinesField = (JTextField) joueurFormPanel.getComponent(7);
                    JTextField typeMineField = (JTextField) joueurFormPanel.getComponent(9);
                    JTextField nbBombesField = (JTextField) joueurFormPanel.getComponent(11);
                    JTextField typeBombeField = (JTextField) joueurFormPanel.getComponent(13);
                    JComboBox<?> fusilComboBox = (JComboBox<?>) joueurFormPanel.getComponent(15);

                    String nom = nomField.getText();
                    int x = Integer.parseInt(xField.getText());
                    int y = Integer.parseInt(yField.getText());
                    int nbMines = Integer.parseInt(nbMinesField.getText());
                    String typeMine = typeMineField.getText();
                    int nbBombes = Integer.parseInt(nbBombesField.getText());
                    String typeBombe = typeBombeField.getText();

                    if (nom.isEmpty() || typeMine.isEmpty() || typeBombe.isEmpty()) {
                        throw new IllegalArgumentException("Tous les champs doivent être remplis.");
                    }

                    Position position = new Position(x, y);
                    if (!grille.estPositionLibre(position)) {
                        JOptionPane.showMessageDialog(this, "Position déjà occupée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    List<Armes> armes = new ArrayList<>();
                    for (int j = 0; j < nbMines; j++) {
                        armes.add(new Mine(typeMine, Constantes.DURABILITE_ARMES.get("mine"), Constantes.MUNITIONS_ARMES.get("mine"), 0, false, false, false, null, true));
                    }
                    for (int j = 0; j < nbBombes; j++) {
                        armes.add(new Bombe(Constantes.DURABILITE_ARMES.get("bombe"), typeBombe, Constantes.MUNITIONS_ARMES.get("bombe"), Constantes.RAYONS_EXPLOSION.get("moyen"), false, null, true));
                    }

                    switch (fusilComboBox.getSelectedIndex()) {
                        case 0 -> armes.add(new FusilAssaut("fusil_assaut", null, true));
                        case 1 -> armes.add(new FusilPrecision("fusil_precision", null,true));
                        case 2 -> armes.add(new Mitrailleuse("mitrailleuse", null,true));
                    }

                    JoueurHumain joueur = new JoueurHumain(nom, 100, position, armes, grille,100);
                    joueurs.add(joueur);
                    controleur.ajouterJoueur(nom,position.getX(), position.getY(), armes);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur dans les données : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Tous les joueurs ont été ajoutés avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        if (onValidated != null) {
            onValidated.run();
        }
    }
}


