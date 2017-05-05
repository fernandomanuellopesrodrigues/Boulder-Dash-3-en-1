package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import main.Coeur;
import main.Partie;

public class ScorePanel extends JPanel {

	public ScorePanel() {

		setPreferredSize(new Dimension(Coeur.FENETRE.getWidth(), (Coeur.FENETRE.getHeight() / 20)));
	}

	protected void paintComponent(Graphics g) {
		if (Coeur.running) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.white);
			for (int i = 1; i <= 5; i++) {
				g.drawLine(getWidth() * i / 5, 0, getWidth() * i / 5, getHeight());
			}
			g.drawString("Diamants : " + Partie.gererNiveau.getNbDiamants() + "/"
					+ Partie.gererNiveau.getNiveau().getDiamonds_required(), getWidth() / 19, getHeight() / 2);
			g.drawString("Score : " + Partie.gererNiveau.getScore(), getWidth() * 18 / 70, getHeight() / 2);
			g.drawString("Temps restant : " + Partie.gererNiveau.getTempsRestant(), getWidth() * 42 / 100,
					getHeight() / 2);
			g.drawString("Niveau : " + Partie.niveau + "/" + Partie.ensembleDeNiveau.getNombre_de_niveaux(),
					getWidth() * 19 / 30, getHeight() / 2);
			g.drawString("Points/diamant : " + (Partie.gererNiveau.getNiveau().getDiamond_value()),
					(getWidth() * 41) / 50, getHeight() * 12 / 30);
			g.drawString("(bonus) : " + Partie.gererNiveau.getNiveau().getDiamond_value_bonus(), (getWidth() * 42) / 50,
					getHeight() * 22 / 30);
		}
	}
}
