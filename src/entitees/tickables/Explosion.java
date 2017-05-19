package entitees.tickables;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;

import static entitees.abstraites.Entitee.Entitees.Explosion;

public class Explosion extends Tickable {

    private int ticksAvantFinExplosion = 3;

    public Explosion(int x, int y) {
        super(x, y);
        setDestructible(true);
        enumeration = Explosion;
    }

    @Override
    protected int contactAutreEntitee(Entitee entitee) {
        return 0;
    }

    @Override
    public void tick() {
        if (ticksAvantFinExplosion > 0) { ticksAvantFinExplosion--; } else {
            mourir();
        }
    }
}
