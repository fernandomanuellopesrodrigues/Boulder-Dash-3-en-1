package tasks;

import main.Coeur;
import main.Constantes;
import main.Partie;

/**
 * La classe TickTask est une classe utilisée par l'objet {@link Coeur#tickTask}
 * afin d'effectuer les tours de jeu automatiquement quand celui-ci est en temps
 * réel.
 * 
 * Elle dispose de plusieurs atributs qui servent à compter le nombre de ticks
 * (tours) par secondes en temps réel.
 * 
 * @author Murloc
 *
 */
public class TickTask implements Runnable {
	/**
	 * Un compteur qui sert à compter le nombre de ticks (tours) par secondes en
	 * temps réel.
	 */
	public static double compteur1 = 0;

	/**
	 * Un compteur qui sert à compter le nombre de ticks (tours) par secondes en
	 * temps réel.
	 */
	public static double compteur2 = System.nanoTime();

	/**
	 * Un compteur qui sert à compter le nombre de ticks (tours) par secondes en
	 * temps réel.
	 */
	public static int compteur3 = 0;

	/**
	 * La méthode que l'objet {@link Coeur#tickTask} appelle un certain nombre
	 * de fois par secondes.
	 * 
	 * Elle effectue un {@link Partie#tick()} si le booleen
	 * {@link Coeur#running} est vrai et que le jeu est en mode temps réel.
	 * 
	 * Elle effectue également un repaint qui est utile dans certains cas.
	 */
	public void run() {
		if (Coeur.running && !Partie.gererNiveau.isTourParTour()) {
			try {
				Partie.tick();
				compteur3++;
				compteur1 = System.nanoTime();
				if (compteur1 - compteur2 > 1000000000) {
					if (Constantes.SYSOUT_TPS) {
						Coeur.FENETRE.setTitre();
					}
					compteur2 = System.nanoTime();
					compteur3 = 0;
				}
				Coeur.FENETRE.repaint();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}