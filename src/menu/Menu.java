package menu;

import java.util.Objects;
import java.util.Scanner;

import main.Coeur;

import static java.lang.System.err;
import static java.lang.System.exit;
import static main.Constantes.HELP;
import static main.Constantes.NOMS;

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
            err.println("Argument(s) manquant(s), entrez l'argument -h pour optenir de l'aide.");
            exit(-1);
        }

        switch (args[0]) {
            case "-name":
                System.out.println(NOMS);
                break;
            case "-h":
                System.out.println(HELP);
                break;
            case "-lis":
                lire(args);
                break;
            case "-joue":
                jouer(args);
                break;
            case "-cal":
                calculer(args);
                break;
            case "-rejoue":
                rejouer(args);
                break;
            case "-simul":
                simuler(args);
                break;
            case "-parfait":
                System.out.println("\nL'IA parfaite n'est pas encore pr\u00E9sente dans cette version.");
                break;
            default:
                err.println("Argument(s) non reconnu(s).");
        }
    }

    /**
     * Cette m\u00E9thode est appel\u00E9e si l'utilisateur a rentr\u00E9 l'argument \"-lis\",
     * elle teste si le nombre d'arguments entr\u00E9s est correct puis lance
     * {@link SousMenu#lireInfos(String)} si oui.
     *
     * @param args Les arguments.
     */
    public static void lire(String[] args) {
        if (args.length == 2) {
            SousMenu.lireInfos(args[1]);
        } else {
            err.println("Argument(s) invalide(s).");
        }
    }

    /**
     * Cette m\u00E9thode est appel\u00E9e si l'utilisateur a rentr\u00E9 l'argument \"-joue\",
     * elle teste si le nombre d'arguments entr\u00E9s est correct puis lance
     * d'autres m\u00E9thodes si oui.
     * Lance la m\u00E9thode {@link Menu#options()} au d\u00E9but pour savoir si oui ou
     * non le joueur veut jouer en mode console/fen\u00E9tr\u00E9/temps r\u00E9el.
     *
     * @param args Les arguments.
     */
    public static void jouer(String[] args) {
        options();
        if (args.length == 2) {
            SousMenu.lancerTousLesNiveaux(args[1]);
        } else if (args.length == 4 && args[2].equals("-niveau")) {
            int niveau = -1;
            try {
                niveau = Integer.valueOf(args[3]);
            } catch (Exception e) {
                err.println("Le 4eme argument doit \u00E9tre un entier.");
            }
            if (niveau > -1) {
                SousMenu.lancerNiveau(args[1], niveau);
            }
        } else {
            err.println("Argument(s) invalide(s).");
        }
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
    public static void calculer(String[] args) {
        if (args.length == 5) {
            int niveau = -1;
            try {
                niveau = Integer.valueOf(args[4]);
            } catch (Exception e) {
                err.println("Le 5eme argument doit \u00E9tre un entier.");
            }
            if (niveau > -1) {
                SousMenu.calculerStrategie(args[1], args[2], niveau);
            }
        }
        if (args.length == 6 && (args[1].equals("-evolue") || args[1].equals("-direvol"))) {
            int niveau = -1;
            int nb = -1;
            try {
                niveau = Integer.valueOf(args[5]);
                nb = Integer.valueOf(args[2]);
            } catch (Exception e) {
                err.println("Le 6eme et le 3eme argument doit \u00E9tre un entier.");
            }
            if (niveau > -1) {
                SousMenu.calculerStrategieEvol(args[1], nb, args[3], niveau);
            }
        } else {
            err.println("Argument(s) invalide(s).");
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
    public static void rejouer(String[] args) {
        if (args.length == 5) {
            Coeur.graphique = true;
            Coeur.FENETRE.setVisible(true);
            Coeur.tempsReel = true;
            int niveau = -1;
            try {
                niveau = Integer.valueOf(args[4]);
            } catch (Exception e) {
                err.println("Le 5eme argument doit \u00E9tre un entier.");
            }
            if (niveau > -1) {
                SousMenu.rejouerNiveau(args[1], args[2], niveau);
            }
        } else {
            err.println("Argument(s) invalide(s).");
        }
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
    public static void simuler(String[] args) {
        if (args.length == 7) {
            int niveau = -1;
            int nombrePartie = -1;
            try {
                niveau = Integer.valueOf(args[6]);
                nombrePartie = Integer.valueOf(args[1]);
            } catch (Exception e) {
                err.println("Le 5eme argument doit \u00E9tre un entier.");
            }
            if (niveau > -1 && nombrePartie > 0) {
                SousMenu.simulerNiveau(nombrePartie, args[2], args[3], args[4], niveau);
            }
        } else {
            err.println("Argument(s) invalide(s).");
        }
    }

    /**
     * Cette m\u00E9thode est lanc\u00E9e en d\u00E9but de programme et demande au joueur
     * diverses informations.
     * Elle change les booleans de {@link Coeur} en fonction des r\u00E9sultats.
     * Elle fait apparaitre la fen\u00E9tre {@link Coeur#FENETRE} si l'utilisateur
     * joue en mode fen\u00E9tr\u00E9.
     */
    public static void options() {

        Scanner sc;
        String reponse = "a";
        do {
            System.out.println("Voulez-vous activer le mode graphique ? (O/N)");
            sc = new Scanner(System.in);
            reponse = sc.nextLine().toUpperCase();
        } while (!reponse.equals("O") && !reponse.equals("N"));
        if (reponse.equals("O")) {
            reponse = "a";
            do {
                System.out.println("Voulez-vous activer le mode temps reel ? (O/N)");
                sc = new Scanner(System.in);
                reponse = sc.nextLine().toUpperCase();
            } while (!reponse.equals("O") && !reponse.equals("N"));
            Coeur.tempsReel = reponse.equals("O");
            Coeur.graphique = true;
            Coeur.FENETRE.setVisible(true);

        } else {
            Coeur.graphique = false;
            Coeur.FENETRE.setVisible(false);
        }

    }
}
