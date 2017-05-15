package iaToolKit;

import entitees.abstraites.Entitee;
import entitees.fixes.Poussiere;
import entitees.fixes.Sortie;
import entitees.fixes.Vide;
import entitees.tickables.Diamant;

public class Noeud implements Comparable<Noeud> {
	private Entitee entite;
	private int x, y;
	private int cout;
	private int heuristique;
	private boolean traversable;
	private Noeud pere;
	private char etat;

	public Noeud(Entitee entite) {
		this.entite = entite;
		this.x = entite.getX();
		this.y = entite.getY();
		this.cout = 0;
		this.heuristique = 0;
		if (entite.getClass().equals(Diamant.class) || entite.getClass().equals(Vide.class)
				|| entite.getClass().equals(Poussiere.class)
				|| (entite.getClass().equals(Sortie.class) && ((Sortie) entite).isOuvert())) {
			this.traversable = true;
		} else {
			this.traversable = false;
		}
	}

	public int compareTo(Noeud n) {
		if (heuristique > n.heuristique) {
			return 1;
		} else if (heuristique == n.heuristique) {
			return 0;
		} else {
			return -1;
		}
	}

	public Entitee getEntite() {
		return entite;
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	public int getHeuristique() {
		return heuristique;
	}

	public void setHeuristique(int heuristique) {
		this.heuristique = heuristique;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isTraversable() {
		return traversable;
	}

	public Noeud getPere() {
		return pere;
	}

	public void setPere(Noeud pere) {
		this.pere = pere;
	}

	public char getEtat() {
		return etat;
	}

	public void setEtat(char etat) {
		this.etat = etat;
	}

}
