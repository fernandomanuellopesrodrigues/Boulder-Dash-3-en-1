package ia;

import static main.Constantes.NOMBRE_DE_TRY_GENERATION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import entitees.abstraites.Entitee;
import entitees.tickables.Diamant;
import iaToolKit.Noeud;
import main.Constantes;
import main.Partie;
import outils.Score;

public class IaDirectiveEvolue extends Ia {

	private List<Score> liste = new ArrayList<>();
	private Score scoreActuel;
	private int nbGenerations;
	private int generationActuelle = 1;
	private int trysDeGeneration = NOMBRE_DE_TRY_GENERATION;
	private List<Score> liste2 = new ArrayList<>();

	public IaDirectiveEvolue(int nbGenerations) {
		this.nbGenerations = nbGenerations;

		for (int i = 0; i < NOMBRE_DE_TRY_GENERATION; i++) {
			Partie.finiEvolution = false;
			String chemin = "";
			IaDirective ia = new IaDirective();
			for (int j = 0; j < (Partie.gererNiveau.getNiveau().getCaveDelay()
					* Partie.gererNiveau.getNiveau().getCave_time() * Constantes.VITESSE_JEU_TEMPS_REEL); j++) {
				Partie.gererNiveau.tickIa(ia);
			}
			chemin = Partie.gererNiveau.getTrajet();
			System.out.println(chemin);
			Score s;
			s = Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, chemin);
			liste.add(s);
		}

		Collections.sort(liste);

	}

	@Override
	public char tick(Entitee[][] map) {
		if (generationActuelle == 1) {

		}
		return 'w';
	}

	@Override
	public void initialiserTry() {
		if (trysDeGeneration > 0) {
			trysDeGeneration--;
		} else {
			trysDeGeneration = NOMBRE_DE_TRY_GENERATION;
			if (generationActuelle > nbGenerations) {
				Partie.gererNiveau.setTrajet(liste.get(0).getChemin());
				Partie.gererNiveau.setScore(liste.get(0).getScore());
				Partie.finNiveau();
			}
			generationActuelle++;
		}
	}

	public Score debut() {
		Score aReturn;
		while (!critereArret()) {
			generationActuelle++;
			for (int i = 0; i < (Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_SURVIVANTS)
					/ 100; i++) {
				liste2.add(liste.get(i));
			}
			for (int i = 0; i < (Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_ALEATOIRE)
					/ 100; i++) {
				Partie.finiEvolution = false;
				String chemin = "";
				for (int j = 0; j < (Partie.gererNiveau.getNiveau().getCaveDelay()
						* Partie.gererNiveau.getNiveau().getCave_time() * Constantes.VITESSE_JEU_TEMPS_REEL); j++) {
					chemin += Ia.directionRandom();
				}
				Score s;
				s = Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, chemin);
				System.out.println(s.getScore());
				liste2.add(s);
			}
			for (int i = 0; i < (Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_MUTATIONS)
					/ 100; i++) {
				int rng = (int) (Math.random()
						* ((Constantes.POURCENTAGE_DES_SELECTIONNES) * Constantes.NOMBRE_DE_TRY_GENERATION) / 100.0);
				Score s = liste.get(rng);
				char rng3 = Ia.directionRandom();
				s.setChemin(s.getChemin().substring(0, s.getParcours() - 1) + rng3
						+ s.getChemin().substring(s.getParcours(), s.getChemin().length()));
				s = Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, s.getChemin());
				liste2.add(s);
			}
			for (int i = 0; i < (Constantes.NOMBRE_DE_TRY_GENERATION
					- ((Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_SURVIVANTS) / 100)
					- ((Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_ALEATOIRE) / 100)
					- ((Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_MUTATIONS) / 100)); i++) {
				int rng = (int) (Math.random()
						* ((Constantes.POURCENTAGE_DES_SELECTIONNES) * Constantes.NOMBRE_DE_TRY_GENERATION) / 100.0);
				int rng2 = (int) (Math.random()
						* ((Constantes.POURCENTAGE_DES_SELECTIONNES) * Constantes.NOMBRE_DE_TRY_GENERATION) / 100.0);
				int tailleDuPLusPetit = liste.get(rng).getParcours() > liste.get(rng2).getParcours()
						? liste.get(rng2).getParcours() : liste.get(rng).getParcours();
				int rng3 = (int) (Math.random() * (tailleDuPLusPetit - 2));
				Score score1 = liste.get(rng);
				Score score2 = liste.get(rng2);

				String s = score1.getChemin().substring(0, rng3)
						+ score2.getChemin().substring(rng3 + 1, score2.getChemin().length());
				Score scoreee = Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, s);
				liste2.add(scoreee);
			}
			liste.clear();
			for (int i = 0; i < liste2.size(); i++) {
				liste.add(liste2.get(i));
			}
			liste2.clear();
			Collections.sort(liste);
			for (Score sc : liste) {
				System.out.println(sc.getParcours() + " ,,, " + sc.getScore());
			}
			System.out.println("\n\n\n");
		}

		aReturn = liste.get(0);
		return aReturn;
	}

	public boolean critereArret() {
		if (generationActuelle > nbGenerations) {
			return true;
		} else {
			return false;
		}
	}

	public void ajouterScore() {
		scoreActuel = new Score(Partie.gererNiveau.getScore(), Partie.gererNiveau.getTrajet().length(),
				Partie.gererNiveau.getListeDiamants());
		scoreActuel.setChemin(Partie.parcours);
		liste.add(scoreActuel);
		Collections.sort(liste);
	}

	public int getNbGenerations() {
		return nbGenerations;
	}

	public int getGenerationActuelle() {
		return generationActuelle;
	}

}