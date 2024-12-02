package personnagesJeu;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Constantes {

    public static final int MAX_MINE = 3;
    public static final int MAX_BOMBE = 3;
    public static int CASE_SIZE = 150;
    public static final int INITIAL_WIDTH = 800;
    public static final int INITIAL_HEIGHT = 600;


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

    public static final List<int[]> POSITION_MURS = new ArrayList<>();

    static {
        POSITION_MURS.add(new int[]{2, 3});
        POSITION_MURS.add(new int[]{5, 6});
        POSITION_MURS.add(new int[]{8, 9});
    }

    public static List<Mur> initialiserMurs() {
        List<Mur> murs = new ArrayList<>();
        for (int[] pos : POSITION_MURS) {
            murs.add(new Mur(pos[0] * CASE_SIZE, pos[1] * CASE_SIZE));
        }
        return murs;
    }


}

