package outils;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class EnsembleDeSprites {

	private List<Paire<Integer, List<Image>>> sprites = new ArrayList<Paire<Integer, List<Image>>>();

	public void add(int vitesse, List<Image> liste) {
		sprites.add(new Paire<Integer, List<Image>>(vitesse, liste));
	}

	public List<Paire<Integer, List<Image>>> get() {
		return sprites;
	}
}
