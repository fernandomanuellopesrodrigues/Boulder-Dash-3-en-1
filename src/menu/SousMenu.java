package menu;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import loader.Loader;
import main.Coeur;
import main.Partie;
import outils.Ecrivain;
import outils.Score;

import static java.lang.System.exit;
import static java.lang.System.out;
import static main.Partie.calculerStrategieEvolue;
import static main.Partie.commencerPartie;
import static main.Partie.enregistrerEssai;
import static menu.Menu.poserQuestionOuiNon;
import static menu.Totaux.CalculTotaux;

/***
 * La classe SousMenu n'est jamais instanciee. contient des methodes statics
 * gerant le lancement et la direction que doit prendre le programme au
 * lancement.
 *
 * Elle lance des methodes de la classe {@link Partie}.
 *
 * @author Murloc
 *
 */
public final class SousMenu {

    private SousMenu() {}

    /**
     * Lance une partie d'un seul niveau.
     *
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF ou se trouve le niveau.
     * @param niveau Le num\u00E9ro du niveau voulu.
     */
    public static void lancerNiveau(final String cheminFichierBDCFF, final int niveau) {
        commencerPartie(cheminFichierBDCFF, niveau);
    }

    /**
     * Lance une partie de tous les niveaux du fichier BDCFF.
     *
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF ou se trouve le niveau.
     */
    public static void lancerTousLesNiveaux(final String cheminFichierBDCFF) {
        commencerPartie(cheminFichierBDCFF);
    }

    /**
     * Affiche sur la console les infos d'un fichier BDCFF pass\u00E9 en param\u00E9tre.
     *
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF ou se trouve le niveau.
     */
    public static void lireInfos(final String cheminFichierBDCFF) {
        afficherMessage(Loader.lireInfos(cheminFichierBDCFF));
    }

    /**
     * Cree un fichier DASH contenant le r\u00E9sultat du meilleur essai parmis les
     * strat\u00E9gies tent\u00E9s (Seulement les strat\u00E9gies non \u00E9volutives ici).
     *
     * @param strategie La strat\u00E9gie voulue.
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF ou se trouve le niveau.
     * @param niveau Le num\u00E9ro du niveau voulu.
     */
    public static void calculerStrategie(final String strategie, final String cheminFichierBDCFF, final int niveau) {
        afficherMessage("Calcul en cours...\n");
        Partie.calculerStrategie(strategie, cheminFichierBDCFF, niveau);
    }

    /**
     * Cree un fichier DASH contenant le r\u00E9sultat du meilleur essai parmis les
     * strat\u00E9gies tent\u00E9s (Seulement les strat\u00E9gies \u00E9volutives ici).
     *
     * @param strategie La strat\u00E9gie voulue.
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF ou se trouve le niveau.
     * @param niveau Le num\u00E9ro du niveau voulu.
     */
    public static void calculerStrategieEvol(final String strategie,
                                             final int nbGenerations,
                                             final String cheminFichierBDCFF,
                                             final int niveau) {
        afficherMessage("Calcul en cours...\n");
        calculerStrategieEvolue(strategie, nbGenerations, cheminFichierBDCFF, niveau);
    }

    /**
     * Rejoue une partie d'un niveau en jouant les d\u00E9placements se trouvant dans
     * le fichier DASH pass\u00E9 en param\u00E9tre.
     *
     * @param cheminFichierDASH Le chemin du fichier DASH.
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF ou se trouve le niveau.
     * @param niveau Le num\u00E9ro du niveau voulu.
     */
    public static void rejouerNiveau(final String cheminFichierDASH,
                                     final String cheminFichierBDCFF,
                                     final int niveau) {
        commencerPartie(cheminFichierBDCFF, niveau, Ecrivain.lireParcours(cheminFichierDASH));
    }

    /**
     * Affiche les meilleurs r\u00E9sultats des deux strat\u00E9gies voulues.
     *
     * @param nombrePartie Le nombre de partie voulues.
     * @param strat1 La strat\u00E9gie 1 voulue.
     * @param strat2 La strat\u00E9gie 2 voulue.
     * @param fichierBDCFF Le chemin du fichier BDCFF ou se trouve le niveau.
     * @param niveau Le num\u00E9ro du niveau voulu.
     */
    public static void simulerNiveau(final int nombrePartie,
                                     final String strat1,
                                     final String strat2,
                                     final String fichierBDCFF,
                                     final int niveau) {
        int nbGenerations = 0;
        final boolean strat1Valid = Objects.equals(strat1, "-direvol") || Objects.equals(strat1, "-evolue");
        final boolean strat2Valid = Objects.equals(strat2, "-direvol") || Objects.equals(strat2, "-evolue");
        if (strat1Valid || strat2Valid) {
            do {
                final Scanner sc = new Scanner(System.in);
                try {
                    afficherMessage("Combien voulez-vous de générations pour les IA's évolutives ?");
                    nbGenerations = sc.nextInt();
                } catch (final InputMismatchException ignored) {
                    nbGenerations = -1;
                }
            } while (nbGenerations <= 0);
        }
        Partie.simulation = true;
        afficherMessage("Calcul en cours...");
        final Totaux totalStrat1 = CalculTotaux(nombrePartie, strat1Valid, strat1, nbGenerations, fichierBDCFF, niveau);
        final Totaux totalStrat2 = CalculTotaux(nombrePartie, strat2Valid, strat2, nbGenerations, fichierBDCFF, niveau);
        afficherMessage("\n\n\nRAPPORT :");
        totalStrat1.afficheRaport();
        totalStrat2.afficheRaport();
        exit(1);
    }

    public static void finIA(final Score score) {
        if (Partie.simulation) {
            return;
        }
        final String fichier = enregistrerEssai(score.getChemin().substring(0, score.getParcours()));
        final boolean resultat = poserQuestionOuiNon("Voulez-vous observer le r\u00E9sultat ? (O/N)");
        if (resultat) {
            Coeur.graphique = true;
            Coeur.FENETRE.setVisible(true);
            Coeur.tempsReel = true;
            rejouerNiveau(fichier, Partie.cheminFichier, Partie.niveau);
        }
    }

    private static void afficherMessage(final String msg) {
        out.println(msg);
    }

}
