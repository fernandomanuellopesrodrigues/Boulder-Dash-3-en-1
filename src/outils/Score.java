package outils;

public class Score implements Comparable<Score> {

	private int score, parcours;
	private String chemin;
	private boolean fini;

	public Score(int score, int parcours) {
		this.score = score;
		this.parcours = parcours;
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
		if(isFini()&&!o.isFini()){
			return -1;
		}else if(!isFini()&&o.isFini()){
			return 1;
		}
		if (o.getScore() > getScore()) {
			return 1;
		} else if (getScore() > o.getScore()) {
			return -1;
		} else {
			if (o.getParcours() > getParcours()) {
				return 1;
			} else if (getParcours() > o.getParcours()) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	public boolean isFini() {
		return fini;
	}

	public void setFini(boolean fini) {
		this.fini = fini;
	}

}
