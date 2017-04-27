package tasks;

import main.Coeur;
import main.Partie;

public class TickTask implements Runnable {

	public void run() {
		if (Coeur.running && !Partie.gererNiveau.isTourParTour()) {
			try {
				Partie.tick();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}