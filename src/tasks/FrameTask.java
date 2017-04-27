package tasks;

import main.Coeur;
import vue.Fenetre;

public class FrameTask implements Runnable {

	public void run() {
		if (Coeur.graphique && Coeur.running) {
			try {
				Coeur.FENETRE.repaint();
			} catch (Exception e) {
			}
		}
	}

}