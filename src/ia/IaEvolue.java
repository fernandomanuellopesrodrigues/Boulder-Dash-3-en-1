package ia;

import static main.Constantes.NOMBRE_DE_TRY_GENERATION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.abstraites.Entitee;
import main.Constantes;
import main.Partie;
import outils.Paire;
import outils.Score;

public class IaEvolue extends Ia {

	private List<Score> liste = new ArrayList<>();
	private Score scoreActuel;
	private int nbGenerations;
	private int generationActuelle = 1;
	private int trysDeGeneration = NOMBRE_DE_TRY_GENERATION;
	private List<Score> liste2 = new ArrayList<>();
	private double tailleCheminMaximale;
	private int nbObjectifs;

	public IaEvolue(int nbGenerations) {
		this.nbGenerations = nbGenerations;
		this.nbObjectifs = Partie.gererNiveau.getNiveau().getDiamonds_required() + 1;
		this.tailleCheminMaximale = Partie.gererNiveau.getNiveau().getCaveDelay()
				* Partie.gererNiveau.getNiveau().getCave_time() * Constantes.VITESSE_JEU_TEMPS_REEL;
		for (int i = 0; i < NOMBRE_DE_TRY_GENERATION; i++) {
			Partie.finiEvolution = false;
			String chemin = "";
			for (int j = 0; j < (tailleCheminMaximale); j++) {
				chemin += Ia.directionRandom();
			}
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
			System.out.println("Génération " + generationActuelle + "/" + nbGenerations);
			generationActuelle++;
			for (int i = 0; i < (Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_SURVIVANTS)
					/ 100; i++) {
				liste2.add(liste.get(i));
			}
			for (int i = 0; i < (Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_ALEATOIRE)
					/ 100; i++) {
				Partie.finiEvolution = false;
				String chemin = "";
				for (int j = 0; j < (tailleCheminMaximale); j++) {
					chemin += Ia.directionRandom();
				}
				Score s;
				s = Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, chemin);
				liste2.add(s);
			}
			for (int i = 0; i < (Constantes.NOMBRE_DE_TRY_GENERATION
					- ((Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_SURVIVANTS) / 100)
					- ((Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_ALEATOIRE) / 100)); i++) {

				int rng = (int) (Math.random()
						* (Constantes.POURCENTAGE_DES_SELECTIONNES * Constantes.NOMBRE_DE_TRY_GENERATION) / 100.0);
				Score s = liste.get(rng);

				int tailleMutation = 1;

				String finChemin = "";
				for (int j = s.getParcours() - tailleMutation; j < s.getChemin().length(); j++) {
					finChemin += Ia.directionRandom();
				}

				s.setChemin(s.getChemin().substring(0, s.getParcours() - tailleMutation) + finChemin);

				s = Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, s.getChemin());
				liste2.add(s);
			}
			liste.clear();
			for (int i = 0; i < liste2.size(); i++) {
				liste.add(liste2.get(i));
			}
			liste2.clear();
			Collections.sort(liste);

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

	public Score ajouterScore() {
		scoreActuel = new Score(Partie.gererNiveau.getScore(), Partie.gererNiveau.getTrajet().length(),
				Partie.gererNiveau.getListeDiamants());
		scoreActuel.setChemin(Partie.parcours);
		liste.add(scoreActuel);
		Collections.sort(liste);
		return scoreActuel;
	}

	public Score ajouterScore(Score s) {
		scoreActuel = s;
		scoreActuel.setChemin(Partie.parcours);
		liste.add(scoreActuel);
		Collections.sort(liste);
		return scoreActuel;
	}

	public int getNbGenerations() {
		return nbGenerations;
	}

	public int getGenerationActuelle() {
		return generationActuelle;
	}

	public int getNbObjectifs() {
		return nbObjectifs;
	}

}
