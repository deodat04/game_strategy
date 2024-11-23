package personnagesJeu;

public class Mitrailleuse extends Armes{
    public Mitrailleuse(String type, Position position) {
        super(
                Constantes.DURABILITE_ARMES.get("mitrailleuse"),
                type,
                Constantes.MUNITIONS_ARMES.get("mitrailleuse"),
                position
        );
    }
}
