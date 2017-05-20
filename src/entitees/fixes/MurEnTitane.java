package entitees.fixes;

import entitees.abstraites.Entitee;

import static entitees.abstraites.Entitee.Type.MurEnTitane;

public final class MurEnTitane extends Entitee {

    public MurEnTitane(final int positionX, final int positionY) {
        super(positionX, positionY, MurEnTitane, false);
    }

    @Override
    public Entitee nouvelle() {
        return new MurEnTitane(getPositionX(), getPositionY());
    }
}
