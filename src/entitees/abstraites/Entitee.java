package entitees.abstraites;

import static entitees.abstraites.Entitee.Entitees.Amibe;
import static entitees.abstraites.Entitee.Entitees.Diamant;
import static entitees.abstraites.Entitee.Entitees.Explosion;
import static entitees.abstraites.Entitee.Entitees.Libellule;
import static entitees.abstraites.Entitee.Entitees.Luciole;
import static entitees.abstraites.Entitee.Entitees.Mur;
import static entitees.abstraites.Entitee.Entitees.MurEnTitane;
import static entitees.abstraites.Entitee.Entitees.MurMagique;
import static entitees.abstraites.Entitee.Entitees.Pierre;
import static entitees.abstraites.Entitee.Entitees.Poussiere;
import static entitees.abstraites.Entitee.Entitees.Rockford;
import static entitees.abstraites.Entitee.Entitees.Sortie;
import static entitees.abstraites.Entitee.Entitees.Vide;

import entitees.fixes.Amibe;
import entitees.fixes.Mur;
import entitees.fixes.MurEnTitane;
import entitees.fixes.MurMagique;
import entitees.fixes.Poussiere;
import entitees.fixes.Sortie;
import entitees.fixes.Vide;
import entitees.tickables.Diamant;
import entitees.tickables.Explosion;
import entitees.tickables.Libellule;
import entitees.tickables.Luciole;
import entitees.tickables.Pierre;
import entitees.tickables.Rockford;
import main.Partie;

public abstract class Entitee implements Cloneable {
	public enum Entitees {
		Vide, Amibe, Mur, MurEnTitane, MurMagique, Poussiere, Sortie, Diamant, Explosion, Pierre, Rockford, Libellule, Luciole;
	}

	protected Entitees enumeration;
	private int x, y;
	private boolean destructible, mort;

	protected Entitee(int x, int y) {
		this.x = x;
		this.y = y;
		enumeration = Vide;
	}

	public boolean mourir() {
		if (destructible) {
			Partie.gererNiveau.getNiveau().placerEntitee(new Vide(x, y));
			mort = true;
		}
		return mort;
	}

	public boolean is(Entitees e) {
		if (e == Vide) {
			return getClass().equals(Vide.class);
		} else if (e == Amibe) {
			return getClass().equals(Amibe.class);
		} else if (e == Mur) {
			return getClass().equals(Mur.class);
		} else if (e == MurEnTitane) {
			return getClass().equals(MurEnTitane.class);
		} else if (e == MurMagique) {
			return getClass().equals(MurMagique.class);
		} else if (e == Poussiere) {
			return getClass().equals(Poussiere.class);
		} else if (e == Sortie) {
			return getClass().equals(Sortie.class);
		} else if (e == Diamant) {
			return getClass().equals(Diamant.class);
		} else if (e == Explosion) {
			return getClass().equals(Explosion.class);
		} else if (e == Pierre) {
			return getClass().equals(Pierre.class);
		} else if (e == Rockford) {
			return getClass().equals(Rockford.class);
		} else if (e == Libellule) {
			return getClass().equals(Libellule.class);
		} else if (e == Luciole) {
			return getClass().equals(Luciole.class);
		} else {
			return false;
		}
	}

	public Entitee clone() {
		if (getClass().equals(Mur.class)) {
			return new Mur(x, y);
		} else if (getClass().equals(Diamant.class)) {
			return new Diamant(x, y);
		} else if (getClass().equals(Amibe.class)) {
			return new Amibe(x, y);
		} else if (getClass().equals(Luciole.class)) {
			return new Luciole(x, y);
		} else if (getClass().equals(Libellule.class)) {
			return new Libellule(x, y);
		} else if (getClass().equals(MurEnTitane.class)) {
			return new MurEnTitane(x, y);
		} else if (getClass().equals(Pierre.class)) {
			return new Pierre(x, y);
		} else if (getClass().equals(Poussiere.class)) {
			return new Poussiere(x, y);
		} else if (getClass().equals(Rockford.class)) {
			return new Rockford(x, y);
		} else if (getClass().equals(Sortie.class)) {
			return new Sortie(x, y);
		} else if (getClass().equals(MurMagique.class)) {
			return new MurMagique(x, y, ((MurMagique) this).getMagicWallTime());
		} else if (getClass().equals(Explosion.class)) {
			return new Explosion(x, y);
		} else {
			return new Vide(x, y);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	protected void setY(int i) {
		y = i;
	}

	protected void setX(int i) {
		x = i;
	}

	public boolean isDestructible() {
		return destructible;
	}

	public void setDestructible(boolean destructible) {
		this.destructible = destructible;
	}

	public boolean isMort() {
		return mort;
	}

	public Entitees getEnumeration() {
		return enumeration;
	}

}
