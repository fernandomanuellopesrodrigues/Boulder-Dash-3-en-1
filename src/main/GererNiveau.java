package main;

import loader.Niveau;

public class GererNiveau {

	private boolean tourParTour;
	private int score, nbDiamants, tempsRestant;
	private Niveau niveau;
	private boolean finiSuccess;
	private char toucheClavier;
	private String trajet = "";

	public GererNiveau(Niveau niveau) {
		this.niveau = niveau;
		if (niveau.getCaveDelay() >= 1) {
			tourParTour = true;
		}
	}

	public void tick() {
		toucheClavier = Coeur.CONTROLEUR.getDirection();
		trajet += toucheClavier;

		Coeur.CONTROLEUR.tick();
	}

	public void finNiveau() {
		Partie.finNiveau();
	}

	public boolean isTourParTour() {
		return tourParTour;
	}

	public int getTicks() {
		return niveau.getCaveDelay();
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public boolean isFiniSuccess() {
		return finiSuccess;
	}

	public int getScore() {
		return score;
	}

	public int getNbDiamants() {
		return nbDiamants;
	}

	public int getTempsRestant() {
		return tempsRestant;
	}

	public char getToucheClavier() {
		return toucheClavier;
	}

}
