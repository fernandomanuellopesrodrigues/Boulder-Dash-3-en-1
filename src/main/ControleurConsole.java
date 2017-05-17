package main;

import java.io.IOException;
import java.util.Scanner;

import jline.ConsoleReader;
import vue.GraphiqueConsole;

public class ControleurConsole {

	private final int TOUCHE_HAUT = 16, TOUCHE_BAS = 14, TOUCHE_GAUCHE = 2, TOUCHE_DROITE = 6, TOUCHE_ATTENTE = 10,
			TOUCHE_RESET = 32;

	public void run(GererNiveau g) {
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
		try {

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
