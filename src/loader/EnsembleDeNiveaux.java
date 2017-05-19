package loader;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe EnsembleDeNiveaux sert à enregistrer des ensembles d'objets
 * {@link Niveau}.
 * 
 * @author Murloc
 *
 */
public class EnsembleDeNiveaux {

	/**
	 * La liste des niveaux.
	 */
	private List<Niveau> niveaux = new ArrayList<Niveau>();;

	/**
	 * Le nombre de niveaux max de cet ensemble de niveaux.
	 */
	private int nbNiveaux;

	/**
	 * Constructeur EnsembleDeNiveaux.
	 * 
	 * Prend en paramètre le nombre de niveaux que contiendra cet ensemble et
	 * initialise l'attribut.
	 * 
	 * @param nombre_de_niveaux
	 *            Le nombre de niveaux.
	 */
	public EnsembleDeNiveaux(int nombre_de_niveaux) {
		this.nbNiveaux = nombre_de_niveaux;
	}

	/**
	 * Ajoute un niveau à l'ensemble de niveaux si la limite n'a pas été
	 * atteinte.
	 * 
	 * @param niveau
	 *            Le niveau à ajouter.
	 * @return Vrai si l'ajout a eu lieu, faux sinon.
	 */
	public boolean ajouterNiveau(Niveau niveau) {
		if (niveaux.size() < nbNiveaux)
			return niveaux.add(niveau);
		return false;
	}

	public String toString() {
		String s = "";
		for (Niveau niveau : niveaux) {
			s += niveau;
			s += "\n_____________________\n\n";
		}
		return s;
	}

	/**
	 * Un setter.
	 * 
	 * @param nbNiveaux
	 *            L'objet en question.
	 */
	public void setNbNiveaux(int nbNiveaux) {
		this.nbNiveaux = nbNiveaux;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getNombre_de_niveaux() {
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
