package main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tasks.FrameTask;
import tasks.TickTask;
import vue.Fenetre;

public class Coeur {
	public static final Fenetre FENETRE = new Fenetre();
	public static final Controleur CONTROLEUR = new Controleur(38, 37, 40, 39, 78, 10, 16);
	public static final ControleurConsole CONTROLEUR_CONSOLE = new ControleurConsole();
	public static ScheduledExecutorService tickTask = Executors.newScheduledThreadPool(1);
	public static final ScheduledExecutorService FRAME_TASK = Executors.newScheduledThreadPool(1);
	public static boolean running = false;
	public static boolean graphique = false;
	public static boolean tempsReel = false;
	static {
		if (Constantes.FPS >= 1 && Constantes.FPS <= 120)
			FRAME_TASK.scheduleAtFixedRate(new FrameTask(), 0, 1000 / Constantes.FPS, TimeUnit.MILLISECONDS);
		else
			FRAME_TASK.scheduleAtFixedRate(new FrameTask(), 0, 1000 / 30, TimeUnit.MILLISECONDS);

	}

	public static void setTicks(int ticks) {
		if (ticks > 1000) {
			ticks = 1000;
		}
		if (tickTask != null)
			tickTask.shutdown();
		tickTask = Executors.newScheduledThreadPool(1);
		tickTask.scheduleAtFixedRate(new TickTask(), 0, (long) (1000000000 / (double)ticks), TimeUnit.NANOSECONDS);
	}
}
