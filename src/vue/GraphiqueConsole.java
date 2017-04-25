package vue;

import java.util.List;

import loader.Niveau;

public class GraphiqueConsole {

	public static void afficher(Niveau niveau) {

	}

	public static void afficherScoreUnNiveau(int niveau, int score) {
		System.out.println("FIN DU JEU , SCORE DU NIVEAU " + niveau + " : " + score + ".\n");
	}

	public static void afficherScoreTousLesNiveaux(List<Integer> scores) {
		System.out.println("FIN DU JEU , SCORE DES NIVEAUX : \n");
		for (int i = 0; i < scores.size(); i++) {
			System.out.println("                         Niveau " + (i + 1) + " : " + scores.get(i) + "\n");
		}
		System.out.println("\n");
	}
}
