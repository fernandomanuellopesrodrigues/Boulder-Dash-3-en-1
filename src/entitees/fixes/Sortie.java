package entitees.fixes;

import entitees.abstraites.Entitee;
import main.Partie;

import static entitees.abstraites.Entitee.Entitees.Sortie;

public class Sortie extends Entitee {

    public Sortie(int x, int y) {
        super(x, y);
        enumeration = Sortie;
    }

    public boolean isOuvert() {
        return Partie.gererNiveau.getNbDiamants() >= Partie.gererNiveau.getNiveau().getDiamonds_required();
    }
}
