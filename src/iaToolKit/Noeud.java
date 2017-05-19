package iaToolKit;

import entitees.abstraites.Entitee;
import entitees.fixes.Poussiere;
import entitees.fixes.Sortie;
import entitees.fixes.Vide;
import entitees.tickables.Diamant;

/**
 * Classe représentant un noeud d'un graphe.
 * 
 * Le contenu d'un noeud est une entitée.
 * 
 * Elle possède les atributs utiles pour effectuer des algorithmes dans les
 * graphes.
 * 
 * @see Entitee
 * @author Murloc
 *
 */
public class Noeud implements Comparable<Noeud> {
	/**
	 * Le contenu du noeud.
	 */
	private Entitee entite;

	/**
	 * Les coordonées du Noeud.
	 */
	private int x, y;

	/**
	 * Le cout du noeud (utilisé en algorithmique de graphes).
	 */
	private int cout;

	/**
	 * L'heuristique du noeud (utilisé en algorithmique de graphes).
	 */
	private int heuristique;

	/**
	 * La traversabilité du noeud (utilisé en algorithmique de graphes).
	 */
	private boolean traversable;

	/**
	 * Le noeud père (utilisé en algorithmique de graphes).
	 */
	private Noeud pere;

	/**
	 * L'état du noeud (utilisé en algorithmique de graphes).
	 */
	private char etat;

	/**
	 * Constructeur Noeud.
	 * 
	 * Prend en paramètre une entitée et crée un noeud.
	 * 
	 * @param entite
	 *            Le contenu du noeud.
	 */
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

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public Entitee getEntite() {
		return entite;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getCout() {
		return cout;
	}

	/**
	 * Un setter.
	 * 
	 * @param cout
	 *            L'objet en question.
	 */
	public void setCout(int cout) {
		this.cout = cout;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getHeuristique() {
		return heuristique;
	}

	/**
	 * Un setter.
	 * 
	 * @param heuristique
	 *            L'objet en question.
	 */
	public void setHeuristique(int heuristique) {
		this.heuristique = heuristique;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Un setter.
	 * 
	 * @param x
	 *            L'objet en question.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Un setter.
	 * 
	 * @param y
	 *            L'objet en question.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public boolean isTraversable() {
		return traversable;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public Noeud getPere() {
		return pere;
	}

	/**
	 * Un setter.
	 * 
	 * @param pere
	 *            L'objet en question.
	 */
	public void setPere(Noeud pere) {
		this.pere = pere;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public char getEtat() {
		return etat;
	}

	/**
	 * Un setter.
	 * 
	 * @param etat
	 *            L'objet en question.
	 */
	public void setEtat(char etat) {
		this.etat = etat;
	}

}
