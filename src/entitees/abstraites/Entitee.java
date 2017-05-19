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

/**
 * Classe repr�sentant les objets d'une partie.
 * 
 * A chaque case d'un niveau correspond une entit�e.
 * 
 * @author Murloc
 *
 */
public abstract class Entitee implements Cloneable {

	/**
	 * Des �num�rations repr�sentant les entit�es.
	 * 
	 * Utiles pour pouvoir changer les comportements des entit�es.
	 * 
	 * @author Murloc
	 *
	 */
	public enum Entitees {
		Vide, Amibe, Mur, MurEnTitane, MurMagique, Poussiere, Sortie, Diamant, Explosion, Pierre, Rockford, Libellule, Luciole, Bombe;
	}

	/**
	 * Outil permettant de jouer des sons.
	 */
	protected SonToolKit sons = new SonToolKit();

	/**
	 * Enumeration propre � l'entit�e.
	 */
	protected Entitees enumeration;

	/**
	 * Coordonn�e de l'entit�e.
	 */
	private int x, y;

	/**
	 * Id de l'entit�e.
	 */
	private long id;

	/**
	 * Boolean permettant de savoir si l'entit�e est destructible.
	 */
	private boolean destructible;

	/**
	 * Boolean permettant de savoir si l'entit�e est morte.
	 */
	private boolean mort;

	/**
	 * Id static permettant de mettre un identifiant diff�rent � chaque entit�e.
	 */
	public static long idTotal = 0;

	/**
	 * Constructeur par d�faut des entit�es.
	 * 
	 * Cr�e une entit�e vide.
	 * 
	 * @param x
	 *            La coordonn�e x de l'entit�e.
	 * @param y
	 *            La coordonn�e y de l'entit�e.
	 */
	protected Entitee(int x, int y) {
		this.x = x;
		this.y = y;
		enumeration = Vide;
		id = idTotal;
		idTotal++;

	}

	/**
	 * Fais mourir l'entit�e.
	 * 
	 * Si elle est destructible cr�e une entit�e vide � la place.
	 * 
	 * @return L'�tat de vie de l'entit�e.
	 */
	public boolean mourir() {
		if (destructible) {
			Partie.gererNiveau.getNiveau().getMap()[x][y] = new Vide(x, y);
			mort = true;
		}
		return mort;
	}

	/**
	 * Compare si l'�num�ration pass�e en param�tre est la m�me que celle de
	 * l'entit�e.
	 * 
	 * @param e
	 *            Entit�e � comparer.
	 * @return Le r�sultat de la comparaison.
	 */
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

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getY() {
		return y;
	}

	protected void setY(int i) {
		y = i;
	}

	/**
	 * Un setter.
	 * 
	 * @param i
	 *            L'objet en question.
	 */
	protected void setX(int i) {
		x = i;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public boolean isDestructible() {
		return destructible;
	}

	/**
	 * Un setter.
	 * 
	 * @param destructible
	 *            L'objet en question.
	 */
	public void setDestructible(boolean destructible) {
		this.destructible = destructible;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public boolean isMort() {
		return mort;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public Entitees getEnumeration() {
		return enumeration;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Un setter.
	 * 
	 * @param id
	 *            L'objet en question.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public SonToolKit getSons() {
		return sons;
	}

}
