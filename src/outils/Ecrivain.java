package outils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import main.Partie;

/**
 * La classe Ecrivain est une classe qui n'est jamais instanci�e, elle dipose de
 * m�thodes statiques servant � cr�er et lire des fichier DASH.
 * 
 * @author Murloc
 *
 */
public class Ecrivain {
	/**
	 * Cette methode cr�e un fichier et �crit du texte dedans.
	 * 
	 * @param aEcrire
	 *            Le texte qui sera �crit dans le fichier.
	 * @param nom
	 *            Le nom du fichier futurement cr��.
	 * @param repertoire
	 *            Le nom du dossier o� le fichier doit �tre cr��.
	 */
	public static void ecrire(String aEcrire, String nom, String repertoire) {
		try {
			File dir = new File(repertoire);
			dir.mkdirs();
			File destinationFile = new File(repertoire + nom);
			destinationFile.createNewFile();
			PrintWriter ecrivain = new PrintWriter(repertoire + nom, "UTF-8");
			ecrivain.print(aEcrire);
			if(Partie.IA){
				System.out.println("\nMeilleur trajet enregistré sous : " + repertoire + nom);
			}else
			System.out.println("\nChemin parcouru enregistre sous : " + repertoire + nom);
			ecrivain.close();
		} catch (Exception e) {
			System.err.println("Impossible d'enregistrer le chemin parcouru");
		}
	}

	/**
	 * Cette m�thode prend en param�tre le chemin d'un fichier DASH et renvoie
	 * le parcours �crit dans le fichier.
	 * 
	 * @param cheminFichierBD
	 *            Chemin du fichier DASH.
	 * @return Le parcours �crit dans le fichier.
	 */
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
