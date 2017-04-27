package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.abstraites.Tickable;
import entitees.fixes.Amibe;
import loader.Niveau;

public class GererNiveau {

	private boolean tourParTour = true;
	private int score, nbDiamants, tempsRestant, compteurTicks;
	private Niveau niveau;
	private boolean finiSuccess;
	private char toucheClavier;
	private String trajet = "";
	private List<Amibe> listeAmibes = new ArrayList<Amibe>();
	private List<Tickable> listeTickable = new ArrayList<Tickable>();
	private List<Amibe> listeAmibesAjout = new ArrayList<Amibe>();
	private List<Tickable> listeTickableAjout = new ArrayList<Tickable>();

	public GererNiveau(Niveau niveau) {
		this.niveau = niveau;
		if (niveau.getCaveDelay() >= 1 && Coeur.tempsReel) {
			tourParTour = false;
		}

		for (int i = 0; i < niveau.getMap().length; i++) {
			for (int j = 0; j < niveau.getMap()[i].length; j++) {
				if (niveau.getMap()[i][j] instanceof Tickable) {
					listeTickable.add((Tickable) niveau.getMap()[i][j]);
				}
			}
		}
		for (int i = 0; i < niveau.getMap().length; i++) {
			for (int j = 0; j < niveau.getMap()[i].length; j++) {
				if (niveau.getMap()[i][j] instanceof Amibe) {
					listeAmibes.add((Amibe) niveau.getMap()[i][j]);
				}
			}
		}
		Collections.sort(listeTickable);
		Collections.shuffle(listeAmibes);
	}

	public void tick() {
		toucheClavier = Coeur.CONTROLEUR.getDirection();
		trajet += toucheClavier;
		gererLesTickables();
		gererLesAmibes();
		Coeur.CONTROLEUR.tick();
		ajouterAll();
		compteurTicks++;
	}

	public void gererLesTickables() {
		for (Tickable t : listeTickable) {
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

	public void ajouterAmibe(Amibe e) {
		listeAmibesAjout.add(e);
	}

	public void ajouterTickable(Tickable e) {
		listeTickableAjout.add(e);
	}

	public void ajouterAll() {
		listeAmibes.addAll(listeAmibesAjout);
		listeAmibesAjout.clear();
		Collections.shuffle(listeAmibes);
		listeTickable.addAll(listeTickable);
		listeTickableAjout.clear();
		Collections.sort(listeTickable);
	}

	public void incrementerNbDiamants() {
		nbDiamants++;
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
