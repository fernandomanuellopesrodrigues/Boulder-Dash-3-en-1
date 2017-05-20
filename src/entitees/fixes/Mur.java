package entitees.fixes;

import entitees.abstraites.Entitee;

import static entitees.abstraites.Entitee.Type.Mur;

public final class Mur extends Entitee {

    public Mur(final int positionX, final int positionY) {
        super(positionX, positionY, Mur, true);
    }

    @Override
    public Entitee nouvelle() {
        return new Mur(getPositionX(), getPositionY());
    }
}
