package entitees.tickables;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;

import static entitees.abstraites.Entitee.Type.Explosion;

public final class Explosion extends Tickable {

    private int ticksAvantFinExplosion = 3;

    public Explosion(final int positionX, final int positionY) {
        super(positionX, positionY, Explosion, true);
    }

    @Override
    public Entitee nouvelle() {
        return new Explosion(getPositionX(), getPositionY());
    }

    @Override
    public int contactAutreEntitee(final Entitee entitee) {
        return 0;
    }

    @Override
    public void tick() {
        if (ticksAvantFinExplosion > 0) {
            ticksAvantFinExplosion--;
        } else {
            mourir();
        }
    }

    @Override
    public int getNumeroPriorite() {
        return 1;
    }
}
