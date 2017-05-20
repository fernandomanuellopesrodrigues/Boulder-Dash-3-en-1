package loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import entitees.abstraites.Entitee;
import entitees.fixes.Amibe;
import entitees.fixes.Mur;
import entitees.fixes.MurEnTitane;
import entitees.fixes.MurMagique;
import entitees.fixes.Poussiere;
import entitees.fixes.Sortie;
import entitees.fixes.Vide;
import entitees.tickables.Diamant;
import entitees.tickables.Libellule;
import entitees.tickables.Luciole;
import entitees.tickables.Pierre;
import entitees.tickables.Rockford;

import static java.lang.System.err;
import static java.lang.System.exit;

/**
 * La classe Loader n'est jamais instanciee, elle sert a creer des ensembles de
 * niveaux a partir des fichiers BDCFF.
 *
 * @author Murloc
 * @see EnsembleDeNiveaux
 */
public final class Loader {

    private Loader() {}

    /**
     * Cette methode prend en parametre un fichier BDCFF et renvoie un ensemble
     * de niveaux ayant la liste de tous les niveaux presents dans le fichier
     * BDCFF.
     * Elle decoupe les fichiers en bouts de string qu'elle envoie a
     * {@link Loader#chargerNiveau(String)} qui en fait des objets
     * {@link Niveau}.
     *
     * @param chemin Le chemin du fichier BDCFF.
     *
     * @return Ensemble de niveaux contenant la liste de tous les niveaux
     * presents dans le fichier BDCFF.
     */
    public static EnsembleDeNiveaux chargerEnsembleDeNiveaux(final String chemin) {
        try (BufferedReader in = new BufferedReader(new FileReader(chemin))) {
            // recuperation du fichier
            final StringBuilder builder = new StringBuilder(10);
            String s;
            while ((s = in.readLine()) != null) {
                builder.append("\n").append(s);
            }
            final String ensembleDuFichier = builder.toString().replace("-", "").replace("[cave]", "-[cave]");
            final String[] niveaux = ensembleDuFichier.split("-");

            // recuperation des infromations de l'ensemble de niveaux
            final String ensembleDeNiveauxInformations = niveaux[0];
            final int nombreDeNiveaux = Integer.valueOf(getStr(ensembleDeNiveauxInformations, "Caves=", "\n"));
            final EnsembleDeNiveaux ensemble = new EnsembleDeNiveaux(nombreDeNiveaux);
            // recuperation des niveaux
            for (int i = 1; i <= ensemble.getNombreDeNiveaux(); i++) {
                final Niveau niveau = chargerNiveau(niveaux[i]);
                if (niveau != null) {
                    ensemble.ajouterNiveau(niveau);
                }
            }
            ensemble.setNbNiveaux(ensemble.getNiveaux().size());
            return ensemble;
        } catch (final IOException ignored) {
            err.println("Impossible de charger les niveaux.");
            exit(-1);
            return null;
        }

    }

    /**
     * M�thode qui prend en parametre un string contenant un niveau boulder dash
     * et en fait un objet {@link Niveau}.
     *
     * @param niveau Le string contenant un niveau boulder dash.
     *
     * @return Le niveau mode lis à partir du string.
     */
    private static Niveau chargerNiveau(final String niveau) {
        final String diamond = getStr(niveau, "DiamondValue=", "\n");
        final String maps = getStr(niveau, "[map]\n", "[/map]");
        int caveDelay;
        try {
            caveDelay = Integer.valueOf(getStr(niveau, "CaveDelay=", "\n"));
            if (caveDelay == 0) {
                caveDelay = 1;
            }
        } catch (Exception e) {
            caveDelay = 7;
        }
        int cave_time;
        try {
            cave_time = Integer.valueOf(getStr(niveau, "CaveTime=", "\n"));
        } catch (Exception e) {
            cave_time = 1000;
        }
        int diamonds_required;
        try {
            diamonds_required = Integer.valueOf(getStr(niveau, "DiamondsRequired=", "\n"));
        } catch (Exception e) {
            diamonds_required = 0;
        }
        int diamond_value;
        try {
            diamond_value = Integer.valueOf(getStr(niveau, "DiamondValue=", " "));
        } catch (Exception e) {
            diamond_value = Integer.valueOf(getStr(niveau, "DiamondValue=", "\n"));
        }

        int diamond_value_bonus;
        try {
            diamond_value_bonus = Integer.valueOf(getStr(diamond, " "));
        } catch (Exception e) {
            diamond_value_bonus = 0;
        }
        int amoeba_time;
        try {
            amoeba_time = Integer.valueOf(getStr(niveau, "AmoebaTime=", "\n"));
        } catch (Exception e) {
            amoeba_time = -1;
        }
        int magic_wall_time;
        try {
            magic_wall_time = Integer.valueOf(getStr(niveau, "MagicWallTime=", "\n"));
        } catch (Exception e) {
            magic_wall_time = -1;
        }

        String[] lignes = maps.split("\n");
        int mapHauteur = lignes.length;
        int mapLongueur = lignes[1].length();
        Entitee[][] map = new Entitee[mapLongueur][mapHauteur];
        for (int j = 0; j < mapHauteur; j++) {
            char[] ligne = lignes[j].toCharArray();
            for (int i = 0; i < mapLongueur; i++) {
                map[i][j] = creerEntitee(ligne[i], i, j, magic_wall_time);
            }
        }
        return new Niveau(map, caveDelay, cave_time, diamonds_required, diamond_value, diamond_value_bonus, amoeba_time,
                          magic_wall_time);
    }

