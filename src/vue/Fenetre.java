package vue;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import main.Constantes;

import static java.lang.String.format;
import static main.Coeur.CONTROLEUR;
import static main.Constantes.HEIGHT_FENETRE;
import static main.Constantes.WIDTH_FENETRE;

/**
 * La classe Fenetre herite de JFrame et sert \u00E0 gerer la fenetre du mode
 * graphique du jeu.
 * Elle implemente la classe {@link KeyListener} afin de pouvoir recuperer les
 * entrees clavier.
 *
 * @author Murloc
 * @see JFrame
 */
public class Fenetre extends JFrame implements KeyListener {

    /**
     * Les FPS.
     */
    private int fps;

    /**
     * Les TPS (ticks par secondes).
     */
    private int tps;

    /**
     * Constructeur Fenetre.
     * Elle ne prend pas de parametres mais trouve les differentes variables
     * dans la classe {@link Constantes}.
     */
    public Fenetre() {
        setTitle(Constantes.TITRE_FENETRE);
        setSize(WIDTH_FENETRE, HEIGHT_FENETRE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        addKeyListener(this);
        setVisible(false);
    }

    /**
     * Methode servant a rafraichir le titre de la fenetre afin d'y inscrire le
     * nombre de frames par secondes et/ou le nombre de ticks par secondes en
     * foncion des booleens de la classe {@link Constantes}.
     */
    public void setTitre() {
        if (Constantes.SYSOUT_FPS && Constantes.SYSOUT_TPS) {
            setTitle(format("[%d FPS , %d TPS] %s", fps, tps, Constantes.TITRE_FENETRE));
        } else if (Constantes.SYSOUT_FPS) {
            setTitle(format("[%d FPS] %s", fps, Constantes.TITRE_FENETRE));
        } else if (Constantes.SYSOUT_TPS) {
            setTitle(format("[%d TPS] %s", tps, Constantes.TITRE_FENETRE));
        }
    }

    /**
     * Methode implementee par l'interface {@link KeyListener} appelee quand une
     * touche vient d'etre tapee. Ne fais rien car
     * {@link Fenetre#keyPressed(KeyEvent)} suffit.
     */
    @Override
    public void keyTyped(final KeyEvent e) {
    }

    /**
     * Methode implementee par l'interface {@link KeyListener} appelee quand une
     * touche vient d'etre enfoncee.
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        CONTROLEUR.keyPressed(e);
    }

    /**
     * Methode implementee par l'interface {@link KeyListener} appelee quand une
     * touche vient d'etre relachee.
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        CONTROLEUR.keyReleased(e);
    }

    /**
     * Un setter.
     *
     * @param fps L'objet en question.
     */
    public void setFps(final int fps) {
        this.fps = fps;
    }

    /**
     * Un setter.
     *
     * @param tps L'objet en question.
     */
    public void setTps(final int tps) {
        this.tps = tps;
    }
}
