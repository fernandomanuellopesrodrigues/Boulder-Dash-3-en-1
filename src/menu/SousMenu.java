package menu;

import loader.Loader;
import main.Partie;

public class SousMenu {

	public static void lancerNiveau(String cheminFichierBDCFF, int niveau) {
		// on doit tester si le chemin et le niveau sont bien
	}

	public static void lancerTousLesNiveaux(String cheminFichierBDCFF) {
		Partie.commencerPartie(cheminFichierBDCFF);
	}

	public static void lireInfos(String cheminFichierBDCFF) {
		System.out.println(Loader.lireInfos(cheminFichierBDCFF));
	}

	public static void calculerStrategie(String strategie, String cheminFichierBDCFF, int niveau) {

	}

	public static void rejouerNiveau(String cheminFichierDASH, String cheminFichierBDCFF, int niveau) {

	}

	public static void simulerNiveau(int nombrePartie, String strategie1, String strategie2, String cheminFichierBDCFF,
			int niveau) {

	}
}
