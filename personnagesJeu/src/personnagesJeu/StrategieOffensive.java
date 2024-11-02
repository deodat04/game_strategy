package personnagesJeu;

public class StrategieOffensive implements Strategie{
    @Override
    public void executerTour(Personnage personnage, ControleurJeu controleur) {
        controleur.deplacement(personnage, Direction.HAUT);
        controleur.actionTirer(personnage, Direction.DROITE);
    }
}
