package entitees.tickables;

import entitees.abstraites.Ennemi;

import static entitees.abstraites.Entitee.Entitees.Luciole;

public class Luciole extends Ennemi {

    public Luciole(int x, int y) {
        super(x, y);
        setDestructible(true);
        setDirection('g');
        enumeration = Luciole;
    }

    @Override
    public void tick() {
        if (!bloque) {
            iASetDirection();
            seDeplacer();
        }
        bloquer();
    }

    protected void iASetDirection() {
        if (isFullLibre()) {
            setDirection('g');
        } else if (isGaucheLibre()) {
            tournerAGauche();
        } else if (isToutDroitLibre()) {

        } else if (isDroiteLibre()) {
            tournerADroite();
        } else if (isDerriereLibre()) {
            tournerADroite();
        }
    }
}
