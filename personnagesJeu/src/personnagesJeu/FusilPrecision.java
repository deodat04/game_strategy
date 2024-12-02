package personnagesJeu;

public class FusilPrecision extends Armes{
    public FusilPrecision(String type, Position position, boolean autorise) {
        super(
                Constantes.DURABILITE_ARMES.get("fusil_precision"),
                type,
                Constantes.MUNITIONS_ARMES.get("fusil_precision"),
                position,
                autorise
        );
    }
}
