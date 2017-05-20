package outils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import main.Partie;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * La classe Ecrivain est une classe qui n'est jamais instanci\u00E9e, elle dipose de
 * m\u00E9thodes statiques servant \u00E0 cr\u00E9er et lire des fichier DASH.
 *
 * @author Murloc
 */
public final class Ecrivain {

    private Ecrivain() {}

    /**
     * Cette methode cree un fichier et \u00E9crit du texte dedans.
     *
     * @param aEcrire Le texte qui sera \u00E9crit dans le fichier.
     * @param nom Le nom du fichier futurement cr\u00E9e.
     * @param repertoire Le nom du dossier ou le fichier doit \u00E9tre cr\u00E9e.
     */
    public static void ecrire(final String aEcrire, final String nom, final String repertoire) {
        try (PrintWriter ecrivain = new PrintWriter(repertoire + nom, "UTF-8")) {
            final File dir = new File(repertoire);
            dir.mkdirs();
            final File destinationFile = new File(repertoire + nom);
            destinationFile.createNewFile();
            ecrivain.print(aEcrire);
            if (Partie.iaValid) {
                out.printf("\nMeilleur trajet enregistr\u00E9 sous : %s%s%n", repertoire, nom);
            } else {
                out.printf("\nChemin parcouru enregistre sous : %s%s%n", repertoire, nom);
            }
        } catch (final IOException e) {
            e.printStackTrace();
            err.println("Impossible d'enregistrer le chemin parcouru");
        }
    }

    /**
     * Cette methode prend en parametre le chemin d'un fichier DASH et renvoie
     * le parcours ecrit dans le fichier.
     *
     * @param cheminFichierBD Chemin du fichier DASH.
     *
     * @return Le parcours ecrit dans le fichier.
     */
    public static String lireParcours(final String cheminFichierBD) {
        try (final BufferedReader in = new BufferedReader(new FileReader(cheminFichierBD))) {
            // recupuration du fichier
            final StringBuilder contenuLu = new StringBuilder(10);
            String line;
            while ((line = in.readLine()) != null) {
                contenuLu.append("\n").append(line);
            }
            final String[] parcours = contenuLu.toString()
                                               .replace("Trajet : ", "-")
                                               .replace("\nScore :", "-")
                                               .split("-");
            return parcours[1];
        } catch (final IOException ignored) {
            err.println("Impossible de lire le fichier indiqu\u00E9, mode al\u00E9atoire lanc\u00E9.");
            return "";
        }
    }
}
