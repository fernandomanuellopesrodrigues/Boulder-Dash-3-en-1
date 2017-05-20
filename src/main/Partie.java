package main;

import java.awt.BorderLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import controleurs.ControleurConsole;
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

import static java.lang.System.out;
import static loader.Loader.chargerEnsembleDeNiveaux;

public class Partie {

    public static final List<Integer> SCORES = new ArrayList<>(10);
    public static String            cheminFichier;
    public static boolean           finiEvolution;
    public static EnsembleDeNiveaux ensembleDeNiveau;
    public static int               niveau;
    public static GererNiveau       gererNiveau;
    public static boolean           tousLesNiveaux;
    public static boolean           IA;
    public static Ia                ia;
    public static boolean           lecture;
    public static boolean           simulation;
    public static String            parcours;
    public static final  SonToolKit SONS = new SonToolKit();
    private static final DateFormat DF   = SimpleDateFormat.("dd:MM:yyyy_HH:mm:ss");
    private static final LocalDateTime TODAY     = LocalDateTime.now();
    private static final String        DATEDEBUT = DF.format(TODAY);

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
        String parcoursParcouru = "";
        int scores = 0;
        List<Paire<Integer, Long>> liste = null;
        while (!parcours.isEmpty() && !gererNiveau.isDemandeReset() && !gererNiveau.isDemandeFin()) {
            final char direction = parcours.charAt(0);
            parcours = parcours.substring(1, parcours.length());
            parcoursParcouru += direction;
            liste = gererNiveau.getListeDiamants();
            score = gererNiveau.getScore();
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
        if (IA) {
            gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());
            if (ia != null) { ia.reset(); }
        } else if (lecture) {
            if (gererNiveau.getNiveau().getRockford().isMort()) {
                System.out.println("Mort de Rockford. Mauvais parcours. Fin du Programme.");
            } else { System.out.println("Fin du temps. Fin Du programme."); }
            System.exit(1);
        } else {
            Coeur.running = false;
            lancerNiveau();
        }

    }

    public static Score calculerStrategie(String strategie, String cheminFichierBDCFF, int niveau) {
        IA = true;
        ensembleDeNiveau = chargerEnsembleDeNiveaux(cheminFichierBDCFF);
        Partie.niveau = niveau;
        gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());
        if (strategie.equals("-simplet")) {
            ia = new IaRandom();
        } else if (strategie.equals("-directif")) {
            ia = new IaDirective();
        }

        if (ia != null) {
            while (!gererNiveau.tickIa(ia)) { }
        }

        Score score = new Score(gererNiveau.getScore(), gererNiveau.getCompteurTicks(), gererNiveau.getListeDiamants());

        SousMenu.finIA(score);
        return score;
    }

    public static Score calculerStrategieEvolue(String strategie, int nbGenerations, String cheminFichierBDCFF,
                                                int niveau) {
        IA = true;
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
                ControleurConsole.prochainNiveau(niveau, gererNiveau.getScore());
            }
            niveau++;
            lancerNiveau();
        } else {
            fin();
        }
    }

    public static void fin() {
        Coeur.running = false;
        if (Coeur.graphique) {
            Coeur.FENETRE.getContentPane().removeAll();
            Coeur.FENETRE.getContentPane().add(new FinPanel());
            Coeur.FENETRE.getContentPane().validate();
            Coeur.FENETRE.repaint();
        } else if (!simulation) {
            if (tousLesNiveaux) {
                GraphiqueConsole.afficherScoreTousLesNiveaux(SCORES);

            } else {
                afficherScoreUnNiveau(niveau, gererNiveau.getScore());
            }
        }
    }

    public static void lancerNiveau() {
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
                if (!IA) {
                    System.err
                      .println("Chemin du fichier terminé or le niveau n'est pas fini, fermeture du programme.");
                    System.exit(0);
                }
            }
        } else {
            gererNiveau.tick();
        }

    }

    public static void preparerFenetre() {
        Coeur.FENETRE.getContentPane().removeAll();
        Coeur.FENETRE.getContentPane().setLayout(new BorderLayout());
        Coeur.FENETRE.getContentPane().add(new JeuPanel(), BorderLayout.CENTER);
        Coeur.FENETRE.getContentPane().add(new ScorePanel(), BorderLayout.NORTH);
        Coeur.FENETRE.getContentPane().validate();
    }

    public static String enregistrerEssai(String trajet) {
        String essai = "Trajet : " + trajet + "\nScore : " + gererNiveau.getScore() + "     Diamants : "
                       + gererNiveau.getNbDiamants() + "      Temps : ";
        if (gererNiveau.isTourParTour() || IA) {
            essai += gererNiveau.getCompteurTicks() + " tours\n";
        } else {
            essai += (double) gererNiveau.getCompteurTicks() / (double) gererNiveau.getNiveau().getCaveDelay()
                     + " secondes\n";
        }

        String chemin = "";
        if (IA) {
            chemin += "Essais_IA/";
        } else {
            chemin += "Essais_Humain/";
        }
        chemin += DATEDEBUT + "/";

        Ecrivain.ecrire(essai, "Niveau_" + niveau + ".dash", chemin);
        return chemin + "Niveau_" + niveau + ".dash";
    }

    /**
     * Affiche le score a la fin d'un niveau.
     *
     * @param niveau Le niveau fini.
     * @param score Le score a afficher.
     */
    public static void afficherScoreUnNiveau(final int niveau, final int score) {
        if (!IA && !lecture) {
            out.printf("FIN DU JEU , SCORE DU NIVEAU %d : %d.\n%n", niveau, score);
        }
    }
}
