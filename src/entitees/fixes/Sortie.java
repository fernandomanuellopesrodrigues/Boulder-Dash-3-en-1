package entitees.fixes;

import entitees.abstraites.Entitee;

import static entitees.abstraites.Entitee.Type.Sortie;
import static main.Partie.gererNiveau;

public final class Sortie extends Entitee {

    public Sortie(final int positionX, final int positionY) {
        super(positionX, positionY, Sortie, false);
    }

    @Override
    public Entitee nouvelle() {
        return new Sortie(getPositionX(), getPositionY());
    }

    public static boolean isOuvert() {
        return gererNiveau.getNbDiamants() >= gererNiveau.getNiveau().getDiamondsRequired();
    }
}
