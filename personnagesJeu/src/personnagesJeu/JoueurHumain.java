package personnagesJeu;
import jeuCombat.Grille;

import java.util.List;

public class JoueurHumain extends Personnage {

    public JoueurHumain(String nom, int energie, Position position, List<Armes> armes, Grille grille) {
        super(nom, energie, position, armes, grille);
    }

    @Override
    public void deplacer(Direction direction) {
        System.out.println(getNom() + " (Joueur Humain) se déplace vers " + direction);
        super.deplacer(direction);
    }

    @Override
    public void tirer(Direction direction) {
        System.out.println(getNom() + " (Joueur Humain) tire vers " + direction);
        super.tirer(direction);
    }

    @Override
    public void deposerMine(Direction direction) {
        System.out.println(getNom() + " (Joueur Humain) dépose une mine en position " + getPosition());
        super.deposerMine(direction);
    }

}
