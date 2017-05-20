package outils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import main.Coeur;
import main.Constantes;
import main.Partie;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import static java.lang.System.err;

/**
 * Cette classe sert \u00E0 jouer des SONS.
 *
 * @author celso
 */
public class Sons {

    /**
     * Chemin du dossier SONS.
     */
    private static final String PATH = Constantes.CHEMIN_DOSSIER_SONS;

    /**
     * Boolean qui est vrai si les SONS ne marchent pas.
     */
    private boolean desactive;

    /**
     * Objet utile pour les SONS.
     */
    private AudioStream audioStream;

    /**
     * Cette m\u00E9thode prend un nom de fichier son en param\u00E8tre et le joue.
     *
     * @param nom Le nom du fichier.
     *
     * @throws IOException Si la lecture du son est impossible.
     */
    private void charger(final String nom) throws IOException {
        if (Constantes.SONS && Coeur.graphique && !Partie.IA) {
            final String gongFile = PATH + nom;
            final InputStream in = new FileInputStream(gongFile);
            audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        }

    }

    /**
     * Cette classe g\u00E8re l'apelle de la m\u00E9thode {@link Sons#audioStream}.
     *
     * @param son Le nom du fichier \u00E0 jouer.
     */
    public void jouer(final String son) {
        try {
            if (!desactive || audioStream == null) {
                charger(son);
            } else if (!AudioPlayer.player.isDaemon()) {
                AudioPlayer.player.start(audioStream);
            }
        } catch (final IOException ignored) {
            desactive = true;
            err.println("Impossible de jouer le son " + son + ". En consequence, les SONS ont ete desactives");
        }
    }

    /**
     * Arr\u00EAte le son en cours.
     */
    public void stop() {
        AudioPlayer.player.stop(audioStream);
    }

}
