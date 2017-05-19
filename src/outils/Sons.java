package outils;

import java.io.FileInputStream;
import java.io.InputStream;

import main.Coeur;
import main.Constantes;
import main.Partie;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Cette classe sert à jouer des sons.
 *
 * @author celso
 */
public class Sons {

    /**
     * Chemin du dossier sons.
     */
    private static final String PATH = Constantes.CHEMIN_DOSSIER_SONS;

    /**
     * Boolean qui est vrai si les sons ne marchent pas.
     */
    private boolean desactive;

    /**
     * Objet utile pour les sons.
     */
    private AudioStream audioStream;

    /**
     * Cette méthode prend un nom de fichier son en paramètre et le joue.
     *
     * @param nom Le nom du fichier.
     *
     * @throws Exception Si la lecture du son est impossible.
     */
    private void charger(String nom) throws Exception {
        if (Constantes.SONS && Coeur.graphique && !Partie.IA) {
            String gongFile = PATH + nom;
            InputStream in = new FileInputStream(gongFile);
            audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        }

    }

    /**
     * Cette classe gère l'apelle de la méthode {@link Sons#audioStream}.
     *
     * @param son Le nom du fichier à jouer.
     */
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

    /**
     * Arrête le son en cours.
     */
    public void stop() {
        AudioPlayer.player.stop(audioStream);
    }

}
