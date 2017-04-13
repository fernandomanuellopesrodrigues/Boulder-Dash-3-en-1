package main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import vue.Fenetre;

public class Coeur {
	public static final Fenetre FENETRE = new Fenetre();
	public static final Controleur CONTROLEUR = new Controleur(38, 37, 40, 39, 78);
	public static final ScheduledExecutorService TASKS = Executors.newScheduledThreadPool(2);
	public static boolean running = false;
	public static boolean graphique = false;
}
