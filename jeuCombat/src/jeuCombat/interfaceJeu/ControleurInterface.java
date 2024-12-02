package jeuCombat.interfaceJeu;

import jeuCombat.Grille;
import jeuCombat.interfaceJeu.view.ActionPanel;
import personnagesJeu.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ControleurInterface {

    private JPanel jeuPanel;
    private JTextArea logArea;
    private List<Mur> murs;
    private List<Personnage> players;
    private List<ObjetEnergie> energyObjects;
    private Grille grille;
    public int joueurActuel;
    private int actionsRestantes;
    private ActionPanel actionPanel;


    public ControleurInterface(List<Personnage> players, JPanel jeuPanel, JTextArea logArea, Grille grille) {
        this.players = players;
        this.jeuPanel = jeuPanel;
        this.logArea = logArea;
        this.grille = grille;
        this.murs = new ArrayList<>();
        this.energyObjects = new ArrayList<>();
        this.joueurActuel = 0;
        this.actionsRestantes = 1;
        this.actionPanel = new ActionPanel(this, logArea);

    }


    public void effectuerAction(Personnage personnage, String action) {
        if (actionsRestantes > 0) {
            //effectuer l'action en fonction de la commande
            switch (action) {
                case "deplacerHaut":
                    deplacerHaut(personnage);
                    ajouterLog(personnage.getNom() + " se deplace en haut.");
                    break;
                case "deplacerBas":
                    deplacerBas(personnage);
                    ajouterLog(personnage.getNom() + " se deplace en bas.");
                    break;
                case "deplacerGauche":
                    deplacerGauche(personnage);
                    ajouterLog(personnage.getNom() + " se deplace a gauche.");
                    break;
                case "deplacerDroite":
                    deplacerDroite(personnage);
                    ajouterLog(personnage.getNom() + " se deplace a droite.");
                    break;
                case "utiliserBouclier":
                    utiliserBouclier();
                    break;
                case "deposerBombe":
                    Position positionBombe = actionPanel.demanderPosition();
                    if (positionBombe != null) {
                        deposerBombe(positionBombe);
                    }
                    break;
                case "deposerMine":
                    Position positionMine = actionPanel.demanderPosition();
                    if (positionMine != null) {
                        deposerMine(positionMine);
                    }
                    break;
                case "tirer":
                    Direction direction = actionPanel.demanderDirection();
                    if (direction != null) {
                        tirer(direction);
                    }
                    break;
                default:
                    System.out.println("Action non reconnue");
            }
            mettreAJourVue();

            actionsRestantes--;

            if (actionsRestantes == 0) {
                joueurActuel = (joueurActuel + 1) % players.size();
                actionsRestantes = 1;
                ajouterLog("C'est au tour du joueur " + (joueurActuel + 1));
                mettreAJourVue();
            }
        } else {
            ajouterLog("Le joueur " + (joueurActuel + 1) + " a épuisé ses actions pour ce tour.");
        }
    }

    public void ajouterMur(int x, int y) {
        if (grille.estPositionValide(x, y)) {
            Mur mur = new Mur(x, y);
            murs.add(mur);
            grille.occuperCase(x, y);
            mettreAJourVue();
        }
    }

    //ajouter un objet d'énergie à une position donnée
    public void ajouterObjetEnergie(int x, int y) {
        if (grille.estPositionValide(x, y)) {
            ObjetEnergie objetEnergie = new ObjetEnergie(x, y);
            energyObjects.add(objetEnergie);
            grille.occuperCase(x, y);
            mettreAJourVue();        }
    }

    public void ajouterJoueur(String nom, int x, int y, List<Armes> armes) {
        if (grille.estPositionValide(x, y)) {
            JoueurHumain joueur = new JoueurHumain(nom, 100, new Position(x, y), armes, grille,100);
            players.add(joueur);
            grille.ajouterJoueur(joueur);
        } else {
            System.out.println("La position est invalide");
        }
    }

    public List<Mur> getMurs() {
        return murs;
    }

    public List<Personnage> getPlayers() {
        return players;
    }

    public List<ObjetEnergie> getEnergyObjects() {
        return energyObjects;
    }

    public void deplacerHaut(Personnage personnage) {
        if (personnage != null && personnage.getEnergie() > 0) {
            Position anciennePosition = personnage.getPosition();
            Position nouvellePosition = new Position(anciennePosition.getX(), anciennePosition.getY() - 1);
            grille.mettreAJourPosition(anciennePosition, nouvellePosition, personnage);
            personnage.setPosition(nouvellePosition);
            System.out.println("Déplacement de " + personnage.getNom() + " de " + anciennePosition + " à " + nouvellePosition);

            List<Object> objetsDansLaCase = grille.getObjets(nouvellePosition);
            for (Object objet : objetsDansLaCase) {
                if (objet instanceof Bombe) {
                    System.out.println("Une bombe a été détectée à la position " + nouvellePosition);
                    Bombe bombe = (Bombe) objet;
                    bombe.detonnerSiNecessaire(personnage, grille);
                    grille.retirerObjet(nouvellePosition, bombe);
                } else if (objet instanceof Mine) {
                    System.out.println("Une mine a été détectée à la position " + nouvellePosition);
                    Mine mine = (Mine) objet;
                    mine.detonnerSiNecessaire(personnage, grille);
                    grille.retirerObjet(nouvellePosition, mine);
                }
            }

            //mettree à jour de l'affichage
            mettreAJourVue();
        } else {
            System.out.println("Personnage n'a pas assez d'énergie pour se déplacer ou est null");
        }
    }


    public void deplacerBas(Personnage personnage) {
        if (personnage != null && personnage.getEnergie() > 0) {
            Position anciennePosition = personnage.getPosition();
            Position nouvellePosition = new Position(anciennePosition.getX(), anciennePosition.getY() + 1);
            grille.mettreAJourPosition(anciennePosition, nouvellePosition, personnage);
            personnage.setPosition(nouvellePosition);
            System.out.println("Déplacement de " + personnage.getNom() + " de " + anciennePosition + " à " + nouvellePosition);

            List<Object> objetsDansLaCase = grille.getObjets(nouvellePosition);
            for (Object objet : objetsDansLaCase) {
                if (objet instanceof Bombe) {
                    System.out.println("Une bombe a été détectée à la position " + nouvellePosition);
                    Bombe bombe = (Bombe) objet;
                    bombe.detonnerSiNecessaire(personnage, grille);
                    grille.retirerObjet(nouvellePosition, bombe);
                } else if (objet instanceof Mine) {
                    System.out.println("Une mine a été détectée à la position " + nouvellePosition);
                    Mine mine = (Mine) objet;
                    mine.detonnerSiNecessaire(personnage, grille);
                    grille.retirerObjet(nouvellePosition, mine);
                }
            }

            mettreAJourVue();
        } else {
            System.out.println("Personnage n'a pas assez d'énergie pour se déplacer ou est null");
        }
    }


    public void deplacerGauche(Personnage personnage) {
        if (personnage != null && personnage.getEnergie() > 0) {
            Position anciennePosition = personnage.getPosition();
            Position nouvellePosition = new Position(anciennePosition.getX() - 1, anciennePosition.getY());
            grille.mettreAJourPosition(anciennePosition, nouvellePosition, personnage);
            personnage.setPosition(nouvellePosition);
            System.out.println("Déplacement de " + personnage.getNom() + " de " + anciennePosition + " à " + nouvellePosition);

            //Vérification des objets bombes et mines dans nouvelle position
            List<Object> objetsDansLaCase = grille.getObjets(nouvellePosition);
            for (Object objet : objetsDansLaCase) {
                if (objet instanceof Bombe) {
                    System.out.println("Une bombe a été détectée à la position " + nouvellePosition);
                    Bombe bombe = (Bombe) objet;
                    bombe.detonnerSiNecessaire(personnage, grille);
                    grille.retirerObjet(nouvellePosition, bombe);
                } else if (objet instanceof Mine) {
                    System.out.println("Une mine a été détectée à la position " + nouvellePosition);
                    Mine mine = (Mine) objet;
                    mine.detonnerSiNecessaire(personnage, grille);
                    grille.retirerObjet(nouvellePosition, mine);
                }
            }

            mettreAJourVue();
        } else {
            System.out.println("Personnage n'a pas assez d'énergie pour se déplacer ou est null");
        }
    }


    public void deplacerDroite(Personnage personnage) {
        if (personnage != null && personnage.getEnergie() > 0) {
            Position anciennePosition = personnage.getPosition();
            Position nouvellePosition = new Position(anciennePosition.getX() + 1, anciennePosition.getY());
            grille.mettreAJourPosition(anciennePosition, nouvellePosition, personnage);
            personnage.setPosition(nouvellePosition);
            System.out.println("Déplacement de " + personnage.getNom() + " de " + anciennePosition + " à " + nouvellePosition);

            List<Object> objetsDansLaCase = grille.getObjets(nouvellePosition);
            for (Object objet : objetsDansLaCase) {
                if (objet instanceof Bombe) {
                    System.out.println("Une bombe a été détectée à la position " + nouvellePosition);
                    Bombe bombe = (Bombe) objet;
                    bombe.detonnerSiNecessaire(personnage, grille);
                    grille.retirerObjet(nouvellePosition, bombe);
                } else if (objet instanceof Mine) {
                    System.out.println("Une mine a été détectée à la position " + nouvellePosition);
                    Mine mine = (Mine) objet;
                    mine.detonnerSiNecessaire(personnage, grille);
                    grille.retirerObjet(nouvellePosition, mine);
                }
            }

            mettreAJourVue();
        } else {
            System.out.println("Personnage n'a pas assez d'énergie pour se déplacer ou est null");
        }
    }


    void mettreAJourVue() {
        jeuPanel.revalidate();
        jeuPanel.repaint();
    }

    public void utiliserBouclier() {
        Personnage personnage = players.get(joueurActuel);
        if (personnage == null) {
            ajouterLog("Erreur : Aucun personnage pour ce tour.");
            return;
        }
        if (personnage.getEnergie() > 0) {
            personnage.activerBouclier();
            ajouterLog(personnage.getNom() + " a activé son bouclier.");
        } else {
            ajouterLog(personnage.getNom() + " n'a pas assez d'énergie pour activer le bouclier.");
        }
    }

    public void tirer(Direction direction) {
        Personnage personnage = players.get(joueurActuel);
        if (personnage == null) {
            ajouterLog("Erreur : Aucun personnage pour ce tour.");
            return;
        }
        if (personnage.getEnergie() <= 0) {
            ajouterLog(personnage.getNom() + " n'a pas assez d'énergie pour tirer.");
            return;
        }

        if (personnage.getArmes().isEmpty()) {
            ajouterLog(personnage.getNom() + " n'a pas d'arme pour tirer.");
            return;
        }

        Armes armeChoisie = personnage.getArmes().get(personnage.getArmes().size() - 1);
        armeChoisie.utiliser(personnage);

        personnage.setEnergie(personnage.getEnergie() - 10);
        ajouterLog(personnage.getNom() + " a tiré en direction de " + direction + " avec " + armeChoisie.getType());

        Position positionActuelle = personnage.getPosition();
        Personnage cible = personnage.getPersonnageDansDirection(positionActuelle, direction);



        Balle balle = new Balle(positionActuelle, direction, personnage,jeuPanel);

        while (true) {
            balle.deplacer();

            //vérifier si la balle touche un personnage
            Personnage personnageCible = personnage.getPersonnageDansDirection(balle.getPosition(), direction);
            if (personnageCible != null) {
                personnageCible.setVie(personnageCible.getVie() - 10);
                ajouterLog("Le tir a touché " + personnageCible.getNom() + ". Énergie restante : " + personnageCible.getEnergie());

                if (personnageCible.getEnergie() <= 0) {
                    ajouterLog(personnageCible.getNom() + " est éliminé !");
                }
                break;
            }

            if (balle.getPosition().estLimite(grille)) {
                ajouterLog("Le tir a échoué, la balle a atteint la limite.");
                break;
            }

            afficherBalle(balle);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void afficherBalle(Balle balle) {
        //Méthode pour afficher la balle dans la direction
        System.out.println("La balle est à la position " + balle.getPosition());
    }

    public void deposerMine(Position position) {
        Personnage personnage = players.get(joueurActuel);
        if (personnage == null) {
            ajouterLog("Erreur : Aucun personnage pour ce tour.");
            return;
        }
        Optional<Mine> mineDisponible = personnage.getArmes().stream()
                .filter(arme -> arme instanceof Mine && !((Mine) arme).isUtilisee())
                .map(arme -> (Mine) arme)
                .findFirst();

        if (mineDisponible.isPresent()) {
            Mine mine = mineDisponible.get();

            if (grille.estPositionLibre(position)) {
                mine.setPosition(position);
                grille.ajouterObjet(position, mine);
                mine.setUtilisee(true);
                ajouterLog(personnage.getNom() + " a déposé une mine en position " + position);
            } else {
                ajouterLog("La position " + position + " est déjà occupée.");
            }
        } else {
            ajouterLog(personnage.getNom() + " n'a plus de mines disponibles.");
        }
    }

    public void deposerBombe(Position position) {
        Personnage personnage = players.get(joueurActuel);
        if (personnage == null) {
            ajouterLog("Erreur : Aucun personnage pour ce tour.");
            return;
        }
        Optional<Bombe> bombeDisponible = personnage.getArmes().stream()
                .filter(arme -> arme instanceof Bombe && !((Bombe) arme).estUtilisee())
                .map(arme -> (Bombe) arme)
                .findFirst();

        if (bombeDisponible.isPresent()) {
            Bombe bombe = bombeDisponible.get();

            if (grille.estPositionLibre(position)) {
                bombe.setPosition(position);
                grille.ajouterObjet(position, bombe);
                bombe.setUtilisee(true);
                bombe.setPoseur(personnage);
                ajouterLog(personnage.getNom() + " a déposé une bombe en position " + position);
            } else {
                ajouterLog("La position " + position + " est déjà occupée.");
            }
        } else {
            ajouterLog(personnage.getNom() + " n'a plus de bombes disponibles.");
        }
    }

    public void ajouterObjetEnergie(ObjetEnergie objetEnergie) {
        energyObjects.add(objetEnergie);
    }

    private void ajouterLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

}
