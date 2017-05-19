package main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tasks.FrameTask;
import tasks.TickTask;
import vue.Fenetre;

/**
 * La classe Coeur n'est jamais instanci�e.
 * 
 * Elle dispose de plusieurs objets static essentiels au fonctionnement du
 * programme.
 * 
 * @author Murloc
 *
 */
public class Coeur {

	/**
	 * La fen�tre de jeu servant si le jeu est en mode graphique.
	 */
	public static final Fenetre FENETRE = new Fenetre();

	/**
	 * Le controleur servant si le jeu est en mode graphique.
	 */
	public static final Controleur CONTROLEUR = new Controleur(38, 37, 40, 39, 78, 10);

	/**
	 * Le controleur console servant si le jeu n'est pas en mode graphique.
	 */
	public static final ControleurConsole CONTROLEUR_CONSOLE = new ControleurConsole();

	/**
	 * L'objet permettant de r�aliser des tours de jeu � un rythme r�gulier dans
	 * un thread quand le jeu est en mode temps r�el.
	 */
	public static ScheduledExecutorService tickTask = Executors.newScheduledThreadPool(1);

	/**
	 * L'objet permettant d'effectuer des frames de jeu � un rythme r�gulier
	 * dans un thread quand le jeu est en mode graphique.
	 */
	public static final ScheduledExecutorService FRAME_TASK = Executors.newScheduledThreadPool(1);

	/**
	 * Bool�an d�finissant si une partie est en train d'avoir lieu ou pas.
	 * 
	 * Si le joueur est en train de jouer il est vrai.
	 * 
	 * Si le programme calcule une strat�gie ou alors qu'il est en train
	 * d'effectuer un changement de niveau il est en faux.
	 */
	public static boolean running = false;

	/**
	 * Bool�an d�finissant si une partie est en mode temps graphique ou console.
	 */
	public static boolean graphique = false;

	/**
	 * Bool�an d�finissant si une partie est en mode temps r�el.
	 */
	public static boolean tempsReel = false;

	/**
	 * On initialsie l'objet qui effectue des FPS tout en limitant les FPS si
	 * ceux-ci sont absurdes. (Oui 120 FPS pour ce jeu c'est d�j� absurde.)
	 */
	static {
		if (Constantes.FPS >= 1 && Constantes.FPS <= 120)
			FRAME_TASK.scheduleAtFixedRate(new FrameTask(), 0, 1000 / Constantes.FPS, TimeUnit.MILLISECONDS);
		else
			FRAME_TASK.scheduleAtFixedRate(new FrameTask(), 0, 1000 / 30, TimeUnit.MILLISECONDS);

	}

	/**
	 * M�thode servant � lancer l'objet qui effectue des tours de jeu dans un
	 * thread ({@link Coeur#tickTask}.
	 * 
	 * Elle prend en param�tre le nombre de tours par secondes qu'elle doit
	 * effetuer et cr�e un nouvel objet en fonction.
	 * 
	 * @param ticks
	 *            Le nombre de tours par secondes voulu.
	 */
	public static void setTicks(int ticks) {
		if (ticks > 1000) {
			ticks = 1000;
		}
		if (tickTask != null)
			tickTask.shutdown();
		tickTask = Executors.newScheduledThreadPool(1);
		tickTask.scheduleAtFixedRate(new TickTask(), 0, (long) (1000000000 / (double) ticks), TimeUnit.NANOSECONDS);
	}
}
