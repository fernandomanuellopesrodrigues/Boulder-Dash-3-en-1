package menu;

import java.util.Scanner;

import main.Coeur;

import static main.Constantes.HELP;
import static main.Constantes.NOMS;

/**
 * La classe Menu contient le main ainsi que d'autres m�thodes statics g�rant la
 * lecture des param�tres du programme.
 * Elle lance des m�thodes de la classe {@link SousMenu} en fonction des
 * param�tres entr�s.
 *
 * @author Murloc
 */
public class Menu {

    /**
     * La m�thode main.
     * Elle lance d'autres m�thodes en fonction des param�tres.
     *
     * @param args Les arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-name")) {
                System.out.println(NOMS);
            } else if (args[0].equals("-h")) {
                System.out.println(HELP);
            } else if (args[0].equals("-lis")) {
                lire(args);
            } else if (args[0].equals("-joue")) {
                jouer(args);
            } else if (args[0].equals("-cal")) {
                calculer(args);
            } else if (args[0].equals("-rejoue")) {
                rejouer(args);
            } else if (args[0].equals("-simul")) {
                simuler(args);
            } else if (args[0].equals("-parfait")) {
                System.out.println("\nL'IA parfaite n'est pas encore présente dans cette version.");
            } else {
                System.err.println("Argument(s) non reconnu(s).");
            }
        } else {
            System.err.println("Argument(s) manquant(s), entrez l'argument -h pour optenir de l'aide.");
        }

    }

    /**
     * Cette m�thode est appel�e si l'utilisateur a rentr� l'argument "-lis",
     * elle teste si le nombre d'arguments entr�s est correct puis lance
     * {@link SousMenu#lireInfos(String)} si oui.
     *
     * @param args Les arguments.
     */
    public static void lire(String[] args) {
        if (args.length == 2) {
            SousMenu.lireInfos(args[1]);
        } else {
            System.err.println("Argument(s) invalide(s).");
        }
    }

    /**
     * Cette m�thode est appel�e si l'utilisateur a rentr� l'argument "-joue",
     * elle teste si le nombre d'arguments entr�s est correct puis lance
     * d'autres m�thodes si oui.
     * Lance la m�thode {@link Menu#options()} au d�but pour savoir si oui ou
     * non le joueur veut jouer en mode console/fen�tr�/temps r�el.
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
                System.err.println("Le 4eme argument doit �tre un entier.");
            }
            if (niveau > -1) {
                SousMenu.lancerNiveau(args[1], niveau);
            }
        } else {
            System.err.println("Argument(s) invalide(s).");
        }
    }

    /**
     * Cette m�thode est appel�e si l'utilisateur a rentr� l'argument "-cal",
     * elle teste si le nombre d'arguments entr�s est correct puis lance
     * d'autres m�thodes si oui.
     * Elle lance {@link SousMenu#calculerStrategie(String, String, int)} si
     * l'ia voulue n'est pas �volutive ou
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
                System.err.println("Le 5eme argument doit �tre un entier.");
            }
            if (niveau > -1) {
                SousMenu.calculerStrategie(args[1], args[2], niveau);
            }
        }
        if (args.length == 6 && ((args[1].equals("-evolue")) || (args[1].equals("-direvol")))) {
            int niveau = -1;
            int nb = -1;
            try {
                niveau = Integer.valueOf(args[5]);
                nb = Integer.valueOf(args[2]);
            } catch (Exception e) {
                System.err.println("Le 6eme et le 3eme argument doit �tre un entier.");
            }
            if (niveau > -1) {
                SousMenu.calculerStrategieEvol(args[1], nb, args[3], niveau);
            }
        } else {
            System.err.println("Argument(s) invalide(s).");
        }
    }

    /**
     * Cette m�thode est appel�e si l'utilisateur a rentr� l'argument "-rejoue",
     * elle teste si le nombre d'arguments entr�s est correct puis lance
     * d'autres m�thodes si oui.
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
                System.err.println("Le 5eme argument doit �tre un entier.");
            }
            if (niveau > -1) {
                SousMenu.rejouerNiveau(args[1], args[2], niveau);
            }
        } else {
            System.err.println("Argument(s) invalide(s).");
        }
    }

    /**
     * Cette m�thode est appel�e si l'utilisateur a rentr� l'argument "-simul",
     * elle teste si le nombre d'arguments entr�s est correct puis lance
     * d'autres m�thodes si oui.
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
                System.err.println("Le 5eme argument doit �tre un entier.");
            }
            if (niveau > -1 && nombrePartie > 0) {
                SousMenu.simulerNiveau(nombrePartie, args[2], args[3], args[4], niveau);
            }
        } else {
            System.err.println("Argument(s) invalide(s).");
        }
    }

    /**
     * Cette m�thode est lanc�e en d�but de programme et demande au joueur
     * diverses informations.
     * Elle change les booleans de {@link Coeur} en fonction des r�sultats.
     * Elle fait apparaitre la fen�tre {@link Coeur#FENETRE} si l'utilisateur
     * joue en mode fen�tr�.
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
            if (reponse.equals("O")) {
                Coeur.tempsReel = true;
            } else {
                Coeur.tempsReel = false;
            }
            Coeur.graphique = true;
            Coeur.FENETRE.setVisible(true);

        } else {
            Coeur.graphique = false;
            Coeur.FENETRE.setVisible(false);
        }

    }
}
