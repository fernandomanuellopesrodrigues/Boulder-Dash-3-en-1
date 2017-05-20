package entitees.abstraites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import entitees.fixes.Vide;
import entitees.tickables.Diamant;
import entitees.tickables.Explosion;
import main.GererNiveau;

import static entitees.abstraites.Entitee.Type.Diamant;
import static entitees.abstraites.Entitee.Type.Mur;
import static entitees.abstraites.Entitee.Type.MurMagique;
import static entitees.abstraites.Entitee.Type.Pierre;
import static entitees.abstraites.Entitee.Type.Vide;
import static main.Coeur.graphique;
import static main.Constantes.BAS;
import static main.Constantes.DROITE;
import static main.Constantes.GAUCHE;
import static main.Constantes.HAUT;
import static main.Partie.SONS;
import static main.Partie.checkEntite;
import static main.Partie.gererNiveau;
import static main.Partie.getEntiteParPosition;
import static main.Partie.setEntiteParPosition;

/**
 * Classe repr\u00E9sentant les objets d'une partie qui sont mobiles.
 *
 * @author Murloc
 */
public abstract class Tickable extends Entitee implements Comparable<Tickable> {

    /**
     * Indique si l'objet est en train de chuter
     */
    private boolean chute;

    /**
     * Booleen indiquant si l'objet est bloqu\u00E9 dans les 4 directions.
     */
    private boolean bloque = true;

    /**
     * Indique la directio nde l'objet, la méthode {@link Tickable#seDeplacer()}
     * se sert de cet attribut.
     */
    private char direction;

    /**
     * Les \u00E9l\u00E9ments de cette liste repr\u00E9sentent quels types d'\u00E9l\u00E9ment notre
     * objet peut se d\u00E9placer.
     */
    private final List<Type> deplacementsPossibles = new ArrayList<>(10);

    /**
     * Booleen servant \u00E0 d\u00E9finir si lors d'un tour de boucle
     * {@link GererNiveau#tick()} l'objet \u00E0 d\u00E9j\u00E0 fait son action.
     */
    private boolean actionEffectue;

    /**
     * Constructeur Tickable.
     * Prend des coordonn\u00E9es en param\u00E8tre.
     *
     * @param positionX Coordonn\u00E9e en x.
     * @param positionY Coordonn\u00E9e en y.
     */
    public Tickable(final int positionX, final int positionY, final Type type, final boolean destructible) {
        super(positionX, positionY, type, destructible);
        deplacementsPossibles.add(Vide);
    }
    // return -1 si l'autre entitee nous mange,0 si deplacement impossible,1si
    // on mange l'entitee

    /**
     * M\u00E9thode appel\u00E9 lors d'un contact entre cet objet et l'objet en param\u00E8tre.
     * Elle devra return -1 si l'autre entitee nous \"mange\",0 si deplacement
     * impossible,1 si on mange l'entitee.
     *
     * @param entitee L'entit\u00E9e avec laquelle se produit le contact.
     *
     * @return Le r\u00E9sultat du contact.
     */
    public abstract int contactAutreEntitee(Entitee entitee);

    /**
     * M\u00E9thode appel\u00E9e \u00E0 tour de boucle du jeu.
     */
    public abstract void tick();

    /**
     * M\u00E9thode permettant de se d\u00E9placer en fonction de la direction actuelle.
     *
     * @return Vrai si le d\u00E9placement a \u00E9t\u00E9 effectu\u00E9, faux sinon.
     */
    public boolean seDeplacer() {
        int x = 0;
        int y = 0;
        if (direction == BAS) {
            y = -1;
        } else if (direction == DROITE) {
            x = 1;
        } else if (direction == GAUCHE) {
            x = -1;
        } else if (direction == HAUT) {
            y = 1;
        }
        final int positionX = getPositionX();
        final int positionY = getPositionY();
        if (placeLibre(positionX + x, positionY + y) && direction != ' ') {
            final Entitee entite = getEntiteParPosition(positionX + x, positionY + y);
            final int contact = contactAutreEntitee(entite);
            if (contact == 1) {
                entite.mourir();
                setEntiteParPosition(positionX, positionY, new Vide(positionX, positionY));
                setPositionX(positionX + x);
                setPositionY(positionY + y);
                setEntiteParPosition(getPositionX(), getPositionY(), this);
                return true;
            } else if (contact == -1) {
                mourir();
            }
        }
        return false;
    }

    /**
     * Cette m\u00E9thode check si l'objet peut \"glisser\" c'est \u00E0 dire s'il est au
     * bord d'un trou il il tombe.
     * Elle efectue la glissade si tel est le cas.
     */
    public void glisser() {
        if (checkEntite(getPositionX(), getPositionY() + 1, Pierre)
            || checkEntite(getPositionX(), getPositionY() + 1, Diamant)
            || checkEntite(getPositionX(), getPositionY() + 1, Mur)
            || checkEntite(getPositionX(), getPositionY() + 1, MurMagique)) {
            if (checkEntite(getPositionX() + 1, getPositionY(), Vide) &&
                checkEntite(getPositionX() + 1, getPositionY() + 1, Vide)) {
                direction = DROITE;
                seDeplacer();
            } else if (checkEntite(getPositionX() - 1, getPositionY(), Vide) &&
                       checkEntite(getPositionX() - 1, getPositionY() + 1, Vide)) {
                direction = GAUCHE;
                seDeplacer();
            }
        }
    }

