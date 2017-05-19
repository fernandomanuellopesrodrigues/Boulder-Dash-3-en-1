package outils;

import java.util.ArrayList;
import java.util.List;

import main.Constantes;

/**
 * Classe servant à comparer et enregistrer un essai d'un niveau.
 * 
 * Enregistre le chemin pris dans un string ainsi que le score obtenu et
 * d'autres informations.
 * 
 * @author Murloc
 *
 */
public class Score implements Comparable<Score> {

	/**
	 * Entier représentant le score obtenu dans cet essai.
	 */
	private int score;

	/**
	 * Entier représentant le nombre de tours durant l'essai.
	 */
	private int parcours;

	/**
	 * String représentant le chemin pris lors de cet essai.
	 */
	private String chemin;

	/**
	 * Boolean indiquant si lors de cet essai Rockford a atteint la sortie.
	 */
	private boolean fini;

	/**
	 * Liste des diamants obtenus lors de cet essai, la clé est le tick durant
	 * lequel le diamant a été attrapé.
	 */
	private List<Paire<Integer, Long>> listeDiamants = new ArrayList<Paire<Integer, Long>>();

	/**
	 * Constructeur Score.
	 * 
	 * @param score
	 *            Entier représentant le score obtenu dans cet essai.
	 * @param parcours
	 *            String représentant le chemin pris lors de cet essai.
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
	 * Le compareTo de cet objet est basé sur des critères faits pour améliorer
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
		double moyenne1 = 0;
		double moyenne2 = 0;

		if (moyenne() < 10) {
			moyenne1 = 10;
		} else {
			moyenne1 = moyenne();
		}

		if (o.moyenne() < 10) {
			moyenne2 = 10;
		} else {
			moyenne2 = o.moyenne();
		}

		// score1 += (listeDiamants.size()*10) +
		// ((1/moyenne1)*Constantes.VALEUR_SCORE_MOYENNE);
		// score2 += (o.listeDiamants.size()*10) +
		// ((1/moyenne2)*Constantes.VALEUR_SCORE_MOYENNE);

		score1 += (listeDiamants.size() * 100) + ((1 / moyenne1) * Constantes.VALEUR_SCORE_MOYENNE)
				+ (chemin.length() - parcours) / 1.5;
		score2 += (o.listeDiamants.size() * 100) + ((1 / moyenne2) * Constantes.VALEUR_SCORE_MOYENNE)
				+ (o.chemin.length() - o.parcours) / 1.5;
		if (score2 > score1) {
			return 1;
		} else if (score2 < score1) {
			return -1;
		} else {
			return 0;
		}
		/*
		 * if (o.getScore() > getScore()) { return 1; } else if (getScore() >
		 * o.getScore()) { return -1; } else { if(o.moyenne2() > moyenne2()){
		 * return -1; }else if(moyenne2() > o.moyenne2()){ return 1; }else{ if
		 * (o.getParcours() > getParcours()) { return 1; } else if
		 * (getParcours() > o.getParcours()) { return -1; } else { return 0; } }
		 * 
		 * }
		 */
	}

	public float moyenne2() {
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
	 * Renvoie la moyenne des objets dans la liste {@link Score#listeDiamants}.
	 * 
	 * @return La moyenne.
	 */
	public float moyenne() {
		int somme = 0;
		for (Paire<Integer, Long> p : listeDiamants) {
			somme += p.getLeft();
		}
		if (listeDiamants.isEmpty()) {
			return 10000;
		}
		return somme / (listeDiamants.size() + 1);
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
