package personnagesJeu;

public class FusilAssaut extends Armes {
    public FusilAssaut(String type, Position position, boolean autorise) {
        super(
                Constantes.DURABILITE_ARMES.get("fusil_assaut"),
                type,
                Constantes.MUNITIONS_ARMES.get("fusil_assaut"),
                position,
                autorise
        );
    }
}
