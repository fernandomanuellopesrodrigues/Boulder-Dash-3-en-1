package menu;

import java.util.Scanner;

import loader.Loader;
import main.Coeur;
import main.Partie;
import outils.Ecrivain;
import outils.Score;

/***
 * La classe SousMenu n'est jamais instanci�e. contient des m�thodes statics
 * g�rant le lancement et la direction que doit prendre le programme au
 * lancement.
 *
 * Elle lance des m�thodes de la classe {@link Partie}.
 *
 * @author Murloc
 *
 */
public class SousMenu {

    /**
     * Lance une partie d'un seul niveau.
     *
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF o� se trouve le niveau.
     * @param niveau Le num�ro du niveau voulu.
     */
    public static void lancerNiveau(String cheminFichierBDCFF, int niveau) {
        Partie.commencerPartie(cheminFichierBDCFF, niveau);
    }

    /**
     * Lance une partie de tous les niveaux du fichier BDCFF.
     *
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF o� se trouve le niveau.
     */
    public static void lancerTousLesNiveaux(String cheminFichierBDCFF) {
        Partie.commencerPartie(cheminFichierBDCFF);
    }

    /**
     * Affiche sur la console les infos d'un fichier BDCFF pass� en param�tre.
     *
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF o� se trouve le niveau.
     */
    public static void lireInfos(String cheminFichierBDCFF) {
        System.out.println(Loader.lireInfos(cheminFichierBDCFF));
    }

    /**
     * Cr�e un fichier DASH contenant le r�sultat du meilleur essai parmis les
     * strat�gies tent�s (Seulement les strat�gies non �volutives ici).
     *
     * @param strategie La strat�gie voulue.
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF o� se trouve le niveau.
     * @param niveau Le num�ro du niveau voulu.
     */
    public static void calculerStrategie(String strategie, String cheminFichierBDCFF, int niveau) {
        System.out.println("Calcul en cours...\n");
        Partie.calculerStrategie(strategie, cheminFichierBDCFF, niveau);
    }

    /**
     * Cr�e un fichier DASH contenant le r�sultat du meilleur essai parmis les
     * strat�gies tent�s (Seulement les strat�gies �volutives ici).
     *
     * @param strategie La strat�gie voulue.
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF o� se trouve le niveau.
     * @param niveau Le num�ro du niveau voulu.
     */
    public static void calculerStrategieEvol(String strategie, int nbGenerations, String cheminFichierBDCFF,
                                             int niveau) {
        System.out.println("Calcul en cours...\n");
        Partie.calculerStrategieEvolue(strategie, nbGenerations, cheminFichierBDCFF, niveau);
    }

    /**
     * Rejoue une partie d'un niveau en jouant les d�placements se trouvant dans
     * le fichier DASH pass� en param�tre.
     *
     * @param cheminFichierDASH Le chemin du fichier DASH.
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF o� se trouve le niveau.
     * @param niveau Le num�ro du niveau voulu.
     */
    public static void rejouerNiveau(String cheminFichierDASH, String cheminFichierBDCFF, int niveau) {
        Partie.jouerFichier(cheminFichierBDCFF, niveau, Ecrivain.lireParcours(cheminFichierDASH));
    }

    /**
     * Affiche les meilleurs r�sultats des deux strat�gies voulues.
     *
     * @param nombrePartie Le nombre de partie voulues.
     * @param strategie1 La strat�gie 1 voulue.
     * @param strategie2 La strat�gie 2 voulue.
     * @param cheminFichierBDCFF Le chemin du fichier BDCFF o� se trouve le niveau.
     * @param niveau Le num�ro du niveau voulu.
     */
    public static void simulerNiveau(int nombrePartie, String strategie1, String strategie2, String cheminFichierBDCFF,
                                     int niveau) {

        int nbGenerations = 0;
        if (strategie1.equals("-direvol") || strategie1.equals("-evolue") || strategie2.equals("-direvol")
            || strategie2.equals("-evolue")) {
            do {
                Scanner sc = new Scanner(System.in);
                try {
                    System.out.println("Combien voulez-vous de générations pour les IA's évolutives ?");
                    nbGenerations = sc.nextInt();
                } catch (Exception e) {

                }
            } while (nbGenerations <= 0);
        }
        Partie.simulation = true;
        System.out.println("Calcul en cours...");
        double scoreMoyen1, scoreMoyen2, longueurMoyenne1, longueurMoyenne2;
        int totalScore1 = 0, totalScore2 = 0, totalLongueur1 = 0, totalLongueur2 = 0;

        System.out.println("\nIA : " + strategie1);
        for (int i = 0; i < nombrePartie; i++) {
            System.out.println("\n\nPartie " + (i + 1) + "/" + (nombrePartie) + "\n");
            Score s;
            if (strategie1.equals("-direvol") || strategie1.equals("-evolue")) {
                s = Partie.calculerStrategieEvolue(strategie1, nbGenerations, cheminFichierBDCFF, niveau);
            } else {
                s = Partie.calculerStrategie(strategie1, cheminFichierBDCFF, niveau);
            }
            totalScore1 += s.getScore();
            totalLongueur1 += s.getParcours();
        }
        System.out.println("\n\nIA : " + strategie2);
        for (int i = 0; i < nombrePartie; i++) {
            System.out.println("\n\nPartie " + ((i + 1) + "/" + (nombrePartie)) + "\n");
            Score s;
            if (strategie2.equals("-direvol") || strategie2.equals("-evolue")) {
                s = Partie.calculerStrategieEvolue(strategie2, nbGenerations, cheminFichierBDCFF, niveau);
            } else {
                s = Partie.calculerStrategie(strategie2, cheminFichierBDCFF, niveau);
            }
            totalScore2 += s.getScore();
            totalLongueur2 += s.getParcours();
        }
        scoreMoyen1 = totalScore1 / nombrePartie;
        scoreMoyen2 = totalScore2 / nombrePartie;
        longueurMoyenne1 = totalLongueur1 / nombrePartie;
        longueurMoyenne2 = totalLongueur2 / nombrePartie;

        System.out.println("\n\n\nRAPPORT :");
        System.out.println("\n" + strategie1 + " :\nDistance Moyenne : " + longueurMoyenne1);
        System.out.println("Score Moyen : " + scoreMoyen1);
        System.out.println("Temps Moyen : "
                           + longueurMoyenne1 * (Partie.gererNiveau.getNiveau().getCave_time()) / 1000.0 +
                           " secondes.\n");

        System.out.println(strategie2 + " :\nDistance Moyenne : " + longueurMoyenne2);
        System.out.println("Score Moyen : " + scoreMoyen2);
        System.out.println("Temps Moyen : "
                           + longueurMoyenne2 * (Partie.gererNiveau.getNiveau().getCave_time()) / 1000.0 +
                           " secondes.");
        System.exit(1);
    }

    public static void finIA(Score s) {
        if (!Partie.simulation) {

            String fichier = Partie.enregistrerEssai(s.getChemin().substring(0, s.getParcours()));
            char reponse = ' ';
            do {
                System.out.println("\nVoulez-vous observer le résultat ? (O/N)");
                Scanner sc = new Scanner(System.in);
                reponse = sc.nextLine().toUpperCase().charAt(0);
            } while (reponse != 'O' && reponse != 'N');
            if (reponse == 'O') {
                Coeur.graphique = true;
                Coeur.FENETRE.setVisible(true);
                Coeur.tempsReel = true;
                SousMenu.rejouerNiveau(fichier, Partie.cheminFichier, Partie.niveau);
            }
        }
    }
}
