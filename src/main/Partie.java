package main;

import java.awt.BorderLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import entitees.abstraites.Entitee;
import entitees.abstraites.Entitee.Type;
import ia.Ia;
import ia.IaDirective;
import ia.IaDirectiveEvolue;
import ia.IaEvolue;
import ia.IaRandom;
import loader.EnsembleDeNiveaux;
import menu.SousMenu;
import outils.Ecrivain;
import outils.Paire;
import outils.Score;
import outils.SonToolKit;
import vue.FinPanel;
import vue.GraphiqueConsole;
import vue.JeuPanel;
import vue.ScorePanel;

import static controleurs.ControleurConsole.prochainNiveau;
import static java.lang.String.format;
import static java.lang.System.err;
import static java.lang.System.out;
import static java.time.format.DateTimeFormatter.ofPattern;
import static loader.Loader.chargerEnsembleDeNiveaux;
import static vue.GraphiqueConsole.afficherScoreTousLesNiveaux;

public final class Partie {

    public static final  List<Integer>     SCORES    = new ArrayList<>(10);
    public static final  SonToolKit        SONS      = new SonToolKit();
    private static final DateTimeFormatter DF        = ofPattern("dd:MM:yyyy_HH:mm:ss");
    private static final LocalDateTime     TODAY     = LocalDateTime.now();
    private static final String            DATEDEBUT = DF.format(TODAY);
    public static String            cheminFichier;
    public static boolean           finiEvolution;
    public static EnsembleDeNiveaux ensembleDeNiveau;
    public static int               niveau;
    public static GererNiveau       gererNiveau;
    public static boolean           tousLesNiveaux;
    public static boolean           iaValid;
    public static Ia                ia;
    public static boolean           lecture;
    public static boolean           simulation;
    public static String            parcours;

    private Partie() {}

    private static void initialiserNiveaux(final String chemin) {
        cheminFichier = chemin;
        ensembleDeNiveau = chargerEnsembleDeNiveaux(chemin);
        tousLesNiveaux = false;
        niveau = 1;
    }

    public static void commencerPartie(final String chemin) {
        initialiserNiveaux(chemin);
        tousLesNiveaux = true;
        lancerNiveau();
    }

    public static void commencerPartie(final String chemin, final int niv) {
        initialiserNiveaux(chemin);
        niveau = niv;
        lancerNiveau();
    }

    public static void commencerPartie(final String chemin, final int niv, final String parc) {
        initialiserNiveaux(chemin);
        niveau = niv;
        lecture = true;
        parcours = parc;
        lancerNiveau();
    }

    public static Entitee getEntiteParPosition(final int positionX, final int positionY) {
        return gererNiveau.getNiveau().getMap()[positionX][positionY];
    }

    public static void setEntiteParPosition(final int positionX, final int positionY, final Entitee entitee) {
        gererNiveau.getNiveau().getMap()[positionX][positionY] = entitee;
    }

    public static boolean checkEntite(final int positionX, final int positionY, final Type type) {
        return gererNiveau.getNiveau().testEntitee(positionX, positionY, type);
    }

