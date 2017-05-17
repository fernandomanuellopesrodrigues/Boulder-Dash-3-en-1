package outils;

import java.util.ArrayList;
import java.util.List;

import main.Partie;

public class Score implements Comparable<Score> {

	private int score, parcours;
	private String chemin;
	private boolean fini;
	private List<Paire<Integer, Long>> listeDiamants = new ArrayList<Paire<Integer, Long>>();

	public Score(int score, int parcours, List<Paire<Integer, Long>> listeDiamants) {
		this.score = score;
		this.parcours = parcours;
		this.listeDiamants = listeDiamants;
	}

	public int getScore() {
		return score;
	}

	public int getParcours() {
		return parcours;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	@Override
	public int compareTo(Score o) {
		if (isFini() && !o.isFini()) {
			return -1;
		} else if (!isFini() && o.isFini()) {
			return 1;
		}
		if (o.getScore() > getScore()) {
			return 1;
		} else if (getScore() > o.getScore()) {
			return -1;
		} else {
			if(o.moyenne() > moyenne()){
				return -1;
			}else if(moyenne() > o.moyenne()){
				return 1;
			}else{
				if (o.getParcours() > getParcours()) {
					return 1;
				} else if (getParcours() > o.getParcours()) {
					return -1;
				} else {
					return 0;
				}
			}
			
		}
	}

	public float moyenne() {
		int somme = 0;
		for (Paire<Integer, Long> p : listeDiamants){
			somme+=p.getLeft();
		}
		if(listeDiamants.isEmpty()){
			return 10000;
		}
		return somme/(listeDiamants.size()+1);
	}

	public boolean isFini() {
		return fini;
	}

	public void setFini(boolean fini) {
		this.fini = fini;
	}

	public List<Paire<Integer, Long>> getListeDiamants() {
		return listeDiamants;
	}

}
