package entitees.tickables;

import entitees.abstraites.Ennemi;
import entitees.abstraites.Entitee;

import static entitees.abstraites.Entitee.Type.Libellule;
import static main.Constantes.DROITE;

public final class Libellule extends Ennemi {

    public Libellule(final int positionX, final int positionY) {
        super(positionX, positionY, Libellule, true);
        setDirection(DROITE);
    }

    @Override
    public int contactAutreEntitee(final Entitee entitee) {
        return 0;
    }

    @Override
    public Entitee nouvelle() {
        return new Libellule(getPositionX(), getPositionY());
    }

    @Override
    public void tick() {
        if (!isBloque()) {
            iASetDirection();
            seDeplacer();
        }
        bloquer();
    }

    @Override
    public int getNumeroPriorite() {
        return 4;
    }

    public void iASetDirection() {
        if (isFullLibre()) {
            setDirection(DROITE);
        } else if (isDroiteLibre()) {
            tournerADroite();
        } else if (isGaucheLibre()) {
            tournerAGauche();
        } else if (isDerriereLibre()) {
            tournerAGauche();
        }
    }

}
