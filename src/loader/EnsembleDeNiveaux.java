package loader;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe EnsembleDeNiveaux sert a enregistrer des ensembles d'objets
 * {@link Niveau}.
 *
 * @author Murloc
 */
public final class EnsembleDeNiveaux {

    /**
     * La liste des niveaux.
     */
    private final List<Niveau> niveaux = new ArrayList<>(10);

    /**
     * Le nombre de niveaux max de cet ensemble de niveaux.
     */
    private int nbNiveaux;

    /**
     * Constructeur EnsembleDeNiveaux.
     * Prend en parametre le nombre de niveaux que contiendra cet ensemble et
     * initialise l'attribut.
     *
     * @param nombreDeNiveaux Le nombre de niveaux.
     */
    public EnsembleDeNiveaux(final int nombreDeNiveaux) {
        this.nbNiveaux = nombreDeNiveaux;
    }

    /**
     * Ajoute un niveau a l'ensemble de niveaux si la limite n'a pas ete
     * atteinte.
     *
     * @param niveau Le niveau a ajouter.
     *
     * @return Vrai si l'ajout a eu lieu, faux sinon.
     */
    public boolean ajouterNiveau(final Niveau niveau) {
        return niveaux.size() < nbNiveaux && niveaux.add(niveau);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder(20);
        for (final Niveau niveau : niveaux) {
            builder.append(niveau)
                   .append("\n_____________________\n\n");
        }
        return builder.toString();
    }

    private int getNbNiveaux() {
        return nbNiveaux;
    }

    /**
     * Un setter.
     *
     * @param nbNiveaux L'objet en question.
     */
    public void setNbNiveaux(final int nbNiveaux) {
        this.nbNiveaux = nbNiveaux;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public int getNombreDeNiveaux() {
        return nbNiveaux;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public List<Niveau> getNiveaux() {
        return niveaux;
    }
}
