package entitees.abstraites;

import static entitees.abstraites.Entitee.Entitees.Amibe;
import static entitees.abstraites.Entitee.Entitees.Rockford;

public abstract class Ennemi extends Tickable {

	protected Ennemi(int x, int y) {
		super(x, y);
		getDeplacementsPossibles().add(Rockford);
		getDeplacementsPossibles().add(Amibe);
	}

	@Override
	protected int contactAutreEntitee(Entitee entitee) {
		if (entitee.is(Rockford)) {
			entitee.mourir();
		} else if (entitee.is(Amibe)) {
			exploser(true);
			return 0;
		}
		return 1;
	}

	

	protected boolean isToutDroitLibre() {
		switch (getDirection()) {
		case 'd':
			return placeLibre(getX() + 1, getY());
		case 'b':
			return placeLibre(getX(), getY() + 1);
		case 'h':
			return placeLibre(getX(), getY() - 1);
		case 'g':
			return placeLibre(getX() - 1, getY());
		default:
			return false;
		}
	}

	protected boolean isDerriereLibre() {
		switch (getDirection()) {
		case 'd':
			return placeLibre(getX() - 1, getY());
		case 'b':
			return placeLibre(getX(), getY() - 1);
		case 'h':
			return placeLibre(getX(), getY() + 1);
		case 'g':
			return placeLibre(getX() + 1, getY());
		default:
			return false;
		}
	}

	protected boolean isGaucheLibre() {
		switch (getDirection()) {
		case 'd':
			return placeLibre(getX(), getY() - 1);
		case 'b':
			return placeLibre(getX() + 1, getY());
		case 'h':
			return placeLibre(getX() - 1, getY());
		case 'g':
			return placeLibre(getX(), getY() + 1);
		default:
			return false;
		}
	}

	protected boolean isDroiteLibre() {
		switch (getDirection()) {
		case 'd':
			return placeLibre(getX(), getY() + 1);
		case 'b':
			return placeLibre(getX() - 1, getY());
		case 'h':
			return placeLibre(getX() + 1, getY());
		case 'g':
			return placeLibre(getX(), getY() - 1);
		default:
			return false;
		}
	}

	protected boolean isFullLibre() {
		if (!(isDroiteLibre() && isDerriereLibre() & isGaucheLibre() && isToutDroitLibre())) {
			return false;
		}
		return (placeLibre(getX() + 1, getY() + 1)
				&& placeLibre(getX() - 1, getY() + 1) & placeLibre(getX() + 1, getY() - 1)
				&& placeLibre(getX() - 1, getY() - 1));
	}

	protected void tournerADroite() {
		switch (getDirection()) {
		case 'd':
			setDirection('b');
			break;
		case 'b':
			setDirection('g');
			break;
		case 'h':
			setDirection('d');
			break;
		case 'g':
			setDirection('h');
			break;
		default:
			break;
		}
	}

	protected void tournerAGauche() {
		switch (getDirection()) {
		case 'd':
			setDirection('h');
			break;
		case 'b':
			setDirection('d');
			break;
		case 'h':
			setDirection('g');
			break;
		case 'g':
			setDirection('b');
			break;
		default:
			break;
		}
	}

}
