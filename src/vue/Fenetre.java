package vue;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import main.Constantes;

import static main.Coeur.CONTROLEUR;
import static main.Constantes.*;

/**
 * La classe Fenêtre hérite de JFrame et sert à gérer la fenêtre du mode
 * graphique du jeu.
 * 
 * Elle implémente la classe {@link KeyListener} afin de pouvoir récupérer les
 * entrées clavier.
 * 
 * @see JFrame
 * 
 * @author Murloc
 *
 */
public class Fenetre extends JFrame implements KeyListener {

	/**
	 * Constructeur Fenetre.
	 * 
	 * Elle ne prend pas de paramètres mais trouve les différentes variables
	 * dans la classe {@link main.Constantes}.
	 */
	public Fenetre() {
		this.setTitle(Constantes.TITRE_FENETRE);
		this.setSize(WIDTH_FENETRE, HEIGHT_FENETRE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.addKeyListener(this);
		this.setVisible(false);
	}

	/**
	 * Méthode servant à rafraichir le titre de la fenêtre afin d'y inscrire le
	 * nombre de frames par secondes et/ou le nombre de ticks par secondes en
	 * foncion des booleens de la classe {@link main.Constantes}.
	 */
	public void setTitre() {
		if (Constantes.SYSOUT_FPS && Constantes.SYSOUT_TPS) {
			this.setTitle("[" + Constantes.SYSOUT_FPS + " FPS , " + Constantes.SYSOUT_TPS + " TPS] "
					+ Constantes.TITRE_FENETRE);
		} else if (Constantes.SYSOUT_FPS) {
			this.setTitle("[" + Constantes.SYSOUT_FPS + " FPS] " + Constantes.TITRE_FENETRE);
		} else if (Constantes.SYSOUT_TPS) {
			this.setTitle("[" + Constantes.SYSOUT_TPS + " TPS] " + Constantes.TITRE_FENETRE);
		}
	}

	/**
	 * Méthode implémentée par l'interface {@link KeyListener} appelée quand une
	 * touche vient d'être tapée.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		CONTROLEUR.keyTyped(e);
	}

	/**
	 * Méthode implémentée par l'interface {@link KeyListener} appelée quand une
	 * touche vient d'être enfoncée.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		CONTROLEUR.keyPressed(e);
	}

	/**
	 * Méthode implémentée par l'interface {@link KeyListener} appelée quand une
	 * touche vient d'être relâchée.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		CONTROLEUR.keyReleased(e);
	}
}
