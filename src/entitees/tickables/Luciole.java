package entitees.tickables;

import static entitees.abstraites.Entitee.Entitees.Luciole;

import entitees.abstraites.Ennemi;

public class Luciole extends Ennemi {

	public Luciole(int x, int y) {
		super(x, y);
		setDestructible(true);
		setDirection('g');
		enumeration = Luciole;
	}

	@Override
	public void tick() {
		iASetDirection();
		seDeplacer();
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
