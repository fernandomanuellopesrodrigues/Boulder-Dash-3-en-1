package loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.abstraites.Entitee;
import entitees.abstraites.Entitee.Type;
import entitees.abstraites.Tickable;
import entitees.fixes.Amibe;
import entitees.fixes.Sortie;
import entitees.tickables.Rockford;

/**
 * La classe Niveau sert � mod�liser un niveau.
 * Elle dispose d'un tableau � deux dimensions d'entit�es pour repr�senter la
 * r�partition des divers �l�ments du niveau dans l'espace ainsi que de nombreux
 * attributs entiers tel que le temps maximum, le nombre de diamants requis pour
 * finir le niveau etc...
 *
 * @author Murloc
 * @see Entitee
 */
public class Niveau implements Cloneable {

    /**
     * Entier stockant le nombre de tours par secondes qu'il doit y avoir dans
     * ce niveau ci celui-ci est en mode temps r�el.
     */
    private final int caveDelay;

    /**
     * Entier stockant le temps maximum pour finir le niveau.
     */
    private int cave_time;

    /**
     * Entier stockant le nombre de diamants requis pour finir le niveau.
     */
    private final int diamonds_required;

    /**
     * Entier stockant la valeur en score des diamants dans ce niveau.
     */
    private final int diamond_value;

    /**
     * Entier stockant la valeur en score des diamants bonus dans ce niveau.
     */
    private final int diamond_value_bonus;

    /**
     * Entier stockant le nombre de tours entre chaque agrandissement de l'amibe
     * dans ce niveau.
     */
    private final int amoeba_time;

    /**
     * Entier stockant le nombre d'utilisations max d'un mur magique dans ce
     * niveau.
     */
    private final int magic_wall_time;

    /**
     * Tableau � deux dimensions d'entit�es servant � repr�senter la r�partition
     * des divers �l�ments du niveau dans l'espace.
     */
    private final Entitee[][] map;

    /**
     * Le rockford du niveau, sert pour y stocker Rockford et ainsi y avoir
     * acc�s facilement.
     */
    private Rockford rockford;

    /**
     * Constructeur de niveau.
     * Il initialiser les attributs.
     *
     * @param map Tableau � deux dimensions d'entit�es servant � repr�senter la
     * r�partition des divers �l�ments du niveau dans l'espace.
     * @param caveDelay Entier stockant le nombre de tours par secondes qu'il doit y
     * avoir dans ce niveau ci celui-ci est en mode temps r�el.
     * @param cave_time Entier stockant le temps maximum pour finir le niveau.
     * @param diamonds_required Entier stockant la valeur en score des diamants dans ce
     * niveau.
     * @param diamond_value Entier stockant la valeur en score des diamants bonus dans ce
     * niveau.
     * @param diamond_value_bonus Entier stockant le nombre de tours entre chaque agrandissement
     * de l'amibe dans ce niveau.
     * @param amoeba_time Entier stockant le nombre d'utilisations max d'un mur magique
     * dans ce niveau.
     * @param magic_wall_time Tableau � deux dimensions d'entit�es servant � repr�senter la
     * r�partition des divers �l�ments du niveau dans l'espace.
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

        // On cherche o� se situe Rockford pour initialiser l'attribut.
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].getClass() == Rockford.class) {
                    this.rockford = (Rockford) this.map[i][j];
                }
            }
        }
    }

    /**
     * M�thode cherchant o� se trouve la sortie du niveau et la renvoyant.
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
     * M�thode testant si aux coordonn�es indiqu�es il s'y trouve une entit�e du
     * type pass� en param�tre, renvoit vrai si oui.
     *
     * @param x La coordonn�e en x.
     * @param y La coordonn�e en y.
     * @param type Enum�ration du type de l'objet que l'on veut tester.
     *
     * @return Le r�sultat du test.
     */
    public boolean testEntitee(int x, int y, Type type) {
        if (x > map.length - 1 || x < 0 || y < 0 || y > map[0].length - 1) {
            return false;
        }
        return map[x][y].getType().equals(enumeration);
    }

    /**
     * M�thode cherchant toutes les amibes du niveau et les renvoyant sous la
     * forme d'une liste.
     *
     * @return La liste contenant toutes les amibes du niveau.
     */
    public List<Amibe> retourneListeDesAmibes() {
        List<Amibe> listeAmibes = new ArrayList<Amibe>();
        for (int i = 0; i < map.length; i++) {
            for (int j = map[i].length - 1; j >= 0; j--) {
                if (map[i][j].getClass().equals(Amibe.class)) {
                    listeAmibes.add((Amibe) map[i][j]);
                }
            }
        }
        return listeAmibes;
    }

    /**
     * M�thode cherchant tous les objets h�ritant de tickable du niveau et les
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

    @Override
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
    public int getCaveTime() {
        return cave_time;
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
    public int getDiamondsRequired() {
        return diamonds_required;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getDiamondValue() {
        return diamond_value;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getDiamondValueBonus() {
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
    public Rockford getRockford() {
        return rockford;
    }

}
