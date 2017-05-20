package tasks;

import main.Coeur;
import main.Constantes;
import main.Partie;

import static main.Coeur.running;
import static main.Partie.gererNiveau;

/**
 * La classe TickTask est une classe utilisee par l'objet {@link Coeur#TICKTASK}
 * afin d'effectuer les tours de jeu automatiquement quand celui-ci est en temps
 * reel.
 * Elle dispose de plusieurs atributs qui servent a compter le nombre de ticks
 * (tours) par secondes en temps reel.
 *
 * @author Murloc
 */
public class TickTask implements Runnable {

    /**
     * Un compteur qui sert a compter le nombre de ticks (tours) par secondes en
     * temps reel.
     */
    private static double compteur2 = System.nanoTime();

    /**
     * Un compteur qui sert a compter le nombre de ticks (tours) par secondes en
     * temps reel.
     */
    private static int compteur3;

    /**
     * La methode que l'objet {@link Coeur#TICKTASK} appelle un certain nombre
     * de fois par secondes.
     * Elle effectue un {@link Partie#tick()} si le booleen
     * {@link Coeur#running} est vrai et que le jeu est en mode temps reel.
     * Elle effectue egalement un repaint qui est utile dans certains cas.
     */
    @Override
    public void run() {
        if (running && !gererNiveau.isTourParTour()) {
            try {
                Partie.tick();
                compteur3++;
                /*
      Un compteur qui sert a compter le nombre de ticks (tours) par secondes en
      temps reel.
     */
                final double compteur1 = System.nanoTime();
                if (compteur1 - compteur2 > 1000000000) {
                    if (Constantes.SYSOUT_TPS) {
                        Coeur.FENETRE.setTps(compteur3);
                        Coeur.FENETRE.setTitre();
                    }
                    compteur2 = System.nanoTime();
                    compteur3 = 0;
                }
                Coeur.FENETRE.repaint();
            } catch (final RuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }

}
