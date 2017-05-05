package main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import constantes.Constantes;
import tasks.FrameTask;
import tasks.TickTask;
import vue.Fenetre;

public class Coeur {
	public static final Fenetre FENETRE = new Fenetre();
	public static final Controleur CONTROLEUR = new Controleur(38, 37, 40, 39, 78,10);
	public static ScheduledExecutorService TICK_TASK = Executors.newScheduledThreadPool(1);
	public static final ScheduledExecutorService FRAME_TASK = Executors.newScheduledThreadPool(1);
	public static boolean running = false;
	public static boolean graphique;
	public static boolean tempsReel;
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
		if (TICK_TASK != null)
			TICK_TASK.shutdown();
		TICK_TASK = Executors.newScheduledThreadPool(1);
		TICK_TASK.scheduleAtFixedRate(new TickTask(), 0, 1000 / ticks, TimeUnit.MILLISECONDS);
	}
}
