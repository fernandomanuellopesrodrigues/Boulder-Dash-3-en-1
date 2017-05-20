package entitees.fixes;

import entitees.abstraites.Entitee;

import static entitees.abstraites.Entitee.Type.Poussiere;

public final class Poussiere extends Entitee {

    public Poussiere(final int positionX, final int positionY) {
        super(positionX, positionY, Poussiere, true);
    }

    @Override
    public Entitee nouvelle() {
        return new Poussiere(getPositionX(), getPositionY());
    }
}
