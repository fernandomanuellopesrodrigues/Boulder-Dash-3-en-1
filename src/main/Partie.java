package main;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import loader.EnsembleDeNiveaux;
import loader.Loader;
import vue.FinPanel;
import vue.GraphiqueConsole;
import vue.JeuPanel;
import vue.ScorePanel;

public class Partie {
	public static final List<Integer> SCORES = new ArrayList<Integer>();
	public static EnsembleDeNiveaux ensembleDeNiveau;
	public static int niveau;
	public static GererNiveau gererNiveau;
	public static boolean tousLesNiveaux;

	public static void commencerPartie(String chemin) {
		ensembleDeNiveau = Loader.charger_ensemble_de_niveaux(chemin);
		tousLesNiveaux = true;
		niveau = 1;
		lancerNiveau();
	}

	public static void finNiveau() {
		Coeur.running = false;
		if (tousLesNiveaux) {
			SCORES.add(gererNiveau.getScore());
		}
		if (tousLesNiveaux && niveau <= ensembleDeNiveau.getNombre_de_niveaux()) {
			niveau++;
			lancerNiveau();
		} else {
			fin();
		}
	}

	public static void fin() {
		if (Coeur.graphique) {
			Coeur.FENETRE.getContentPane().removeAll();
			Coeur.FENETRE.getContentPane().add(new FinPanel());
			Coeur.FENETRE.getContentPane().validate();
			Coeur.FENETRE.repaint();
		} else {
			if (tousLesNiveaux) {
				GraphiqueConsole.afficherScoreTousLesNiveaux(SCORES);

			} else {
				GraphiqueConsole.afficherScoreUnNiveau(niveau, gererNiveau.getScore());
			}
		}
	}

	public static void lancerNiveau(int niveau) {
		Partie.niveau=niveau;
		lancerNiveau();
	}

	public static void lancerNiveau() {
		gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau + 1).clone());
		Coeur.setTicks((ensembleDeNiveau.getNiveaux().get(niveau + 1).getCaveDelay()));
		if (Coeur.graphique) {
			preparerFenetre();
		}
		Coeur.running = true;
	}

	public static void resetNiveau() {
		Coeur.running = false;
		lancerNiveau();
	}

	public static void tick() {
		if (!Coeur.graphique) {
			GraphiqueConsole.afficher(gererNiveau.getNiveau());
		}
		gererNiveau.tick();
	}

	public static void preparerFenetre() {
		Coeur.FENETRE.getContentPane().removeAll();
		Coeur.FENETRE.getContentPane().setLayout(new BorderLayout());
		Coeur.FENETRE.getContentPane().add(new JeuPanel(), BorderLayout.CENTER);
		Coeur.FENETRE.getContentPane().add(new ScorePanel(), BorderLayout.NORTH);
		Coeur.FENETRE.getContentPane().validate();
	}
}
