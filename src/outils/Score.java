package outils;

import java.util.ArrayList;
import java.util.List;

import entitees.abstraites.Entitee;
import ia.IaEvolue;
import main.Partie;

/**
 * Classe servant � comparer et enregistrer un essai d'un niveau.
 * 
 * Enregistre le chemin pris dans un string ainsi que le score obtenu et
 * d'autres informations.
 * 
 * @author Murloc
 *
 */
public class Score implements Comparable<Score> {

	/**
	 * Entier repr�sentant le score obtenu dans cet essai.
	 */
	private int score;

	/**
	 * Entier repr�sentant le nombre de tours durant l'essai.
	 */
	private int parcours;

	/**
	 * String repr�sentant le chemin pris lors de cet essai.
	 */
	private String chemin;

	/**
	 * Boolean indiquant si lors de cet essai Rockford a atteint la sortie.
	 */
	private boolean fini;

	/**
	 * Liste des diamants obtenus lors de cet essai, la cl� est le tick durant
	 * lequel le diamant a �t� attrap�.
	 */
	private List<Paire<Integer, Long>> listeDiamants = new ArrayList<Paire<Integer, Long>>();
	private Entitee[][] mapFinParcours;

	/**
	 * Constructeur Score.
	 * 
	 * @param score
	 *            Entier représentant le score obtenu dans cet essai.
	 * @param parcours
	 *            Entier représentant le nombre de tours durant l'essai.
	 * @param listeDiamants
	 *            Liste des diamants obtenus lors de cet essai, la clé est le
	 *            tick durant lequel le diamant a été attrapé.
	 */
	public Score(int score, int parcours, List<Paire<Integer, Long>> listeDiamants) {
		this.score = score;
		this.parcours = parcours;
		this.listeDiamants = listeDiamants;
	}

	/**
	 * Le compareTo de cet objet est bas� sur des crit�res faits pour am�liorer
	 * les essais.
	 */
	@Override
	public int compareTo(Score o) {
		if (isFini() && !o.isFini()) {
			return -1;
		} else if (!isFini() && o.isFini()) {
			return 1;
		}
		int score1 = 0;
		int score2 = 0;
		if (Partie.ia instanceof IaEvolue) {
			score1 += (listeDiamants.size() * 10) + (chemin.length() - parcours) / 1.5;
			score2 += (o.listeDiamants.size() * 10) + (o.chemin.length() - o.parcours) / 1.5;
		} else {
			score1 += (listeDiamants.size() * 100) + (chemin.length() - parcours) / 30;
			score2 += (o.listeDiamants.size() * 100) + (chemin.length() - parcours) / 30;
		}
		if (score2 > score1) {
			return 1;
		} else if (score2 < score1) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * Renvoie la moyenne des objets dans la liste {@link Score#listeDiamants}.
	 * 
	 * @return La moyenne.
	 */
	public float moyenne() {
		int somme = 0;
		if (!listeDiamants.isEmpty()) {
			somme = listeDiamants.get(0).getLeft();
		}
		for (int i = 1; i < listeDiamants.size(); i++) {
			somme += (listeDiamants.get(i).getLeft() - listeDiamants.get(i - 1).getLeft());
		}
		if (listeDiamants.isEmpty()) {
			return 10000;
		}
		return somme / (listeDiamants.size());
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public boolean isFini() {
		return fini;
	}

	/**
	 * Un setter.
	 * 
	 * @param fini
	 *            L'objet en question.
	 */
	public void setFini(boolean fini) {
		this.fini = fini;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public List<Paire<Integer, Long>> getListeDiamants() {
		return listeDiamants;
	}

	public Entitee[][] getMapFinParcours() {
		return mapFinParcours;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public int getParcours() {
		return parcours;
	}

	/**
	 * Un getter.
	 * 
	 * @return L'objet en question.
	 */
	public String getChemin() {
		return chemin;
	}

	/**
	 * Un setter.
	 * 
	 * @param chemin
	 *            L'objet en question.
	 */
	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

}
