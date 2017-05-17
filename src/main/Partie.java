package main;

import java.awt.BorderLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ia.Ia;
import ia.IaDirective;
import ia.IaEvolue;
import ia.IaRandom;
import loader.EnsembleDeNiveaux;
import loader.Loader;
import outils.Score;
import outils.SonToolKit;
import vue.FinPanel;
import vue.GraphiqueConsole;
import vue.JeuPanel;
import vue.ScorePanel;

public class Partie {
	public static final List<Integer> SCORES = new ArrayList<Integer>();
	public static String cheminFichier;
	public static boolean finiEvolution;
	public static EnsembleDeNiveaux ensembleDeNiveau;
	public static int niveau;
	public static GererNiveau gererNiveau;
	public static boolean tousLesNiveaux;
	public static boolean IA;
	public static Ia ia;
	public static boolean lecture, simulation;
	public static String parcours;
	public static SonToolKit sons = new SonToolKit();
	static DateFormat df = new SimpleDateFormat("dd:MM:yyyy_HH:mm:ss");
	static Date today = Calendar.getInstance().getTime();
	static String dateDebut = df.format(today);

	public static void commencerPartie(String chemin) {
		ensembleDeNiveau = Loader.charger_ensemble_de_niveaux(chemin);
		tousLesNiveaux = true;
		niveau = 1;
		lancerNiveau();
	}

	public static void commencerPartie(String chemin, int niveau) {
		ensembleDeNiveau = Loader.charger_ensemble_de_niveaux(chemin);
		tousLesNiveaux = false;
		Partie.niveau = niveau;
		lancerNiveau();
	}

	public static void jouerFichier(String cheminFichierBDCFF, int niveau, String parcours) {
		ensembleDeNiveau = Loader.charger_ensemble_de_niveaux(cheminFichierBDCFF);
		cheminFichier = cheminFichierBDCFF;
		tousLesNiveaux = false;
		Partie.niveau = niveau;
		lecture = true;
		Partie.parcours = parcours;
		lancerNiveau();
	}

	public static Score jouerFichierScore(String chemin, int niveau, String parcours) {
		ensembleDeNiveau = Loader.charger_ensemble_de_niveaux(chemin);
		cheminFichier = chemin;
		tousLesNiveaux = false;
		Partie.niveau = niveau;
		lecture = true;
		Partie.parcours = parcours;
		String parcours2 = parcours;
		gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());
		Score s;
		String parcoursParcouru = "";
		while (parcours.length() > 0 && !gererNiveau.isDemandeReset() && !gererNiveau.isDemandeFin()) {
			char direction = parcours.charAt(0);

			parcours = parcours.substring(1, parcours.length());
			parcoursParcouru += direction;
			if (gererNiveau.tickLecture(direction) || gererNiveau.getNiveau().getRockford().isMort()) {

				break;
			}
		}

