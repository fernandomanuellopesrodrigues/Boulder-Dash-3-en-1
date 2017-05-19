package entitees.abstraites;

import java.util.ArrayList;
import java.util.List;

import entitees.fixes.Amibe;
import entitees.fixes.Vide;
import entitees.tickables.Diamant;
import entitees.tickables.Explosion;
import entitees.tickables.Pierre;
import entitees.tickables.Rockford;
import main.Coeur;
import main.GererNiveau;
import main.Partie;

import static entitees.abstraites.Entitee.Entitees.Diamant;
import static entitees.abstraites.Entitee.Entitees.Mur;
import static entitees.abstraites.Entitee.Entitees.MurMagique;
import static entitees.abstraites.Entitee.Entitees.Pierre;
import static entitees.abstraites.Entitee.Entitees.Vide;

/**
 * Classe représentant les objets d'une partie qui sont mobiles.
 *
 * @author Murloc
 */
public abstract class Tickable extends Entitee implements Comparable<Tickable> {

    /**
     * Indique si l'objet est en train de chuté.
     */
    protected boolean chute;
    /**
     * Booleen indiquant si l'objet est bloqué dans les 4 directions.
     */
    protected boolean bloque = true;
    /**
     * Indique la directio nde l'objet, la méthode {@link Tickable#seDeplacer()}
     * se sert de cet attribut.
     */
    private char direction;
    /**
     * Les éléments de cette liste représentent quels types d'élément notre
     * objet peut se déplacer.
     */
    private List<Entitees> deplacementsPossibles = new ArrayList<Entitees>();
    /**
     * Booleen servant à définir si lors d'un tour de boucle
     * {@link GererNiveau#tick()} l'objet à déjà fait son action.
     */
    private boolean actionEffectue;

    /**
     * Constructeur Tickable.
     * Prend des coordonnées en paramètre.
     *
     * @param x Coordonnée en x.
     * @param y Coordonnée en y.
     */
    protected Tickable(int x, int y) {
        super(x, y);
        deplacementsPossibles.add(Vide);
    }

    // return -1 si l'autre entitee nous mange,0 si deplacement impossible,1si
    // on mange l'entitee

    /**
     * Méthode appelé lors d'un contact entre cet objet et l'objet en paramètre.
     * Elle devra return -1 si l'autre entitee nous "mange",0 si deplacement
     * impossible,1 si on mange l'entitee.
     *
     * @param entitee L'entitée avec laquelle se produit le contact.
     *
     * @return Le résultat du contact.
     */
    protected abstract int contactAutreEntitee(Entitee entitee);

    /**
     * Méthode appelée à tour de boucle du jeu.
     */
    public abstract void tick();

    /**
     * Méthode permettant de se déplacer en fonction de la direction actuelle.
     *
     * @return Vrai si le déplacement a été effectué, faux sinon.
     */
    public boolean seDeplacer() {
        int x = 0;
        int y = 0;
        if (direction == 'h') {
            y = -1;
        } else if (direction == 'd') {
            x = 1;
        } else if (direction == 'g') {
            x = -1;
        } else if (direction == 'b') {
            y = 1;
        }
        if (placeLibre(getX() + x, getY() + y) && direction != ' ') {
            int contact = contactAutreEntitee(Partie.gererNiveau.getNiveau().getMap()[getX() + x][getY() + y]);
            if (contact == 1) {
                Partie.gererNiveau.getNiveau().getMap()[getX() + x][getY() + y].mourir();
                Partie.gererNiveau.getNiveau().getMap()[getX()][getY()] = new Vide(getX(), getY());
                setX(getX() + x);
                setY(getY() + y);
                Partie.gererNiveau.getNiveau().getMap()[getX()][getY()] = this;
                return true;
            } else if (contact == -1) {
                mourir();
            }
        }
        return false;
    }

