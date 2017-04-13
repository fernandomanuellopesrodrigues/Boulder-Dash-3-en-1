package main;

import java.util.ArrayList;
import java.util.List;

import loader.EnsembleDeNiveaux;
import loader.Loader;

public class Partie {
	public static final List<Integer> SCORES = new ArrayList<Integer>();
	public static EnsembleDeNiveaux ensembleDeNiveau;
	public static int niveau;
	public static GererNiveau gererNiveau;

	public static void commencerPartie(String chemin) {
		ensembleDeNiveau = Loader.charger_ensemble_de_niveaux(chemin);
		niveau = 1;
		Coeur.running = true;
	}

	public static void prochainNiveau() {

	}

	public static void niveauPrecedent() {

	}

	public static void resetNiveau() {

	}

	public static void chargerNiveau() {

	}

	public static void tick() {
		gererNiveau.tick();
	}
}
