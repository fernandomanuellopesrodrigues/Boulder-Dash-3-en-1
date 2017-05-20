package loader;

import entitees.abstraites.Entitee;
import entitees.abstraites.Entitee.Type;
import entitees.fixes.Sortie;
import entitees.tickables.Rockford;

import static entitees.abstraites.Entitee.Type.Rockford;
import static entitees.abstraites.Entitee.Type.Sortie;

/**
 * La classe Niveau sert a modeliser un niveau.
 * Elle dispose d'un tableau a deux dimensions d'entitees pour representer la
 * repartition des divers elements du niveau dans l'espace ainsi que de nombreux
 * attributs entiers tel que le temps maximum, le nombre de diamants requis pour
 * finir le niveau etc...
 *
 * @author Murloc
 * @see Entitee
 */
public final class Niveau implements Cloneable {

    /**
     * Entier stockant le nombre de tours par secondes qu'il doit y avoir dans
     * ce niveau ci celui-ci est en mode temps reel.
     */
    private final int caveDelay;

    /**
     * Entier stockant le temps maximum pour finir le niveau.
     */
    private int caveTime;

    /**
     * Entier stockant le nombre de diamants requis pour finir le niveau.
     */
    private final int diamondsRequired;

    /**
     * Entier stockant la valeur en score des diamants dans ce niveau.
     */
    private final int diamondValue;

    /**
     * Entier stockant la valeur en score des diamants bonus dans ce niveau.
     */
    private final int diamondValueBonus;

    /**
     * Entier stockant le nombre de tours entre chaque agrandissement de l'amibe
     * dans ce niveau.
     */
    private final int amoebaTime;

    /**
     * Entier stockant le nombre d'utilisations max d'un mur magique dans ce
     * niveau.
     */
    private final int magicWallTime;

    /**
     * Tableau a deux dimensions d'entitees servant a representer la repartition
     * des divers elements du niveau dans l'espace.
     */
    private final Entitee[][] map;

    /**
     * Le rockford du niveau, sert pour y stocker Rockford et ainsi y avoir
     * acces facilement.
     */
    private final Rockford rockford;

    /**
     * Constructeur de niveau.
     * Il initialiser les attributs.
     *
     * @param map Tableau a deux dimensions d'entitees servant a reprasenter la
     * repartition des divers elements du niveau dans l'espace.
     * @param caveDelay Entier stockant le nombre de tours par secondes qu'il doit y
     * avoir dans ce niveau ci celui-ci est en mode temps reel.
     * @param caveTime Entier stockant le temps maximum pour finir le niveau.
     * @param diamondsRequired Entier stockant la valeur en score des diamants dans ce
     * niveau.
     * @param diamondValue Entier stockant la valeur en score des diamants bonus dans ce
     * niveau.
     * @param diamondValueBonus Entier stockant le nombre de tours entre chaque agrandissement
     * de l'amibe dans ce niveau.
     * @param amoebaTime Entier stockant le nombre d'utilisations max d'un mur magique
     * dans ce niveau.
     * @param magicWallTime Tableau a deux dimensions d'entitees servant a representer la
     * repartition des divers elements du niveau dans l'espace.
     */
    public Niveau(final Entitee[][] map,
                  final int caveDelay,
                  final int caveTime,
                  final int diamondsRequired,
                  final int diamondValue,
                  final int diamondValueBonus,
                  final int amoebaTime,
                  final int magicWallTime) {
        this.caveDelay = caveDelay;
        this.map = map;
        this.caveTime = caveTime;
        this.diamondsRequired = diamondsRequired;
        this.diamondValue = diamondValue;
        this.diamondValueBonus = diamondValueBonus;
        this.amoebaTime = amoebaTime;
        this.magicWallTime = magicWallTime;

        // On cherche ou se situe Rockford pour initialiser l'attribut.
        this.rockford = (Rockford) trouveEntite(Rockford);
    }

    /**
     * Methode cherchant ou se trouve la sortie du niveau et la renvoyant.
     *
     * @return La sortie du niveau.
     */
    public Sortie getSortie() {
        return (Sortie) trouveEntite(Sortie);
    }

    /**
     * M�thode testant si aux coordonnees indiquees il s'positionY trouve une entitee du
     * type passe en parametre, renvoit vrai si oui.
     *
     * @param positionX La coordonnee en positionX.
     * @param positionY La coordonnee en positionY.
     * @param type Enumeration du type de l'objet que l'on veut tester.
     *
     * @return Le rsultat du test.
     */
    public boolean testEntitee(final int positionX, final int positionY, final Type type) {
        return !(positionX > map.length - 1 || positionX < 0 || positionY < 0 || positionY > map[0].length - 1) &&
               map[positionX][positionY].getType() == type;
    }

    private Entitee trouveEntite(final Type type) {
        for (final Entitee[] aMap : map) {
            for (final Entitee entitee : aMap) {
                if (entitee.getType() == type) {
                    return entitee;
                }
            }
        }
        return null;
    }

    @Override
    public Niveau clone() {
        final Entitee[][] newmap = new Entitee[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                newmap[i][j] = map[i][j].clone();
            }
        }
        return new Niveau(newmap, caveDelay, caveTime, diamondsRequired, diamondValue, diamondValueBonus,
                          amoebaTime, magicWallTime);
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getCaveTime() {
        return caveTime;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public void setCaveTime(int caveTime) {
        this.caveTime = caveTime;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getDiamondsRequired() {
        return diamondsRequired;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getDiamondValue() {
        return diamondValue;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getDiamondValueBonus() {
        return diamondValueBonus;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getAmoebaTime() {
        return amoebaTime;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getMagicWallTime() {
        return magicWallTime;
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
