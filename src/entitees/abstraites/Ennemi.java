package entitees.abstraites;

import entitees.tickables.Luciole;

import static entitees.abstraites.Entitee.Entitees.Amibe;
import static entitees.abstraites.Entitee.Entitees.Rockford;

/**
 * Classe représentant les objets d'une partie qui sont mobiles et hostiles
 * envers Rockford.
 *
 * @author Murloc
 */
public abstract class Ennemi extends Tickable {

    /**
     * Constructeur Ennemi.
     * Prend des coordonnées en paramètre.
     *
     * @param x Coordonnée en x.
     * @param y Coordonnée en y.
     */
    protected Ennemi(int x, int y) {
        super(x, y);
        getDeplacementsPossibles().add(Rockford);
        getDeplacementsPossibles().add(Amibe);
    }

    @Override
    protected int contactAutreEntitee(Entitee entitee) {
        if (entitee.getEnumeration() == Rockford) {
            entitee.mourir();
        } else if (entitee.getEnumeration() == Amibe) {
            if (getClass().equals(Luciole.class)) { exploser(false); } else { exploser(true); }
            return 0;
        }
        return 1;
    }

    /**
     * Retourne vrai si la case face à l'objet est accessible pour lui.
     * En face c'est à dire en se basant sur la direction de l'objet.
     *
     * @return Vrai si la case est disponible, faux sinon.
     */
    protected boolean isToutDroitLibre() {
        switch (getDirection()) {
            case 'd':
                return placeLibre(getX() + 1, getY());
            case 'b':
                return placeLibre(getX(), getY() + 1);
            case 'h':
                return placeLibre(getX(), getY() - 1);
            case 'g':
                return placeLibre(getX() - 1, getY());
            default:
                return false;
        }
    }

    /**
     * Retourne vrai si la case derrière à l'objet est accessible pour lui.
     * Derrière c'est à dire en se basant sur la direction de l'objet.
     *
     * @return Vrai si la case est disponible, faux sinon.
     */
    protected boolean isDerriereLibre() {
        switch (getDirection()) {
            case 'd':
                return placeLibre(getX() - 1, getY());
            case 'b':
                return placeLibre(getX(), getY() - 1);
            case 'h':
                return placeLibre(getX(), getY() + 1);
            case 'g':
                return placeLibre(getX() + 1, getY());
            default:
                return false;
        }
    }

    /**
     * Retourne vrai si la case à gauche de l'objet est accessible pour lui.
     * A gauche de l'objet c'est à dire en se basant sur la direction de
     * l'objet.
     *
     * @return Vrai si la case est disponible, faux sinon.
     */
    protected boolean isGaucheLibre() {
        switch (getDirection()) {
            case 'd':
                return placeLibre(getX(), getY() - 1);
            case 'b':
                return placeLibre(getX() + 1, getY());
            case 'h':
                return placeLibre(getX() - 1, getY());
            case 'g':
                return placeLibre(getX(), getY() + 1);
            default:
                return false;
        }
    }

    /**
     * Retourne vrai si la case à droite de l'objet est accessible pour lui.
     * A droite de l'objet c'est à dire en se basant sur la direction de
     * l'objet.
     *
     * @return Vrai si la case est disponible, faux sinon.
     */
    protected boolean isDroiteLibre() {
        switch (getDirection()) {
            case 'd':
                return placeLibre(getX(), getY() + 1);
            case 'b':
                return placeLibre(getX() - 1, getY());
            case 'h':
                return placeLibre(getX() + 1, getY());
            case 'g':
                return placeLibre(getX(), getY() - 1);
            default:
                return false;
        }
    }

    /**
     * Retourne vrai si les 4 cases autour de l'objet sont accessibles pour lui.
     *
     * @return Vrai si elles le sont, faux sinon.
     */
    protected boolean isFullLibre() {
        if (!(isDroiteLibre() && isDerriereLibre() & isGaucheLibre() && isToutDroitLibre())) {
            return false;
        }
        return placeLibre(getX() + 1, getY() + 1)
               && placeLibre(getX() - 1, getY() + 1) & placeLibre(getX() + 1, getY() - 1)
               && placeLibre(getX() - 1, getY() - 1);
    }

    /**
     * Change la direction de l'objet dans le sens des aiguilles d'une montre.
     */
    protected void tournerADroite() {
        switch (getDirection()) {
            case 'd':
                setDirection('b');
                break;
            case 'b':
                setDirection('g');
                break;
            case 'h':
                setDirection('d');
                break;
            case 'g':
                setDirection('h');
                break;
            default:
                break;
        }
    }

    /**
     * Change la direction de l'objet dans le sens inverse des aiguilles d'une
     * montre.
     */
    protected void tournerAGauche() {
        switch (getDirection()) {
            case 'd':
                setDirection('h');
                break;
            case 'b':
                setDirection('d');
                break;
            case 'h':
                setDirection('g');
                break;
            case 'g':
                setDirection('b');
                break;
            default:
                break;
        }
    }

}
