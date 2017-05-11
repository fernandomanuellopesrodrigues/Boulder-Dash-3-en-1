package tasks;

import constantes.Constantes;
import main.Coeur;
import main.Partie;

public class TickTask implements Runnable {
	public static double compteur1 = 0;
	public static double compteur2 = System.nanoTime();
	public static int compteur3 = 0;

	public void run() {
		if (Coeur.running && !Partie.gererNiveau.isTourParTour()) {
			try {
				Partie.tick();
				compteur3++;
				compteur1 = System.nanoTime();
				if (compteur1 - compteur2 > 1000000000) {
					if (Constantes.SYSOUT_TPS) {
						Coeur.FENETRE.setTPS(compteur3);
						Coeur.FENETRE.setTitre();
					}
					compteur2 = System.nanoTime();
					compteur3 = 0;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}