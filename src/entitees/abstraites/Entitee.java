package entitees.abstraites;

import java.util.concurrent.atomic.AtomicLong;

import entitees.fixes.Vide;
import main.Partie;

import static entitees.abstraites.Entitee.Type.Vide;

/**
 * Classe repr\u00E9sentant les objets d'une partie.
 * A chaque case d'un niveau correspond une entit\u00E9e.
 *
 * @author Murloc
 */
public abstract class Entitee implements Cloneable {

    /**
     * Id static permettant de mettre un conteur diff\u00E9rent \u00E0 chaque entit\u00E9e.
     */
    private static final AtomicLong conteur = new AtomicLong(0);

    /**
     * Enumeration propre \u00E0 l'entite.
     */
    private Type type;

    /**
     * Coordonn\u00E9e de l'entit\u00E9e.
     */
    private int positionX;
    private int positionY;

    /**
     * Id de l'entit\u00E9e.
     */
    private final long identitifant;

    /**
     * Boolean permettant de savoir si l'entit\u00E9e est destructible.
     */
    private boolean destructible;

    /**
     * Boolean permettant de savoir si l'entit\u00E9e est morte.
     */
    private boolean mort;

    /**
     * Constructeur par d\u00E9faut des entit\u00E9es.
     * Cr\u00E9e une entit\u00E9e vide.
     *
     * @param positionX La coordonn\u00E9e positionX de l'entit\u00E9e.
     * @param positionY La coordonn\u00E9e positionY de l'entit\u00E9e.
     */
    public Entitee(final int positionX, final int positionY, final Type type, final boolean destructible) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.type = type;
        this.destructible = destructible;
        this.identitifant = conteur.getAndIncrement();
    }

    /**
     * Constructeur par d\u00E9faut des entit\u00E9es.
     * Cr\u00E9e une entit\u00E9e vide.
     *
     * @param positionX La coordonn\u00E9e positionX de l'entit\u00E9e.
     * @param positionY La coordonn\u00E9e positionY de l'entit\u00E9e.
     */
    @Deprecated
    public Entitee(final int positionX, final int positionY) {
        this(positionX, positionY, Vide, false);
    }

    /**
     * Fais mourir l'entit\u00E9e.
     * Si elle est destructible cr\u00E9e une entit\u00E9e vide \u00E0 la place.
     *
     * @return L'\u00E9tat de vie de l'entit\u00E9e.
     */
    public boolean mourir() {
        if (destructible) {
            Partie.gererNiveau.getNiveau().getMap()[positionX][positionY] = new Vide(positionX, positionY);
            mort = true;
        }
        return mort;
    }

    /**
     * Compare si l'\u00E9num\u00E9ration pass\u00E9e en param\u00E8tre est la m\u00EAme que celle de
     * l'entit\u00E9e.
     *
     * @param autre Entit\u00E9e \u00E0 comparer.
     *
     * @return Le r\u00E9sultat de la comparaison.
     */
    public boolean checkType(final Type autre) {
        return type == autre;
    }


    public abstract Entitee nouvelle();

    @Override
    public Entitee clone() {
        return nouvelle();
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Un setter.
     *
     * @param i L'objet en question.
     */
    public void setPositionX(int i) {
        positionX = i;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int i) {
        positionY = i;
    }

    /**
     * Un setter.
     *
     * @param destructible L'objet en question.
     */
    public void setDestructible(boolean destructible) {
        this.destructible = destructible;
    }

    public boolean isDestructible() {
        return destructible;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public boolean isMort() {
        return mort;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public Type getType() {
        return type;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public long getIdentitifant() {
        return identitifant;
    }

    /**
     * Des \u00E9num\u00E9rations repr\u00E9sentant les entit\u00E9es.
     * Utiles pour pouvoir changer les comportements des entit\u00E9es.
     *
     * @author Murloc
     */
    public enum Type {
        Vide,
        Amibe,
        Mur,
        MurEnTitane,
        MurMagique,
        Poussiere,
        Sortie,
        Diamant,
        Explosion,
        Pierre,
        Rockford,
        Libellule,
        Luciole,
        Bombe
    }

}
