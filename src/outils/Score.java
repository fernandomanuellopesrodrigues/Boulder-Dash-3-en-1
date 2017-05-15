package outils;

public class Score implements Comparable<Score>{

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
		
		return 0;
	}

	public boolean isFini() {
		return fini;
	}

	public void setFini(boolean fini) {
		this.fini = fini;
	}

}
