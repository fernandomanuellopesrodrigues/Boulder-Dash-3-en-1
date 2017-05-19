package main;

import java.io.IOException;

import jline.ConsoleReader;
import vue.GraphiqueConsole;

/**
 * La classe ControleurConsole sert � g�rer les entr�es clavier et les tours de
 * boucle du jeu quand celui-ci est en mode console.
 * 
 * @author Murloc
 *
 */
public class ControleurConsole {

	/**
	 * Entiers repr�sentants les touches du clavier qui servent � jouer. Ils
	 * sont final pour pouvoi �tre utilis�s dans un switch case.
	 */
	private final int TOUCHE_HAUT = 16, TOUCHE_BAS = 14, TOUCHE_GAUCHE = 2, TOUCHE_DROITE = 6, TOUCHE_ATTENTE = 10,
			TOUCHE_RESET = 32;

	/**
	 * M�thode prenant en param�tre un objet GererNiveau.
	 * 
	 * Cette m�thode s'occupe de g�rer les tours durant la partie ainsi que les
	 * entr�es clavier.
	 * 
	 * Elle lance un thread qui affiche en boucle le niveau afin que celui-ci
	 * mette � jour le temps restant en temps r�el.
	 * 
	 * Elle effectue un tour de jeu quand le joueur appuye sur une des touches
	 * qui sert � jouer.
	 * 
	 * @param g
	 *            L'objet {@link GererNiveau} du niveau en question.
	 */
	public void run(GererNiveau g) {

		try {
			Thread t = new Thread() {
				public void run() {
					while (true) {
						if (Coeur.running) {
							GraphiqueConsole.afficher(g.getNiveau());
							g.gererTemps();
							if (g.isDemandeReset()) {
								g.tick();
							}
						}

						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
					}
				}
			};
			t.start();
			ConsoleReader console = new ConsoleReader();
			int input;
			boolean fin = false;
			while (!fin) {
				GraphiqueConsole.afficher(g.getNiveau());
				input = console.readVirtualKey();
				console.clearScreen();
				char touche = ' ';
				switch (input) {
				case TOUCHE_HAUT:
					touche = 'h';
					break;
				case TOUCHE_GAUCHE:
					touche = 'g';
					break;
				case TOUCHE_BAS:
					touche = 'b';
					break;
				case TOUCHE_DROITE:
					touche = 'd';
					break;
				case TOUCHE_ATTENTE:
					touche = 'a';
					break;
				case TOUCHE_RESET:
					touche = 'r';
					g.setDemandeReset(true);
					break;
				}
				if (g.isDemandeFin() || g.isDemandeReset()) {
					fin = true;
				}
				if (touche != ' ') {
					g.tickConsole(touche);
				}
				if (g.isDemandeFin() || g.isDemandeReset()) {
					fin = true;
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * M�thode appel�e entre deux niveaux afin d'effectuer une pause et
	 * d'attendre que le joueur appuye sur entr�e pour continuer le jeu.
	 * 
	 * @param niveau
	 *            Le niveau que le joueur vient de finir.
	 * @param score
	 *            Le score obtenu au niveau que le joueur vient de finir.
	 */
	public static void prochainNiveau(int niveau, int score) {

		ConsoleReader console;
		try {
			int input;
			console = new ConsoleReader();
			boolean fin = false;
			while (!fin) {
				System.out.println("FIN DU NIVEAU " + niveau + " , SCORE : " + score + " \n");
				System.out.println("APPUYEZ SUR ENTREE POUR PASSER AU NIVEAU SUIVANT");
				input = console.readVirtualKey();
				if (input == 10) {
					fin = true;
				}
			}
		} catch (IOException e) {
		}
	}
}
