package entitees.abstraites;

import static entitees.abstraites.Entitee.Type.Amibe;
import static entitees.abstraites.Entitee.Type.Rockford;
import static main.Constantes.BAS;
import static main.Constantes.DROITE;
import static main.Constantes.GAUCHE;
import static main.Constantes.HAUT;

/**
 * Classe repr\u00E9sentant les objets d'une partie qui sont mobiles et hostiles
 * envers Rockford.
 *
 * @author Murloc
 */
public abstract class Ennemi extends Tickable {

    /**
     * Constructeur Ennemi.
     * Prend des coordonnées en paramètre.
     *
     * @param positionX Coordonn\u00E9e en x.
     * @param positionY Coordonn\u00E9e en y.
     */
    public Ennemi(final int positionX, final int positionY, final Type type, final boolean destructible) {
        super(positionX, positionY, type, destructible);
        addDeplacementPossible(Rockford, Amibe);
    }

    /**
     * Retourne vrai si la case face \\u00E0 l'objet est accessible pour lui.
     * En face c'est \\u00E0 dire en se basant sur la direction de l'objet.
     *
     * @return Vrai si la case est disponible, faux sinon.
     */
    public boolean isToutDroitLibre() {
        int nPX = getPositionX();
        int nPY = getPositionY();
        switch (getDirection()) {
            case DROITE:
                nPX++;
                break;
            case BAS:
                nPY++;
                break;
            case HAUT:
                nPY--;
                break;
            case GAUCHE:
                nPX--;
                break;
            default:
        }
        return placeLibre(nPX, nPY);
    }

    /**
     * Retourne vrai si la case derri\u00E8re \u00E0 l'objet est accessible pour lui.
     * Derri\u00E8re c'est \u00E0 dire en se basant sur la direction de l'objet.
     *
     * @return Vrai si la case est disponible, faux sinon.
     */
    public boolean isDerriereLibre() {
        int nPX = getPositionX();
        int nPY = getPositionY();
        switch (getDirection()) {
            case DROITE:
                nPX--;
                break;
            case BAS:
                nPY--;
                break;
            case HAUT:
                nPY++;
                break;
            case GAUCHE:
                nPX++;
                break;
            default:
        }
        return placeLibre(nPX, nPY);
    }

    /**
     * Retourne vrai si la case à gauche de l'objet est accessible pour lui.
     * A gauche de l'objet c'est à dire en se basant sur la direction de
     * l'objet.
     *
     * @return Vrai si la case est disponible, faux sinon.
     */
    public boolean isGaucheLibre() {
        int nPX = getPositionX();
        int nPY = getPositionY();
        switch (getDirection()) {
            case DROITE:
                nPY--;
                break;
            case BAS:
                nPX++;
                break;
            case HAUT:
                nPX--;
                break;
            case GAUCHE:
                nPY++;
                break;
            default:

        }
        return placeLibre(nPX, nPY);
    }

    /**
     * Retourne vrai si la case à droite de l'objet est accessible pour lui.
     * A droite de l'objet c'est à dire en se basant sur la direction de
     * l'objet.
     *
     * @return Vrai si la case est disponible, faux sinon.
     */
    public boolean isDroiteLibre() {
        int nPX = getPositionX();
        int nPY = getPositionY();
        switch (getDirection()) {
            case DROITE:
                nPY++;
                break;
            case BAS:
                nPX--;
                break;
            case HAUT:
                nPX++;
                break;
            case GAUCHE:
                nPY--;
                break;
            default:
        }
        return placeLibre(nPX, nPY);
    }

    /**
     * Retourne vrai si les 4 cases autour de l'objet sont accessibles pour lui.
     *
     * @return Vrai si elles le sont, faux sinon.
     */
    public boolean isFullLibre() {
        return isDroiteLibre() && isDerriereLibre() & isGaucheLibre() && isToutDroitLibre() &&
               placeLibre(getPositionX() + 1, getPositionY() + 1) &&
               placeLibre(getPositionX() - 1, getPositionY() + 1) &
               placeLibre(getPositionX() + 1, getPositionY() - 1) &&
               placeLibre(getPositionX() - 1, getPositionY() - 1);
    }

    /**
     * Change la direction de l'objet dans le sens des aiguilles d'une montre.
     */
    public void tournerADroite() {
        tourneDroiteOuGauche(true);
    }

    /**
     * Change la direction de l'objet dans le sens inverse des aiguilles d'une
     * montre.
     */
    public void tournerAGauche() {
        tourneDroiteOuGauche(false);
    }

    private void tourneDroiteOuGauche(final boolean sens) {
        switch (getDirection()) {
            case DROITE:
                setDirection(sens ? BAS : HAUT);
                break;
            case BAS:
                setDirection(sens ? GAUCHE : DROITE);
                break;
            case HAUT:
                setDirection(sens ? DROITE : GAUCHE);
                break;
            case GAUCHE:
                setDirection(sens ? HAUT : BAS);
                break;
            default:
        }
    }

}
