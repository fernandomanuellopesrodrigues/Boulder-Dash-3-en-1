package tasks;

import main.Coeur;
import main.Constantes;
import vue.Fenetre;

/**
 * La classe FrameTask est une classe utilis�e par l'objet
 * {@link Coeur#FRAME_TASK} afin d'effectuer les frames de jeu automatiquement
 * quand celui-ci est en temps r�el.
 * Elle dispose de plusieurs atributs qui servent � compter le nombre de frames
 * par secondes en temps r�el.
 *
 * @author Murloc
 */
public class FrameTask implements Runnable {

    /**
     * Un compteur qui sert � compter le nombre de ticks (tours) par secondes en
     * temps r�el.
     */
    public static double compteur1 = 0;

    /**
     * Un compteur qui sert � compter le nombre de ticks (tours) par secondes en
     * temps r�el.
     */
    public static double compteur2 = System.nanoTime();

    /**
     * Un compteur qui sert � compter le nombre de ticks (tours) par secondes en
     * temps r�el.
     */
    public static int compteur3 = 0;

    /**
     * La m�thode que l'objet {@link Coeur#FRAME_TASK} appelle un certain nombre
     * de fois par secondes.
     * Elle effectue un {@link Fenetre#repaint()} si le booleen
     * {@link Coeur#running} est vrai, que le jeu est en mode temps r�el et en
     * mode graphique.
     */
    public void run() {
        if (Coeur.graphique && Coeur.running) {
            try {
                Coeur.FENETRE.repaint();
                FrameTask.compteur3++;
                compteur1 = System.nanoTime();
                if (compteur1 - compteur2 > 1000000000) {
                    if (Constantes.SYSOUT_FPS) {
                        Coeur.FENETRE.setFps(compteur3);
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
