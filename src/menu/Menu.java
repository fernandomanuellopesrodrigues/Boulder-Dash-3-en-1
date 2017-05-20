package menu;

import java.util.Objects;
import java.util.Scanner;

import main.Coeur;

import static java.lang.Integer.valueOf;
import static java.lang.System.err;
import static java.lang.System.exit;
import static java.lang.System.out;
import static main.Constantes.HELP;
import static main.Constantes.NOMS;
import static menu.SousMenu.calculerStrategie;
import static menu.SousMenu.calculerStrategieEvol;
import static menu.SousMenu.lancerNiveau;
import static menu.SousMenu.lancerTousLesNiveaux;
import static menu.SousMenu.lireInfos;
import static menu.SousMenu.rejouerNiveau;
import static menu.SousMenu.simulerNiveau;

/**
 * La classe Menu contient le main ainsi que d'autres m\u00E9thodes statics g\u00E9rant la
 * lecture des param\u00E9tres du programme.
 * Elle lance des m\u00E9thodes de la classe {@link SousMenu} en fonction des
 * param\u00E9tres entr\u00E9s.
 *
 * @author Murloc
 */
public final class Menu {

    private Menu() {}

    /**
     * La m\u00E9thode main.
     * Elle lance d'autres m\u00E9thodes en fonction des param\u00E9tres.
     *
     * @param args Les arguments
     */
    public static void main(final String... args) {
        if (args.length == 0) {
            quitterAvecMessageErreur("Argument(s) manquant(s), entrez l'argument -h pour optenir de l'aide.");
        }
        switch (args[0]) {
            case "-name":
                quitterAvecMessageInformatif(NOMS);
                break;
            case "-h":
                quitterAvecMessageInformatif(HELP);
                break;
            case "-lis":
                checkLire(args);
                break;
            case "-joue":
                checkJouer(args);
                break;
            case "-cal":
                checkCalculer(args);
                break;
            case "-rejoue":
                checkRejouer(args);
                break;
            case "-simul":
                checkSimuler(args);
                break;
            case "-parfait":
                quitterAvecMessageErreur("\nL'iaValid parfaite n'est pas encore pr\u00E9sente dans cette version.");
                break;
            default:
                quitterArgumentsInvalides();
        }
    }

    /**
     * Cette m\u00E9thode est appel\u00E9e si l'utilisateur a rentr\u00E9 l'argument \"-lis\",
     * elle teste si le nombre d'arguments entr\u00E9s est correct puis lance
     * {@link SousMenu#lireInfos(String)} si oui.
     *
     * @param args Les arguments.
     */
    private static void checkLire(final String... args) {
        if (args.length != 2) {
            quitterArgumentsInvalides();
        }
        lireInfos(args[1]);
    }

    /**
     * Cette m\u00E9thode est appel\u00E9e si l'utilisateur a rentr\u00E9 l'argument \"-joue\",
     * elle teste si le nombre d'arguments entr\u00E9s est correct puis lance
     * d'autres m\u00E9thodes si oui.
     * Lance la m\u00E9thode {@link Menu#choisirOptions()} au d\u00E9but pour savoir si oui ou
     * non le joueur veut checkJouer en mode console/fen\u00E9tr\u00E9/temps r\u00E9el.
     *
     * @param args Les arguments.
     */
    private static void checkJouer(final String... args) {
        choisirOptions();
        if (args.length == 2) {
            lancerTousLesNiveaux(args[1]);
            return;
        }
        if (args.length != 4 || !Objects.equals(args[2], "-niveau")) {
            quitterArgumentsInvalides();
        }
        final int niveau = recupererEtCheckerNiveau(args[3]);
        lancerNiveau(args[1], niveau);
    }

    /**
     * Cette m\u00E9thode est appel\u00E9e si l'utilisateur a rentr\u00E9 l'argument \"-cal\",
     * elle teste si le nombre d'arguments entr\u00E9s est correct puis lance
     * d'autres m\u00E9thodes si oui.
     * Elle lance {@link SousMenu#calculerStrategie(String, String, int)} si
     * l'ia voulue n'est pas \u00E9volutive ou
     * {@link SousMenu#calculerStrategieEvol(String, int, String, int)} si elle
     * l'est.
     *
     * @param args Les arguments.
     */
    private static void checkCalculer(final String... args) {
        if (args.length == 5) {
            final int niveau = recupererEtCheckerNiveau(args[4]);
            calculerStrategie(args[1], args[2], niveau);
            return;
        }
        if (args.length != 6 || (!Objects.equals(args[1], "-evolue") && !Objects.equals(args[1], "-direvol"))) {
            quitterArgumentsInvalides();
        }
        final int niveau = recupererEtCheckerNiveau(args[5]);
        try {
            final int nb = valueOf(args[2]);
            calculerStrategieEvol(args[1], nb, args[3], niveau);
        } catch (final NumberFormatException ignored) {
            quitterAvecMessageErreur("Le nombre doit \u00E9tre un entier.");
        }
    }

