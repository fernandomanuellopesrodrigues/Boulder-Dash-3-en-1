package tasks;

import main.Coeur;
import main.Partie;

public class TickTask implements Runnable {

	public void run() {
		if (Coeur.running) {
			try {
				Partie.tick();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}