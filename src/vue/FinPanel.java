package vue;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import main.Partie;

/**
 * La classe FinPanel h�rite de JPanel et sert � afficher l'�cran de fin de jeu
 * quand celui-ci est en mode fen�tr�.
 *
 * @author Murloc
 * @see JPanel
 */
public class FinPanel extends JPanel {

    /**
     * La m�thode servant � dessiner l'�cran de fin de jeu.
     * Elle se sert du boolean {@link Partie#tousLesNiveaux} pour savoir si
     * le joueur viens d'effectuer un ou plusieurs niveaux.
     * Si le joueur a effectu� plusieurs niveaux elle se sert de la liste
     * d'entiers {@link Partie#SCORES} pour connaitre les scores �
     * afficher.
     * Sinon se sert de {@link Partie#gererNiveau} pour r�cuperer dans ses
     * attributs le score du niveau actuel, qui est donc celui que le joueur
     * vient de faire.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Dessin du fond.
        g.setColor(new Color(88, 41, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.white);

        // Si le joueur a effectu� plusieurs niveaux.
        if (Partie.tousLesNiveaux) {
            // Dessine les scores dans l'ordre.
            int compteur = 1;
            int somme = 0;
            g.drawString("FIN DU JEU, SCORES : ", getWidth() / 6, getWidth() / 10);
            for (Integer i : Partie.SCORES) {
                g.drawString("Niveau " + compteur + " : " + i, getWidth() / 3,
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
