package entitees.fixes;

import entitees.abstraites.Entitee;
import entitees.abstraites.Tickable;
import entitees.tickables.Diamant;
import entitees.tickables.Pierre;

import static entitees.abstraites.Entitee.Type.Diamant;
import static entitees.abstraites.Entitee.Type.MurMagique;
import static entitees.abstraites.Entitee.Type.Pierre;
import static entitees.abstraites.Entitee.Type.Vide;
import static main.Partie.checkEntite;
import static main.Partie.gererNiveau;
import static main.Partie.getEntiteParPosition;
import static main.Partie.setEntiteParPosition;

public final class MurMagique extends Tickable {

    private int magicWallTime;

    public MurMagique(final int positionX, final int positionY, final int magicWallTime) {
        super(positionX, positionY, MurMagique, true);
        this.magicWallTime = magicWallTime;
    }

    @Override
    public Entitee nouvelle() {
        return new MurMagique(getPositionX(), getPositionY(), magicWallTime);
    }

    private void decrementerMagicWallTime() {
        magicWallTime--;
        if (magicWallTime <= 0) {
            mourir();
            setEntiteParPosition(getPositionX(), getPositionY(), new Mur(getPositionX(), getPositionY()));
        }
    }

    private boolean traverser() {
        final int px = getPositionX();
        final int py = getPositionY();
        if (!checkEntite(px, py + 1, Vide)) {
            return false;
        }
        if (checkEntite(px, py - 1, Diamant)) {
            getEntiteParPosition(px, py - 1).mourir();
            final Pierre pierre = new Pierre(px, py + 1);
            setEntiteParPosition(px, py + 1, pierre);
            gererNiveau.ajouterTickable(pierre);
            decrementerMagicWallTime();
        } else if (checkEntite(px, py - 1, Pierre)) {
            getEntiteParPosition(px, py - 1).mourir();
            final Diamant diamant = new Diamant(px, py + 1);
            setEntiteParPosition(px, py + 1, diamant);
            gererNiveau.ajouterTickable(diamant);
            diamant.setChute(true);
            decrementerMagicWallTime();
        }
        return true;

    }

    @Override
    public int contactAutreEntitee(Entitee entitee) {
        return 0;
    }

    @Override
    public void tick() {
        traverser();
    }

    @Override
    public int getNumeroPriorite() {
        return 1;
    }

}
