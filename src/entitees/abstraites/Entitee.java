package entitees.abstraites;

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
import outils.SonToolKit;

public abstract class Entitee implements Cloneable {
	public enum Entitees {
		Vide, Amibe, Mur, MurEnTitane, MurMagique, Poussiere, Sortie, Diamant, Explosion, Pierre, Rockford, Libellule, Luciole, Bombe;
	}

	protected SonToolKit sons = new SonToolKit();
	protected Entitees enumeration;
	private int x, y;
	private long id;
	private boolean destructible, mort;
	public static long idTotal = 0;

	protected Entitee(int x, int y) {
		this.x = x;
		this.y = y;
		enumeration = Vide;
		id = idTotal;
		idTotal++;

	}

	public boolean mourir() {
		if (destructible) {
			Partie.gererNiveau.getNiveau().getMap()[x][y] = new Vide(x, y);
			mort = true;
		}
		return mort;
	}

	public boolean is(Entitees e) {
		return enumeration == e;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
