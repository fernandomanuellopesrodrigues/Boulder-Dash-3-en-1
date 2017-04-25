package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import main.Coeur;
import main.Partie;

public class FinPanel extends JPanel {

	protected void paintComponent(Graphics g) {
		g.setColor(new Color(88, 41, 0));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.white);

		if (Partie.tousLesNiveaux) {
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