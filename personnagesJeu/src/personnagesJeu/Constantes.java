package personnagesJeu;

import java.util.Map;

public class Constantes {

    public static final int MAX_MINE = 3;
    public static final int MAX_BOMBE = 3;

    public static final Map<String, Integer> RAYONS_EXPLOSION = Map.of(
            "petit", 2,
            "moyen", 4,
            "grand", 6
    );

    public static final Map<String, Integer> DURABILITE_ARMES = Map.of(
            "mine", 3,
            "bombe", 5,
            "fusil_assaut", 8,
            "fusil_precision", 10,
            "mitrailleuse", 7
    );

    public static final Map<String, Integer> MUNITIONS_ARMES = Map.of(
            "mine", 5,
            "bombe", 4,
            "fusil_assaut", 30,
            "fusil_precision", 15,
            "mitrailleuse", 50
    );
}

