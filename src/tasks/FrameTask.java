package tasks;

import main.Coeur;
import main.Constantes;
import vue.Fenetre;

public class FrameTask implements Runnable {
	public static double compteur1 = 0;
	public static double compteur2 = System.nanoTime();
	public static int compteur3 = 0;

	public void run() {
		if (Coeur.graphique && Coeur.running) {
			try {
				Coeur.FENETRE.repaint();
				FrameTask.compteur3++;
				compteur1 = System.nanoTime();
				if (compteur1 - compteur2 > 1000000000) {
					if (Constantes.SYSOUT_FPS) {
						Coeur.FENETRE.setFPS(compteur3);
						Coeur.FENETRE.setTitre();
					}
					compteur2 = System.nanoTime();
					compteur3 = 0;
				}
			} catch (Exception e) {
			}
		}
	}

}