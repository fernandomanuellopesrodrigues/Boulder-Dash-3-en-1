package outils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class Sons {
    private static final String PATH = "ressources/sons/";
    private boolean desactive;
    private AudioStream audioStream;

   
    private void charger(String nom) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        String gongFile = PATH + nom;
        InputStream in = new FileInputStream(gongFile);
        audioStream = new AudioStream(in);
        AudioPlayer.player.start(audioStream);
    }


  
    public void jouer(String son) {
        try {
            if (!desactive || audioStream == null) {
                charger(son);
            } else if(!AudioPlayer.player.isDaemon()) {
                AudioPlayer.player.start(audioStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Impossible de jouer le son " + son + ". En consequence, les sons ont ete desactives");
        }
    }
    
    public void stop(){
             AudioPlayer.player.stop(audioStream);
    }


}
