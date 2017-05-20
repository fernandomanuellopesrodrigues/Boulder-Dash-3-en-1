package entitees.tickables;

import entitees.abstraites.Ennemi;
import entitees.abstraites.Entitee;

import static entitees.abstraites.Entitee.Type.Luciole;
import static main.Constantes.GAUCHE;

public final class Luciole extends Ennemi {

    public Luciole(final int positionX, final int positionY) {
        super(positionX, positionY, Luciole, true);
        setDirection(GAUCHE);
    }

    @Override
    public int contactAutreEntitee(final Entitee entitee) {
        return 0;
    }

    @Override
    public Entitee nouvelle() {
        return new Luciole(getPositionX(), getPositionY());
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

    private void iASetDirection() {
        if (isFullLibre()) {
            setDirection(GAUCHE);
        } else if (isGaucheLibre()) {
            tournerAGauche();
        } else if (isDroiteLibre() || isDerriereLibre()) {
            tournerADroite();
        }
    }
}