    /**
     * Cette m\u00E9thode est appel\u00E9e si l'utilisateur a rentr\u00E9 l'argument \"-rejoue\",
     * elle teste si le nombre d'arguments entr\u00E9s est correct puis lance
     * d'autres m\u00E9thodes si oui.
     * Puis elle lance {@link SousMenu#rejouerNiveau(String, String, int)} si
     * tout est ok.
     *
     * @param args Les arguments.
     */
    private static void checkRejouer(final String... args) {
        if (args.length != 5) {
            quitterArgumentsInvalides();
        }
        Coeur.graphique = true;
        Coeur.FENETRE.setVisible(true);
        Coeur.tempsReel = true;
        final int niveau = recupererEtCheckerNiveau(args[4]);
        rejouerNiveau(args[1], args[2], niveau);
    }

    /**
     * Cette m\u00E9thode est appel\u00E9e si l'utilisateur a rentr\u00E9 l'argument \"-simul\",
     * elle teste si le nombre d'arguments entr\u00E9s est correct puis lance
     * d'autres m\u00E9thodes si oui.
     * Si tout est ok elle lance
     * {@link SousMenu#simulerNiveau(int, String, String, String, int)}.
     *
     * @param args Les arguments.
     */
    private static void checkSimuler(final String... args) {
        if (args.length != 7) {
            quitterArgumentsInvalides();
        }
        final int niveau = recupererEtCheckerNiveau(args[6]);
        try {
            final int nombrePartie = valueOf(args[1]);
            if (nombrePartie < 1) {
                throw new NumberFormatException();
            }
            simulerNiveau(nombrePartie, args[2], args[3], args[4], niveau);
        } catch (final NumberFormatException ignored) {
            quitterAvecMessageErreur("Le nombre de parties doit \u00E9tre un entier. > 0");
        }
    }

    /**
     * Cette m\u00E9thode est lanc\u00E9e en d\u00E9but de programme et demande au joueur
     * diverses informations.
     * Elle change les booleans de {@link Coeur} en fonction des r\u00E9sultats.
     * Elle fait apparaitre la fen\u00E9tre {@link Coeur#FENETRE} si l'utilisateur
     * joue en mode fen\u00E9tr\u00E9.
     */
    private static void choisirOptions() {
        final boolean pasGraphique = !poserQuestionOuiNon("Voulez-vous activer le mode graphique ? (O/N)");
        if (pasGraphique) {
            Coeur.graphique = false;
            Coeur.FENETRE.setVisible(false);
            return;
        }
        Coeur.tempsReel = poserQuestionOuiNon("Voulez-vous activer le mode temps reel ? (O/N)");
        Coeur.graphique = true;
        Coeur.FENETRE.setVisible(true);
    }

    /**
     * verifie que le niveau est un entier positif ou nul
     */
    private static int recupererEtCheckerNiveau(final String arg) {
        int niveau = 0;
        try {
            niveau = valueOf(arg);
            if (niveau < 0) {
                throw new NumberFormatException();
            }
        } catch (final NumberFormatException ignored) {
            quitterAvecMessageErreur("Le niveau doit \u00E9tre un entier positif ou nul.");
        }
        return niveau;
    }

    /**
     * pose une question a l'utilisateur
     */
    public static boolean poserQuestionOuiNon(final String msg) {
        String reponse;
        do {
            out.println(msg);
            final Scanner sc = new Scanner(System.in);
            reponse = sc.nextLine().toUpperCase();
        } while (!Objects.equals(reponse, "O") && !Objects.equals(reponse, "N"));
        return Objects.equals(reponse, "O");
    }

    private static void quitterArgumentsInvalides() {
        quitterAvecMessageErreur("Argument(s) invalide(s).");
    }

    private static void quitterAvecMessageErreur(final String msg) {
        err.println(msg);
        exit(-1);
    }

    private static void quitterAvecMessageInformatif(final String msg) {
        out.println(msg);
        exit(0);
    }
}
