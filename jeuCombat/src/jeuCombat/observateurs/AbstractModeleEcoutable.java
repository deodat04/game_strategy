package jeuCombat.observateurs;

import java.util.ArrayList;
import java.util.List;

public class AbstractModeleEcoutable implements ModeleEcoutable {

    private List<EcouteurModele> ecouteurs = new ArrayList<>();

    @Override
    public void ajouterEcouteur(EcouteurModele e) {
        ecouteurs.add(e);
    }

    @Override
    public void retirerEcouteur(EcouteurModele e) {
        ecouteurs.remove(e);
    }

    @Override
    public void fireChangement() {
        for (EcouteurModele e : ecouteurs) {
            e.misAJour();
        }
    }
}
