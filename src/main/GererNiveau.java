package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.abstraites.Tickable;
import entitees.fixes.Amibe;
import loader.Niveau;

public class GererNiveau {

	private boolean tourParTour;
	private int score, nbDiamants, tempsRestant, compteurTicks;
	private Niveau niveau;
	private boolean finiSuccess;
	private char toucheClavier;
	private String trajet = "";
	private List<Amibe> listeAmibes = new ArrayList<Amibe>();
	private List<Tickable> listeTickable = new ArrayList<Tickable>();

	public GererNiveau(Niveau niveau) {
		this.niveau = niveau;
		if (niveau.getCaveDelay() >= 1) {
			tourParTour = true;
		}
		for (Tickable t : listeTickable) {
			this.listeTickable.add((Tickable) t);
		}
	}

	public void tick() {
		toucheClavier = Coeur.CONTROLEUR.getDirection();
		trajet += toucheClavier;
		gererLesTickables();
		Coeur.CONTROLEUR.tick();
		compteurTicks++;
	}

	public void gererLesTickables() {
		List<Tickable> listeTickable2 = new ArrayList<Tickable>();
		for (Tickable t : listeTickable) {
			listeTickable2.add(t);
		}

		for (Tickable t : listeTickable2) {
			t.tick();
		}
	}

	public void gererLesAmibes() {
		if (listeAmibes.size() > 0 && niveau.getAmoeba_time() != -1 && compteurTicks % niveau.getAmoeba_time() == 0) {
			Collections.shuffle(listeAmibes);
			for (Amibe amibe : listeAmibes) {
				if (amibe.sePropager()) {
					return;
				}
			}
			listeAmibes.get(0).transformerTousLesAmibesEnDiamant();
		}
	}

	public void setFiniSuccess(boolean finiSuccess) {
		this.finiSuccess = finiSuccess;
	}

	public void setScore(int score) {
		this.score = score;
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

	public List<Amibe> getListeAmibes() {
		return listeAmibes;
	}

	public List<Tickable> getListeTickable() {
		return listeTickable;
	}

}
