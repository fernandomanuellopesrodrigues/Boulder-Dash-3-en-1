package vue;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import main.Partie;

/**
 * La classe FinPanel hérite de JPanel et sert à afficher l'écran de fin de jeu
 * quand celui-ci est en mode fenêtré.
 * 
 * @see JPanel
 * 
 * @author Murloc
 */
public class FinPanel extends JPanel {

	/**
	 * La méthode servant à dessiner l'écran de fin de jeu.
	 * 
	 * Elle se sert du boolean {@link main.Partie#tousLesNiveaux} pour savoir si
	 * le joueur viens d'effectuer un ou plusieurs niveaux.
	 * 
	 * Si le joueur a effectué plusieurs niveaux elle se sert de la liste
	 * d'entiers {@link main.Partie#SCORES} pour connaitre les scores à
	 * afficher.
	 * 
	 * Sinon se sert de {@link main.Partie#gererNiveau} pour récuperer dans ses
	 * attributs le score du niveau actuel, qui est donc celui que le joueur
	 * vient de faire.
	 */
	protected void paintComponent(Graphics g) {
		// Dessin du fond.
		g.setColor(new Color(88, 41, 0));
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.white);

		// Si le joueur a effectué plusieurs niveaux.
		if (Partie.tousLesNiveaux) {
			// Dessine les scores dans l'ordre.
			int compteur = 1;
			int somme = 0;
			g.drawString("FIN DU JEU, SCORES : ", getWidth() / 6, getWidth() / 10);
			for (Integer i : Partie.SCORES) {
				g.drawString("Niveau " + (compteur) + " : " + i, getWidth() / 3,
						getHeight() * compteur / (Partie.SCORES.size() * 2));
				somme += i;
				compteur++;
			}
			compteur++;
			g.drawString("TOTAL : " + somme, getWidth() / 6, getWidth() / 5);
		} else {
			g.drawString("FIN DU JEU, SCORE : " + Partie.gererNiveau.getScore(), getWidth() / 6, getWidth() / 10);
		}
	}
}