		s = new Score(gererNiveau.getScore(), parcoursParcouru.length());
		s.setChemin(parcours2);
		if (gererNiveau.isDemandeFin()) {
			s.setFini(true);
		} else {
			s.setFini(false);
		}
		return s;
	}

	public static void resetNiveau() {
		if (IA) {
			gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());
			if (ia != null)
				ia.reset();
		} else if (lecture) {
			System.out.println("Mort de Rockford. Mauvais parcours. Fin du Programme.");
		} else {
			Coeur.running = false;
			lancerNiveau();
		}

	}

	public static Score calculerStrategie(String strategie, String cheminFichierBDCFF, int niveau) {
		IA = true;

		ensembleDeNiveau = Loader.charger_ensemble_de_niveaux(cheminFichierBDCFF);
		Partie.niveau = niveau;
		gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());
		if (strategie.equals("-simplet")) {
			ia = new IaRandom();
		} else if (strategie.equals("-directif")) {
			ia = new IaDirective();
		}

		if (ia != null) {
			while (!gererNiveau.tickIa(ia))
				;
		}

		Score score = new Score(gererNiveau.getScore(), gererNiveau.getCompteurTicks());
		return score;
	}

	public static Score calculerStrategieEvolue(String strategie, int nbGenerations, String cheminFichierBDCFF,
			int niveau) {
		IA = true;
		Partie.cheminFichier = cheminFichierBDCFF;
		ensembleDeNiveau = Loader.charger_ensemble_de_niveaux(cheminFichierBDCFF);
		Partie.niveau = niveau;
		gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());

		if (strategie.equals("-evolue")) {
			ia = new IaEvolue(nbGenerations);
		}
		Score score = ((IaEvolue) ia).debut();
		System.out.println(score.getChemin().substring(0, score.getParcours()));
		return score;
	}

	public static void finNiveau() {
		sons.stopAll();
		finiEvolution = true;
		Coeur.running = false;
		String essai = "Trajet : " + gererNiveau.getTrajet() + "\nScore : " + gererNiveau.getScore()
				+ "     Diamants : " + gererNiveau.getNbDiamants() + "      Temps : ";
		if (gererNiveau.isTourParTour() || IA) {
			essai += (gererNiveau.getCompteurTicks() + " tours\n");
		} else {
			essai += (((double) gererNiveau.getCompteurTicks()) / ((double) gererNiveau.getNiveau().getCaveDelay()))
					+ " secondes\n";
		}
		if (!lecture && !simulation)
			enregistrerEssai(essai);
		if (tousLesNiveaux) {
			SCORES.add(gererNiveau.getScore());
		}
		if (tousLesNiveaux && niveau < ensembleDeNiveau.getNombre_de_niveaux()) {
			if(!Coeur.graphique){
				ControleurConsole.prochainNiveau(niveau, gererNiveau.getScore());
			}
			niveau++;
			lancerNiveau();
		} else {
			fin();
		}
	}

	public static void fin() {
		Coeur.running = false;
		if (Coeur.graphique) {
			Coeur.FENETRE.getContentPane().removeAll();
			Coeur.FENETRE.getContentPane().add(new FinPanel());
			Coeur.FENETRE.getContentPane().validate();
			Coeur.FENETRE.repaint();
		} else if (!simulation) {
			if (tousLesNiveaux) {
				GraphiqueConsole.afficherScoreTousLesNiveaux(SCORES);

			} else {
				GraphiqueConsole.afficherScoreUnNiveau(niveau, gererNiveau.getScore());
			}
		}
	}

	public static void lancerNiveau() {
		if (gererNiveau != null)
			gererNiveau.stop();
		gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());
		Coeur.running = true;
		if (Coeur.graphique) {
			Coeur.setTicks((int) (ensembleDeNiveau.getNiveaux().get(niveau - 1).getCaveDelay()
					* Constantes.VITESSE_JEU_TEMPS_REEL));
			preparerFenetre();
		
		} else {
			Coeur.CONTROLEUR_CONSOLE.run(gererNiveau);
		}

	}

	public static void tick() {
		if (!Coeur.graphique) {
			GraphiqueConsole.afficher(gererNiveau.getNiveau());
		}
		if (lecture) {
			if (parcours.length() > 0) {
				gererNiveau.tickLecture(parcours.charAt(0));
				parcours = parcours.substring(1, parcours.length());
			} else {
				finiEvolution = true;
				if (!IA) {
					System.err
							.println("Chemin du fichier terminé or le niveau n'est pas fini, fermeture du programme.");
					System.exit(0);
				}
			}
		} else {
			gererNiveau.tick();
		}

	}

	public static void preparerFenetre() {
		Coeur.FENETRE.getContentPane().removeAll();
		Coeur.FENETRE.getContentPane().setLayout(new BorderLayout());
		Coeur.FENETRE.getContentPane().add(new JeuPanel(), BorderLayout.CENTER);
		Coeur.FENETRE.getContentPane().add(new ScorePanel(), BorderLayout.NORTH);
		Coeur.FENETRE.getContentPane().validate();
	}

	public static void enregistrerEssai(String essai) {
		String chemin = "";
		if (IA) {
			chemin += "Essais_IA/";
		} else {
			chemin += "Essais_Humain/";
		}
		chemin += dateDebut + "/";

		outils.Ecrivain.ecrire(essai, "Niveau_" + niveau + ".bd", chemin);
	}
}
