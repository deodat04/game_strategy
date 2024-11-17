package personnagesJeu;

public class StrategieDefensive implements Strategie {
    @Override
    public void executerTour(Personnage personnage, ControleurJeu controleur) {
        personnage.activerBouclier();
        controleur.deplacement(personnage, Direction.DROITE);
    }
}
