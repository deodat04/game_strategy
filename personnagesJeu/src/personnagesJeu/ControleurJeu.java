package personnagesJeu;

import jeuCombat.Grille;
import jeuCombat.observateurs.AbstractModeleEcoutable;

import java.util.ArrayList;
import java.util.List;

public class ControleurJeu extends AbstractModeleEcoutable {
    private Grille grille;
    private List<Personnage> personnages;
    private List<Strategie> strategies;
    private int tourActuel;

    public  ControleurJeu(Grille grille, List<Personnage> personnages, List<Strategie> strategies) {
        this.grille = grille;
        this.personnages = personnages;
        this.strategies = strategies;
        this.tourActuel = 0;
    }

    public Grille getGrille() {
        return grille;
    }
    public List<Personnage> getPersonnages() {
        return personnages;
    }

    public void actionTirer(Personnage personnage, Direction direction) {
        if (personnage.getEnergie() > 0) {
            personnage.tirer(direction);
            System.out.println(personnage.getNom() + " a tiré en direction de " + direction);
            fireChangement();
        } else {
            System.out.println(personnage.getNom() + " n'a pas assez d'énergie pour tirer.");
        }
    }

    public void deplacement(Personnage personnage, Direction direction) {
        if (personnage.getEnergie() > 0) {
            Position lastPosition = personnage.getPosition().clone();
            personnage.deplacer(direction);
            Position nextPosition = personnage.getPosition();
            if (!lastPosition.equals(nextPosition)) {
                System.out.println("Déplacement de " + personnage.getNom() + " de " + lastPosition + " à " + nextPosition);
            } else {
                System.out.println(personnage.getNom() + " n'a pas bougé.");
                System.out.println("Position actuelle : " + nextPosition);
            }
            fireChangement();
            grille.afficherGrille();
        } else {
            System.out.println(personnage.getNom() + " n'a pas assez d'énergie pour se déplacer.");
        }
    }


    public void actionDeposerMine(Personnage personnage, Direction direction) {
        if (personnage.getEnergie() > 0) {
            personnage.deposerMine(direction);
            System.out.println(personnage.getNom() + " a déposé une mine en direction de " + direction);
            fireChangement();
        } else {
            System.out.println(personnage.getNom() + " ne peut pas déposer de mine.");
        }
    }

    public void actionActiverBouclier(Personnage personnage) {
        if (personnage.getEnergie() > 0) {
            personnage.activerBouclier();
        } else {
            System.out.println(personnage.getNom() + " ne peut pas activer le bouclier.");
        }
    }

    public void actionRienFaire(Personnage personnage) {
        personnage.rienFaire();
    }

    public void startPartie() {
        System.out.println("Début de la partie !");
        while (!endPartie()) {
            effectuerTour();
            passerAuProchainTour();
        }
        System.out.println("La partie est terminée !");

        Personnage gagnant = null;
        int maxEnergie = -1;

        for (Personnage personnage : personnages) {
            if (personnage.getEnergie() > maxEnergie) {
                maxEnergie = personnage.getEnergie();
                gagnant = personnage;
            }
        }

        if (gagnant != null) {
            System.out.println("Le joueur " + gagnant.getNom() + " a gagné avec " + maxEnergie + " points d'énergie restants !");
        } else {
            System.out.println("Aucun gagnant. Tous les personnages ont perdu.");
        }
    }

    private void effectuerTour() {
        Personnage personnageEnCours = personnages.get(tourActuel);
        Strategie strategie = strategies.get(tourActuel);
        System.out.println("\nTour de : " + personnageEnCours.getNom());

        personnageEnCours.regenererEnergie();
        strategie.executerTour(personnageEnCours, this);
        fireChangement();
    }


    private void passerAuProchainTour() {
        tourActuel = (tourActuel + 1) % personnages.size();
        System.out.println("Tour suivant : " + personnages.get(tourActuel).getNom());
    }

    private boolean endPartie() {
        System.out.println("Fin du tour !");
        int joueursActifs = 0;
        for (Personnage personnage : personnages) {
            if (personnage.getEnergie() > 0) {
                joueursActifs++;
            }
        }
        return joueursActifs <= 1;
    }
}
