package menu;

import loader.Loader;
import main.Partie;
import outils.Comparaison;
import outils.Ecrivain;
import outils.Score;

public class SousMenu {

	public static void lancerNiveau(String cheminFichierBDCFF, int niveau) {
		Partie.commencerPartie(cheminFichierBDCFF, niveau);
	}

	public static void lancerTousLesNiveaux(String cheminFichierBDCFF) {
		Partie.commencerPartie(cheminFichierBDCFF);
	}

	public static void lireInfos(String cheminFichierBDCFF) {
		System.out.println(Loader.lireInfos(cheminFichierBDCFF));
	}

	public static void calculerStrategie(String strategie, String cheminFichierBDCFF, int niveau) {
		System.out.println("Calcul en cours...");
		Partie.calculerStrategie(strategie, cheminFichierBDCFF, niveau);
	}public static void calculerStrategieEvol(String strategie,int nbGenerations, String cheminFichierBDCFF, int niveau) {
		System.out.println("Calcul en cours...");
		Partie.calculerStrategieEvolue(strategie, nbGenerations, cheminFichierBDCFF, niveau);
	}

	public static void rejouerNiveau(String cheminFichierDASH, String cheminFichierBDCFF, int niveau) {
		Partie.jouerFichier(cheminFichierBDCFF, niveau, Ecrivain.lireParcours(cheminFichierDASH));
	}

	public static void simulerNiveau(int nombrePartie, String strategie1, String strategie2, String cheminFichierBDCFF,
			int niveau) {
		Partie.simulation = true;
		System.out.println("Calcul en cours...");
		Comparaison comp1 = new Comparaison(nombrePartie);
		Comparaison comp2 = new Comparaison(nombrePartie);
		for (int i = 0; i < nombrePartie; i++) {
			Score s = Partie.calculerStrategie(strategie1, cheminFichierBDCFF, niveau);
			comp1.addScore(s.getScore(), s.getParcours());
			System.out.println((i + 1) + "/" + (nombrePartie * 2));
		}
		comp1.fin();
		for (int i = 0; i < nombrePartie; i++) {
			Score s = Partie.calculerStrategie(strategie2, cheminFichierBDCFF, niveau);
			comp2.addScore(s.getScore(), s.getParcours());
			System.out.println((nombrePartie + i + 1) + "/" + (nombrePartie * 2));
		}
		comp2.fin();
		System.out.println("\n" + strategie1 + " :\nDistance Moyenne : " + comp1.getDistanceMoyenne());
		System.out.println("Score Moyen : " + comp1.getScoreMoyen());
		System.out.println("Temps Moyen : " + comp1.getTempsMoyen() + " millisecondes.\n");

		System.out.println(strategie2 + " :\nDistance Moyenne : " + comp2.getDistanceMoyenne());
		System.out.println("Score Moyen : " + comp2.getScoreMoyen());
		System.out.println("Temps Moyen : " + comp2.getTempsMoyen() + " millisecondes.");
		System.exit(1);
	}
}
