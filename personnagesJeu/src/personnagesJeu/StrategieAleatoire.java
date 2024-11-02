package personnagesJeu;

public class StrategieAleatoire implements Strategie {
    @Override
    public void executerTour(Personnage personnage, ControleurJeu controleur) {
        controleur.actionTirer(personnage, Direction.DROITE);
        controleur.actionDeposerMine(personnage, Direction.BAS);
    }
}
