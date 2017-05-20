package ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entitees.abstraites.Entitee;
import main.Constantes;
import main.GererNiveau;
import main.Partie;
import outils.Score;

import static java.lang.Math.random;
import static java.lang.System.out;
import static main.Constantes.NOMBRE_DE_TRY_GENERATION;
import static main.Constantes.POURCENTAGE_DE_ALEATOIRE;
import static main.Constantes.POURCENTAGE_DE_SURVIVANTS;
import static main.Constantes.VITESSE_JEU_TEMPS_REEL;
import static main.Partie.ensembleDeNiveau;
import static main.Partie.finiEvolution;
import static main.Partie.gererNiveau;

public class IaDirectiveEvolue extends Ia {

    private final List<Score> scoreDirective = new ArrayList<>(10);
    private final List<Score> scoreAleatoire = new ArrayList<>(10);
    private final int nbGenerations;
    private int generationActuelle = 1;
    private int trysDeGeneration   = NOMBRE_DE_TRY_GENERATION;


    public IaDirectiveEvolue(final int nbGenerations) {
        this.nbGenerations = nbGenerations;
        computeScoresDirective();
    }

    @Override
    public char tick(Entitee[][] map) {
        return 'w';
    }

    @Override
    public void initialiserTry() {
        if (trysDeGeneration > 0) {
            trysDeGeneration--;
        } else {
            trysDeGeneration = NOMBRE_DE_TRY_GENERATION;
            if (generationActuelle > nbGenerations) {
                gererNiveau.setTrajet(scoreDirective.get(0).getChemin());
                gererNiveau.setScore(scoreDirective.get(0).getScore());
                Partie.finNiveau();
            }
            generationActuelle++;
        }
    }

    private void computeScoresDirective() {
        for (int i = 0; i < NOMBRE_DE_TRY_GENERATION; i++) {
            gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(Partie.niveau - 1).clone());
            finiEvolution = false;
            final StringBuilder chemin = new StringBuilder();
            final GererNiveau gererNiveau = Partie.gererNiveau;
            final IaDirective directive = new IaDirective();
            final int caveDelay = Partie.gererNiveau.getNiveau().getCaveDelay();
            final int caveTime = Partie.gererNiveau.getNiveau().getCaveTime();
            final double time = caveDelay * caveTime * VITESSE_JEU_TEMPS_REEL;
            for (int j = 0; j < time; j++) {
                if (gererNiveau.isDemandeReset() || gererNiveau.isDemandeFin()) {
                    while (gererNiveau.getTrajet().length() + chemin.length() <= time) {
                        chemin.append(Ia.directionRandom());
                    }
                } else {
                    gererNiveau.tickIaDirevol(directive);
                }
            }
            final String trajet = gererNiveau.getTrajet() + chemin;
            final Score score = Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, trajet);
            scoreDirective.add(score);
        }
        Collections.sort(scoreDirective);
    }

    public Score computeScores() {
        while (!critereArret()) {
            out.printf("Génération %d/%d%n", generationActuelle, nbGenerations);
            generationActuelle++;
            final int survivants = NOMBRE_DE_TRY_GENERATION * POURCENTAGE_DE_SURVIVANTS;
            for (int i = 0; i < survivants / 100; i++) {
                final Score unScore = scoreDirective.get(i);
                final Score score = new Score(unScore.getScore(), unScore.getParcours(), unScore.getListDiamants());
                score.setChemin(unScore.getChemin());
                scoreAleatoire.add(score);
            }
            final int aleatoires = NOMBRE_DE_TRY_GENERATION * POURCENTAGE_DE_ALEATOIRE;
            for (int i = 0; i < aleatoires / 100; i++) {
                finiEvolution = false;
                final StringBuilder chemin = new StringBuilder();
                // FIXME a calculer
                int tailleCheminMaximale = 1;
                for (int j = 0; j < tailleCheminMaximale; j++) {
                    chemin.append(Ia.directionRandom());
                }
                final Score score = Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, chemin.toString());
                scoreAleatoire.add(score);
            }
            for (int i = 0; i < NOMBRE_DE_TRY_GENERATION - survivants / 100 - aleatoires / 100; i++) {
                final int rng =
                  (int) (random() * (Constantes.POURCENTAGE_DES_SELECTIONNES * NOMBRE_DE_TRY_GENERATION) / 100);
                Score score = scoreDirective.get(rng);
                gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(Partie.niveau - 1).clone());
                int tailleMutation = 2;
                final StringBuilder chemin = new StringBuilder(10);
                final GererNiveau niveau = gererNiveau;
                if (niveau.isDemandeFin()) {
                    tailleMutation = 0;
                }
                final IaDirective ia = new IaDirective();
                for (int j = 0; j < score.getParcours() - tailleMutation; j++) {
                    niveau.tickIaController(ia, score.getChemin().charAt(j));
                }
                for (int j = score.getParcours() - tailleMutation; j < score.getParcours(); j++) {
                    final char direction = Ia.directionRandom();
                    niveau.tickIaController(ia, direction);
                }
                for (int j = score.getParcours(); j < score.getChemin().length(); j++) {
                    // niveau = Partie.gererNiveau;
                    if (niveau.isDemandeReset() || niveau.isDemandeFin()) {
                        final double cheminMax = gererNiveau.getNiveau().getCaveDelay()
                                         * gererNiveau.getNiveau().getCaveTime()
                                         * VITESSE_JEU_TEMPS_REEL;
                        while (niveau.getTrajet().length() + chemin.length() <= cheminMax) {
                            chemin.append(Ia.directionRandom());
                        }
                    } else {
                        niveau.tickIaDirevol(ia);
                    }
                }
                final String parcours  = niveau.getTrajet() + chemin;
                score.setChemin(parcours);
                // score.setChemin(score.getChemin().substring(0, score.getParcours() -
                // tailleMutation) + finChemin);
                final Score fichierScore = Partie.jouerFichierScore(Partie.cheminFichier, Partie.niveau, parcours);
                scoreAleatoire.add(fichierScore);
            }
            scoreDirective.clear();
            for (final Score aScoreAleatoire : scoreAleatoire) {
                scoreDirective.add(aScoreAleatoire);
            }
            scoreAleatoire.clear();
            Collections.sort(scoreDirective);
        }
        return scoreDirective.get(0);
    }

    private boolean critereArret() {
        return generationActuelle > nbGenerations;
    }


}
