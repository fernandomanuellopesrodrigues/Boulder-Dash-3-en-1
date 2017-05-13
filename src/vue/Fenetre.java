package vue;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import constantes.Constantes;

import static constantes.Constantes.*;
import static main.Coeur.CONTROLEUR;

public class Fenetre extends JFrame implements KeyListener {

	private int TPS, FPS;

	public Fenetre() {
		this.setTitle(Constantes.TITRE_FENETRE);
		this.setTitre();
		this.setSize(WIDTH_FENETRE, HEIGHT_FENETRE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.addKeyListener(this);
		this.setVisible(false);
		this.setAlwaysOnTop(true);
		this.requestFocusInWindow();
		this.toFront();
		this.setState(this.NORMAL);
	}

	public void setTitre() {
		if (Constantes.SYSOUT_FPS && Constantes.SYSOUT_TPS) {
			this.setTitle("[" + FPS + " FPS , " + TPS + " TPS] " + Constantes.TITRE_FENETRE);
		} else if (Constantes.SYSOUT_FPS) {
			this.setTitle("[" + FPS + " FPS] " + Constantes.TITRE_FENETRE);
		} else if (Constantes.SYSOUT_TPS) {
			this.setTitle("[" + TPS + " TPS] " + Constantes.TITRE_FENETRE);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		CONTROLEUR.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		CONTROLEUR.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		CONTROLEUR.keyReleased(e);
	}

	public void setTPS(int tPS) {
		TPS = tPS;
	}

	public void setFPS(int fPS) {
		FPS = fPS;
	}
}
