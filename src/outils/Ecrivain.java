package outils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * La classe Ecrivain est une classe qui n'est jamais instanciée, elle dipose de
 * méthodes statiques servant à créer et lire des fichier DASH.
 * 
 * @author Murloc
 *
 */
public class Ecrivain {
	/**
	 * Cette methode crée un fichier et écrit du texte dedans.
	 * 
	 * @param aEcrire
	 *            Le texte qui sera écrit dans le fichier.
	 * @param nom
	 *            Le nom du fichier futurement créé.
	 * @param repertoire
	 *            Le nom du dossier où le fichier doit être créé.
	 */
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

	/**
	 * Cette méthode prend en paramètre le chemin d'un fichier DASH et renvoie
	 * le parcours écrit dans le fichier.
	 * 
	 * @param cheminFichierBD
	 *            Chemin du fichier DASH.
	 * @return Le parcours écrit dans le fichier.
	 */
	public static String lireParcours(String cheminFichierBD) {
		try {
			FileReader lecteur = new FileReader(cheminFichierBD);
			BufferedReader in = new BufferedReader(lecteur);

			// récupération du fichier
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
			System.err.println("Impossible de lire le fichier indiquÃ©, mode alÃ©atoire lancÃ©.");
			return "";
		}
	}
}
