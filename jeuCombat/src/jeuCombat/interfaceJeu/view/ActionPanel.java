package jeuCombat.interfaceJeu.view;


//les boutons pour les actions du joueur et les flèches de direction.

import jeuCombat.interfaceJeu.ControleurInterface;
import personnagesJeu.Direction;
import personnagesJeu.Personnage;
import personnagesJeu.Position;

import javax.swing.*;


public class ActionPanel  extends JPanel {
    private ControleurInterface controleur;

    public ActionPanel(ControleurInterface controleur, JTextArea logArea) {
        this.controleur = controleur;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton bombButton = new JButton("Déposer Bombe");
        bombButton.addActionListener(e -> {
            Position position = demanderPosition();
            if (position != null) {
                controleur.effectuerAction(controleur.getPlayers().get(controleur.joueurActuel), "deposerBombe");
            }
        });
        add(bombButton);

        JButton mineButton = new JButton("Déposer Mine");
        mineButton.addActionListener(e -> {
            Position position = demanderPosition(); // Récupérer la position pour la mine
            if (position != null) {
                controleur.effectuerAction(controleur.getPlayers().get(controleur.joueurActuel), "deposerMine");
            }
        });
        add(mineButton);

        JButton tirerButton = new JButton("Tirer");
        tirerButton.addActionListener(e -> {
            Direction direction = demanderDirection();
            if (direction != null) {
                controleur.effectuerAction(controleur.getPlayers().get(controleur.joueurActuel), "tirer");
            }
        });
        add(tirerButton);

        JButton shieldButton = new JButton("Bouclier");
        shieldButton.addActionListener(e -> {
            controleur.effectuerAction(controleur.getPlayers().get(controleur.joueurActuel), "utiliserBouclier");
        });
        add(shieldButton);

        JButton upButton = new JButton("↑");
        upButton.addActionListener(e -> controleur.effectuerAction(controleur.getPlayers().get(controleur.joueurActuel), "deplacerHaut"));
        add(upButton);

        JButton downButton = new JButton("↓");
        downButton.addActionListener(e -> controleur.effectuerAction(controleur.getPlayers().get(controleur.joueurActuel), "deplacerBas"));
        add(downButton);

        JButton leftButton = new JButton("←");
        leftButton.addActionListener(e -> controleur.effectuerAction(controleur.getPlayers().get(controleur.joueurActuel), "deplacerGauche"));
        add(leftButton);

        JButton rightButton = new JButton("→");
        rightButton.addActionListener(e -> controleur.effectuerAction(controleur.getPlayers().get(controleur.joueurActuel), "deplacerDroite"));
        add(rightButton);


        add(logArea);
    }
    public Position demanderPosition() {
        String input = JOptionPane.showInputDialog("Entrez la position (x, y) :");
        if (input != null && input.matches("\\d+,\\d+")) {
            String[] parts = input.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            return new Position(x, y);
        }
        JOptionPane.showMessageDialog(null, "Position invalide !");
        return null;
    }

    public Direction demanderDirection() {
        Direction[] directions = Direction.values();
        Direction choix = (Direction) JOptionPane.showInputDialog(
                null,
                "Choisissez une direction :",
                "Direction",
                JOptionPane.QUESTION_MESSAGE,
                null,
                directions,
                directions[0]
        );
        return choix;
    }

    public void setPersonnage(Personnage personnage) {
    }
}
