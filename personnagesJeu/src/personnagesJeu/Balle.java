package personnagesJeu;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Balle {
    private Position position;
    private Direction direction;
    private Personnage tireur;
    private JPanel panel;

    public Balle(Position position, Direction direction, Personnage tireur, JPanel panel) {
        this.position = position;
        this.direction = direction;
        this.tireur = tireur;
        this.panel = panel;
    }

    public Position getPosition() {
        return position;
    }

    public void deplacer() {
        if (direction == Direction.HAUT) {
            position = new Position(position.getX(), position.getY() - 1);
        } else if (direction == Direction.BAS) {
            position = new Position(position.getX(), position.getY() + 1);
        } else if (direction == Direction.GAUCHE) {
            position = new Position(position.getX() - 1, position.getY());
        } else if (direction == Direction.DROITE) {
            position = new Position(position.getX() + 1, position.getY());
        }
        panel.repaint();  // Redessine la balle sur le panneau après chaque mouvement
    }

    public boolean touchePersonnage(Personnage personnage) {
        return personnage.getPosition().equals(position);
    }

    public void afficherBalle(Graphics g) {
        g.setColor(java.awt.Color.RED); // Couleur du trait de la balle
        g.fillOval(position.getX(), position.getY(), 5, 5); // Dessine un cercle (taille de la balle)
    }


}
