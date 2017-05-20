package entitees.tickables;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;

import static entitees.abstraites.Entitee.Type.Bombe;
import static main.Constantes.BOOM;
import static main.Partie.gererNiveau;

public final class Bombe extends Tickable {

    private int tempsRestantAvantExplosion = BOOM;

    public Bombe(final int positionX, final int positionY) {
        super(positionX, positionY, Bombe, false);
        gererNiveau.ajouterTickable(this);
    }

    @Override
    public Entitee nouvelle() {
        return new Bombe(getPositionX(), getPositionY());
    }

    @Override
    public int contactAutreEntitee(final Entitee entitee) {
        return 0;
    }

    @Override
    public void tick() {
        if (tempsRestantAvantExplosion == 0 && !isMort()) {
            exploser(false);
        }
        tempsRestantAvantExplosion--;
    }

    @Override
    public int getNumeroPriorite() {
        return 1;
    }

    @Override
    public boolean mourir() {
        if (tempsRestantAvantExplosion > 0) {
            tempsRestantAvantExplosion = -1;
            exploser(false);
        }
        return true;
    }

    public int getTempsRestantAvantExplosion() {
        return tempsRestantAvantExplosion;
    }
}
