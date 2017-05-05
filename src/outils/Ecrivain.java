package outils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Ecrivain {
    public static void ecrire(String aEcrire, String nom, String repertoire) {
        try {
            File dir = new File(repertoire);
            dir.mkdirs();
            File destinationFile = new File(repertoire + nom);
            destinationFile.createNewFile();
            PrintWriter ecrivain = new PrintWriter(repertoire + nom, "UTF-8");
            ecrivain.print(aEcrire);
            System.out.println("Chemin parcouru enregistre sous : " + repertoire + nom);
            ecrivain.close();
        } catch (Exception e) {
            System.err.println("Impossible d'enregistrer le chemin parcouru");
        }
    }

    public static String lireParcours(String cheminFichierBD) {
        try {
            FileReader lecteur = new FileReader(cheminFichierBD);
            BufferedReader in = new BufferedReader(lecteur);

            // r�cup�ration du fichier
            String ensemble_du_fichier = "";
            String s;

            while ((s = in.readLine()) != null) {
                ensemble_du_fichier += "\n" + s;
            }
            in.close();
            ensemble_du_fichier = ensemble_du_fichier.replace("Trajet : ", "-");
            ensemble_du_fichier = ensemble_du_fichier.replace("\nScore :", "-");
            String[] parcours;
            parcours = ensemble_du_fichier.split("-");
            return parcours[1];
        } catch (Exception e) {
            System.err.println("Impossible de lire le fichier indiqué, mode aléatoire lancé.");
            return "";
        }
    }
}
