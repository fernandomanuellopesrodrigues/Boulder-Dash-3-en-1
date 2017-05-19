package menu;

import loader.Loader;
import main.Partie;
import outils.Comparaison;
import outils.Ecrivain;
import outils.Score;

/***
 * La classe SousMenu n'est jamais instanciée. contient des méthodes statics
 * gérant le lancement et la direction que doit prendre le programme au
 * lancement.
 * 
 * Elle lance des méthodes de la classe {@link Partie}.
 * 
 * @author Murloc
 *
 */
public class SousMenu {

	/**
	 * Lance une partie d'un seul niveau.
	 * 
	 * @param cheminFichierBDCFF
	 *            Le chemin du fichier BDCFF où se trouve le niveau.
	 * @param niveau
	 *            Le numéro du niveau voulu.
	 */
	public static void lancerNiveau(String cheminFichierBDCFF, int niveau) {
		Partie.commencerPartie(cheminFichierBDCFF, niveau);
	}

	/**
	 * Lance une partie de tous les niveaux du fichier BDCFF.
	 * 
	 * @param cheminFichierBDCFF
	 *            Le chemin du fichier BDCFF où se trouve le niveau.
	 */
	public static void lancerTousLesNiveaux(String cheminFichierBDCFF) {
		Partie.commencerPartie(cheminFichierBDCFF);
	}

	/**
	 * Affiche sur la console les infos d'un fichier BDCFF passé en paramètre.
	 * 
	 * @param cheminFichierBDCFF
	 *            Le chemin du fichier BDCFF où se trouve le niveau.
	 */
	public static void lireInfos(String cheminFichierBDCFF) {
		System.out.println(Loader.lireInfos(cheminFichierBDCFF));
	}

	/**
	 * Crée un fichier DASH contenant le résultat du meilleur essai parmis les
	 * stratégies tentés (Seulement les stratégies non évolutives ici).
	 * 
	 * @param strategie
	 *            La stratégie voulue.
	 * @param cheminFichierBDCFF
	 *            Le chemin du fichier BDCFF où se trouve le niveau.
	 * @param niveau
	 *            Le numéro du niveau voulu.
	 */
	public static void calculerStrategie(String strategie, String cheminFichierBDCFF, int niveau) {
		System.out.println("Calcul en cours...");
		Partie.calculerStrategie(strategie, cheminFichierBDCFF, niveau);
	}

	/**
	 * 
	 * Crée un fichier DASH contenant le résultat du meilleur essai parmis les
	 * stratégies tentés (Seulement les stratégies évolutives ici).
	 * 
	 * @param strategie
	 *            La stratégie voulue.
	 * @param nbGenerations
	 * @param cheminFichierBDCFF
	 *            Le chemin du fichier BDCFF où se trouve le niveau.
	 * @param niveau
	 *            Le numéro du niveau voulu.
	 */
	public static void calculerStrategieEvol(String strategie, int nbGenerations, String cheminFichierBDCFF,
			int niveau) {
		System.out.println("Calcul en cours...");
		Partie.calculerStrategieEvolue(strategie, nbGenerations, cheminFichierBDCFF, niveau);
	}

	/**
	 * Rejoue une partie d'un niveau en jouant les déplacements se trouvant dans
	 * le fichier DASH passé en paramètre.
	 * 
	 * @param cheminFichierDASH
	 *            Le chemin du fichier DASH.
	 * @param cheminFichierBDCFF
	 *            Le chemin du fichier BDCFF où se trouve le niveau.
	 * @param niveau
	 *            Le numéro du niveau voulu.
	 */
	public static void rejouerNiveau(String cheminFichierDASH, String cheminFichierBDCFF, int niveau) {
		Partie.jouerFichier(cheminFichierBDCFF, niveau, Ecrivain.lireParcours(cheminFichierDASH));
	}

	/**
	 * Affiche les meilleurs résultats des deux stratégies voulues.
	 * 
	 * @param nombrePartie
	 *            Le nombre de partie voulues.
	 * @param strategie1
	 *            La stratégie 1 voulue.
	 * @param strategie2
	 *            La stratégie 2 voulue.
	 * @param cheminFichierBDCFF
	 *            Le chemin du fichier BDCFF où se trouve le niveau.
	 * @param niveau
	 *            Le numéro du niveau voulu.
	 */
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
