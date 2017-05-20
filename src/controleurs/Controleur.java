package controleurs;

import java.awt.event.KeyEvent;

import main.Coeur;
import main.Partie;

/**
 * La classe Controleur est une classe qui va gérer les entrées clavier.
 * Elle dispose de 3 méthodes "Key listener" qui sont appelées par
 * {@link Coeur#FENETRE} quand une touche subit un évènement.
 * Elle dispose aussi d'une méthode {@link Controleur#getDirection()} qui est
 * appelée à chaque tour de boucle du jeu et qui renvoie le mouvement que doit
 * effectuer Rockford en fonction des évènements clavier qui ont eu lieu.
 *
 * @author Murloc
 */
public class Controleur {

    /**
     * Booleans servant à savoir si les touches qu'ils représentent sont
     * enfoncées. Vrai = enfoncé.
     */
    private boolean haut, bas, gauche, droite, space, pierre, bombe;
    /**
     * Booleans servant à savoir si les touches qu'ils représentent étaient
     * enfoncées le tour de boucle précèdent. Vrai = enfoncé. Utile pour
     * améliorer le gameplay en mode temps réel.
     */
    private boolean hautwas, baswas, gauchewas, droitewas;

    /**
     * Entiers utiles pour savoir quelle touche a été enfoncée en dernier,
     * chaque entier représente une touche et l'entier le plus grand signifie
     * que c'est cette touche qui a été enfoncée en dernier.
     */
    private int hautInt, basInt, gaucheInt, droiteInt, rInt;

    /**
     * Entiers représentants les touches du clavier qui servent à jouer.
     */
    private final int toucheHaut;
    private final int toucheBas;
    private final int toucheGauche;
    private final int toucheDroite;
    private final int touchePierre;
    private final int toucheBombe;

    /**
     * Constructeur qui initialise quelles touches du clavier servent à jouer.
     *
     * @param toucheHaut L'entier de la touche qui sert à aller vers le haut.
     * @param toucheGauche L'entier de la touche qui sert à aller vers la gauche.
     * @param toucheBas L'entier de la touche qui sert à aller vers le bas.
     * @param toucheDroite L'entier de la touche qui sert à aller vers la droite.
     * @param touchePierre L'entier de la touche qui sert à se camoufler.
     * @param toucheBombe L'entier de la touche qui sert à poser une bombe.
     */
    public Controleur(int toucheHaut, int toucheGauche, int toucheBas, int toucheDroite, int touchePierre,
                      int toucheBombe) {
        this.toucheBas = toucheBas;
        this.toucheDroite = toucheDroite;
        this.toucheGauche = toucheGauche;
        this.toucheHaut = toucheHaut;
        this.toucheBombe = toucheBombe;
        this.touchePierre = touchePierre;
    }

    /**
     * Méthode appelée à chaque tour de boucle en mode temps réel servant à
     * améliorer le gameplay.
     */
    public void tick() {
        droitewas = false;
        hautwas = false;
        baswas = false;
        gauchewas = false;
        if (!haut) {
            hautInt = 0;
        } else {
            hautwas = true;
        }
        if (!bas) {
            basInt = 0;
        } else {
            baswas = true;
        }
        if (!gauche) {
            gaucheInt = 0;
        } else {
            gauchewas = true;
        }
        if (!droite) {
            droiteInt = 0;
        } else {
            droitewas = true;
        }
    }

    /**
     * Méthode qui effectue de nombreux calculs afin de renvoyer un caractere
     * représentant l'action à effectuer voulue par le joueur.
     *
     * @return Caractere représentant l'action à effectuer voulue par le joueur.
     */
    public char getDirection() {

        if (pierre) {
            return 'p';
        }
        if (bombe) {
            bombe = false;
            return 'B';
        }
        if (getMax() == 0) {
            return ' ';
        }
        int x = Partie.gererNiveau.getNiveau().getRockford().getPositionX();
        int y = Partie.gererNiveau.getNiveau().getRockford().getPositionY();
        if (hautInt == getMax() && (!hautwas || haut)) {
            if (Partie.gererNiveau.getNiveau().getRockford().placeLibre(x, y - 1)) {
                return 'h';
            } else {
                return get2eme();
            }
        }
        if (droiteInt == getMax() && (!droitewas || droite)) {
            if (Partie.gererNiveau.getNiveau().getRockford().placeLibre(x + 1, y)) {
                return 'd';
            } else {
                return get2eme();
            }
        }
        if (gaucheInt == getMax() && (!gauchewas || gauche)) {
            if (Partie.gererNiveau.getNiveau().getRockford().placeLibre(x - 1, y)) {
                return 'g';
            } else {
                return get2eme();
            }
        }
        if (basInt == getMax() && (!baswas || bas)) {
            if (Partie.gererNiveau.getNiveau().getRockford().placeLibre(x, y + 1)) {
                return 'b';
            } else {
                return get2eme();
            }
        }

        if (getMax() != 0) {
            resetMax();
            return getDirection();
        }
        return ' ';
    }

