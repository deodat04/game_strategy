package jeuCombat.observateurs;


public interface ModeleEcoutable {

    void ajouterEcouteur(EcouteurModele e);
    void retirerEcouteur(EcouteurModele e);
    void fireChangement();
}




