package vue;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import main.Partie;

import static java.lang.String.format;

/**
 * La classe FinPanel herite de JPanel et sert a afficher l'ecran de fin de jeu
 * quand celui-ci est en mode fenetre.
 *
 * @author Murloc
 * @see JPanel
 */
public class FinPanel extends JPanel {

    /**
     * La methode servant a dessiner l'ecran de fin de jeu.
     * Elle se sert du boolean {@link Partie#tousLesNiveaux} pour savoir si
     * le joueur viens d'effectuer un ou plusieurs niveaux.
     * Si le joueur a effectue plusieurs niveaux elle se sert de la liste
     * d'entiers {@link Partie#SCORES} pour connaitre les scores a
     * afficher.
     * Sinon se sert de {@link Partie#gererNiveau} pour recuperer dans ses
     * attributs le score du niveau actuel, qui est donc celui que le joueur
     * vient de faire.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        // Dessin du fond.
        g.setColor(new Color(88, 41, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.white);

        // Si le joueur a effectue plusieurs niveaux.
        if (Partie.tousLesNiveaux) {
            // Dessine les scores dans l'ordre.
            g.drawString("FIN DU JEU, SCORES : ", getWidth() / 6, getWidth() / 10);
            int compteur = 1;
            int somme = 0;
            for (Integer i : Partie.SCORES) {
                g.drawString(format("Niveau %d : %d", compteur, i), getWidth() / 3,
                             getHeight() * compteur / (Partie.SCORES.size() << 1));
                somme += i;
                compteur++;
            }
            compteur++;
            g.drawString(format("TOTAL : %d", somme), getWidth() / 6, getWidth() / 5);
        } else {
            g.drawString(format("FIN DU JEU, SCORE : %d", Partie.gererNiveau.getScore()), getWidth() / 6,
                         getWidth() / 10);
        }
    }
}
