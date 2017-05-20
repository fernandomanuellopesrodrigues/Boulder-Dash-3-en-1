package outils;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import vue.Sprites;

/**
 * Classe permettant de gerer quel sprites doivent s'afficher \u00E0 quelle frame.
 *
 * @see Sprites
 */

public class EnsembleDeSprites extends  ArrayList<Paire<Integer, List<Image>>> {

    /**
     * Methode qui ajoute un objet \u00E0 la liste.
     *
     * @param vitesse La cl\u00E9 de l'objet \u00E0 ajouter.
     * @param liste La valeur de l'objet \u00E0 ajouter.
     */
    public void add(final int vitesse, final List<Image> liste) {
        add(new Paire<>(vitesse, liste));
    }
}