    /**
     * Méthode appelée par {@link Coeur#FENETRE} quand une touche est enfoncée.
     * Si la partie est en tour par tour elle effectue un tick.
     *
     * @param e La touche enfoncée.
     */
    public void keyPressed(KeyEvent e) {
        if (!Partie.iaValid && !Partie.lecture) {

            int max = getMax();
            if (e.getKeyCode() == toucheHaut) {
                haut = true;
                hautInt = max + 1;

            }
            if (e.getKeyCode() == toucheDroite) {
                droite = true;
                droiteInt = max + 1;
            }
            if (e.getKeyCode() == toucheGauche) {
                gauche = true;
                gaucheInt = max + 1;
            }
            if (e.getKeyCode() == toucheBas) {
                bas = true;
                basInt = max + 1;
            }
            if (e.getKeyChar() == ' ') {
                Partie.gererNiveau.setDemandeReset(true);
            }
            if (e.getKeyCode() == touchePierre) {
                pierre = true;
            }
            if (e.getKeyCode() == toucheBombe) {
                bombe = true;
            }
            if (Partie.gererNiveau.isTourParTour()) {
                Partie.tick();
            }
        }
    }

    /**
     * Méthode appelée par {@link Coeur#FENETRE} quand une touche est relachée.
     *
     * @param e La touche relâchée.
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == toucheHaut) {
            haut = false;
        }
        if (e.getKeyCode() == toucheDroite) {
            droite = false;
        }
        if (e.getKeyCode() == toucheGauche) {
            gauche = false;
        }
        if (e.getKeyCode() == toucheBas) {
            bas = false;
        }
        if (e.getKeyChar() == ' ') {
            space = false;
        }
        if (e.getKeyCode() == touchePierre) {
            pierre = false;
        }
    }

    /**
     * Méthode appelée par {@link Controleur#getDirection()} permettant de
     * réinitialiser les entiers servant connaitre la touche enfoncée en
     * dernier.
     */
    private void resetMax() {
        int max;
        max = getMax();
        if (max == hautInt) {
            hautInt = 0;
        }
        if (max == basInt) {
            basInt = 0;
        }
        if (max == droiteInt) {
            droiteInt = 0;
        }
        if (max == gaucheInt) {
            gaucheInt = 0;
        }
        if (max == rInt) {
            rInt = 0;
        }
    }

    /**
     * Méthode qui retourne la valeur du plus grand entiers parmis les entiers
     * servant connaitre la touche enfoncée en dernier.
     *
     * @return La valeur du plus grand entier.
     */
    private int getMax() {
        int max;
        max = 0;
        if (max < hautInt) {
            max = hautInt;
        }
        if (max < basInt) {
            max = basInt;
        }
        if (max < droiteInt) {
            max = droiteInt;
        }
        if (max < gaucheInt) {
            max = gaucheInt;
        }
        if (max < rInt) {
            max = rInt;
        }
        return max;
    }

    /**
     * Méthode qui retourne la valeur du deuxième plus grand entiers parmis les
     * entiers servant connaitre la touche enfoncée en dernier.
     *
     * @return La valeur du deuxième plus grand entier.
     */
    private char get2eme() {
        int max = getMax();
        int max2 = 0;
        char max2c = ' ';
        if (max2 < hautInt && hautInt != max) {
            max2 = hautInt;
            max2c = 'h';
        }
        if (max2 < basInt && basInt != max) {
            max2c = 'b';
            max2 = basInt;
        }
        if (max2 < droiteInt && droiteInt != max) {
            max2c = 'd';
            max2 = droiteInt;
        }
        if (max2 < gaucheInt && gaucheInt != max) {
            max2c = 'g';
            max2 = gaucheInt;
        }
        return max2c;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public boolean isSpace() {
        return space;
    }

    /**
     * Un setter.
     *
     * @param space L'objet en question.
     */
    public void setSpace(boolean space) {
        this.space = space;
    }

    /**
     * Un getter.
     *
     * @return L'objet en question.
     */
    public boolean isPierre() {
        return pierre;
    }

    /**
     * Un setter.
     *
     * @param pierre L'objet en question.
     */
    public void setPierre(boolean pierre) {
        this.pierre = pierre;
    }
}