    /**
     * Cette m\u00E9thode g\u00E8re la gravit\u00E9 de l'objet.
     * Si l'objet a son boolean chute en vrai elle le fait tomber d'une case,
     * sinon elle regarde si le sol en dessous de l'objet est vide et met le
     * boolean chute en vrai si tel est le cas.
     */
    public void gererChute() {
        if (chute && placeLibre(getPositionX(), getPositionY() + 1)) {
            direction = BAS;
            seDeplacer();
        } else if (checkEntite(getPositionX(), getPositionY() + 1, Vide)
                   || checkEntite(getPositionX(), getPositionY() + 1, MurMagique)
                      && checkEntite(getPositionX(), getPositionY() + 2, Vide)) {
            chute = true;
            gererChute();
        } else {
            if (!placeLibre(getPositionX(), getPositionY() + 1)) {
                if (checkType(Pierre)) {
                    SONS.jouerSonStone();
                }
                chute = false;
            }
            glisser();
        }

    }

    /**
     * Cette m\u00E9thode fait exploser l'objet en d\u00E9truisant les objets aux
     * alentours et en y cr\u00E9ant des objets {@link Explosion}.
     * Si le booleen en param\u00E8tre est vrai, cela ne cr\u00E9e pas des explosions mais
     * des diamants.
     *
     * @param popDiamants Si vrai : apparition de diamant au lieu d'explosions.
     */
    public void exploser(final boolean popDiamants) {
        if (popDiamants) {
            SONS.jouerSonExplosionDiamant();
        } else {
            SONS.jouerSonExplosion();
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                explosion(i, j, popDiamants);
            }
        }
    }

    /**
     * Cette m\u00E9thode est appel\u00E9e par {@link Tickable#exploser(boolean)}.
     * Une explosion est cr\u00E9\u00E9e aux coordonn\u00E9es pass\u00E9es en param\u00E8tre, un diamant
     * l'est si le boolean en param\u00E8tre est vrai.
     *
     * @param positionX Coordonn\u00E9e en x de l'objet.
     * @param positionY Coordonn\u00E9e en y de l'objet.
     * @param popDiamants Si vrai : apparition d'un diamant au lie ud'une explosion.
     */
    public void explosion(final int positionX, final int positionY, final boolean popDiamants) {
        final int nPX = getPositionX() + positionX;
        final int nPY = getPositionY() + positionY;
        if (getEntiteParPosition(nPX, nPY).mourir()) {
            if (popDiamants) {
                setEntiteParPosition(nPX, nPY, new Diamant(nPX, nPY));
                if (((Diamant) getEntiteParPosition(nPX, nPY)).placeLibre(getPositionX(), getPositionY() + 1)) {
                    ((Diamant) getEntiteParPosition(nPX, nPY)).setChute(true);
                }
            } else {
                if (graphique) {
                    setEntiteParPosition(nPX, nPY, new Explosion(nPX, nPY));
                }
            }
            if (graphique || popDiamants) {
                gererNiveau.ajouterTickable((Tickable) getEntiteParPosition(nPX, nPY));
            }
        }
    }

    /**
     * Cette m\u00E9thode regarde l'objet aux coordonn\u00E9es pass\u00E9es en param\u00E8tre et
     * regarde en se basant sur la liste de d\u00E9placements possible si on peut
     * effectuer un d\u00E9placement sur les coordonn\u00E9es indiqu\u00E9es.
     *
     * @param positionX Coordonn\u00E9e en x.
     * @param positionY Coordonn\u00E9e en y.
     *
     * @return Vrai si le d\u00E9placement est possible, faux sinon.
     */
    public boolean placeLibre(final int positionX, final int positionY) {
        final Type type = getEntiteParPosition(positionX, positionY).getType();
        for (final Type deplace : deplacementsPossibles) {
            if (Objects.equals(type, deplace)) {
                return true;
            }
        }
        return false;
    }

    /**
     * M\u00E9thode utilis\u00E9e dans le compareTo.
     *
     * @return Un entier utile dans le compareTo.
     */
    public abstract int getNumeroPriorite();

    /**
     * Met \u00E0 jout le boolean bloque.
     * Vrai si aucunes des 4 case autour sont inaccessibles, faux sinon.
     */
    public void bloquer() {
        bloque = !(placeLibre(getPositionX() + 1, getPositionY())
                   || placeLibre(getPositionX(), getPositionY() + 1)
                   || placeLibre(getPositionX(), getPositionY() - 1)
                   || placeLibre(getPositionX() - 1, getPositionY()));
    }

    @Override
    public int compareTo(final Tickable other) {
        return other.getNumeroPriorite() - getNumeroPriorite();
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public char getDirection() {
        return direction;
    }

    /**
     * Un setter.
     *
     * @param direction L'objet en question.
     */
    public void setDirection(final char direction) {
        this.direction = direction;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public List<Type> getDeplacementsPossibles() {
        return deplacementsPossibles;
    }

    public void addDeplacementPossible(final Type... entites) {
        deplacementsPossibles.addAll(Arrays.asList(entites));
    }

    /**
     * Un setter.
     *
     * @param chute L'objet en question.
     */
    public void setChute(final boolean chute) {
        this.chute = chute;
    }

    /**
     *
     * @return
     */
    public boolean isChute() {
        return chute;
    }

    public boolean isBloque() {
        return bloque;
    }

    public void setBloque(final boolean bloque) {
        this.bloque = bloque;
    }

    private boolean isActionEffectue() {
        return actionEffectue;
    }

    private void setActionEffectue(final boolean actionEffectue) {
        this.actionEffectue = actionEffectue;
    }

}
