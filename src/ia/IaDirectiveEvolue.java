package ia;

import static main.Constantes.NOMBRE_DE_TRY_GENERATION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import entitees.abstraites.Entitee;
import entitees.fixes.Sortie;
import entitees.tickables.Diamant;
import entitees.tickables.Rockford;
import iaToolKit.Noeud;
import main.Constantes;
import main.GererNiveau;
import main.Partie;
import outils.Score;

public class IaDirectiveEvolue extends Ia {

	private List<Score> liste = new ArrayList<>();
	private Score scoreActuel;
	private int nbGenerations;
	private int generationActuelle = 1;
	private int trysDeGeneration = NOMBRE_DE_TRY_GENERATION;
	private List<Score> liste2 = new ArrayList<>();
	private double tailleCheminMaximale;
	private int nbObjectifs;

	
	public IaDirectiveEvolue(int nbGenerations) {
		this.nbGenerations = nbGenerations;
		String chemin;
		for (int i = 0; i < NOMBRE_DE_TRY_GENERATION; i++) {
			Partie.gererNiveau = new GererNiveau(Partie.ensembleDeNiveau.getNiveaux().get(Partie.niveau - 1).clone());
			Partie.finiEvolution = false;
			chemin = "";
			GererNiveau g = Partie.gererNiveau;
			IaDirective ia = new IaDirective();
			for (int j = 0; j < (Partie.gererNiveau.getNiveau().getCaveDelay() * Partie.gererNiveau.getNiveau().getCave_time()
					* Constantes.VITESSE_JEU_TEMPS_REEL); j++) {
				if (g.isDemandeReset() || g.isDemandeFin()) {
					while (g.getTrajet().length() + chemin.length() <= (Partie.gererNiveau.getNiveau().getCaveDelay() * Partie.gererNiveau.getNiveau().getCave_time()
							* Constantes.VITESSE_JEU_TEMPS_REEL)) {
						chemin += Ia.directionRandom();
					}
				} else {
					g.tickIaDirevol(ia);
				}
			}

			chemin = g.getTrajet() + chemin;
			System.out.println("ok");
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
			for (int i = 0; i < (Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_SURVIVANTS) / 100; i++) {
				liste2.add(liste.get(i));
			}
			for (int i = 0; i < (Constantes.NOMBRE_DE_TRY_GENERATION * Constantes.POURCENTAGE_DE_ALEATOIRE) / 100; i++) {
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
				System.out.println("debut mutation");
				int rng = (int) (Math.random() * (Constantes.POURCENTAGE_DES_SELECTIONNES * Constantes.NOMBRE_DE_TRY_GENERATION) / 100.0);
				Score s = liste.get(rng);

				Partie.gererNiveau = new GererNiveau(Partie.ensembleDeNiveau.getNiveaux().get(Partie.niveau - 1).clone());
				int tailleMutation = 2;
				String chemin = "";
				GererNiveau g = Partie.gererNiveau;				
				IaDirective ia = new IaDirective();
				for(int j = 0; j< s.getParcours() - tailleMutation; j++){
					g.tickIaController(ia, s.getChemin().charAt(j));
					//System.out.println(g.getNiveau().getRockford().getX() + " ; " + g.getNiveau().getRockford().getY());
				}
				//System.out.println("   ");
				for (int j = s.getParcours() - tailleMutation; j < s.getParcours(); j++) {
					char c = Ia.directionRandom();
					g.tickIaController(ia, c);
					//System.out.print(c);
				}
				//System.out.println(finChemin);
				for (int j = s.getParcours(); j < s.getChemin().length(); j++) {
					// g = Partie.gererNiveau;
					if (g.isDemandeReset() || g.isDemandeFin()) {
						while (g.getTrajet().length() + chemin.length() <= (Partie.gererNiveau.getNiveau().getCaveDelay() * Partie.gererNiveau.getNiveau().getCave_time()
								* Constantes.VITESSE_JEU_TEMPS_REEL)) {
							chemin += Ia.directionRandom();
						}
					} else {
						g.tickIaDirevol(ia);
					}
				}
				chemin = g.getTrajet() + chemin;
				System.out.println(s.getChemin());
				s.setChemin(chemin);
				//s.setChemin(s.getChemin().substring(0, s.getParcours() - tailleMutation) + finChemin);
				s = Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, chemin);
				liste2.add(s);
				/*
				 * if(s.getParcours() > (9/10) * s.getChemin().length()){
				 * tailleMutation += s.getParcours()/10; }
				 */
				// tailleMutation += s.getParcours()/5;

				/*
				 * String finChemin = ""; for (int j = s.getParcours() -
				 * tailleMutation; j < s.getChemin().length(); j++) { finChemin
				 * += Ia.directionRandom(); } // char rng3 =
				 * Ia.directionRandom(); s.setChemin(s.getChemin().substring(0,
				 * s.getParcours() - tailleMutation) + finChemin); s =
				 * Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau,
				 * s.getChemin()); liste2.add(s);
				 */
			}

			/*
			 * List<Paire<Integer, Long>> listeDiamants = s.getListeDiamants();
			 * while ((listeDiamants.size() > 1) &&
			 * listeDiamants.get(1).getLeft() - listeDiamants.get(0).getLeft() <
			 * (this.nbObjectifs / this.tailleCheminMaximale)) {
			 * listeDiamants.remove(0); } int debutMutation; if
			 * (listeDiamants.isEmpty()) { debutMutation = 0; } else {
			 * debutMutation = listeDiamants.get(0).getLeft(); }
			 * 
			 * String finChemin = ""; for (int j = debutMutation + 1; j <
			 * tailleCheminMaximale; j++) { finChemin += Ia.directionRandom(); }
			 * s.setChemin(s.getChemin().substring(0, debutMutation) +
			 * finChemin);
			 */

			// changements

			/*
			 * for (int i = 0; i < (Constantes.NOMBRE_DE_TRY_GENERATION -
			 * ((Constantes.NOMBRE_DE_TRY_GENERATION *
			 * Constantes.POURCENTAGE_DE_SURVIVANTS) / 100) -
			 * ((Constantes.NOMBRE_DE_TRY_GENERATION *
			 * Constantes.POURCENTAGE_DE_ALEATOIRE) / 100) -
			 * ((Constantes.NOMBRE_DE_TRY_GENERATION *
			 * Constantes.POURCENTAGE_DE_MUTATIONS) / 100)); i++) { int rng =
			 * (int) (Math.random() ((Constantes.POURCENTAGE_DES_SELECTIONNES) *
			 * Constantes.NOMBRE_DE_TRY_GENERATION) / 100.0); int rng2 = (int)
			 * (Math.random() ((Constantes.POURCENTAGE_DES_SELECTIONNES) *
			 * Constantes.NOMBRE_DE_TRY_GENERATION) / 100.0); int
			 * tailleDuPLusPetit = liste.get(rng).getParcours() >
			 * liste.get(rng2).getParcours() ? liste.get(rng2).getParcours() :
			 * liste.get(rng).getParcours(); int rng3 = (int) (Math.random() *
			 * (tailleDuPLusPetit - 2)); Score score1 = liste.get(rng); Score
			 * score2 = liste.get(rng2);
			 * 
			 * String s = score1.getChemin().substring(0, rng3) +
			 * score2.getChemin().substring(rng3 + 1,
			 * score2.getChemin().length()); Score scoreee =
			 * Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, s);
			 * liste2.add(scoreee); }
			 */
			liste.clear();
			for (int i = 0; i < liste2.size(); i++) {
				liste.add(liste2.get(i));
			}
			liste2.clear();
			Collections.sort(liste);
			for (Score s : liste) {
				System.out.println(s.getScore() + "     ....     " + s.moyenne2() + "     ....     " + s.getParcours());
			}
			System.out.println(generationActuelle + "/" + nbGenerations);
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
		scoreActuel = new Score(Partie.gererNiveau.getScore(), Partie.gererNiveau.getTrajet().length(), Partie.gererNiveau.getListeDiamants());
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

	public int getNbObjectifs() {
		return nbObjectifs;
	}

}