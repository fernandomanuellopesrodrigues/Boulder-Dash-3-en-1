package entitees.fixes;

import static entitees.abstraites.Entitee.Entitees.Amibe;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import entitees.abstraites.Entitee;
import entitees.tickables.Diamant;
import entitees.tickables.Pierre;
import main.Partie;

public class Amibe extends Entitee {

	public Amibe(int x, int y) {
		super(x, y);
		setDestructible(true);
		enumeration = Amibe;
	}

	public boolean sePropager() {
		List<Point> points = new ArrayList<Point>();
		if (placeLibre(getX() + 1, getY())) {
			points.add(new Point(getX() + 1, getY()));
		}
		if (placeLibre(getX() - 1, getY())) {
			points.add(new Point(getX() - 1, getY()));
		}
		if (placeLibre(getX(), getY() - 1)) {
			points.add(new Point(getX(), getY() - 1));
		}
		if (placeLibre(getX(), getY() + 1)) {
			points.add(new Point(getX(), getY() + 1));
		}
		if (!points.isEmpty()) {
			int rng = (int) (Math.random() * points.size());
			Partie.gererNiveau.getNiveau().placerEntitee(new Amibe(points.get(rng).x, points.get(rng).y));
			checkDetruireAmibes();
			return true;
		} else {
			return false;
		}
	}

	private void checkDetruireAmibes() {
		if (Partie.gererNiveau.getListeAmibes().size() >= 200) {
			for (Amibe amibe : Partie.gererNiveau.getListeAmibes()) {
				Partie.gererNiveau.getNiveau().placerEntitee(new Pierre(amibe.getX(), amibe.getY()));
			}
			Partie.gererNiveau.getListeAmibes().removeAll(Partie.gererNiveau.getListeAmibes());
		}
	}

	public void transformerTousLesAmibesEnDiamant() {
		for (Amibe amibe : Partie.gererNiveau.getListeAmibes()) {
			Partie.gererNiveau.getNiveau().placerEntitee(new Diamant(amibe.getX(), amibe.getY()));
		}
		Partie.gererNiveau.getListeAmibes().removeAll(Partie.gererNiveau.getListeAmibes());
	}

	public boolean mourir() {
		Partie.gererNiveau.getListeAmibes().remove(this);
		return super.mourir();
	}

	private boolean placeLibre(int x, int y) {
		if (Partie.gererNiveau.getNiveau().getMap()[x][y].getClass().equals(Vide.class)
				|| Partie.gererNiveau.getNiveau().getMap()[x][y].getClass().equals(Poussiere.class)) {
			return true;
		}
		return false;
	}
}
