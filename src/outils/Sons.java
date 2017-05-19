package outils;

import java.io.FileInputStream;
import java.io.InputStream;

import main.Coeur;
import main.Constantes;
import main.Partie;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Sons {
	private static final String PATH = Constantes.CHEMIN_DOSSIER_SONS;
	private boolean desactive;
	private AudioStream audioStream;

	private void charger(String nom) throws Exception {
		if (Constantes.SONS && Coeur.graphique && !Partie.IA) {
			String gongFile = PATH + nom;
			InputStream in = new FileInputStream(gongFile);
			audioStream = new AudioStream(in);
			AudioPlayer.player.start(audioStream);
		}

	}

	public void jouer(String son) {
		try {
			if (!desactive || audioStream == null) {
				charger(son);
			} else if (!AudioPlayer.player.isDaemon()) {
				AudioPlayer.player.start(audioStream);
			}
		} catch (Exception e) {
			desactive = true;
			System.err.println("Impossible de jouer le son " + son + ". En consequence, les sons ont ete desactives");
		}
	}

	public void stop() {
		AudioPlayer.player.stop(audioStream);
	}

}
