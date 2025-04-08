# Jeu de Combat au Tour par Tour
 Il s'agit d'un jeu de stratégie basé sur des déplacements et des actions réalisées par des personnages sur une grille. Chaque joueur contrôle un personnage avec des actions limitées, telles que se déplacer, tirer, poser des mines ou des bombes, et interagir avec des objets d'énergie sur la carte.
 
## Programme en Console

- **Chemin vers le main** : `jeuCombat.JeuCombat.java`
- **À l'exécution du programme** :
  - Il faut entrer les configurations initiales.
  - Le vainqueur est le dernier joueur encore en vie (avec des points de vie restants).
  - Les actions consomment de l'énergie des joueurs mais n'affectent pas directement leurs points de vie.

### 🛠️ Informations sur les Configurations Initiales

- Pour entrer la position d'un joueur : utilisez le format `x y` (par exemple, `3 5` pour x = 3 et y = 5).
- Chaque joueur a droit à **3 bombes** et **3 mines** au maximum.
- Pour choisir une arme, entrez le numéro correspondant, par exemple :
  - Tapez `1` pour sélectionner **1 - Fusil d'assaut**.
- Le même processus s'applique pour les directions et les actions.
- Pour exécuter le fichier `.jar` du projet en console naviguer vers le répertoire : 
    `/out/artifacts/jeuCombat_jar2/` et exécuter la commande  `java -jar jeuCombat.jar`
  
  
## 🚀 Programme avec Interface Graphique

- **Version de Java** : 21
- **Chemin vers le main** : `jeuCombat.interfaceJeu.GUI.java`
- **À l'exécution du programme** :
  - Entrez les configurations initiales avant d'accéder à la grille.
  - Les actions disponibles à gauche de même que les logs d'événements
  - Pour ajouter une bombe, entrez la position sous la forme x,y.
  - Les éléments graphiques sont représentés comme suit :
  	- Le cercle rouge représente le joueur 1.
  	- Le cercle bleu représente le joueur 2.
  	- Le petit cercle vert représente les objets d'énergie.
  	- Les carrés représentent les mines.
  	- Le cercle orange représente les bombes.
  	- Les cases noires représentent les murs.
  	- Pour exécuter le fichier `.jar` du projet en interface naviguer vers le répertoire : 
    `/out/artifacts/jeuCombat_jar/` et exécuter la commande  `java -jar jeuCombat.jar`
    - les fichiers jar des deux applications sont disponibles également dans `/jeuCombat/dist`
   
### Copyright
- [Déodat ADANDEDJAN](https://github.com/deodat04) & Quentin Levallois