    public static Score jouerFichierScore(final String chemin, final int niv, final String parc) {
        initialiserNiveaux(chemin);
        niveau = niv;
        lecture = true;
        parcours = parc;
        gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1));
        final StringBuilder parcoursParcouru = new StringBuilder(10);
        int scores = 0;
        List<Paire<Integer, Long>> liste = null;
        while (!parcours.isEmpty() && !gererNiveau.isDemandeReset() && !gererNiveau.isDemandeFin()) {
            final char direction = parcours.charAt(0);
            parcours = parcours.substring(1, parcours.length());
            parcoursParcouru.append(direction);
            liste = gererNiveau.getListeDiamants();
            scores = gererNiveau.getScore();
            if (gererNiveau.tickLecture(direction) || gererNiveau.getNiveau().getRockford().isMort()) {
                break;
            }
        }
        final Score score = new Score(scores, parcoursParcouru.length(), liste);
        score.setChemin(parc);
        score.setFini(gererNiveau.isDemandeFin());
        return score;
    }

    public static void resetNiveau() {
        if (iaValid) {
            gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());
            if (ia != null) {
                ia.reset();
            }
        } else if (lecture) {
            if (gererNiveau.getNiveau().getRockford().isMort()) {
                out.println("Mort de Rockford. Mauvais parcours. Fin du Programme.");
            } else {
                out.println("Fin du temps. Fin Du programme.");
            }
            System.exit(1);
        } else {
            Coeur.running = false;
            lancerNiveau();
        }

    }

    public static Score calculerStrategie(final String strategie,
                                          final String cheminFichierBDCFF,
                                          final int niveau) {
        iaValid = true;
        ensembleDeNiveau = chargerEnsembleDeNiveaux(cheminFichierBDCFF);
        Partie.niveau = niveau;
        gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());
        if (Objects.equals(strategie, "-simplet")) {
            ia = new IaRandom();
        } else if (Objects.equals(strategie, "-directif")) {
            ia = new IaDirective();
        }
        if (ia != null) {
            while (!gererNiveau.tickIa(ia)) { }
        }

        final Score score =
          new Score(gererNiveau.getScore(), gererNiveau.getCompteurTicks(), gererNiveau.getListeDiamants());
        SousMenu.finIA(score);
        return score;
    }

    public static Score calculerStrategieEvolue(final String strategie,
                                                final int nbGenerations,
                                                final String cheminFichierBDCFF,
                                                final int niveau) {
        iaValid = true;
        cheminFichier = cheminFichierBDCFF;
        ensembleDeNiveau = chargerEnsembleDeNiveaux(cheminFichierBDCFF);
        Partie.niveau = niveau;
        gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());
        if (strategie.equals("-evolue")) {
            ia = new IaEvolue(nbGenerations);
        } else if (strategie.equals("-direvol")) {
            ia = new IaDirectiveEvolue(nbGenerations);
        }
        Score score = null;
        if (ia instanceof IaEvolue) {
            score = ((IaEvolue) ia).debut();
        } else if (ia instanceof IaDirectiveEvolue) {
            score = ((IaDirectiveEvolue) ia).computeScores();
        }
        SousMenu.finIA(score);
        return score;
    }

    public static void finNiveau() {
        SONS.stopAll();
        finiEvolution = true;
        Coeur.running = false;
        if (!lecture && !simulation) {
            enregistrerEssai(gererNiveau.getTrajet());
        }
        if (tousLesNiveaux) {
            SCORES.add(gererNiveau.getScore());
        }
        if (tousLesNiveaux && niveau < ensembleDeNiveau.getNombreDeNiveaux()) {
            if (!Coeur.graphique) {
                prochainNiveau(niveau, gererNiveau.getScore());
            }
            niveau++;
            lancerNiveau();
        } else {
            fin();
        }
    }

    private static void fin() {
        Coeur.running = false;
        if (Coeur.graphique) {
            Coeur.FENETRE.getContentPane().removeAll();
            Coeur.FENETRE.getContentPane().add(new FinPanel());
            Coeur.FENETRE.getContentPane().validate();
            Coeur.FENETRE.repaint();
        } else if (!simulation) {
            if (tousLesNiveaux) {
                afficherScoreTousLesNiveaux(SCORES);
            } else {
                afficherScoreUnNiveau(niveau, gererNiveau.getScore());
            }
        }
    }

    private static void lancerNiveau() {
        if (gererNiveau != null) { gererNiveau.stop(); }
        gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());
        Coeur.running = true;
        if (Coeur.graphique) {
            Thread t = new Thread(() -> {
                while (true) {
                    if (Coeur.running) {
                        gererNiveau.gererTemps();
                        if (gererNiveau.isDemandeReset()) {
                            gererNiveau.tick();
                        }
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                }
            });
            t.start();
            Coeur.setTicks((int) (ensembleDeNiveau.getNiveaux().get(niveau - 1).getCaveDelay()
                                  * Constantes.VITESSE_JEU_TEMPS_REEL));
            preparerFenetre();

        } else {
            Coeur.CONTROLEUR_CONSOLE.run(gererNiveau);
        }

    }

    public static void tick() {
        if (!Coeur.graphique) {
            GraphiqueConsole.afficher(gererNiveau.getNiveau());
        }
        if (lecture) {
            if (parcours.length() > 0) {
                gererNiveau.tickLecture(parcours.charAt(0));
                parcours = parcours.substring(1, parcours.length());
            } else {
                finiEvolution = true;
                if (!iaValid) {
                    err.println("Chemin du fichier terminé or le niveau n'est pas fini, fermeture du programme.");
                    System.exit(0);
                }
            }
        } else {
            gererNiveau.tick();
        }

    }

    private static void preparerFenetre() {
        Coeur.FENETRE.getContentPane().removeAll();
        Coeur.FENETRE.getContentPane().setLayout(new BorderLayout());
        Coeur.FENETRE.getContentPane().add(new JeuPanel(), BorderLayout.CENTER);
        Coeur.FENETRE.getContentPane().add(new ScorePanel(), BorderLayout.NORTH);
        Coeur.FENETRE.getContentPane().validate();
    }

    public static String enregistrerEssai(String trajet) {
        String essai = format("Trajet : %s\nScore : %d     Diamants : %d      Temps : ", trajet, gererNiveau.getScore(),
                              gererNiveau.getNbDiamants());
        essai += gererNiveau.isTourParTour() || iaValid
                 ? gererNiveau.getCompteurTicks() + " tours\n" :
                 gererNiveau.getCompteurTicks() / (double) gererNiveau.getNiveau().getCaveDelay() + " secondes\n";
        String chemin = "";
        chemin += iaValid ? "Essais_IA/" : "Essais_Humain/";
        chemin += format("%s/", DATEDEBUT);
        Ecrivain.ecrire(essai, format("Niveau_%d.dash", niveau), chemin);
        return format("%sNiveau_%d.dash", chemin, niveau);
    }

    /**
     * Affiche le score a la fin d'un niveau.
     *
     * @param niveau Le niveau fini.
     * @param score Le score a afficher.
     */
    private static void afficherScoreUnNiveau(final int niveau, final int score) {
        if (!iaValid && !lecture) {
            out.printf("FIN DU JEU , SCORE DU NIVEAU %d : %d.\n%n", niveau, score);
        }
    }
}
