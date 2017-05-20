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

import static java.lang.Integer.valueOf;
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

    private static final String SLASHN = "\n";

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
            final int nombreDeNiveaux = valueOf(getStr(ensembleDeNiveauxInformations, "Caves=", "\n"));
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
     * Methode qui prend en parametre un string contenant un niveau boulder dash
     * et en fait un objet {@link Niveau}.
     *
     * @param niveau Le string contenant un niveau boulder dash.
     *
     * @return Le niveau mode lis \u00E0 partir du string.
     */
    private static Niveau chargerNiveau(final String niveau) {
        final String diamond = getValue(niveau, "DiamondValue=", SLASHN, String.class, "");
        final String maps = getValue(niveau, "[map]\n", "[/map]", String.class, "");
        final int caveDelay = getValue(niveau, "CaveDelay=", SLASHN, Integer.class, 7);
        final int caveTime = getValue(niveau, "CaveTime=", SLASHN, Integer.class, 1000);
        final int diamondsRequired = getValue(niveau, "DiamondsRequired=", SLASHN, Integer.class, 0);
        final int diamondValue = getValue(niveau, "DiamondValue=", " ", Integer.class, valueOf(diamond));
        final int diamondValueBonus = getValue(diamond, " ", Integer.class, 0);
        final int amoebaTime = getValue(niveau, "AmoebaTime=", SLASHN, Integer.class, -1);
        final int magicWallTime = getValue(niveau, "MagicWallTime=", SLASHN, Integer.class, -1);
        final String[] lignes = maps.split(SLASHN);
        final int mapHauteur = lignes.length;
        final int mapLongueur = lignes[1].length();
        final Entitee[][] map = new Entitee[mapLongueur][mapHauteur];
        for (int j = 0; j < mapHauteur; j++) {
            final char[] ligne = lignes[j].toCharArray();
            for (int i = 0; i < mapLongueur; i++) {
                map[i][j] = creerEntitee(ligne[i], i, j, magicWallTime);
            }
        }
        return new Niveau(map, caveDelay, caveTime, diamondsRequired, diamondValue, diamondValueBonus, amoebaTime,
                          magicWallTime);
    }

    /**
     * Cette methode prend en parametre un fichier BDCFF et les infos de ce
     * fichier.
     *
     * @param chemin Le chemin du fichier BDCFF.
     *
     * @return Les infos de ce fichier.
     */
    public static String lireInfos(final String chemin) {
        try (BufferedReader in = new BufferedReader(new FileReader(chemin))) {
            // recuperation du fichier
            final StringBuilder builder = new StringBuilder(10);
            String s;
            while ((s = in.readLine()) != null) {
                builder.append(SLASHN).append(s);
            }
            final String ensembleDuFichier = builder.toString().replace("-", "").replace("[cave]", "-[cave]");
            final String[] niveaux = ensembleDuFichier.split("-");
            // recuperation des infromations de l'ensemble de niveaux
            return niveaux[0];
        } catch (final IOException ignored) {
            err.println("Impossible de charger le niveau");
            exit(-1);
            return null;
        }
    }

    /**
     * Cette methode cree un objet {@link Entitee} a partir de diverses
     * informations.
     * Elle est appelee par {@link Loader#chargerNiveau(String)} pour creer la
     * carte du niveau.
     *
     * @param car Le caractere representant l'entite.
     * @param x La coordonne en x de l'entite.
     * @param y La coordonne en y de l'entite.
     * @param magicWallTime Le nombre d'utilisations limites de l'entitee si celle si est
     * un mur magique.
     *
     * @return L'entitee voulue.
     */
    private static Entitee creerEntitee(final char car, final int x, final int y, final int magicWallTime) {
        final Entitee entitee;
        switch (car) {
            case 'w':
                entitee = new Mur(x, y);
                break;
            case 'd':
                entitee = new Diamant(x, y);
                break;
            case 'a':
                entitee = new Amibe(x, y);
                break;
            case 'q':
            case 'Q':
            case 'F':
            case 'o':
            case 'O':
                entitee = new Luciole(x, y);
                break;
            case 'b':
            case 'B':
            case 'c':
            case 'C':
                entitee = new Libellule(x, y);
                break;
            case 'W':
                entitee = new MurEnTitane(x, y);
                break;
            case 'r':
                entitee = new Pierre(x, y);
                break;
            case '.':
                entitee = new Poussiere(x, y);
                break;
            case 'P':
                entitee = new Rockford(x, y);
                break;
            case 'X':
                entitee = new Sortie(x, y);
                break;
            case 'M':
                entitee = new MurMagique(x, y, magicWallTime);
                break;
            case ' ':
                entitee = new Vide(x, y);
                break;
            default:
                entitee = new Diamant(x, y);
        }
        return entitee;
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

    private static <T> T getValue(final String texte,
                                  final String basliseEntrante,
                                  final String baliseSortant,
                                  final Class<T> type,
                                  final T defaultValue) {
        try {
            return type.cast(getStr(texte, basliseEntrante, baliseSortant));
        } catch (final ClassCastException ignored) {
            return defaultValue;
        }
    }

    private static <T> T getValue(final String texte,
                                  final String basliseEntrante,
                                  final Class<T> type,
                                  final T defaultValue) {
        try {
            return type.cast(getStr(texte, basliseEntrante));
        } catch (final ClassCastException ignored) {
            return defaultValue;
        }
    }
}
