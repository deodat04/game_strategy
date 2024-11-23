package personnagesJeu;

public class FusilPrecision extends Armes{
    public FusilPrecision(String type, Position position) {
        super(
                Constantes.DURABILITE_ARMES.get("fusil_precision"),
                type,
                Constantes.MUNITIONS_ARMES.get("fusil_precision"),
                position
        );
    }
}
