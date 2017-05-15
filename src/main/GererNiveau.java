package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.abstraites.Tickable;
import entitees.fixes.Amibe;
import loader.Niveau;
import tasks.TickTask;

public class GererNiveau {

	public List<Amibe> getListeAmibesAjout() {
		return listeAmibesAjout;
	}

	private boolean tourParTour = true, demandeReset, demandeFin;
	private int score, nbDiamants, tempsRestant, tempsDispo, compteurTicks;
	private long tempsAuDebut = System.currentTimeMillis();
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
		tempsRestant = niveau.getCave_time();
		tempsDispo = tempsRestant;
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
		tickInterne();
	}

	public void tickLecture(char diretion) {
		toucheClavier = diretion;
		tickInterne();
	}

	public void tickInterne() {
		gererLesTickables();
		gererLesAmibes();
		ajouterAll();
		compteurTicks++;
		if (demandeReset) {
			Partie.resetNiveau();
		}
		if (demandeFin) {
			Partie.finNiveau();
		}
		gererTemps();
		Coeur.CONTROLEUR.tick();
	}

	public void gererLesTickables() {
		for (Tickable t : listeTickable) {
			if (!t.isMort())
				t.tick();
		}
	}

	public void gererLesAmibes() {
		//son
		if (listeAmibes.isEmpty()) {
			Partie.sons.stopSon1();
		}
		if (!getListeAmibes().isEmpty()) {
			Partie.sons.jouerSon1("amoeba.wav", 965);
		}
		//fin son
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

	public void gererTemps() {
		long temps = System.currentTimeMillis();
		if (temps - tempsAuDebut > tempsDispo * 1000) {
			demandeReset = true;
		}
		tempsRestant = (int) (tempsDispo - ((temps - tempsAuDebut) / 1000));
	}

	public void ajouterAmibe(Amibe e) {
		boolean ok = true;
		for (Amibe a : listeAmibes) {
			if (a.getId() == e.getId()) {
				ok = false;
				break;
			}
		}
		if (ok)
			listeAmibesAjout.add(e);
	}

	public void ajouterTickable(Tickable e) {
		boolean ok = true;
		for (Tickable a : listeTickable) {
			if (a.getId() == e.getId()) {
				ok = false;
				break;
			}
		}
		if (ok)
			listeTickableAjout.add(e);
	}

	public void ajouterAll() {
		listeAmibes.addAll(listeAmibesAjout);
		listeAmibesAjout.clear();
		for (int i = 0; i < listeAmibes.size(); i++) {
			if (listeAmibes.get(i).isMort()) {
				listeAmibes.remove(i);
				i--;
			}
		}
		Collections.shuffle(listeAmibes);
		listeTickable.addAll(listeTickableAjout);
		listeTickableAjout.clear();
		for (int i = 0; i < listeTickable.size(); i++) {
			if (listeTickable.get(i).isMort()) {
				listeTickable.remove(i);
				i--;
			}
		}
		Collections.sort(listeTickable);
	}

	public void stop() {
		listeTickable.clear();
		listeTickableAjout.clear();
		listeAmibes.clear();
		listeAmibesAjout.clear();
		niveau = null;
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

	public void setDemandeReset(boolean demandeReset) {
		this.demandeReset = demandeReset;
	}

	public boolean isDemandeReset() {
		return demandeReset;
	}

	public boolean isDemandeFin() {
		return demandeFin;
	}

	public void setDemandeFin(boolean demandeFin) {
		this.demandeFin = demandeFin;
	}

	public String getTrajet() {
		return trajet;
	}

	public int getCompteurTicks() {
		return compteurTicks;
	}
	
}
