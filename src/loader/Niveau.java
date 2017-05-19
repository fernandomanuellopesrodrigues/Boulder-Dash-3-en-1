package loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.abstraites.Entitee;
import entitees.abstraites.Entitee.Entitees;
import entitees.abstraites.Tickable;
import entitees.fixes.Amibe;
import entitees.fixes.Sortie;
import entitees.tickables.Rockford;

/**
 * La classe Niveau sert à modéliser un niveau.
 * 
 * Elle dispose d'un tableau à deux dimensions d'entitées pour représenter la
 * répartition des divers éléments du niveau dans l'espace ainsi que de nombreux
 * attributs entiers tel que le temps maximum, le nombre de diamants requis pour
 * finir le niveau etc...
 * 
 * @see Entitee
 * @author Murloc
 *
 */
public class Niveau implements Cloneable {

	/**
	 * Entier stockant le nombre de tours par secondes qu'il doit y avoir dans
	 * ce niveau ci celui-ci est en mode temps réel.
	 */
	private int caveDelay;

	/**
	 * Entier stockant le temps maximum pour finir le niveau.
	 */
	private int cave_time;

	/**
	 * Entier stockant le nombre de diamants requis pour finir le niveau.
	 */
	private int diamonds_required;

	/**
	 * Entier stockant la valeur en score des diamants dans ce niveau.
	 */
	private int diamond_value;

	/**
	 * Entier stockant la valeur en score des diamants bonus dans ce niveau.
	 */
	private int diamond_value_bonus;

	/**
	 * Entier stockant le nombre de tours entre chaque agrandissement de l'amibe
	 * dans ce niveau.
	 */
	private int amoeba_time;

	/**
	 * Entier stockant le nombre d'utilisations max d'un mur magique dans ce
	 * niveau.
	 */
	private int magic_wall_time;

	/**
	 * Tableau à deux dimensions d'entitées servant à représenter la répartition
	 * des divers éléments du niveau dans l'espace.
	 */
	private Entitee[][] map;

	/**
	 * Le rockford du niveau, sert pour y stocker Rockford et ainsi y avoir
	 * accés facilement.
	 */
	private Rockford rockford;

	/**
	 * Constructeur de niveau.
	 * 
	 * Il initialiser les attributs.
	 * 
	 * @param map
	 *            Tableau à deux dimensions d'entitées servant à représenter la
	 *            répartition des divers éléments du niveau dans l'espace.
	 * @param caveDelay
	 *            Entier stockant le nombre de tours par secondes qu'il doit y
	 *            avoir dans ce niveau ci celui-ci est en mode temps réel.
	 * @param cave_time
	 *            Entier stockant le temps maximum pour finir le niveau.
	 * @param diamonds_required
	 *            Entier stockant la valeur en score des diamants dans ce
	 *            niveau.
	 * @param diamond_value
	 *            Entier stockant la valeur en score des diamants bonus dans ce
	 *            niveau.
	 * @param diamond_value_bonus
	 *            Entier stockant le nombre de tours entre chaque agrandissement
	 *            de l'amibe dans ce niveau.
	 * @param amoeba_time
	 *            Entier stockant le nombre d'utilisations max d'un mur magique
	 *            dans ce niveau.
	 * @param magic_wall_time
	 *            Tableau à deux dimensions d'entitées servant à représenter la
	 *            répartition des divers éléments du niveau dans l'espace.
	 */
	public Niveau(Entitee[][] map, int caveDelay, int cave_time, int diamonds_required, int diamond_value,
			int diamond_value_bonus, int amoeba_time, int magic_wall_time) {
		this.caveDelay = caveDelay;
		this.map = map;
		this.cave_time = cave_time;
		this.diamonds_required = diamonds_required;
		this.diamond_value = diamond_value;
		this.diamond_value_bonus = diamond_value_bonus;
		this.amoeba_time = amoeba_time;
		this.magic_wall_time = magic_wall_time;

		// On cherche où se situe Rockford pour initialiser l'attribut.
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j].getClass() == Rockford.class) {
					this.rockford = (Rockford) this.map[i][j];
				}
			}
		}
	}

	/**
	 * Méthode cherchant où se trouve la sortie du niveau et la renvoyant.
	 * 
	 * @return La sortie du niveau.
	 */
	public Sortie getSortie() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j].getClass() == Sortie.class) {
					return (Sortie) map[i][j];
				}
			}
		}
		return null;
	}

	/**
	 * Méthode testant si aux coordonnées indiquées il s'y trouve une entitée du
	 * type passé en paramètre, renvoit vrai si oui.
	 * 
	 * @param x
	 *            La coordonnée en x.
	 * @param y
	 *            La coordonnée en y.
	 * @param enumeration
	 *            Enumération du type de l'objet que l'on veut tester.
	 * @return Le résultat du test.
	 */
	public boolean testEntitee(int x, int y, Entitees enumeration) {
		if (x > map.length - 1 || x < 0 || y < 0 || y > map[0].length - 1) {
			return false;
		}
		if (map[x][y].getEnumeration().equals(enumeration)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode cherchant toutes les amibes du niveau et les renvoyant sous la
	 * forme d'une liste.
	 * 
	 * @return La liste contenant toutes les amibes du niveau.
	 */
	public List<Amibe> retourneListeDesAmibes() {
		List<Amibe> listeAmibes = new ArrayList<Amibe>();
		for (int i = 0; i < map.length; i++) {
			for (int j = map[i].length - 1; j >= 0; j--) {
				if ((map[i][j]).getClass().equals(Amibe.class)) {
					listeAmibes.add(((Amibe) (map[i][j])));
				}
			}
		}
		return listeAmibes;
	}

	/**
	 * Méthode cherchant tous les objets héritant de tickable du niveau et les
	 * renvoyant sous la forme d'une liste.
	 * 
	 * @return La liste contenant tous les tickables du niveau.
	 */
	public List<Tickable> getListeTickable() {
		List<Tickable> listeTickable = new ArrayList<Tickable>();
		for (int i = 0; i < getMap().length; i++) {
			for (int j = getMap()[i].length - 1; j >= 0; j--) {
				if (getMap()[i][j] instanceof Tickable) {
					listeTickable.add((Tickable) getMap()[i][j]);
				}
			}
		}
		Collections.sort(listeTickable);
		return listeTickable;
	}

	public Niveau clone() {
		Entitee[][] newmap = new Entitee[map.length][map[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				newmap[i][j] = map[i][j].clone();
			}
		}
		return new Niveau(newmap, caveDelay, cave_time, diamonds_required, diamond_value, diamond_value_bonus,
				amoeba_time, magic_wall_time);
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getCave_time() {
		return cave_time;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getDiamonds_required() {
		return diamonds_required;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getDiamond_value() {
		return diamond_value;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getDiamond_value_bonus() {
		return diamond_value_bonus;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getAmoeba_time() {
		return amoeba_time;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getMagic_wall_time() {
		return magic_wall_time;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public Entitee[][] getMap() {
		return map;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */

	public int getCaveDelay() {
		return caveDelay;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public void setCave_time(int cave_time) {
		this.cave_time = cave_time;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public Rockford getRockford() {
		return rockford;
	}
}