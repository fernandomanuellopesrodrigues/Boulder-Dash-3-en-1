package menu;

import static constantes.Constantes.*;

import java.util.Scanner;

import main.Coeur;

public class Menu {

	public static void main(String[] args) {
		if (args.length > 0) {
			if (args[0].equals("-name")) {
				System.out.println(NOMS);
			} else if (args[0].equals("-h")) {
				System.out.println(HELP);
			} else if (args[0].equals("-lis")) {
				lire(args);
			} else if (args[0].equals("-joue")) {
				jouer(args);
			} else if (args[0].equals("-cal")) {
				calculer(args);
			} else if (args[0].equals("-rejoue")) {
				rejouer(args);
			} else if (args[0].equals("-simul")) {
				simuler(args);
			} else {
				System.err.println("Argument(s) non reconnu(s).");
			}
		} else {
			System.err.println("Argument(s) manquant(s), entrez l'argument -h pour optenir de l'aide.");
		}

	}

	public static void lire(String[] args) {
		if (args.length == 2) {
			SousMenu.lireInfos(args[1]);
		} else {
			System.err.println("Argument(s) invalide(s).");
		}
	}

	public static void jouer(String[] args) {
		modeGraphique();
		if (args.length == 2) {
			SousMenu.lancerTousLesNiveaux(args[1]);
		} else if (args.length == 4 && args[2].equals("-niveau.")) {
			int niveau = -1;
			try {
				niveau = Integer.valueOf(args[3]);
			} catch (Exception e) {
				System.err.println("Le 4eme argument doit �tre un entier.");
			}
			if (niveau > -1) {
				SousMenu.lancerNiveau(args[1], niveau);
			}
		} else {
			System.err.println("Argument(s) invalide(s).");
		}
	}

	public static void calculer(String[] args) {
		if (args.length == 5) {
			int niveau = -1;
			try {
				niveau = Integer.valueOf(args[4]);
			} catch (Exception e) {
				System.err.println("Le 5eme argument doit �tre un entier.");
			}
			if (niveau > -1) {
				SousMenu.calculerStrategie(args[1], args[2], niveau);
			}
		} else {
			System.err.println("Argument(s) invalide(s).");
		}
	}

	public static void rejouer(String[] args) {
		modeGraphique();
		if (args.length == 5) {
			int niveau = -1;
			try {
				niveau = Integer.valueOf(args[4]);
			} catch (Exception e) {
				System.err.println("Le 5eme argument doit �tre un entier.");
			}
			if (niveau > -1) {
				SousMenu.rejouerNiveau(args[1], args[2], niveau);
			}
		} else {
			System.err.println("Argument(s) invalide(s).");
		}
	}

	public static void simuler(String[] args) {
		if (args.length == 7) {
			int niveau = -1;
			int nombrePartie = -1;
			try {
				niveau = Integer.valueOf(args[6]);
				nombrePartie = Integer.valueOf(args[1]);
			} catch (Exception e) {
				System.err.println("Le 5eme argument doit �tre un entier.");
			}
			if (niveau > -1 && nombrePartie > 0) {
				SousMenu.simulerNiveau(nombrePartie, args[2], args[3], args[4], niveau);
			}
		} else {
			System.err.println("Argument(s) invalide(s).");
		}
	}

	public static void modeGraphique() {

		Scanner sc;
		String reponse = "a";
		do {
			System.out.println("Voulez-vous activer le mode graphique ? (O/N)");
			sc = new Scanner(System.in);
			reponse = sc.nextLine().toUpperCase();
		} while (!reponse.equals("O") && !reponse.equals("N"));
		if (reponse.equals("O")) {
			Coeur.graphique = true;
			Coeur.FENETRE.setVisible(true);
		} else {
			Coeur.graphique = false;
			Coeur.FENETRE.setVisible(false);
		}
	}
}
