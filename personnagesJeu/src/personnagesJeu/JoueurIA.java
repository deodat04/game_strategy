package personnagesJeu;
import java.util.List;


public class JoueurIA extends Personnage {

    public JoueurIA(String nom, int energie, Position position, List<Armes> armes) {
        super(nom, energie, position, armes);
    }

    @Override
    public void deplacer(Direction direction) {
        System.out.println(getNom() + " (Joueur IA) se déplace automatiquement vers " + direction);
        super.deplacer(direction);
    }

    @Override
    public void tirer(Direction direction) {
        System.out.println(getNom() + " (Joueur IA) tire automatiquement vers " + direction);
        super.tirer(direction);
    }

    @Override
    public void deposerMine(Direction direction) {
        System.out.println(getNom() + " (Joueur IA) dépose automatiquement une mine en position " + getPosition());
        super.deposerMine(direction);
    }

}
