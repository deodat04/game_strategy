package personnagesJeu;
import jeuCombat.Grille;

import java.util.List;

public class JoueurHumain extends Personnage {

    public JoueurHumain(String nom, int energie, Position position, List<Armes> armes, Grille grille, int vie) {
        super(nom, energie, position, armes, grille, vie);
    }

    @Override
    public void deplacer(Direction direction) {
        //System.out.println(getNom() + " (Joueur Humain) se déplace vers " + direction);
        super.deplacer(direction);
    }

    @Override
    public void tirer(Direction direction) {
        //System.out.println(getNom() + " (Joueur Humain) tire vers " + direction);
        super.tirer(direction);
    }


}
