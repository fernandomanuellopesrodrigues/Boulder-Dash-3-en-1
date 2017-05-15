package loader;

import java.io.BufferedReader;
import java.io.FileReader;

import entitees.abstraites.Entitee;
import entitees.fixes.Amibe;
import entitees.fixes.Mur;
import entitees.fixes.MurEnTitane;
import entitees.fixes.MurMagique;
import entitees.fixes.Poussiere;
import entitees.fixes.Sortie;
import entitees.fixes.Vide;
import entitees.tickables.Diamant;
import entitees.tickables.Libellule;
import entitees.tickables.Luciole;
import entitees.tickables.Pierre;
import entitees.tickables.Rockford;

public abstract class Loader {

	public static EnsembleDeNiveaux charger_ensemble_de_niveaux(String chemin) {

		EnsembleDeNiveaux ensemble;
		String[] niveaux;

		try {
			// lecture du fichier
			FileReader lecteur = new FileReader(chemin);
			BufferedReader in = new BufferedReader(lecteur);

			// r�cup�ration du fichier
			String ensemble_du_fichier = "";
			String s;
			while ((s = in.readLine()) != null) {
				ensemble_du_fichier += "\n" + s;
			}
			in.close();
			ensemble_du_fichier = ensemble_du_fichier.replace("-", "");
			ensemble_du_fichier = ensemble_du_fichier.replace("[cave]", "-[cave]");

			niveaux = ensemble_du_fichier.split("-");

			// r�cup�ration des infromations de l'ensemble de niveaux
			String ensemble_de_niveaux_informations = niveaux[0];
			int nombre_de_niveaux = Integer.valueOf(getStr(ensemble_de_niveaux_informations, "Caves=", "\n"));
			ensemble = new EnsembleDeNiveaux(nombre_de_niveaux);
			// r�cup�ration des niveaux
			for (int i = 1; i <= ensemble.getNombre_de_niveaux(); i++) {
				Niveau niveau = charger_niveau(niveaux[i]);
				if (!niveau.equals(null))
					ensemble.ajouterNiveau(niveau);
			}

			ensemble.setNbNiveaux(ensemble.getNiveaux().size());
			return ensemble;
		} catch (Exception e) {
			System.err.println("Impossible de charger les niveaux.");
			return null;
		}

	}

	private static Niveau charger_niveau(String niveau) {
		String diamond;

		try {
			diamond = getStr(niveau, "DiamondValue=", "\n");
		} catch (Exception e) {
			diamond = "";
		}
		String map_string;
		try {
			map_string = getStr(niveau, "[map]\n", "[/map]");
		} catch (Exception e) {
			return null;
		}
		int caveDelay;
		try {
			caveDelay = Integer.valueOf(getStr(niveau, "CaveDelay=", "\n"));
			if (caveDelay == 0) {
				caveDelay = 1;
			}
		} catch (Exception e) {
			caveDelay = 7;
		}
		int cave_time;
		try {
			cave_time = Integer.valueOf(getStr(niveau, "CaveTime=", "\n"));
		} catch (Exception e) {
			cave_time = 1000;
		}
		int diamonds_required;
		try {
			diamonds_required = Integer.valueOf(getStr(niveau, "DiamondsRequired=", "\n"));
		} catch (Exception e) {
			diamonds_required = 0;
		}
		int diamond_value;
		try {
			diamond_value = Integer.valueOf(getStr(niveau, "DiamondValue=", " "));
		} catch (Exception e) {
			diamond_value = Integer.valueOf(getStr(niveau, "DiamondValue=", "\n"));
		}

		int diamond_value_bonus;
		try {
			diamond_value_bonus = Integer.valueOf(getStr(diamond, " "));
		} catch (Exception e) {
			diamond_value_bonus = 0;
		}
		int amoeba_time;
		try {
			amoeba_time = Integer.valueOf(getStr(niveau, "AmoebaTime=", "\n"));
		} catch (Exception e) {
			amoeba_time = -1;
		}
		int magic_wall_time;
		try {
			magic_wall_time = Integer.valueOf(getStr(niveau, "MagicWallTime=", "\n"));
		} catch (Exception e) {
			magic_wall_time = -1;
		}

		String[] lignes = map_string.split("\n");
		boolean sortieCacher;
		if (diamonds_required <= 0) {
			sortieCacher = false;
		} else {
			sortieCacher = true;
		}
		int mapHauteur = lignes.length;
		int mapLongueur = lignes[1].length();
		Entitee[][] map = new Entitee[mapLongueur][mapHauteur];
		for (int j = 0; j < mapHauteur; j++) {
			char[] ligne = lignes[j].toCharArray();
			for (int i = 0; i < mapLongueur; i++) {
				map[i][j] = creerEntitee(ligne[i], i, j, magic_wall_time, sortieCacher);
			}
		}
		return new Niveau(map, caveDelay, cave_time, diamonds_required, diamond_value, diamond_value_bonus, amoeba_time,
				magic_wall_time);
	}

	public static String lireInfos(String chemin) {
		EnsembleDeNiveaux ensemble;
		String[] niveaux;

		try {
			// lecture du fichier
			FileReader lecteur = new FileReader(chemin);
			BufferedReader in = new BufferedReader(lecteur);

			// r�cup�ration du fichier
			String ensemble_du_fichier = "";
			String s;
			while ((s = in.readLine()) != null) {
				ensemble_du_fichier += "\n" + s;
			}
			in.close();
			ensemble_du_fichier = ensemble_du_fichier.replace("-", "");
			ensemble_du_fichier = ensemble_du_fichier.replace("[cave]", "-[cave]");

			niveaux = ensemble_du_fichier.split("-");

			// r�cup�ration des infromations de l'ensemble de niveaux
			String ensemble_de_niveaux_informations = niveaux[0];
			return ensemble_de_niveaux_informations;
		} catch (Exception e) {
			System.err.println("Impossible de charger le niveau");
			return "\n";
		}
	}

	private static Entitee creerEntitee(char car, int x, int y, int magicWallTime, boolean sortieCacher) {
		if (car == 'w') {
			return new Mur(x, y);
		} else if (car == 'd') {
			return new Diamant(x, y);
		} else if (car == 'a') {
			return new Amibe(x, y);
		} else if (car == 'q' || car == 'Q' || car == 'F' || car == 'o' || car == 'O') {
			return new Luciole(x, y);
		} else if (car == 'b' || car == 'B' || car == 'c' || car == 'C') {
			return new Libellule(x, y);
		} else if (car == 'W') {
			return new MurEnTitane(x, y);
		} else if (car == 'r') {
			return new Pierre(x, y);
		} else if (car == '.') {
			return new Poussiere(x, y);
		} else if (car == 'P') {
			return new Rockford(x, y);
		} else if (car == 'X') {
			return new Sortie(x, y);
		} else if (car == 'M') {
			return new MurMagique(x, y, magicWallTime);
		} else if (car == ' ') {
			return new Vide(x, y);
		} else {
			return new Diamant(x, y);
		}
	}

	private static String getStr(String texte, String string1, String string2) {
		String s = getStr(texte, string1);
		return s.substring(s.indexOf(string1) + 1, s.indexOf(string2));
	}

	private static String getStr(String texte, String string1) {
		return texte.substring(texte.indexOf(string1) + string1.length());
	}

}
