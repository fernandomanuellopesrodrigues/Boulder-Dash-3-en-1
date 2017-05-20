package main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import controleurs.Controleur;
import controleurs.ControleurConsole;
import tasks.FrameTask;
import tasks.TickTask;
import vue.Fenetre;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static main.Constantes.FPS;

/**
 * La classe Coeur n'est jamais instanciee.
 * Elle dispose de plusieurs objets static essentiels au fonctionnement du
 * programme.
 *
 * @author Murloc
 */
public class Coeur {

    /**
     * La fenetre de jeu servant si le jeu est en mode graphique.
     */
    public static final Fenetre FENETRE = new Fenetre();

    /**
     * Le controleur servant si le jeu est en mode graphique.
     */
    public static final Controleur CONTROLEUR = new Controleur(38, 37, 40, 39, 10, 16);

    /**
     * Le controleur console servant si le jeu n'est pas en mode graphique.
     */
    public static final ControleurConsole        CONTROLEUR_CONSOLE = new ControleurConsole();
    /**
     * L'objet permettant d'effectuer des frames de jeu a un rythme regulier
     * dans un thread quand le jeu est en mode graphique.
     */
    public static final ScheduledExecutorService FRAME_TASK         = Executors.newScheduledThreadPool(1);
    /**
     * L'objet permettant de realiser des tours de jeu a un rythme regulier dans
     * un thread quand le jeu est en mode temps reel.
     */
    public static       ScheduledExecutorService TICKTASK           = Executors.newScheduledThreadPool(1);
    /**
     * Boolean definissant si une partie est en train d'avoir lieu ou pas.
     * Si le joueur est en train de jouer il est vrai.
     * Si le programme calcule une strategie ou alors qu'il est en train
     * d'effectuer un changement de niveau il est en faux.
     */
    public static boolean running;

    /**
     * Boolean definissant si une partie est en mode temps graphique ou console.
     */
    public static boolean graphique;

    /**
     * Boolean definissant si une partie est en mode temps r�el.
     */
    public static boolean tempsReel;

    /**
     * On initialsie l'objet qui effectue des FPS tout en limitant les FPS si
     * ceux-ci sont absurdes. (Oui 120 FPS pour ce jeu c'est deja absurde.)
     */
    static {
        if (FPS >= 1 && FPS <= 120) {
            FRAME_TASK.scheduleAtFixedRate(new FrameTask(), 0, 1000 / FPS, MILLISECONDS);
        } else {
            FRAME_TASK.scheduleAtFixedRate(new FrameTask(), 0, 1000 / 30, MILLISECONDS);
        }
    }

    /**
     * Methode servant a lancer l'objet qui effectue des tours de jeu dans un
     * thread ({@link Coeur#TICKTASK}.
     * Elle prend en parametre le nombre de tours par secondes qu'elle doit
     * effetuer et cree un nouvel objet en fonction.
     *
     * @param ticks Le nombre de tours par secondes voulu.
     */
    public static void setTicks(int ticks) {
        if (ticks > 1000) {
            ticks = 1000;
        }
        if (TICKTASK != null) {
            TICKTASK.shutdown();
        }
        TICKTASK = Executors.newScheduledThreadPool(1);
        TICKTASK.scheduleAtFixedRate(new TickTask(), 0, (long) (1000000000 / (double) ticks), NANOSECONDS);
    }
}
