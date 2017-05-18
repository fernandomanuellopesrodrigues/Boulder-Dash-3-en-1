package outils;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import vue.Sprites;

/**
 * Classe permettant de gérer quel sprites doivent s'afficher à quelle frame.
 * 
 * @see Sprites
 */

public class EnsembleDeSprites {

	/**
	 * La liste qui stock les sprites.
	 */
	private List<Paire<Integer, List<Image>>> sprites = new ArrayList<Paire<Integer, List<Image>>>();

	/**
	 * Méthode qui ajoute un objet à la liste.
	 * 
	 * @param vitesse
	 *            La clé de l'objet à ajouter.
	 * @param liste
	 *            La valeur de l'objet à ajouter.
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
