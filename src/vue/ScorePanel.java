package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import main.Coeur;
import main.Partie;

/**
 * La classe ScorePanel h�rite de JPanel et sert � afficher la barre contenant
 * les informations de jeu en haut de la fen�tre.
 * Est utilis�e seulement quand le jeu est en mode fen�tr�.
 *
 * @author Murloc
 * @see JPanel
 */
public class ScorePanel extends JPanel {

    /**
     * Constructeur ScorePanel.
     * Initialise les dimensions en se basant sur les dimensions de la fen�tre
     * {@link Coeur#FENETRE}.
     */
    public ScorePanel() {
        setPreferredSize(new Dimension(Coeur.FENETRE.getWidth(), Coeur.FENETRE.getHeight() / 20));
    }

    /**
     * La methode servant a �crire les informations.
     * Appell�e par la fen�tre � chaque {@link Fenetre#repaint()}.
     * Elle se sert des informations de {@link Partie#gererNiveau}.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Dessine le fond noir.
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Dessine des traits blancs en se basant sur la taille du panel servant
        // � d�limiter les informations.
        g.setColor(Color.white);
        for (int i = 1; i <= 5; i++) {
            g.drawLine(getWidth() * i / 5, 0, getWidth() * i / 5, getHeight());
        }

		/*
         * Dessine les diverses informations.
		 */
        g.drawString("Diamants : " + Partie.gererNiveau.getNbDiamants() + "/"
                     + Partie.gererNiveau.getNiveau().getDiamondsRequired(), getWidth() / 19, getHeight() / 2);
        g.drawString("Score : " + Partie.gererNiveau.getScore(), getWidth() * 18 / 70, getHeight() / 2);
        g.drawString("Temps restant : " + Partie.gererNiveau.getTempsRestant(), getWidth() * 42 / 100, getHeight() / 2);
        g.drawString("Niveau : " + Partie.niveau + "/" + Partie.ensembleDeNiveau.getNombreDeNiveaux(),
                     getWidth() * 19 / 30, getHeight() / 2);
        g.drawString("Points/diamant : " + Partie.gererNiveau.getNiveau().getDiamondValue(), getWidth() * 41 / 50,
                     getHeight() * 12 / 30);
        g.drawString("(bonus) : " + Partie.gererNiveau.getNiveau().getDiamondValueBonus(), getWidth() * 42 / 50,
                     getHeight() * 22 / 30);

    }
}
