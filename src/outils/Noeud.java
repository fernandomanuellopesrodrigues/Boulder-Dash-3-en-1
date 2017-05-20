package outils;

import java.util.Objects;

import entitees.abstraites.Entitee;
import entitees.fixes.Poussiere;
import entitees.fixes.Sortie;
import entitees.fixes.Vide;
import entitees.tickables.Diamant;

/**
 * Classe representant un noeud d'un graphe.
 * Le contenu d'un noeud est une entitee.
 * Elle possede les atributs utiles pour effectuer des algorithmes dans les
 * graphes.
 *
 * @author Murloc
 * @see Entitee
 */
public class Noeud implements Comparable<Noeud> {

    /**
     * Le contenu du noeud.
     */
    private final Entitee entite;

    /**
     * Les coordonees du Noeud.
     */
    private int positionX;
    private int positionY;

    /**
     * Le cout du noeud (utilise en algorithmique de graphes).
     */
    private int cout;

    /**
     * L'heuristique du noeud (utilise en algorithmique de graphes).
     */
    private int heuristique;

    /**
     * La traversabilite du noeud (utilise en algorithmique de graphes).
     */
    private final boolean traversable;

    /**
     * Le noeud pere (utilise en algorithmique de graphes).
     */
    private Noeud pere;

    /**
     * L'etat du noeud (utilise en algorithmique de graphes).
     */
    private char etat;

    /**
     * Constructeur Noeud.
     * Prend en parametre une entitee et cree un noeud.
     *
     * @param entite Le contenu du noeud.
     */
    public Noeud(final Entitee entite) {
        this.entite = entite;
        this.positionX = entite.getX();
        this.positionY = entite.getY();
        this.cout = 0;
        this.heuristique = 0;
        this.traversable = Objects.equals(entite.getClass(), Diamant.class)
                           || Objects.equals(entite.getClass(), Vide.class)
                           || Objects.equals(entite.getClass(), Poussiere.class)
                           || (Objects.equals(entite.getClass(), Sortie.class) && ((Sortie) entite).isOuvert());
    }

    @Override
    public int compareTo(final Noeud another) {
        if (heuristique > another.heuristique) {
            return 1;
        } else if (heuristique == another.heuristique) {
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
     * @param cout L'objet en question.
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
     * @param heuristique L'objet en question.
     */
    public void setHeuristique(int heuristique) {
        this.heuristique = heuristique;
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
     * @param positionX L'objet en question.
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Un setter.
     *
     * @param positionY L'objet en question.
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
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
     * @param pere L'objet en question.
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
     * @param etat L'objet en question.
     */
    public void setEtat(char etat) {
        this.etat = etat;
    }

}
