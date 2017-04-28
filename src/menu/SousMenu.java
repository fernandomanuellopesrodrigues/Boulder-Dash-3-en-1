package menu;

import loader.Loader;
import main.Partie;
import outils.Ecrivain;

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

	}

	public static void rejouerNiveau(String cheminFichierDASH, String cheminFichierBDCFF, int niveau) {
		Partie.jouerFichier(cheminFichierBDCFF,niveau,Ecrivain.lireParcours(cheminFichierDASH));
	}

	public static void simulerNiveau(int nombrePartie, String strategie1, String strategie2, String cheminFichierBDCFF,
			int niveau) {

	}
}
