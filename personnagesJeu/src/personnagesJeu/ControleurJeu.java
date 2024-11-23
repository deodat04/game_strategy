package personnagesJeu;

import jeuCombat.Grille;
import jeuCombat.observateurs.AbstractModeleEcoutable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ControleurJeu extends AbstractModeleEcoutable {
    private Grille grille;
    private List<Personnage> personnages;
    private List<Armes> armes;
    //private List<Strategie> strategies;
    private int tourActuel;

    public  ControleurJeu(Grille grille, List<Personnage> personnages, List<Armes> armes) {
        this.grille = grille;
        this.personnages = personnages;
        this.armes = armes;
        //this.strategies = strategies;
        this.tourActuel = 0;
    }

    public Grille getGrille() {
        return grille;
    }
    public List<Personnage> getPersonnages() {
        return personnages;
    }

    public void actionTirer(Personnage personnage, Direction direction) {
        if (personnage.getEnergie() <= 0) {
            System.out.println(personnage.getNom() + " n'a pas assez d'énergie pour tirer.");
            return;
        }

        if (personnage.getArmes().isEmpty()) {
            System.out.println(personnage.getNom() + " n'a pas d'arme pour tirer.");
            return;
        }

        Armes armeChoisie = personnage.getArmes().get(personnage.getArmes().size() - 1);
        armeChoisie.utiliser(personnage);
        personnage.setEnergie(personnage.getEnergie() - 10);
        System.out.println(personnage.getNom() + " a tiré en direction de " + direction + " avec " + armeChoisie.getType());
        fireChangement();
    }


    public void deplacement(Personnage personnage, Direction direction) {
        if (personnage.getEnergie() > 0) {
            Position lastPosition = personnage.getPosition().clone();
            personnage.deplacer(direction);
            Position nextPosition = personnage.getPosition();
            if (!lastPosition.equals(nextPosition)) {
                System.out.println("Déplacement de " + personnage.getNom() + " de " + lastPosition + " à " + nextPosition);

                Object objetDansLaCase = grille.getObjet(nextPosition);
                if (objetDansLaCase instanceof Mine) {
                    ((Mine) objetDansLaCase).detonnerSiNecessaire(personnage, grille);
                } else if (objetDansLaCase instanceof Bombe) {
                    ((Bombe) objetDansLaCase).detonnerSiNecessaire(personnage, grille);
                }
                grille.mettreAJourPosition(lastPosition, nextPosition, personnage);
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

    public void actionDeposerMine(Personnage personnage, Position position) {
        //vérifie si le personnage a des mines restantes
        Optional<Mine> mineDisponible = personnage.getArmes().stream()
                .filter(arme -> arme instanceof Mine && !((Mine) arme).isUtilisee())
                .map(arme -> (Mine) arme)
                .findFirst();

        if (mineDisponible.isPresent()) {
            Mine mine = mineDisponible.get();

            //vérifie si la position est libre sur la grille
            if (grille.estPositionLibre(position)) {
                //placer la mine sur la grille
                mine.setPosition(position);
                grille.ajouterObjet(position, mine);
                mine.setUtilisee(true);
                fireChangement();
                System.out.println(personnage.getNom() + " a déposé une mine en position " + position);
            } else {
                System.out.println("La position " + position + " est déjà occupée.");
            }
        } else {
            System.out.println(personnage.getNom() + " n'a plus de mines disponibles.");
        }
    }

    public void actionDeposerBombe(Personnage personnage, Position position) {
        //vérifie si le personnage a des mines restantes
        Optional<Bombe> bombeDisponible = personnage.getArmes().stream()
                .filter(arme -> arme instanceof Bombe && !((Bombe) arme).estUtilisee())
                .map(arme -> (Bombe) arme)
                .findFirst();

        if (bombeDisponible.isPresent()) {
            Bombe bombe = bombeDisponible.get();

            //vérifie si la position est libre sur la grille
            if (grille.estPositionLibre(position)) {
                //placer la bombe sur la grille
                bombe.setPosition(position);
                grille.ajouterObjet(position, bombe);
                bombe.setUtilisee(true);
                fireChangement();
                System.out.println(personnage.getNom() + " a déposé une bombe en position " + position);
            } else {
                System.out.println("La position " + position + " est déjà occupée.");
            }
        } else {
            System.out.println(personnage.getNom() + " n'a plus de bombes disponibles.");
        }
    }



//    public void actionDeposerMine(Personnage personnage, Position position) {
//        if (personnage.getEnergie() > 0) {
//            personnage.deposerMine(position);
//            System.out.println(personnage.getNom() + " a déposé une mine en direction de " + position);
//            fireChangement();
//        } else {
//            System.out.println(personnage.getNom() + " ne peut pas déposer de mine.");
//        }
//    }

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
        //Strategie strategie = strategies.get(tourActuel);
        System.out.println("\nTour de : " + personnageEnCours.getNom());

        //strategie.executerTour(personnageEnCours, this);
        fireChangement();
    }


    private void passerAuProchainTour() {
        tourActuel = (tourActuel + 1) % personnages.size();
        //System.out.println("Tour suivant : " + personnages.get(tourActuel).getNom());
    }


//    private boolean endPartie() {
//        System.out.println("Fin du tour !");
//        int joueursActifs = 0;
//        for (Personnage personnage : personnages) {
//            if (personnage.getEnergie() > 0) {
//                joueursActifs++;
//            }
//        }
//        return joueursActifs <= 1;
//    }

    private Personnage verifierGagnant(List<Personnage> joueurs) {
        List<Personnage> joueursRestants = joueurs.stream()
                .filter(joueur -> joueur.getEnergie() > 0)
                .toList();

        if (joueursRestants.size() == 1) {
            return joueursRestants.get(0);
        }

        return null;
    }



    public void partieManuel() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nombre de personnages :");
        int nombrePersonnages = scanner.nextInt();
        scanner.nextLine();

        //List<Personnage> joueurs = new ArrayList<>();
        for (int i = 1; i <= nombrePersonnages; i++) {
            System.out.println("Personnage " + i + ":");

            System.out.print("Entrez votre nom : ");
            String nom = scanner.nextLine();

            System.out.print("Choisissez votre position initiale (x, y) : ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            scanner.nextLine();

            Position positionInitiale = new Position(x, y);

            System.out.println("Choisissez vos armes (max 3 mines et 3 bombes) :");
            List<Armes> armes = new ArrayList<>();

            System.out.print("Entrez le nombre de mines (max " + Constantes.MAX_MINE + ") : ");
            int nbMines = Math.min(scanner.nextInt(), Constantes.MAX_MINE);
            scanner.nextLine();

            for (int j = 0; j < nbMines; j++) {
                System.out.print("Entrez le type de la mine " + (j + 1) + " : ");
                String typeMine = scanner.nextLine();
                Mine mine = new Mine(
                        typeMine,
                        Constantes.DURABILITE_ARMES.get("mine"),
                        Constantes.MUNITIONS_ARMES.get("mine"),
                        0,
                        false,
                        false,
                        false,
                        null
                );
                armes.add(mine);
                grille.ajouterObjet(positionInitiale, mine);
            }

            System.out.print("Entrez le nombre de bombes (max " + Constantes.MAX_BOMBE + ") : ");
            int nbBombes = Math.min(scanner.nextInt(), Constantes.MAX_BOMBE);
            scanner.nextLine();

            for (int j = 0; j < nbBombes; j++) {
                System.out.print("Entrez le type de la bombe " + (j + 1) + " : ");
                String typeBombe = scanner.nextLine();
                Bombe bombe = new Bombe(
                        Constantes.DURABILITE_ARMES.get("bombe"),
                        typeBombe,
                        Constantes.MUNITIONS_ARMES.get("bombe"),
                        Constantes.RAYONS_EXPLOSION.get("moyen"),
                        false,
                        null
                );
                armes.add(bombe);
                grille.ajouterObjet(positionInitiale, bombe);

            }

            System.out.println("Choisissez votre fusil :");
            System.out.println("1- Fusil d'assaut");
            System.out.println("2- Fusil de précision");
            System.out.println("3- Mitraillette");

            int choixFusil = scanner.nextInt();
            scanner.nextLine();

            switch (choixFusil) {
                case 1 -> armes.add(new FusilAssaut("fusil_assaut", null));
                case 2 -> armes.add(new FusilPrecision("fusil_precision", null));
                case 3 -> armes.add(new Mitrailleuse("mitrailleuse", null));
            }

            JoueurHumain joueur = new JoueurHumain(nom, 100, positionInitiale, armes, grille);
            personnages.add(joueur);
            grille.ajouterObjet(positionInitiale, joueur);
        }

        System.out.println("La partie va démarrer !");
        grille.afficherGrille();

        // Tour par tour
        while (!endPartie()) {
            for (Personnage personnage : personnages) {
                if (personnage.getEnergie() <= 0) continue;

                for (int action = 0; action < 2; action++) {
                    System.out.println("\nTour de : " + personnage.getNom());
                    System.out.println("1- Déposer Bombe");
                    System.out.println("2- Déposer Mine");
                    System.out.println("3- Se déplacer");
                    System.out.println("4- Utiliser arme");

                    int choixAction = scanner.nextInt();
                    scanner.nextLine();

                    switch (choixAction) {
                        case 1 -> {
                            System.out.print("Entrez la position pour la bombe (x, y) : ");
                            int x = scanner.nextInt();
                            int y = scanner.nextInt();
                            scanner.nextLine();
                            Position posBombe = new Position(x, y);
                            actionDeposerBombe(personnage, posBombe);
                        }
                        case 2 -> {
                            System.out.print("Entrez la position pour la mine (x, y) : ");
                            int x = scanner.nextInt();
                            int y = scanner.nextInt();
                            scanner.nextLine();
                            Position posMine = new Position(x, y);
                            actionDeposerMine(personnage, posMine);
                        }
                        case 3 -> {
                            System.out.println("Dans quelle direction souhaitez-vous vous déplacer ?");
                            System.out.println("1- DROITE");
                            System.out.println("2- GAUCHE");
                            System.out.println("3- HAUT");
                            System.out.println("4- BAS");
                            int choixDirection = scanner.nextInt();
                            scanner.nextLine();

                            Direction direction = switch (choixDirection) {
                                case 1 -> Direction.DROITE;
                                case 2 -> Direction.GAUCHE;
                                case 3 -> Direction.HAUT;
                                case 4 -> Direction.BAS;
                                default -> {
                                    System.out.println("Choix invalide, déplacement par défaut : DROITE.");
                                    yield Direction.DROITE;
                                }
                            };

                            deplacement(personnage, direction);
                        }
                        case 4 -> {
                            if (personnage.getArmes().isEmpty()) {
                                System.out.println(personnage.getNom() + " n'a pas d'arme disponible pour tirer.");
                                break;
                            }

                            //demander la direction de tir
                            System.out.println("Dans quelle direction souhaitez-vous tirer ?");
                            System.out.println("1- DROITE");
                            System.out.println("2- GAUCHE");
                            System.out.println("3- HAUT");
                            System.out.println("4- BAS");
                            int choixDirection = scanner.nextInt();
                            scanner.nextLine();

                            Direction direction = switch (choixDirection) {
                                case 1 -> Direction.DROITE;
                                case 2 -> Direction.GAUCHE;
                                case 3 -> Direction.HAUT;
                                case 4 -> Direction.BAS;
                                default -> {
                                    System.out.println("Choix invalide, direction par défaut : DROITE.");
                                    yield Direction.DROITE;
                                }
                            };
                            actionTirer(personnage, direction);
                        }
                    }
                }
            }
            System.out.println("\nÉnergie restante des joueurs :");
            for (Personnage joueur : personnages) {
                System.out.println(joueur.getNom() + " : " + joueur.getEnergie() + " points d'énergie.");
            }

            grille.afficherGrille();

            Personnage gagnant = verifierGagnant(personnages);
            if (gagnant != null) {
                System.out.println("\nLe gagnant est : " + gagnant.getNom() + " !");
                return;
            }
        }
    }

    private boolean endPartie() {
        System.out.println("Vérification de la fin de partie...");
        long joueursAvecEnergie = personnages.stream().filter(joueur -> joueur.getEnergie() > 0).count();
        System.out.println(" joueurs : " + personnages);
        System.out.println("Nombre de joueurs avec énergie : " + joueursAvecEnergie);

        if (joueursAvecEnergie <= 1) {
            personnages.stream()
                    .filter(joueur -> joueur.getEnergie() > 0)
                    .findFirst()
                    .ifPresentOrElse(
                            gagnant -> System.out.println("Le gagnant est : " + gagnant.getNom()),
                            () -> System.out.println("Tous les joueurs sont éliminés. Match nul !")
                    );
            return true;
        }
        return false;
    }

}
