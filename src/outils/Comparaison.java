package outils;

public class Comparaison {

	private long score, tours, tempsDepart, tempsPris, nombreTrys;
	private boolean first = true;

	public Comparaison(int nombreTrys) {
		this.nombreTrys = nombreTrys;

	}

	public void addScore(int score, int distance) {
		if (first) {
			tempsDepart = System.currentTimeMillis();
			first = false;
		}
		this.tours = this.tours + tours;
		this.score = this.score + score;
		this.nombreTrys++;
	}

	public void fin() {
		this.tempsPris = System.currentTimeMillis() - tempsDepart;
	}

	public long getScoreMoyen() {
		return score / nombreTrys;
	}

	public long getDistanceMoyenne() {
		return tours / nombreTrys;
	}

	public long getTempsMoyen() {
		return tempsPris / nombreTrys;
	}
}
