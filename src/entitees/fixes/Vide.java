package entitees.fixes;

import entitees.abstraites.Entitee;

import static entitees.abstraites.Entitee.Type.Vide;

public final class Vide extends Entitee {

    public Vide(final int positionX, final int positionY) {
        super(positionX, positionY, Vide, true);
    }

    @Override
    public Entitee nouvelle() {
        return new Vide(getPositionX(), getPositionY());
    }
}
