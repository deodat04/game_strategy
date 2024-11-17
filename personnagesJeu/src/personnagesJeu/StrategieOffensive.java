package personnagesJeu;

public class StrategieOffensive implements Strategie{
    @Override
    public void executerTour(Personnage personnage, ControleurJeu controleur) {
        controleur.deplacement(personnage, Direction.BAS);
        controleur.actionTirer(personnage, Direction.DROITE);
    }
}
