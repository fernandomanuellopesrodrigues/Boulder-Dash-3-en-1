package ia;

import static constantes.Constantes.NOMBRE_DE_TRY_GENERATION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.abstraites.Entitee;
import main.Partie;
import outils.Score;

public class IaEvolue extends Ia {

	private List<Score> liste = new ArrayList<>();
	private Score scoreActuel;
	private int nbGenerations;
	private int generationActuelle = 1;
	private int trysDeGeneration = NOMBRE_DE_TRY_GENERATION;
	private List<Score> liste2 = new ArrayList<>();
	private List<Score> liste3 = new ArrayList<>();

	public IaEvolue(int nbGenerations) {
		this.nbGenerations = nbGenerations;
	
		for (int i = 0; i < NOMBRE_DE_TRY_GENERATION; i++) {
			Partie.finiEvolution = false;
			String chemin = "";
			for (int j = 0; j < (Partie.gererNiveau.getNiveau().getCaveDelay()
					* Partie.gererNiveau.getNiveau().getCave_time()); j++) {
				chemin += Ia.tickStatic();
			}
			Score s;
			
			s= Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, chemin);
			liste.add(s);
			
		}
		Collections.sort(liste);
		for (Score s : liste) {
			System.out.println(s.getParcours());
		}
		
	}

	@Override
	public char tick(Entitee[][] map) {
		if (generationActuelle == 1) {
			IaRandom ia = new IaRandom();
			return ia.direction(map);
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

	public void ajouterScore() {
		scoreActuel = new Score(Partie.gererNiveau.getScore(), Partie.gererNiveau.getTrajet().length());
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