    /**
     * Cette méthode check si l'objet peut "glisser" c'est à dire s'il est au
     * bord d'un trou il il tombe.
     * Elle efectue la glissade si tel est le cas.
     */
    protected void glisser() {
        if (Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, Pierre)
            || Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, Diamant)
            || Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, Mur)
            || Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, MurMagique)) {
            if (Partie.gererNiveau.getNiveau().testEntitee(getX() + 1, getY(), Vide)
                && Partie.gererNiveau.getNiveau().testEntitee(getX() + 1, getY() + 1, Vide)) {
                direction = 'd';
                seDeplacer();
            } else if (Partie.gererNiveau.getNiveau().testEntitee(getX() - 1, getY(), Vide)
                       && Partie.gererNiveau.getNiveau().testEntitee(getX() - 1, getY() + 1, Vide)) {
                direction = 'g';
                seDeplacer();
            }
        }
    }

    /**
     * Cette méthode gère la gravité de l'objet.
     * Si l'objet a son boolean chute en vrai elle le fait tomber d'une case,
     * sinon elle regarde si le sol en dessous de l'objet est vide et met le
     * boolean chute en vrai si tel est le cas.
     */
    protected void gererChute() {
        if (chute && placeLibre(getX(), getY() + 1)) {
            direction = 'b';
            seDeplacer();
        } else if (Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, Vide)
                   || (Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 1, MurMagique)
                       && Partie.gererNiveau.getNiveau().testEntitee(getX(), getY() + 2, Vide))) {

            chute = true;
            gererChute();
        } else {
            if (!placeLibre(getX(), getY() + 1)) {
                if (enumeration == Pierre) {
                    sons.jouerSon1("stone.wav", 1);
                }
                chute = false;
            }
            glisser();
        }

    }

    /**
     * Cette méthode fait exploser l'objet en détruisant les objets aux
     * alentours et en y créant des objets {@link Explosion}.
     * Si le booleen en paramètre est vrai, cela ne crée pas des explosions mais
     * des diamants.
     *
     * @param popDiamants Si vrai : apparition de diamant au lieu d'explosions.
     */
    protected void exploser(boolean popDiamants) {
        if (popDiamants) { sons.jouerSon1("explosionDiamant.wav", 1); } else { sons.jouerSon1("explosion.wav", 1); }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                explosion(i, j, popDiamants);
            }
        }
    }

    /**
     * Cette méthode est appelée par {@link Tickable#exploser(boolean)}.
     * Une explosion est créée aux coordonnées passées en paramètre, un diamant
     * l'est si le boolean en paramètre est vrai.
     *
     * @param i Coordonnée en x de l'objet.
     * @param j Coordonnée en y de l'objet.
     * @param popDiamants Si vrai : apparition d'un diamant au lie ud'une explosion.
     */
    protected void explosion(int i, int j, boolean popDiamants) {
        if (Partie.gererNiveau.getNiveau().getMap()[getX() + i][getY() + j].mourir()) {
            if (popDiamants) {
                Partie.gererNiveau.getNiveau().getMap()[getX() + i][getY() + j] = new Diamant(getX() + i, getY() + j);
                if (((Diamant) Partie.gererNiveau.getNiveau().getMap()[getX() + i][getY() + j]).placeLibre(getX(),
                                                                                                           getY() +
                                                                                                           1)) {
                    ((Diamant) Partie.gererNiveau.getNiveau().getMap()[getX() + i][getY() + j]).setChute(true);
                }
            } else {
                if (Coeur.graphique) {
                    Partie.gererNiveau.getNiveau().getMap()[getX() + i][getY() + j] = new Explosion(getX() + i,
                                                                                                    getY() + j);
                }
            }
            if (Coeur.graphique || popDiamants) {
                Partie.gererNiveau
                  .ajouterTickable((Tickable) Partie.gererNiveau.getNiveau().getMap()[getX() + i][getY() + j]);
            }
        }
    }

    /**
     * Cette méthode regarde l'objet aux coordonnées passées en paramètre et
     * regarde en se basant sur la liste de déplacements possible si on peut
     * effectuer un déplacement sur les coordonnées indiquées.
     *
     * @param x Coordonnée en x.
     * @param y Coordonnée en y.
     *
     * @return Vrai si le déplacement est possible, faux sinon.
     */
    public boolean placeLibre(int x, int y) {
        for (Entitees e : deplacementsPossibles) {
            if (Partie.gererNiveau.getNiveau().getMap()[x][y].getEnumeration().equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode utilisée dans le compareTo.
     *
     * @return Un entier utile dans le compareTo.
     */
    public int getNumeroPriorite() {
        if (getClass().equals(Rockford.class)) {
            return 5;
        } else if (this instanceof Ennemi) {
            return 4;
        } else if (getClass().equals(Pierre.class) || getClass().equals(Diamant.class)) {
            return 3;
        } else if (getClass().equals(Amibe.class)) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * Met à jout le boolean bloque.
     * Vrai si aucunes des 4 case autour sont inaccessibles, faux sinon.
     */
    protected void bloquer() {
        bloque = !(placeLibre(getX() + 1, getY()) || placeLibre(getX(), getY() + 1) || placeLibre(getX(), getY() - 1)
                   || placeLibre(getX() - 1, getY()));
    }

    public int compareTo(Tickable t) {
        return t.getNumeroPriorite() - getNumeroPriorite();
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
    public void setDirection(char direction) {
        this.direction = direction;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public boolean isActionEffectue() {
        return actionEffectue;
    }

    /**
     * Un setter.
     *
     * @param actionEffectue L'objet en question.
     */
    public void setActionEffectue(boolean actionEffectue) {
        this.actionEffectue = actionEffectue;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public List<Entitees> getDeplacementsPossibles() {
        return deplacementsPossibles;
    }

    /**
     * Un setter.
     *
     * @param chute L'objet en question.
     */
    public void setChute(boolean chute) {
        this.chute = chute;
    }
}
