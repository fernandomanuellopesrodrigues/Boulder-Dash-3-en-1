package entitees.tickables;

import static entitees.abstraites.Entitee.Entitees.Libellule;

import entitees.abstraites.Ennemi;

public class Libellule extends Ennemi {

	public Libellule(int x, int y) {
		super(x, y);
		setDestructible(true);
		setDirection('d');
		enumeration = Libellule;
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
			setDirection('d');
		} else if (isDroiteLibre()) {
			tournerADroite();
		} else if (isToutDroitLibre()) {

		} else if (isGaucheLibre()) {
			tournerAGauche();
		} else if (isDerriereLibre()) {
			tournerAGauche();
		}
	}

}
