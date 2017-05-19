package outils;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import vue.Sprites;

/**
 * Classe permettant de g�rer quel sprites doivent s'afficher � quelle frame.
 *
 * @see Sprites
 */

public class EnsembleDeSprites {

    /**
     * La liste qui stock les sprites.
     */
    private final List<Paire<Integer, List<Image>>> sprites = new ArrayList<Paire<Integer, List<Image>>>();

    /**
     * M�thode qui ajoute un objet � la liste.
     *
     * @param vitesse La cl� de l'objet � ajouter.
     * @param liste La valeur de l'objet � ajouter.
     */
    public void add(int vitesse, List<Image> liste) {
        sprites.add(new Paire<Integer, List<Image>>(vitesse, liste));
    }

    /**
     * Getter de la liste.
     *
     * @return La liste {@link EnsembleDeSprites#sprites}
     */
    public List<Paire<Integer, List<Image>>> get() {
        return sprites;
    }
}
