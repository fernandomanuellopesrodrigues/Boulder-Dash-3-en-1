package main;

import java.awt.BorderLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import constantes.Constantes;
import loader.EnsembleDeNiveaux;
import loader.Loader;
import vue.FinPanel;
import vue.GraphiqueConsole;
import vue.JeuPanel;
import vue.ScorePanel;

public class Partie {
    public static final List<Integer> SCORES = new ArrayList<Integer>();
    public static EnsembleDeNiveaux ensembleDeNiveau;
    public static int niveau;
    public static GererNiveau gererNiveau;
    public static boolean tousLesNiveaux;
    public static boolean IA;
    static DateFormat df = new SimpleDateFormat("dd:MM:yyyy_HH:mm:ss");
    static Date today = Calendar.getInstance().getTime();
    static String dateDebut = df.format(today);

    public static void commencerPartie(String chemin) {
        ensembleDeNiveau = Loader.charger_ensemble_de_niveaux(chemin);
        tousLesNiveaux = true;
        niveau = 1;
        lancerNiveau();
    }

    public static void commencerPartie(String chemin, int niveau) {
        ensembleDeNiveau = Loader.charger_ensemble_de_niveaux(chemin);
        tousLesNiveaux = false;
        Partie.niveau = niveau;
        lancerNiveau();
    }

    public static void finNiveau() {
        Coeur.running = false;
        String essai="Trajet : "+gererNiveau.getTrajet() +"\nScore : " + gererNiveau.getScore() + "     Diamants : " + gererNiveau.getNbDiamants()+"      Temps : ";
        if(gererNiveau.isTourParTour()){
            essai+=(gererNiveau.getCompteurTicks()+" tours\n");
        }else{
            essai+=(((double)gererNiveau.getCompteurTicks())/((double)gererNiveau.getNiveau().getCaveDelay()))+" secondes\n";
        }
        enregistrerEssai(essai);
        if (tousLesNiveaux) {
            SCORES.add(gererNiveau.getScore());
        }
        if (tousLesNiveaux && niveau <= ensembleDeNiveau.getNombre_de_niveaux()) {
            niveau++;
            lancerNiveau();
        } else {
            fin();
        }
    }

    public static void fin() {
        if (Coeur.graphique) {
            Coeur.FENETRE.getContentPane().removeAll();
            Coeur.FENETRE.getContentPane().add(new FinPanel());
            Coeur.FENETRE.getContentPane().validate();
            Coeur.FENETRE.repaint();
        } else {
            if (tousLesNiveaux) {
                GraphiqueConsole.afficherScoreTousLesNiveaux(SCORES);

            } else {
                GraphiqueConsole.afficherScoreUnNiveau(niveau, gererNiveau.getScore());
            }
        }
    }

    public static void lancerNiveau() {
        if (gererNiveau != null)
            gererNiveau.stop();
        gererNiveau = new GererNiveau(ensembleDeNiveau.getNiveaux().get(niveau - 1).clone());
        Coeur.setTicks((int) (ensembleDeNiveau.getNiveaux().get(niveau - 1).getCaveDelay()
                * Constantes.VITESSE_JEU_TEMPS_REEL));
        if (Coeur.graphique) {
            preparerFenetre();
        }
        Coeur.running = true;
    }

    public static void resetNiveau() {
        Coeur.running = false;
        lancerNiveau();
    }

    public static void tick() {
        if (!Coeur.graphique) {
            GraphiqueConsole.afficher(gererNiveau.getNiveau());
        }
        gererNiveau.tick();
    }

    public static void preparerFenetre() {
        Coeur.FENETRE.getContentPane().removeAll();
        Coeur.FENETRE.getContentPane().setLayout(new BorderLayout());
        Coeur.FENETRE.getContentPane().add(new JeuPanel(), BorderLayout.CENTER);
        Coeur.FENETRE.getContentPane().add(new ScorePanel(), BorderLayout.NORTH);
        Coeur.FENETRE.getContentPane().validate();
    }

    public static void enregistrerEssai(String essai) {
        String chemin = "";
        if (IA) {
            chemin += "Essais_IA/";
        } else {
            chemin += "Essais_Humain/";
        }
        chemin += dateDebut + "/";

        outils.Ecrivain.ecrire(essai, "Niveau_" + niveau + ".bd", chemin);
    }
}
