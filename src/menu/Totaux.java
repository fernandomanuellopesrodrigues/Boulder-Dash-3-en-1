package menu;

import main.Partie;
import outils.Score;

import static java.lang.String.format;
import static java.lang.System.out;
import static main.Partie.calculerStrategieEvolue;
import static main.Partie.gererNiveau;

public class Totaux {

    private String strat;
    private int    totalScore;
    private int    totalLongueur;
    private int    nombrePartie;

    private Totaux() {}

    public static Totaux CalculTotaux(final int nombrePartie,
                                      final boolean stratValid,
                                      final String strat,
                                      final int nbGenerations,
                                      final String cheminFichierBDCFF,
                                      final int niveau) {
        final Totaux totaux = new Totaux();
        totaux.nombrePartie = nombrePartie;
        totaux.strat = strat;
        afficherMessage(format("\nIA : %s", strat));
        for (int i = 0; i < totaux.nombrePartie; i++) {
            afficherMessage(format("\n\nPartie %d/%d\n", i + 1, totaux.nombrePartie));
            final Score score = stratValid
                                ? calculerStrategieEvolue(strat, nbGenerations, cheminFichierBDCFF, niveau)
                                : Partie.calculerStrategie(strat, cheminFichierBDCFF, niveau);
            totaux.totalScore += score.getScore();
            totaux.totalLongueur += score.getParcours();
        }
        return totaux;
    }

    private double getScoreMoyen() {
        return totalScore / nombrePartie;
    }

    private double getLongueurMoyenne() {
        return totalLongueur / nombrePartie;
    }

    public void afficheRaport() {
        afficherMessage(format("\n%s :\nDistance Moyenne : %s", strat, getLongueurMoyenne()));
        afficherMessage(format("Score Moyen : %s", getScoreMoyen()));
        afficherMessage(format("Temps Moyen : %s secondes.\n",
                               getLongueurMoyenne() * gererNiveau.getNiveau().getCaveTime() / 1000.0));
    }

    private static void afficherMessage(final String msg) {
        out.println(msg);
    }
}