    /**
     * Cette m�thode prend en param�tre un fichier BDCFF et les infos de ce
     * fichier.
     *
     * @param chemin Le chemin du fichier BDCFF.
     *
     * @return Les infos de ce fichier.
     */
    public static String lireInfos(String chemin) {
        String[] niveaux;

        try {
            // lecture du fichier
            FileReader lecteur = new FileReader(chemin);
            BufferedReader in = new BufferedReader(lecteur);

            // r�cup�ration du fichier
            String ensemble_du_fichier = "";
            String s;
            while ((s = in.readLine()) != null) {
                ensemble_du_fichier += "\n" + s;
            }
            in.close();
            ensemble_du_fichier = ensemble_du_fichier.replace("-", "");
            ensemble_du_fichier = ensemble_du_fichier.replace("[cave]", "-[cave]");

            niveaux = ensemble_du_fichier.split("-");

            // r�cup�ration des infromations de l'ensemble de niveaux
            String ensemble_de_niveaux_informations = niveaux[0];
            return ensemble_de_niveaux_informations;
        } catch (Exception e) {
            err.println("Impossible de charger le niveau");
            return "\n";
        }
    }

    /**
     * Cette m�thode cr�e un objet {@link Entitee} � partir de diverses
     * informations.
     * Elle est appel�e par {@link Loader#chargerNiveau(String)} pour cr�er la
     * carte du niveau.
     *
     * @param car Le caract�re repr�sentant l'entit�e.
     * @param x La coordonn�e en x de l'entit�e.
     * @param y La coordonn�e en y de l'entit�e.
     * @param magicWallTime Le nombre d'utilisations limites de l'entit�e si celle si est
     * un mur magique.
     *
     * @return L'entit�e voulue.
     */
    public static Entitee creerEntitee(char car, int x, int y, int magicWallTime) {
        if (car == 'w') {
            return new Mur(x, y);
        } else if (car == 'd') {
            return new Diamant(x, y);
        } else if (car == 'a') {
            return new Amibe(x, y);
        } else if (car == 'q' || car == 'Q' || car == 'F' || car == 'o' || car == 'O') {
            return new Luciole(x, y);
        } else if (car == 'b' || car == 'B' || car == 'c' || car == 'C') {
            return new Libellule(x, y);
        } else if (car == 'W') {
            return new MurEnTitane(x, y);
        } else if (car == 'r') {
            return new Pierre(x, y);
        } else if (car == '.') {
            return new Poussiere(x, y);
        } else if (car == 'P') {
            return new Rockford(x, y);
        } else if (car == 'X') {
            return new Sortie(x, y);
        } else if (car == 'M') {
            return new MurMagique(x, y, magicWallTime);
        } else if (car == ' ') {
            return new Vide(x, y);
        } else {
            return new Diamant(x, y);
        }
    }

    /**
     * Prend un string en parametre et renvoit un sous-string se trouvant dans
     * le premier string qui est delimite par le deuxieme string et le troisieme
     * string tous deux en parametre.*
     *
     * @param texte Le string source.
     * @param basliseEntrante Le delimiteur 1.
     * @param baliseSortante Le delimiteur 2.
     *
     * @return Le resultat.
     */
    private static String getStr(final String texte, final String basliseEntrante, final String baliseSortante) {
        final String str = getStr(texte, basliseEntrante);
        return str.substring(str.indexOf(basliseEntrante) + 1, str.indexOf(baliseSortante));
    }

    /**
     * Prend un string en parametre et renvoit un sous-string se trouvant dans
     * le premier string qui est delimite par le deuxieme string en parametre.
     *
     * @param texte Le string source.
     * @param baliseEntrante Le delimiteur.
     *
     * @return Le resultat.
     */
    private static String getStr(final String texte, final String baliseEntrante) {
        return texte.substring(texte.indexOf(baliseEntrante) + baliseEntrante.length());
    }

}
